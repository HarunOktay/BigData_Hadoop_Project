package com.mapreduce.jobs.dailyCountOfDiffTailNumber;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

public class DailyCountOfDiffTailNumberMapper extends MapReduceBase implements Mapper<LongWritable, Text, Text, Text> {

    public void map(LongWritable key, Text value, OutputCollector<Text, Text> output, Reporter reporter) throws IOException {
        String valueString = value.toString();
        String[] valueSplit = valueString.split(",");

        try {
            // Skip malformed rows
            if (valueSplit.length < 12) {
                return;
            }

            // Get date components
            String year = valueSplit[1];
            String month = valueSplit[2];
            String day = valueSplit[3];
            
            // Get tail number
            String tailNum = valueSplit[11];
            
            // Skip rows with missing tail numbers
            if (tailNum == null || tailNum.trim().isEmpty()) {
                return;
            }
            
            // Create date key in format YYYY-MM-DD
            String dateKey = year + "-" + month + "-" + day;
            
            // Output date as key and tail number as value
            output.collect(new Text(dateKey), new Text(tailNum));
            
        } catch (Exception e) {
            System.err.println("Error processing record: " + e.toString());
        }
    }
} 