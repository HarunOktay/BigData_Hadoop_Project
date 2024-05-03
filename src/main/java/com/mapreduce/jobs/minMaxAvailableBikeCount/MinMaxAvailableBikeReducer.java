package com.mapreduce.jobs.minMaxAvailableBikeCount;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;

public class MinMaxAvailableBikeReducer extends MapReduceBase implements Reducer<Text, MinMaxBikeCount, Text, MinMaxBikeCount> {

    public void reduce(Text key, Iterator<MinMaxBikeCount> values, OutputCollector<Text, MinMaxBikeCount> output, Reporter reporter) throws IOException {
        MinMaxBikeCount resultCount = new MinMaxBikeCount();
        resultCount.setMax(null);
        resultCount.setMin(null);

        while (values.hasNext()) {
            MinMaxBikeCount minMaxTemp = values.next();
            if (resultCount.getMin()== null || (minMaxTemp.getMin() < resultCount.getMin())) {
                resultCount.setMin(minMaxTemp.getMin());
            }    

            if (resultCount.getMax() == null || (minMaxTemp.getMax() > resultCount.getMax())) {
                resultCount.setMax(minMaxTemp.getMax());
            }

        }
        output.collect(key, resultCount);

    }

}
