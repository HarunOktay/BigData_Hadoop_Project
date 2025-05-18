package com.mapreduce.jobs.dailyCountOfDiffTailNumber;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;

public class DailyCountOfDiffTailNumberReducer extends MapReduceBase implements Reducer<Text, Text, Text, IntWritable> {

    public void reduce(Text key, Iterator<Text> values, OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException {
        // Use a HashSet to track unique tail numbers for this date
        Set<String> uniqueTailNums = new HashSet<>();
        
        // Add all tail numbers to the set (duplicates will be automatically ignored)
        while (values.hasNext()) {
            Text tailNum = values.next();
            String tailNumStr = tailNum.toString();
            if (tailNumStr != null && !tailNumStr.trim().isEmpty()) {
                uniqueTailNums.add(tailNumStr);
            }
        }
        
        // Output the date and count of unique tail numbers
        output.collect(key, new IntWritable(uniqueTailNums.size()));
    }
} 