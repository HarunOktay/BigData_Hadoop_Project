package com.mapreduce.jobs.ebikeMechBikeComp;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;


public class EbikeMechBikeCompMapper extends MapReduceBase implements Mapper<LongWritable, Text, Text,EbikeMechBikeCount >{

    public void map(LongWritable key, Text valueText, OutputCollector<Text, EbikeMechBikeCount> output, Reporter reporter) throws IOException {
        String valueString = valueText.toString();
        String[] valueSplit = valueString.split(",");

        try {
                long unixTimestamp = Long.parseLong(valueSplit[12]);
                Date date = new Date(unixTimestamp * 1000L); // Unix zaman damgası saniye cinsinden olduğu için 1000 ile çarpmamız gerekiyor
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String[] formattedDateTime = sdf.format(date).split(" ");
                int hour = Integer.parseInt(formattedDateTime[1].split(":")[0]);

                int ebike = Integer.parseInt(valueSplit[3]);
                int mechBike = Integer.parseInt(valueSplit[2]);

                int ebikeComparison = ebike > mechBike ? 1 : 0;
                int mechBikeComparison = mechBike > ebike ? 1 : 0;
                int equalComparison = ebike == mechBike ? 1 : 0;

                // Create an instance of EbikeMechBikeCount
                EbikeMechBikeCount count = new EbikeMechBikeCount(ebikeComparison, mechBikeComparison, equalComparison);
                
                if (hour<=8) {
                    output.collect(new Text(formattedDateTime[0] + " 00:00-08:00"),count);
                }else if (hour<=16){
                    output.collect(new Text(formattedDateTime[0] + " 08:00-16:00"),count);
                }else{
                    output.collect(new Text(formattedDateTime[0] + " 16:00-24:00"),count);
                }

        } catch (Exception e) {
            System.err.println("------------------");
            System.err.println(e.toString());
            System.err.println("------------------");
        }

    }

}