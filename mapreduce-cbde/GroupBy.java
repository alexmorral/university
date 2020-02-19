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

public class GroupBy extends Configured implements Tool {
       private static String inputTable;
       private static String outputTable;
       private static String attribute;
       private static String value;



//=================================================================== Main
       /*
            ***** INPUT *****
            key1                    column=a:a, timestamp=1429220741795, value=10
            key1                    column=b:b, timestamp=1429220753172, value=20
            key2                    column=a:a, timestamp=1429220774990, value=100
            key2                    column=b:b, timestamp=1429221211418, value=20
            key2                    column=c:c, timestamp=1429220783793, value=500


            ***** QUERY *****
            hadoop jar Ra.jar GroupBy g124D_input g124D_output a b


            ***** OUTPUT ***** SUM(a) WHERE b=b
            b:20                    column=a:a, timestamp=1429221254718, value=110

       */

       public static void main(String[] args) throws Exception {
         if (args.length<4) {
            System.err.println("Parameters missing: 'inputTable outputTable aggregateAttribute groupByAttribute'");
	          System.exit(1);
           }
         inputTable = args[0];
         outputTable = args[1];


         int tablesRight = checkIOTables(args);
         if (tablesRight==0) {
           int ret = ToolRunner.run(new GroupBy(), args);
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

         // Get an HBase descriptor pointing at the new table
         HTableDescriptor htdOutput = new HTableDescriptor(outputTable.getBytes());

         htdOutput.addFamily(new HColumnDescriptor(args[2]));

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
         job.setJarByClass(GroupBy.class);
         //Set the job name
         job.setJobName("GroupBy");

         String header = "";
         //We set the information to be passed to de map
         header = args[2]+","+args[3];
         job.getConfiguration().setStrings("information",header);
         //Set the Map and Reduce function
         TableMapReduceUtil.initTableMapperJob(inputTable, new Scan(), Mapper.class, Text.class, Text.class, job);
         TableMapReduceUtil.initTableReducerJob(outputTable, Reducer.class, job);

         boolean success = job.waitForCompletion(true);
         return success ? 0 : 4;
       }


//=================================================================== Mapper
       public static class Mapper extends TableMapper<Text, Text> {

         public void map(ImmutableBytesWritable rowMetadata, Result values, Context context) throws IOException, InterruptedException {

           String rowId = new String(rowMetadata.get(), "US-ASCII");

           String[] info = context.getConfiguration().getStrings("information","empty");
           String aggregateAttribute = info[0];
           String groupByAttribute = info[1];

           String agrValue = new String(values.getValue(aggregateAttribute.getBytes(), aggregateAttribute.getBytes()));
           String gBValue = new String(values.getValue(groupByAttribute.getBytes(), groupByAttribute.getBytes()));
           if (!agrValue.equals("") && !gBValue.equals("")) context.write(new Text(gBValue), new Text(agrValue));
         }
       }

//================================================================== Reducer
       public static class Reducer extends TableReducer<Text, Text, Text> {

          public void reduce(Text key, Iterable<Text> inputList, Context context) throws IOException, InterruptedException {

            String[] info = context.getConfiguration().getStrings("information","empty");
            String aggregateAttribute = info[0];
            String groupByAttribute = info[1];
            int sum = 0;
            while (inputList.iterator().hasNext()) {
                Text val = inputList.iterator().next();
                sum += Integer.parseInt(val.toString());
            }
            String sumString = Integer.toString(sum);
            String outputTupleKey = groupByAttribute + ":" + key.toString();
            Put put = new Put(outputTupleKey.getBytes());
            put.add(aggregateAttribute.getBytes(), aggregateAttribute.getBytes(), sumString.getBytes());
            context.write(new Text(outputTupleKey), put);

         }
       }
    }
