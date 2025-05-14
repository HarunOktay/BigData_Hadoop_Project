package com.mapreduce.jobs.minMaxAvailableBikeCount;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;


public class MinMaxAvailableBikeMapper  extends MapReduceBase implements Mapper<LongWritable, Text, Text, MinMaxBikeCount>{


    public void map(LongWritable key, Text valueText, OutputCollector<Text, MinMaxBikeCount> output, Reporter reporter) throws IOException {
        String valueString = valueText.toString();
        String[] splitted = valueString.split(",");

        try {
                long unixTimestamp = Long.parseLong(splitted[12]);
                Date date = new Date(unixTimestamp * 1000L); // Unix zaman damgası saniye cinsinden olduğu için 1000 ile çarpmamız gerekiyor
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String[] formattedDateTime = sdf.format(date).split(" ");

                String hour = formattedDateTime[1].split(":")[0];
                hour = hour + ":00" + "-" + (Integer.parseInt(hour)+1)+ ":00";


            Integer bikeCount = Integer.parseInt(splitted[1]);
            output.collect(new Text(formattedDateTime[0] +" "+ hour), new MinMaxBikeCount(bikeCount,bikeCount));

        } catch (Exception e) {
            System.err.println(e.toString());
            System.out.println("--------------------");
        }
    
    }

}
