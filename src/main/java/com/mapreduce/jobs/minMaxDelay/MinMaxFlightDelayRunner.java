package com.mapreduce.jobs.minMaxDelay;

import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.TextInputFormat;
import org.apache.hadoop.mapred.TextOutputFormat;

public class MinMaxFlightDelayRunner {

    public static void run(String[] args, String output) {
        try {
            JobConf conf = new JobConf(MinMaxFlightDelayRunner.class);
            conf.setJobName("Min/Max Flight Delay by Airline");
            
            conf.setOutputKeyClass(Text.class);
            conf.setOutputValueClass(Text.class);
            
            conf.setMapperClass(MinMaxFlightDelayMapper.class);
            conf.setReducerClass(MinMaxFlightDelayReducer.class);
            
            conf.setMapOutputKeyClass(Text.class);
            conf.setMapOutputValueClass(DoubleWritable.class);
            
            conf.setInputFormat(TextInputFormat.class);
            conf.setOutputFormat(TextOutputFormat.class);
            
            // Add the input paths
            for (String arg : args) {
                FileInputFormat.addInputPath(conf, new Path(arg));
            }
            
            // Set the output path
            FileOutputFormat.setOutputPath(conf, new Path(output));
            
            // Run the job
            JobClient.runJob(conf);
            
            System.out.println("Min/Max Flight Delay job completed successfully!");
            
        } catch (IOException e) {
            System.err.println("Error running job: " + e.getMessage());
            e.printStackTrace();
        }
    }
} 