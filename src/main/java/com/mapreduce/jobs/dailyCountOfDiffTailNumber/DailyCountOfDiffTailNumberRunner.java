package com.mapreduce.jobs.dailyCountOfDiffTailNumber;

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

public class DailyCountOfDiffTailNumberRunner {

    public static void run(String[] args, String output) {
        try {
            JobConf conf = new JobConf(DailyCountOfDiffTailNumberRunner.class);
            conf.setJobName("Daily Count of Different Tail Numbers");
            
            conf.setOutputKeyClass(Text.class);
            conf.setOutputValueClass(IntWritable.class);
            
            conf.setMapperClass(DailyCountOfDiffTailNumberMapper.class);
            conf.setReducerClass(DailyCountOfDiffTailNumberReducer.class);
            
            conf.setMapOutputKeyClass(Text.class);
            conf.setMapOutputValueClass(Text.class);
            
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
            
            System.out.println("Daily Count of Different Tail Numbers job completed successfully!");
            
        } catch (IOException e) {
            System.err.println("Error running job: " + e.getMessage());
            e.printStackTrace();
        }
    }
} 