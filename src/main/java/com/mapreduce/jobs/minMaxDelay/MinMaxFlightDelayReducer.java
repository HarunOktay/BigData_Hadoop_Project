package com.mapreduce.jobs.minMaxDelay;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;

public class MinMaxFlightDelayReducer extends MapReduceBase implements Reducer<Text, DoubleWritable, Text, Text> {

    public void reduce(Text key, Iterator<DoubleWritable> values, OutputCollector<Text, Text> output, Reporter reporter) throws IOException {
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;
        
        while (values.hasNext()) {
            double delay = values.next().get();
            min = Math.min(min, delay);
            max = Math.max(max, delay);
        }
        
        // Only output if we found valid min/max values
        if (min != Double.MAX_VALUE && max != Double.MIN_VALUE) {
            output.collect(key, new Text(min + "\t" + max));
        }
    }
} 