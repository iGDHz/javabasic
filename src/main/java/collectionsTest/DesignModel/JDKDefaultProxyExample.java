package collectionsTest.DesignModel;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JDKDefaultProxyExample {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        TargetObjectInterface proxy = (TargetObjectInterface)TargetObjectFactory.getProxy(new TargetObject());
//        Field h = proxy.getClass().getSuperclass().getDeclaredField("h"); //从被代理的对象找到父类，即reflect对象，获取当中为“h”的字段
//        h.setAccessible(true);
//        Object o = h.get(proxy); //通过h字段的值获取InvocationHandler
//        Field target = o.getClass().getDeclaredField("target"); //通过代理对象代理的target
//        target.setAccessible(true);
//        Object o1 = target.get(o); //获取target的实际对象
        proxy.sendMessage("代理对象执行方法");
    }
}

interface TargetObjectInterface{
    default public void sendMessage(String message){
        System.out.println("This is a TargetObject interface");
    }
}

class TargetObject implements TargetObjectInterface{

    @Override
    public void sendMessage(String message) {
        System.out.println("Send Message:"+message);
    }
}

/*
 *   逻辑判断类
 *
 * */

class MyInvocationHandler implements InvocationHandler{
    private final Object target;

    public MyInvocationHandler(Object target) {
        this.target = target;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("Before Method:"+method.getName());
        Object res = method.invoke(target, args);//执行方法
        System.out.println("After Method:"+method.getName());
        return res;
    }
}

/*
 *   工厂
 * */
class TargetObjectFactory{
    public static Object getProxy(Object target){
        return Proxy.newProxyInstance(target.getClass().getClassLoader(),target.getClass().getInterfaces(),new MyInvocationHandler(target));
    }
}