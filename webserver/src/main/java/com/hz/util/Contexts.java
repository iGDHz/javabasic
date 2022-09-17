package com.hz.util;

import com.hz.pojo.HzApplicationContext;
import com.hz.proxy.PostProcessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Contexts {

    //上下文环境链表
    public static List<HzApplicationContext> AppContext = new ArrayList<>();

    //对应Context在链表上的索引值
    public static Map<String,Integer> nameContexIndex = new HashMap<>();

    //前置处理器
    public static Map<Class, List<PostProcessor>> PreProcessorContext = new HashMap<>();

    //后置处理器
    public static Map<Class,List<PostProcessor>> AfterProcessorContext = new HashMap<>();

}
