package com.hz.mapper;

import com.hz.pojo.Order;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class HzMapper extends Mapper<Text, Order,Text,Text> {
        @Override
        protected void map(Text key, Order value, Mapper.Context context) throws IOException, InterruptedException{
            context.write(new Text(String.valueOf(value.getProductid())),new Text(value.getPrice().toString()));
        }

}
