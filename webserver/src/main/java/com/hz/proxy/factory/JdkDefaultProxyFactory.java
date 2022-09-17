package com.hz.proxy.factory;

import com.hz.proxy.PostProcessor;
import com.hz.util.Contexts;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JdkDefaultProxyFactory {
    public static Object getProxt(Object target){
        return Proxy.newProxyInstance(target.getClass().getClassLoader(),target.getClass().getInterfaces(),new AppInvocationHandler(target));
    }
}

class AppInvocationHandler implements InvocationHandler{

    private final Object target;

    AppInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        PostProcessor[] pre = (PostProcessor[]) Contexts.PreProcessorContext.get(target.getClass()).toArray(); //获取代理目标的前置处理器
        for (PostProcessor postProcessor : pre) {
            postProcessor.invoke();
        }
        Object res = method.invoke(target,args);
        PostProcessor[] after = (PostProcessor[]) Contexts.AfterProcessorContext.get(target.getClass()).toArray();
        for (PostProcessor postProcessor : after) {
            postProcessor.invoke();
        }
        return res;
    }
}
