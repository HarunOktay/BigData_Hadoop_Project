package com.mapreduce.jobs.averageDelayTime;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

public class AverageFlightDelayMapper extends MapReduceBase implements Mapper<LongWritable, Text, Text, IntWritable>{

    public void map(LongWritable key, Text value, OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException {
        String valueString = value.toString();
        String[] valueSplit = valueString.split(",");

        try {
            // Skip header or malformed rows
            if (valueSplit.length < 16 || !isNumeric(valueSplit[15])) {
                return;
            }

            // Get arrival delay and day of week
            int arrivalDelay = (int) Double.parseDouble(valueSplit[15]); // ArrDelay
            String dayOfWeek = valueSplit[3]; // DayOfWeek - field 4 (0-indexed: 3)

            // Output day of week as key and arrival delay as value
            output.collect(new Text(dayOfWeek), new IntWritable(arrivalDelay));
            
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