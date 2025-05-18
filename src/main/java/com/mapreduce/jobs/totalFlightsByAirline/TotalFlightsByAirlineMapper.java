package com.mapreduce.jobs.totalFlightsByAirline;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

public class TotalFlightsByAirlineMapper extends MapReduceBase implements Mapper<LongWritable, Text, Text, IntWritable> {

    private static final IntWritable ONE = new IntWritable(1);

    public void map(LongWritable key, Text value, OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException {
        String valueString = value.toString();
        String[] valueSplit = valueString.split(",");

        try {
            // Skip malformed rows
            if (valueSplit.length < 10) {
                return;
            }

            // Get airline code
            String airline = valueSplit[9]; // UniqueCarrier - field 10 (0-indexed: 9)

            // Output airline as key and count of 1 as value
            output.collect(new Text(airline), ONE);
            
        } catch (Exception e) {
            System.err.println("Error processing record: " + e.toString());
        }
    }
} 