package com.mapreduce.jobs.averageBikeCount;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

public class AverageBikeCountMapper extends MapReduceBase implements Mapper<LongWritable, Text, Text, IntWritable>{

    
    public void map(LongWritable key, Text value, OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException {
        String valueString = value.toString();
        String[] valueSplit = valueString.split(",");

        try {
            //long unixTimestamp = Long.parseLong(valueSplit[12]);
            //Date date = new Date(unixTimestamp * 1000L); // Unix zaman damgası saniye cinsinden olduğu için 1000 ile çarpmamız gerekiyor
            //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //String[] formattedDateTime = sdf.format(date).split(" ");

            int arriveDelay = Integer.parseInt(valueSplit[15]);
            String dateWeek = valueSplit[4];

            output.collect(new Text(dateWeek) , new IntWritable(arriveDelay));
        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }

}
