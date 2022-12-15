package collectionsTest.DesignModel;

import java.util.ArrayList;
import java.util.List;

public class BuilderExample {
    public static void main(String[] args) {
        Director director = new Director();
        ConcreteBuilderA builderA = new ConcreteBuilderA();
        ConcreteBuilderB builderB = new ConcreteBuilderB();
        director.Construct(builderA);
        Product product = builderA.GetResult();
        product.show();
        director.Construct(builderB);
        Product product1 = builderB.GetResult();
        product1.show();
    }
}

class Product{
    private List<String> res = new ArrayList<>();

    public void append(String part){
        res.add(part);
    }

    public void show(){
        System.out.println("产品 创建");
        for (String s : res) {
            System.out.println(s);
        }
    }
}

abstract class Builder{
    Product product = new Product();
    abstract void BuildPartA();
    abstract void BuildPartB();
    Product GetResult() {
        return product;
    }
}

class ConcreteBuilderA extends Builder{



    @Override
    void BuildPartA() {
        product.append("组件A1");
    }

    @Override
    void BuildPartB() {
        product.append("组件B1");
    }

}

class ConcreteBuilderB extends Builder{

    @Override
    void BuildPartA() {
        product.append("组件A2");
    }

    @Override
    void BuildPartB() {
        product.append("组件B2");
    }

}

class Director{
    public void Construct(Builder builder){
        builder.BuildPartA();
        builder.BuildPartB();
    }
}