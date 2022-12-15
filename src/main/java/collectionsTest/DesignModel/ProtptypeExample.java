package DesignModel;

import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;

public class ProtptypeExample {
    public static void main(String[] args) throws CloneNotSupportedException {
        PrototypePropertyA propertyA = new PrototypePropertyA("原型属性");
        ConcretePrototypeA id1 = new ConcretePrototypeA("id1", propertyA);
        ConcretePrototypeB id2 = new ConcretePrototypeB("id2", propertyA);
        id1.num = id2.num = 1;
        ConcretePrototypeA id1_copy = (ConcretePrototypeA) id1.clone();
        ConcretePrototypeB id2_copy = (ConcretePrototypeB) id2.clone();



        System.out.println("id1 == id1_copy 为 " + (id1 == id1_copy));
        System.out.println("id2 == id2_copy 为 " + (id2 == id2_copy));

        System.out.println("内在属性");

        id1.num = 0;
        id2.num = 0;
        System.out.println("id1.peoperty == id1_copy.property 为" + (id1.property == id1_copy.property));
        System.out.println("id1.name == id1_copy.name 为" + (id1.name == id1_copy.name));
        System.out.println("id2.peoperty == id2_copy.property 为" + (id2.property == id2_copy.property));
        System.out.println("id2.name == id2_copy.name 为" + (id2.name == id2_copy.name));
    }
}


abstract class Prototype{
    String name;
    int num;
    PrototypePropertyA property;
}

class PrototypePropertyA implements Cloneable{
    String name;

    public PrototypePropertyA(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}

class ConcretePrototypeA extends Prototype implements Cloneable{
    int num;
    String name;
    PrototypePropertyA property;

    public ConcretePrototypeA(String name, PrototypePropertyA property) {
        this.name = name;
        this.property = property;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PrototypePropertyA getProperty() {
        return property;
    }

    public void setProperty(PrototypePropertyA property) {
        this.property = property;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}

class ConcretePrototypeB extends Prototype implements Cloneable{
    String name;
    PrototypePropertyA property;
    int num;

    public ConcretePrototypeB() {
    }

    public ConcretePrototypeB(String name, PrototypePropertyA property) {
        this.name = name;
        this.property = property;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PrototypePropertyA getProperty() {
        return property;
    }

    public void setProperty(PrototypePropertyA property) {
        this.property = property;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        ConcretePrototypeB concretePrototypeB = new ConcretePrototypeB();
        concretePrototypeB.property = (PrototypePropertyA) property.clone();
        concretePrototypeB.name = new String(this.name);
        concretePrototypeB.num = new Integer(this.num);
        return concretePrototypeB;
    }
}