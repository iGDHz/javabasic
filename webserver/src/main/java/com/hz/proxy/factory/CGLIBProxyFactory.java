package com.hz.proxy.factory;

import com.hz.proxy.PostProcessor;
import com.hz.util.Contexts;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CGLIBProxyFactory {
    public static Object getProxy(Class<?> clazz) {
        // 创建动态代理增强类
        Enhancer enhancer = new Enhancer();
        // 设置类加载器
        enhancer.setClassLoader(clazz.getClassLoader());
        // 设置被代理类
        enhancer.setSuperclass(clazz);
        // 设置方法拦截器
        enhancer.setCallback(new AppMethodInterceptor());
        // 创建代理类
        return enhancer.create();
    }
}

class AppMethodInterceptor implements MethodInterceptor {

    /**
     *  o 是代理对象的引用，如果要使用methodProxy.invoke(),需要用到目标对象
     */
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        PostProcessor[] pre = (PostProcessor[]) Contexts.PreProcessorContext.get(o.getClass()).toArray(); //获取代理目标的前置处理器
        for (PostProcessor postProcessor : pre) {
            postProcessor.invoke();
        }
        Object res = methodProxy.invokeSuper(o, objects);
        PostProcessor[] after = (PostProcessor[]) Contexts.AfterProcessorContext.get(o.getClass()).toArray();
        for (PostProcessor postProcessor : after) {
            postProcessor.invoke();
        }
        return res;
    }
}
