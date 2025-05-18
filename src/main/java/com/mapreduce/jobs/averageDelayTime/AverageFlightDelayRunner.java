package com.mapreduce.jobs.averageDelayTime;

import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.TextInputFormat;
import org.apache.hadoop.mapred.TextOutputFormat;

public class AverageFlightDelayRunner {

    public static void run(String[] args, String output) {
        try {
            JobConf conf = new JobConf(AverageFlightDelayRunner.class);
            conf.setJobName("Average Flight Delay by Day of Week");
            
            conf.setOutputKeyClass(Text.class);
            conf.setOutputValueClass(DoubleWritable.class);
            
            conf.setMapperClass(AverageFlightDelayMapper.class);
            conf.setReducerClass(AverageFlightDelayReducer.class);
            
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
            
            System.out.println("Job completed successfully!");
            
        } catch (IOException e) {
            System.err.println("Error running job: " + e.getMessage());
            e.printStackTrace();
        }
    }
} 