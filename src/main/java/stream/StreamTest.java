package stream;

import Extends.FunctionInterfaceTest;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static java.util.stream.IntStream.range;

public class StreamTest {
//    public StreamTest(int a,int b){
//
//    }
//    public StreamTest(int a,String b,char c){
//
//    }
    public StreamTest(int a,char b){

    }
    public StreamTest(BiFunction function){

    }
    public static void main(String[] args) {
        new StreamTest((a,b) -> a);
        long res = 0;
        long time = System.currentTimeMillis();
        for(int i = 0; i < 100_000_000; i++){
            res += i;
        }
        System.out.println("---------for circle uses " + (System.currentTimeMillis()-time)+"ms---------");
        time = System.currentTimeMillis();
        res = 0;
        for(int i : range(0,100_000_000).toArray()){
            res += i;
        }
        System.out.println("---------for and range uses " + (System.currentTimeMillis()-time)+"ms---------");
        time = System.currentTimeMillis();
        res = LongStream.range(0,100_000_000).sum();
        System.out.println("---------stream uses " +(System.currentTimeMillis()-time)+"ms---------");
        function(()->{
            System.out.println("hello world");
        });

        //生成随机数后过滤
        Random rand = new Random(47);
        int[] randnum = new int[100_000_000];
        for (int i = 0; i < 100_000_000; i++) {
            randnum[i] = rand.nextInt(100);
        }

        int[] temp = randnum.clone();
        time = System.currentTimeMillis();
        List<Integer> list = new ArrayList<>();
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < 100_000_000; i++) {
            if(set.contains(temp[i])){
                continue;
            }
            set.add(temp[i]);
            list.add(temp[i]);
        }
        System.out.println("---------for filter uses " + (System.currentTimeMillis()-time)+"ms---------");
        temp = randnum.clone();
        time = System.currentTimeMillis();
        IntStream distinct = Arrays.stream(temp).distinct();
        int[] ints = distinct.toArray();
        System.out.println(ints.length);
        System.out.println("---------stream filter uses " + (System.currentTimeMillis()-time)+"ms---------");

        System.out.println("---------FOREACH TEST---------");
        foreachTest();
        System.out.println("---------FLATMAP TEST---------");
        flatMapTest();
//        List<String> strings = Arrays.asList("abc", "def", "gkh", "abc");
//        //返回符合条件的stream
//        Stream<String> stringStream = strings.stream().filter(s -> "abc".equals(s));
//        Callback<String, TestClass> aNew = TestClass::new;
//        Function<String, TestClass> aNew1 = TestClass::new;
//        TestClass test = aNew1.apply("test");
//        BiFunction<Integer, Integer, StreamTest> aNew2 = StreamTest::new;
//        Runnable functionInterfaceTest = StreamTest::new;
//        Function<Integer, StreamTest> test1 = StreamTest::new;
//        Function<Integer, StreamTest> test2 = StreamTest::new;
//        range(10,20).sum();
//        Runnable temp = test::temp;
//        Stream.generate(()-> temp).limit(10).forEach(System.out::println);
//        //计算流符合条件的流的数量
//        long count = stringStream.count();
//        //forEach遍历->打印元素
//        strings.stream().forEach(System.out::println);
//
//        //limit 获取到1个元素的stream
//        Stream<String> limit = strings.stream().limit(1);
//        //toArray 比如我们想看这个limitStream里面是什么，比如转换成String[],比如循环
//        String[] array = limit.toArray(String[]::new);
//
//        //map 对每个元素进行操作返回新流
//        Stream<String> map = strings.stream().map(s -> s + "22");
//
//        //sorted 排序并打印
//        strings.stream().sorted().forEach(System.out::println);
//
//        //Collectors collect 把abc放入容器中
//        List<String> collect = strings.stream().filter(string -> "abc".equals(string)).collect(Collectors.toList());
//        //把list转为string，各元素用，号隔开
//        String mergedString = strings.stream().filter(string -> !string.isEmpty()).collect(Collectors.joining(","));
//
//        //对数组的统计，比如用
//        List<Integer> number = Arrays.asList(1, 2, 5, 4);
//
//        IntSummaryStatistics statistics = number.stream().mapToInt((x) -> x).summaryStatistics();
//        System.out.println("列表中最大的数 : "+statistics.getMax());
//        System.out.println("列表中最小的数 : "+statistics.getMin());
//        System.out.println("平均数 : "+statistics.getAverage());
//        System.out.println("所有数之和 : "+statistics.getSum());
//
//        //concat 合并流
//        List<String> strings2 = Arrays.asList("xyz", "jqx");
//        Stream.concat(strings2.stream(),strings.stream()).count();
//
//        //注意 一个Stream只能操作一次，不能断开，否则会报错。
//        Stream stream = strings.stream();
//        //第一次使用
//        stream.limit(2);
//        //第二次使用
//        stream.forEach(System.out::println);
//        //报错 java.lang.IllegalStateException: stream has already been operated upon or closed
//
//        //但是可以这样, 连续使用
//        stream.limit(2).forEach(System.out::println);
    }

    private static void print(Runnable runnable) {
        runnable.run();
    }

    public static void foreachTest(){
        ArrayList<String> list = new ArrayList<>();
        list.add("aaa");
        list.add("bbb");
        list.add("ccc");
        list.add("ddd");
        list.stream().forEach((s) -> {
            if (s.equals("ccc")) return;
            System.out.println(s);
        });

    }

    public static void flatMapTest(){
        Stream.of(1,2,3)
                .flatMap(i -> Stream.of("hello","world",i))
                .forEach(System.out::println);
    }
    public static void function(FunctionInterfaceTest function){
        function.function();
    }

}
