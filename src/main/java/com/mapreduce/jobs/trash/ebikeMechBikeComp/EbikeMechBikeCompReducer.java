package com.mapreduce.jobs.ebikeMechBikeComp;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;

public class EbikeMechBikeCompReducer extends MapReduceBase implements Reducer<Text, EbikeMechBikeCount, Text, EbikeMechBikeCount> {
    

    public void reduce(Text key, Iterator<EbikeMechBikeCount> values, OutputCollector<Text, EbikeMechBikeCount> output, Reporter reporter) throws IOException {
        EbikeMechBikeCount resultCount = new EbikeMechBikeCount(0,0,0);

        while (values.hasNext()) {
            EbikeMechBikeCount bikeTemp = values.next();
            resultCount.setEbike(resultCount.getEbike() + bikeTemp.getEbike());
            resultCount.setMechBike(resultCount.getMechBike() + bikeTemp.getMechBike());            
            resultCount.setEqualBike(resultCount.getEqualBike() + bikeTemp.getEqualBike());
        }

        output.collect(key, resultCount);

    }

}
