// Standard classes
import java.io.IOException;
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
// Hadoop classes
import org.apache.hadoop.util.ToolRunner;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Reducer.Context;

public class Selection extends Configured implements Tool {
       private static String inputTable;
       private static String outputTable;
       private static String attribute;
       private static String value;



//=================================================================== Main
        /*
             ***** INPUT *****
             key1                    column=a:a, timestamp=1429220741795, value=10
             key1                    column=b:b, timestamp=1429220753172, value=20
             key2                    column=a:a, timestamp=1429220774990, value=15
             key2                    column=b:b, timestamp=1429221211418, value=20
             key2                    column=c:c, timestamp=1429220783793, value=500


             ***** QUERY *****
             hadoop jar Ra.jar Selection g124D_input g124D_output a 10


             ***** OUTPUT ***** SELECT * WHERE a = 10
             key1                    column=a:a, timestamp=1429220741795, value=10
             key1                    column=b:b, timestamp=1429220753172, value=20

        */

       public static void main(String[] args) throws Exception {
         if (args.length<4) {
            System.err.println("Parameters missing: 'inputTable outputTable attribute value'");
	          System.exit(1);
           }
         inputTable = args[0];
         outputTable = args[1];
         attribute = args[2];
         value = args[3];


         int tablesRight = checkIOTables(args);
         if (tablesRight==0) {
           int ret = ToolRunner.run(new Selection(), args);
           System.exit(ret);
         } else {
           System.exit(tablesRight);
         }
       }


//============================================================== checkTables
       private static int checkIOTables(String [] args) throws Exception {
         // Obtain HBase's configuration
         Configuration config = HBaseConfiguration.create();
         // Create an HBase administrator
         HBaseAdmin hba = new HBaseAdmin(config);

         // With an HBase administrator we check if the input table exists
         if (!hba.tableExists(inputTable)) {
            System.err.println("Input table does not exist");
	          return 2;
           }
         // Check if the output table exists
         if (hba.tableExists(outputTable)) {
            System.err.println("Output table already exists");
	          return 3;
         }
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
       public int run(String [] args) throws Exception {
         //Create a new job to execute

         //Retrive the configuration
         Job job = new Job(HBaseConfiguration.create());
         //Set the MapReduce class
         job.setJarByClass(Selection.class);
         //Set the job name
         job.setJobName("Selection");
         //Create an scan object
         Scan scan = new Scan();
         //Set the columns to scan and keep header to project
         String header = "";
         //We set the information to be passed to de map
         header = attribute+","+value;
         job.getConfiguration().setStrings("information",header);
         //Set the Map and Reduce function
         TableMapReduceUtil.initTableMapperJob(inputTable, scan, Mapper.class, Text.class, Text.class, job);
         TableMapReduceUtil.initTableReducerJob(outputTable, Reducer.class, job);

         boolean success = job.waitForCompletion(true);
         return success ? 0 : 4;
       }


//=================================================================== Mapper
       public static class Mapper extends TableMapper<Text, Text> {

         public void map(ImmutableBytesWritable rowMetadata, Result values, Context context) throws IOException, InterruptedException {

           String rowId = new String(rowMetadata.get(), "US-ASCII");
           //Get the attribute and the value;
           String[] info = context.getConfiguration().getStrings("information","empty");
           String attribute = info[0];
           String value = info[1];

           KeyValue[] atrs = values.raw();

           String atrValue = new String(values.getValue(attribute.getBytes(), attribute.getBytes()));

           if(atrValue.equals(value)) {
             String tuple = "";
             tuple = new String(atrs[0].getFamily()) + ":" + new String(atrs[0].getValue());
             for (int i=1;i<atrs.length;i++) {
               tuple = tuple + ";" + new String(atrs[i].getFamily()) + ":" + new String(atrs[i].getValue());
             }
             context.write(new Text(rowId), new Text(tuple));
           }

         }
       }

//================================================================== Reducer
       public static class Reducer extends TableReducer<Text, Text, Text> {

          public void reduce(Text key, Iterable<Text> inputList, Context context) throws IOException, InterruptedException {

            while (inputList.iterator().hasNext()) {
              Text outputKey = inputList.iterator().next();
              //Get all the rows that we sent and split them
              String[] rows = outputKey.toString().split(";");
              //For every row, we put the row into the output table
              Put put = new Put(key.getBytes());
              for(String row : rows) {
                //We split the row into attribute and value
                String[] info = row.split(":");
                put.add(info[0].getBytes(), info[0].getBytes(), info[1].getBytes());
              }
              //We insert the Put into the output table
              context.write(outputKey, put);
            }
         }
       }
    }
