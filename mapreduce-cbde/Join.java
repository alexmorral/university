// Standard classes
import java.io.IOException;
import java.util.Vector;
// HBase classes
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.mapreduce.TableReducer;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.util.Bytes;

// Hadoop classes
import org.apache.hadoop.util.ToolRunner;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Reducer.Context;

public class Join extends Configured implements Tool {

       // These are four global variables, which coincide with the four parameters in the call
       private static String inputTable;
       private static String leftDiscriminant;
       private static String rightDiscriminant;
       private static String outputTable;
       private static String leftAttribute;
	     private static String rightAttribute;


//=================================================================== Main
      /*
           ***** INPUT *****
           LEFT1                   column=a:a, timestamp=1429223193428, value=10
           LEFT1                   column=b:b, timestamp=1429223207488, value=20
           LEFT2                   column=a:a, timestamp=1429223221959, value=30
           RIGHT1                  column=a:a, timestamp=1429223238183, value=10
           RIGHT2                  column=b:b, timestamp=1429223244268, value=10
           RIGHT3                  column=a:a, timestamp=1429223255600, value=40


           ***** QUERY *****
           hadoop jar Ra.jar Join g124D_input g124D_output LEF RIG a a


           ***** OUTPUT *****
            LEFT1RIGHT1             column=a:a, timestamp=1429223358833, value=10
            LEFT1RIGHT3             column=a:a, timestamp=1429223358833, value=40
            LEFT2RIGHT1             column=a:a, timestamp=1429223358833, value=10
            LEFT2RIGHT3             column=a:a, timestamp=1429223358833, value=40

      */

       public static void main(String[] args) throws Exception {
         if (args.length<6) {
           System.err.println("Parameters missing: 'inputTable outputTable leftDiscriminant rightDiscriminant LeftAttribute RightAttribute'");
           System.exit(1);
         }
         inputTable = args[0];
         outputTable = args[1];
         leftDiscriminant = args[2];
         rightDiscriminant = args[3];
         leftAttribute = args[4];
         rightAttribute = args[5];


         if (leftDiscriminant.length()!=3 || rightDiscriminant.length()!=3) {
           System.err.println("The length of the discriminants must be 3");
           System.exit(2);
         }

         int tablesRight = checkIOTables(args);
         if (tablesRight==0) {
           int ret = ToolRunner.run(new Join(), args);
           System.exit(ret);
         } else {
           System.exit(tablesRight);
         }
       }


//============================================================== checkTables

        // Explanation: Handles HBase and its relationship with the MapReduce task

       private static int checkIOTables(String [] args) throws Exception {
         // Obtain HBase's configuration
         Configuration config = HBaseConfiguration.create();
         // Create an HBase administrator
         HBaseAdmin hba = new HBaseAdmin(config);

         /* With an HBase administrator we check if the input table exists.
            You can modify this bit and create it here if you want. */
         if (!hba.tableExists(inputTable)) {
           System.err.println("Input table does not exist");
           return 2;
         }
         /* Check whether the output table exists.
            Just the opposite as before, we do create the output table.
            Normally, MapReduce tasks attack an existing table (not created on the fly) and
            they store the result in a new table. */
         if (hba.tableExists(outputTable)) {
           System.err.println("Output table already exists");
           return 3;
         }
         // If you want to insert data through the API, do it here
         // -- Inserts
         /*
                     HTable table = new HTable(config, inputTable);
                	 //You may not need this, but if batch loading, set the recovery manager to Not Force (setAutoFlush false) and deactivate the WAL
                     //table.setAutoFlush(false); --This is needed to avoid HBase to flush at every put (instead, it will buffer them and flush when told by using FlushCommits)
                     //put.setWriteToWAL(false); --This is to deactivate the Write Ahead Logging
                     Put put = new Put(Bytes.toBytes("LEFT1")); //creates a new row with key LEFT1
	    			 put.add(Bytes.toBytes("Family"), Bytes.toBytes("Attribute"), Bytes.toBytes("Value")); //Add an attribute named Attribute that belongs to the family Family with value Value
                     table.put(put); // Inserts data

                     //If you do it through the HBase Shell, this is equivalent to:
                     //put 'G11AInputTable', 'LEFT1', 'Family:Attribute', 'Value'
         */
         // -- Inserts
         // Get an HBase descriptor pointing at the input table (the HBase descriptor handles the table metadata)
         HTableDescriptor htdInput = hba.getTableDescriptor(inputTable.getBytes());
         // Get an HBase descriptor pointing at the new table
         HTableDescriptor htdOutput = new HTableDescriptor(outputTable.getBytes());
         // We copy the structure of the input table in the output one by adding the input columns to the new table
         for(byte[] key: htdInput.getFamiliesKeys()) {
           htdOutput.addFamily(new HColumnDescriptor(key));
           }
         //Create the new output table based on the descriptor we have been configuring
         hba.createTable(htdOutput);
         return 0;
         }


//============================================================== Job config
       //Create a new job to execute. This is called from the main and starts the MapReduce task
       public int run(String [] args) throws Exception {

         //Create a new MapReduce configuration object.
         Job job = new Job(HBaseConfiguration.create());
         //Set the MapReduce class
         job.setJarByClass(Join.class);
         //Set the job name
         job.setJobName("Join");
         // To pass parameters to the mapper and reducer we must use the setStrings of the Configuration object
         job.getConfiguration().setStrings("External",leftDiscriminant);
         job.getConfiguration().setStrings("Internal",rightDiscriminant);
         job.getConfiguration().setStrings("externalAttr", leftAttribute);
         job.getConfiguration().setStrings("internalAttr", rightAttribute);
         /* Set the Map and Reduce function:
            These are special mapper and reducers, which are prepared to read and store data on HBase tables
         */
         TableMapReduceUtil.initTableMapperJob(inputTable, new Scan(), Mapper.class, Text.class, Text.class, job);
         TableMapReduceUtil.initTableReducerJob(outputTable, Reducer.class, job);

         //Shall we wait til the MapReduce task finishes? (block the program)
         boolean success = job.waitForCompletion(true);
         return success ? 0 : 1;
       }


//=================================================================== Mapper

