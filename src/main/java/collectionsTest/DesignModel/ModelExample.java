package DesignModel;

public class ModelExample {
    public static void main(String[] args) {
        ConcreteClassA LclassA = new ConcreteClassA();
        ConcreteClassB classB = new ConcreteClassB();
        LclassA.TemplateMethod();
        classB.TemplateMethod();

        Abstractclass Abclass = new ConcreteClassA();
        Abclass.TemplateMethod();
        Abclass = new ConcreteClassB();
        Abclass.TemplateMethod();

    }
}

abstract class Abstractclass{
    public abstract void PrimitiveOperationA();
    public abstract void PrimitiveOperationB();

    public void TemplateMethod(){
        PrimitiveOperationA();
        PrimitiveOperationB();
        System.out.println("模板方法执行完毕");
    }
}

class ConcreteClassA extends Abstractclass{

    @Override
    public void PrimitiveOperationA() {
        System.out.println("具体方法A1实现");
    }

    @Override
    public void PrimitiveOperationB() {
        System.out.println("具体方法A2实现");
    }
}

class ConcreteClassB extends Abstractclass{

    @Override
    public void PrimitiveOperationA() {
        System.out.println("具体方法B1实现");
    }

    @Override
    public void PrimitiveOperationB() {
        System.out.println("具体方法B2实现");
    }
}