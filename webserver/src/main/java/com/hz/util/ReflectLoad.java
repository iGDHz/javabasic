package com.hz.util;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ReflectLoad {
    public static Logger logger = LogManager.getLogger(ReflectLoad.class);

    //TODO 通过classname加载类信息
    public static <T> T LoadClass(T obj, String classname) throws IllegalAccessException, InstantiationException {
        T target = null;
        try {
            logger.debug("Loading class : "+ classname);
            target = (T) Class.forName(classname).newInstance();
        } catch (ClassNotFoundException e) {
            logger.info("Class "+ classname +" not found in your project");
        }
        return target;
    }

    //TODO 有参构造方法加载类信息（待完成）
    public static <T> T LoadClass(T obj, String classname,String ... parmes) throws IllegalAccessException, InstantiationException {
        T target = null;
        try {
            logger.debug("Loading class : "+ classname);
            Class<?> tClass = Class.forName(classname);
            tClass.getConstructors();
            Constructor<?> constructor = tClass.getConstructor(String.class);
            Object targetobj = constructor.newInstance(parmes[0]);
            target = (T) tClass.cast(targetobj);
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
            logger.info("Class "+ classname +" not found in your project");
        }
        return target;
    }

    //TODO 通过序列化文件加载类信息
    public static <T> T LoadClass(T obj, File serfile) throws IOException, ClassNotFoundException {
        ObjectInputStream stream = new ObjectInputStream(new FileInputStream(serfile));
        return (T) stream.readObject();
    }
}
