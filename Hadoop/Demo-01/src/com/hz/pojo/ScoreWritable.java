package com.hz.pojo;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * 学习成绩读写类
 * 数据格式参考：19020090017 小讲 90 99 100 89 95
 * @author Bertron
 * 需要自定义一个 ScoreWritable 类实现 WritableComparable 接口，将学生各门成绩封装起来。
 */
public class ScoreWritable implements WritableComparable< Object > {//其实这里，跟TVPlayData一样的
//  注意：    Hadoop通过Writable接口实现的序列化机制，不过没有提供比较功能，所以和java中的Comparable接口合并，提供一个接口WritableComparable。（自定义比较）
//         Writable接口提供两个方法(write和readFields)。


    private float Chinese;
    private float Math;
    private float English;
    private float Physics;
    private float Chemistry;


//    问：这里我们自己编程时，是一定要创建一个带有参的构造方法，为什么还要显式的写出来一个带无参的构造方法呢？
//    答：构造器其实就是构造对象实例的方法，无参数的构造方法是默认的，但是如果你创造了一个带有参数的构造方法，那么无参的构造方法必须显式的写出来，否则会编译失败。

    public ScoreWritable(){}//java里的无参构造函数，是用来在创建对象时初始化对象
    //在hadoop的每个自定义类型代码里，好比，现在的ScoreWritable，都必须要写无参构造函数。


    //问：为什么我们在编程的时候，需要创建一个带有参的构造方法？
    //答:就是能让赋值更灵活。构造一般就是初始化数值，你不想别人用你这个类的时候每次实例化都能用另一个构造动态初始化一些信息么(当然没有需要额外赋值就用默认的)。

    public ScoreWritable(float Chinese,float Math,float English,float Physics,float Chemistry){//java里的有参构造函数,是用来在创建对象时初始化对象
        this.Chinese = Chinese;
        this.Math = Math;
        this.English = English;
        this.Physics = Physics;
        this.Chemistry = Chemistry;
    }

    //问：其实set和get方法，这两个方法只是类中的setxxx和getxxx方法的总称，
    //    那么，为什么在编程时，有set和set***两个，只有get***一个呢？

    public void set(float Chinese,float Math,float English,float Physics,float Chemistry){
        this.Chinese = Chinese;//即float Chinese赋值给private float Chinese;
        this.Math = Math;
        this.English = English;
        this.Physics = Physics;
        this.Chemistry = Chemistry;
    }
//    public float get(float Chinese,float Math,float English,float Physics,float Chemistry){因为这是错误的，所以对于set可以分开，get只能是get***
//        return Chinese;
//        return Math;
//        return English;
//        return Physics;
//        return Chemistry;
//    }


    public float getChinese() {//拿值，得返回，所以需有返回类型float
        return Chinese;
    }
    public void setChinese(float Chinese){//设值，不需，所以空返回类型
        this.Chinese = Chinese;
    }
    public float getMath() {//拿值
        return Math;
    }
    public void setMath(float Math){//设值
        this.Math = Math;
    }
    public float getEnglish() {//拿值
        return English;
    }
    public void setEnglish(float English){//设值
        this.English = English;
    }
    public float getPhysics() {//拿值
        return Physics;
    }
    public void setPhysics(float Physics){//设值
        this.Physics = Physics;
    }
    public float getChemistry() {//拿值
        return Chemistry;
    }
    public void setChemistry(float Chemistry) {//拿值
        this.Chemistry = Chemistry;
    }

    // 实现WritableComparable的readFields()方法
//    对象不能传输的，需要转化成字节流！
//    将对象转换为字节流并写入到输出流out中是序列化，write 的过程（最好记!!!）
//    从输入流in中读取字节流反序列化为对象      是反序列化，readFields的过程（最好记!!!）
    public void readFields(DataInput in) throws IOException {//拿代码来说的话，对象就是比如Chinese、Math。。。。
        Chinese = in.readFloat();//因为，我们这里的对象是float类型，所以是readFloat()
        Math = in.readFloat();
        English = in.readFloat();//注意:反序列化里，需要生成对象对吧，所以，是用到的是get那边对象
        Physics = in.readFloat();
        Chemistry = in.readFloat();
//        in.readByte()
//        in.readChar()
//        in.readDouble()
//        in.readLine()
//        in.readFloat()
//        in.readLong()
//        in.readShort()
    }

    // 实现WritableComparable的write()方法，以便该数据能被序列化后完成网络传输或文件输出
//    将对象转换为字节流并写入到输出流out中是序列化，write 的过程（最好记!!!）
//    从输入流in中读取字节流反序列化为对象      是反序列化，readFields的过程（最好记!!!）
    public void write(DataOutput out) throws IOException {//拿代码来说的话，对象就是比如Chinese、Math。。。。
        out.writeFloat(Chinese);//因为，我们这里的对象是float类型，所以是writeFloat()
        out.writeFloat(Math);
        out.writeFloat(English);//注意:序列化里，需要对象对吧，所以，用到的是set那边的对象
        out.writeFloat(Physics);
        out.writeFloat(Chemistry);
//        out.writeByte()
//        out.writeChar()
//        out.writeDouble()
//        out.writeFloat()
//        out.writeLong()
//        out.writeShort()
//        out.writeUTF()
    }

    public int compareTo(Object o) {//java里的比较，Java String.compareTo()
        return 0;
    }


//    Hadoop中定义了两个序列化相关的接口：Writable 接口和 Comparable 接口，这两个接口可以合并成一个接口 WritableComparable。
//    Writable 接口中定义了两个方法，分别为write(DataOutput out)和readFields(DataInput in)
//    所有实现了Comparable接口的对象都可以和自身相同类型的对象比较大小


//    Hadoop中定义了两个序列化相关的接口：Writable 接口和 Comparable 接口，这两个接口可以合并成一个接口 WritableComparable。
//    Writable 接口中定义了两个方法，分别为write(DataOutput out)和readFields(DataInput in)
//    所有实现了Comparable接口的对象都可以和自身相同类型的对象比较大小


//  源码是
//    package java.lang;
//    import java.util.*;
//    public interface Comparable {
//        /**
//        * 将this对象和对象o进行比较，约定：返回负数为小于，零为大于，整数为大于
//        */
//        public int compareTo(T o);
//    }

}