package jdkdif.java18;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 3,time = 1,timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 3,time = 1,timeUnit = TimeUnit.SECONDS)
@Fork(1)
public class VectorDemo {
    private int[] nums;
    @Param({
            "100",
            "1000",
            "10000",
            "100000",
            "1000000",
            "10000000",
            "100000000",
            "250000000"
    })
    int size;

    @Setup
    public void setup(){
        nums = new int[size];
    }

    @Benchmark
    public int[] StreamComputation(){
        int[] result = new int[size];
        Arrays.parallelSetAll(result, i -> (nums[i] * i + nums[i] * nums[i])*-1);
        return result;
    }


    @Benchmark
    public void ParallelComputation() throws InterruptedException {
        int[] result = new int[size];
        CountDownLatch count = new CountDownLatch(8);
        for (int i = 0; i < 8; i++) {
            final int start = i;
            new Thread(()->{
                for (int j = start; j < nums.length; j += 8) {
                    result[j] = (nums[j]*j+nums[j]*nums[j])*-1;
                }
                count.countDown();
            });
        }
        count.await();
    }

//    @Benchmark
//    public int[] Vector128Computation(){
//        int[] result = new int[size];
//        VectorSpecies<Integer> species = IntVector.SPECIES_128;
//        int loop = species.loopBound(nums.length);
//        int i = 0;
//        for (; i < loop;  i += species.length()) {
//            IntVector va = IntVector.fromArray(species,nums,i);
//            IntVector vb = IntVector.fromArray(species,nums,i);
//            IntVector vc = va.mul(va)
//                    .add(vb.mul(vb))
//                    .neg();
//            vc.intoArray(result,i);
//        }
//        for (; i < nums.length; i++) {
//            result[i] = nums[i]*i+nums[i]*nums[i]*(-1);
//        }
//        return result;
//    }
//
//    @Benchmark
//    public int[] Vector256Computation(){
//        int[] result = new int[size];
//        VectorSpecies<Integer> species = IntVector.SPECIES_256;
//        int loop = species.loopBound(nums.length);
//        int i = 0;
//        for (; i < loop;  i += species.length()) {
//            IntVector va = IntVector.fromArray(species,nums,i);
//            IntVector vb = IntVector.fromArray(species,nums,i);
//            IntVector vc = va.mul(va)
//                    .add(vb.mul(vb))
//                    .neg();
//            vc.intoArray(result,i);
//        }
//        for (; i < nums.length; i++) {
//            result[i] = nums[i]*i+nums[i]*nums[i]*(-1);
//        }
//        return result;
//    }
//
//    @Benchmark
//    public int[] Vector512Computation(){
//        int[] result = new int[size];
//        VectorSpecies<Integer> species = IntVector.SPECIES_512;
//        int loop = species.loopBound(nums.length);
//        int i = 0;
//        for (; i < loop;  i += species.length()) {
//            IntVector va = IntVector.fromArray(species,nums,i);
//            IntVector vb = IntVector.fromArray(species,nums,i);
//            IntVector vc = va.mul(va)
//                    .add(vb.mul(vb))
//                    .neg();
//            vc.intoArray(result,i);
//        }
//        for (; i < nums.length; i++) {
//            result[i] = nums[i]*i+nums[i]*nums[i]*(-1);
//        }
//        return result;
//    }

    @Benchmark
    public int[] defaultComputation(){
        int[] result = new int[size];
        for (int i = 0; i < nums.length; i++) {
            result[i] = (nums[i]*i+nums[i]*nums[i])*-1;
        }
        return result;
    }

    public static void main(String[] args) throws RunnerException, IOException {
        Options opts = new OptionsBuilder()
                .include(VectorDemo.class.getSimpleName())
                .resultFormat(ResultFormatType.JSON)
                .output(new File("jmh.log").getCanonicalPath())
                .build();
        new Runner(opts).run();
    }

}