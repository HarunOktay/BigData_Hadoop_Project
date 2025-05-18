package com.mapreduce.jobs.stdFlightDelay;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;

public class StdDevFlightDelayReducer extends MapReduceBase implements Reducer<Text, DoubleWritable, Text, DoubleWritable> {

    public void reduce(Text key, Iterator<DoubleWritable> values, OutputCollector<Text, DoubleWritable> output, Reporter reporter) throws IOException {
        // Store all values in a list to calculate mean first
        List<Double> delayList = new ArrayList<>();
        double sum = 0.0;
        
        // Calculate sum and store all values
        while (values.hasNext()) {
            double delay = values.next().get();
            delayList.add(delay);
            sum += delay;
        }
        
        // Calculate mean
        int count = delayList.size();
        if (count == 0) {
            output.collect(key, new DoubleWritable(0.0));
            return;
        }
        
        double mean = sum / count;
        
        // Calculate sum of squared differences
        double sumSquaredDiff = 0.0;
        for (double delay : delayList) {
            double diff = delay - mean;
            sumSquaredDiff += (diff * diff);
        }
        
        // Calculate standard deviation
        double stdDev = Math.sqrt(sumSquaredDiff / count);
        
        output.collect(key, new DoubleWritable(stdDev));
    }
} 