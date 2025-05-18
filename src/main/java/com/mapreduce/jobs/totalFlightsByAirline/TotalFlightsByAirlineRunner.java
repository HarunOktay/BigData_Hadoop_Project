package com.mapreduce.jobs.totalFlightsByAirline;

import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.TextInputFormat;
import org.apache.hadoop.mapred.TextOutputFormat;

public class TotalFlightsByAirlineRunner {

    public static void run(String[] args, String output) {
        try {
            JobConf conf = new JobConf(TotalFlightsByAirlineRunner.class);
            conf.setJobName("Total Flights By Airline");
            
            conf.setOutputKeyClass(Text.class);
            conf.setOutputValueClass(IntWritable.class);
            
            conf.setMapperClass(TotalFlightsByAirlineMapper.class);
            conf.setReducerClass(TotalFlightsByAirlineReducer.class);
            
            conf.setMapOutputKeyClass(Text.class);
            conf.setMapOutputValueClass(IntWritable.class);
            
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
            
            System.out.println("Total Flights By Airline job completed successfully!");
            
        } catch (IOException e) {
            System.err.println("Error running job: " + e.getMessage());
            e.printStackTrace();
        }
    }
} 