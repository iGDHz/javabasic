package collectionsTest.DesignModel;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Arrays;

public class CGLIBProxyExample {
    public static void main(String[] args) {
        TargetObject proxy = (TargetObject)GCLIBFactory.getProxy(TargetObject.class);
        proxy.sendMessage("cglib proxy");
    }
}

class MyMethodInterceptor implements MethodInterceptor{

    /**
     *  o 是代理对象的引用，如果要使用methodProxy.invoke(),需要用到目标对象
     */
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("Before Method:"+method.getName());
        Object res = methodProxy.invokeSuper(o, objects);
        System.out.println("After Method:"+method.getName());
        return res;
    }
}

class GCLIBFactory{
    public static Object getProxy(Class<?> clazz) {
        // 创建动态代理增强类
        Enhancer enhancer = new Enhancer();
        // 设置类加载器
        enhancer.setClassLoader(clazz.getClassLoader());
        // 设置被代理类
        enhancer.setSuperclass(clazz);
        // 设置方法拦截器
        enhancer.setCallback(new MyMethodInterceptor());
        // 创建代理类
        return enhancer.create();
    }
}

