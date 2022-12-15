package DesignModel;

public class SingletonExample {
}

class Singleton{
    private Singleton singleton;
    private static Object lock = new Object();
    public Singleton(){

    }

    public Singleton getSingleton(){
        if(this.singleton == null){
            synchronized (lock){//防止执行时singletion还未实例化
                if (this.singleton == null){
                    this.singleton = new Singleton();
                }
            }
        }
        return this.singleton;
    }
}
