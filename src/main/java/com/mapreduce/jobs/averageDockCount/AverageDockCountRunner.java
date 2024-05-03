package com.mapreduce.jobs.averageDockCount;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.TextInputFormat;
import org.apache.hadoop.mapred.TextOutputFormat;

import com.mapreduce.Singletons;

public class AverageDockCountRunner {
        public static void run(String[] inputs, String output){
        JobConf jobConf = new JobConf(AverageDockCountRunner.class);

        jobConf.setJobName("AverageDockCount");
    
        jobConf.setOutputKeyClass(Text.class);
        jobConf.setOutputValueClass(IntWritable.class);

        jobConf.setMapperClass(AverageDockCountMapper.class);
        jobConf.setReducerClass(AverageDockCountReducer.class);

        jobConf.setInputFormat(TextInputFormat.class);
        jobConf.setOutputFormat(TextOutputFormat.class);

        Path[] inputPaths = new Path[inputs.length];
        for (int i = 0; i < inputPaths.length; i++) {
            inputPaths[i] = new Path(inputs[i]);
        }

        FileInputFormat.setInputPaths(jobConf, inputPaths);
        FileOutputFormat.setOutputPath(jobConf, new Path(output));
        Singletons.jobClient.setConf(jobConf);
        
        try{
            System.out.println("******************************************** runner initiating");
            JobClient.runJob(jobConf);
            System.out.println("******************************************** runner done");
            
        }catch (Exception e){
            System.err.println(e.toString());
        }

    }

}
