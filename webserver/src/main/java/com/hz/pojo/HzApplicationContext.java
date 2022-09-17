package com.hz.pojo;

//AppContext
public interface HzApplicationContext<T> {
    //获取上下文的Value值
    T getValue(String key);

    //获取上下文的value值，若不存在则返回defval值
    T getOrDefault(String key,T defval);


}
