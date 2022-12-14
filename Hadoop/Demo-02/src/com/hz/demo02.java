package com.hz;

import com.hz.formater.HzInputFormat;
import com.hz.mapper.HzMapper;
import com.hz.reducer.HzReducer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.hadoop.util.Tool;

import java.io.FileOutputStream;
import java.io.IOException;

public class demo02 {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();
        String[] otherargs = new GenericOptionsParser(conf, args).getRemainingArgs();
        if(otherargs.length < 2){
            System.err.println("Usage: wordcount <in> {<int> ...} <out>");
            System.exit(2);
        }
        Job job = Job.getInstance(conf, "HzMapReduce");
        job.setInputFormatClass(HzInputFormat.class);
        job.setJarByClass(demo02.class);
        job.setMapperClass(HzMapper.class);
        job.setCombinerClass(HzReducer.class);
        job.setReducerClass(HzReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        for (int i = 0; i < otherargs.length - 1; i++) {
            FileInputFormat.addInputPath(job,new Path(otherargs[i]));
        }

        FileOutputFormat.setOutputPath(job,new Path(otherargs[otherargs.length-1]));
        if(job.waitForCompletion(true)){
            for (int i = 0; i < otherargs.length-1; i++) {
                Path path = new Path(otherargs[i]);
                FileSystem system = FileSystem.get(conf);
                system.deleteOnExit(path);
            }
            System.exit(0);
        }
        System.exit(1);
    }

}
