package com.mapreduce.jobs.minMaxDelay;

import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

public class MinMaxFlightDelayMapper extends MapReduceBase implements Mapper<LongWritable, Text, Text, DoubleWritable> {

    public void map(LongWritable key, Text value, OutputCollector<Text, DoubleWritable> output, Reporter reporter) throws IOException {
        String valueString = value.toString();
        String[] valueSplit = valueString.split(",");

        try {
            // Skip header or malformed rows
            if (valueSplit.length < 16 || !isNumeric(valueSplit[15])) {
                return;
            }

            // Get arrival delay and airline
            double arrivalDelay = Double.parseDouble(valueSplit[15]); // ArrDelay
            String airline = valueSplit[9]; // UniqueCarrier - field 10 (0-indexed: 9)

            // Output airline as key and arrival delay as value
            output.collect(new Text(airline), new DoubleWritable(arrivalDelay));
            
        } catch (Exception e) {
            System.err.println("Error processing record: " + e.toString());
        }
    }
    
    private boolean isNumeric(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
} 