       /* This is the mapper class, which implements the map method.
          The MapReduce framework will call this method automatically when needed.
          Note its header defines the input key-value and an object where to store the resulting key-values produced in the map.
          - ImmutableBytesWritable rowMetadata: The input key
          - Result values: The input value associated to that key
          - Context context: The object where to store all the key-values generated (i.e., the map output) */

       public static class Mapper extends TableMapper<Text, Text> {

         public void map(ImmutableBytesWritable rowMetadata, Result values, Context context) throws IOException, InterruptedException {
           int i;
           String tuple = new String(rowMetadata.get(), "US-ASCII");
           String[] external = context.getConfiguration().getStrings("External","Default");
           String[] internal = context.getConfiguration().getStrings("Internal","Default");

           // We create a string as follows for each key: key;family:attributeValue
           KeyValue[] attributes = values.raw();
           for (i=0;i<attributes.length;i++) {
             tuple = tuple+";"+new String(attributes[i].getFamily())+":"+new String(attributes[i].getValue());
           }

           //Is this key external (e.g., starts with LEFT)?
           if (tuple.startsWith(external[0])) {
             //This writes a key-value pair to the context object
             //If it is external, it gets as key a hash value and it is written only once in the context object
             context.write(new Text(Integer.toString(Double.valueOf(Math.random()*10).intValue())), new Text(tuple));
           }
           //Is this key internal (e.g., starts with RIGHT)?
           //If it is internal, it is written to the context object many times, each time having as key one of the potential hash values
           if (tuple.startsWith(internal[0])) {
             for(i=0;i<10;i++) {
               context.write(new Text(Integer.toString(i)), new Text(tuple));
             }
           }
         }
       }

//================================================================== Reducer
       public static class Reducer extends TableReducer<Text, Text, Text> {

          /* The reduce is automatically called by the MapReduce framework after the Merge Sort step.
          It receives a key, a list of values for that key, and a context object where to write the resulting key-value pairs. */

          public void reduce(Text key, Iterable<Text> inputList, Context context) throws IOException, InterruptedException {
           int i,j,k;
           Put put;
           String eTuple, iTuple;
           String outputKey;
           String[] external = context.getConfiguration().getStrings("External","Default");
           String[] internal = context.getConfiguration().getStrings("Internal","Default");
           String[] externalAttr = context.getConfiguration().getStrings("externalAttr","Default");
           String[] internalAttr = context.getConfiguration().getStrings("internalAttr","Default");
           String[] eAttributes, iAttributes;
           String[] attribute_value;

           //All tuples with the same hash value are stored in a vector
           Vector<String> tuples = new Vector<String>();
           for (Text val : inputList) {
             tuples.add(val.toString());
           }

           //In this for, each internal tuple is joined with each external tuple
           //Since the result must be stored in a HBase table, we configure a new Put, fill it with the joined data and write it in the context object
           for (i=0;i<tuples.size();i++) {
             eTuple=tuples.get(i);
             eAttributes=eTuple.split(";");
             if (eTuple.startsWith(external[0])) {
               for (j=0;j<tuples.size();j++) {
                 iTuple=tuples.get(j);
                 iAttributes=iTuple.split(";");
                 if (iTuple.startsWith(internal[0])) {
                   // Create a key for the output
                   outputKey = eAttributes[0]+iAttributes[0];
                   // Create a tuple for the output table
                   put = new Put(outputKey.getBytes());
                   //Set the values for the columns of the external table
                   for (k=1;k<eAttributes.length;k++) {
                     attribute_value = eAttributes[k].split(":");
                     if(attribute_value[0].equals(externalAttr[0])){
                       put.add(attribute_value[0].getBytes(), attribute_value[0].getBytes(), attribute_value[1].getBytes());
                     }
                   }
                   //Set the values for the columns of the internal table
                   for (k=1;k<iAttributes.length;k++) {
                     attribute_value = iAttributes[k].split(":");
                     if(attribute_value[0].equals(internalAttr[0])){
                       put.add(attribute_value[0].getBytes(), attribute_value[0].getBytes(), attribute_value[1].getBytes());
                     }
                   }
                   // Put the tuple in the output table through the context object
                   context.write(new Text(outputKey), put);
                 }
               }
             }
           }
         }
       }
    }
