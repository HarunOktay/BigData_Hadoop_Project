package com.mapreduce.jobs.averageDelayTime;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;

public class AverageFlightDelayReducer extends MapReduceBase implements Reducer<Text, IntWritable, Text, DoubleWritable> {

    public void reduce(Text key, Iterator<IntWritable> values, OutputCollector<Text, DoubleWritable> output, Reporter reporter) throws IOException {
        int total = 0;
        int count = 0;

        while (values.hasNext()) {
            IntWritable value = values.next();
            total += value.get();
            count++;
        }
        
        double average = count > 0 ? total / (double) count : 0.0;
        output.collect(key, new DoubleWritable(average));
    }
} 