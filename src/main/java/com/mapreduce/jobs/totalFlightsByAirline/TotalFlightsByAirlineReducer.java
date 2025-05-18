package com.mapreduce.jobs.totalFlightsByAirline;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;

public class TotalFlightsByAirlineReducer extends MapReduceBase implements Reducer<Text, IntWritable, Text, IntWritable> {

    public void reduce(Text key, Iterator<IntWritable> values, OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException {
        int sum = 0;
        
        // Sum up all the flight counts
        while (values.hasNext()) {
            sum += values.next().get();
        }
        
        // Output the airline and total number of flights
        output.collect(key, new IntWritable(sum));
    }
} 