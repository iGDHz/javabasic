package DesignModel;

import javafx.event.Event;

import java.beans.EventHandler;
import java.util.ArrayList;
import java.util.List;

public class ObserverExample {
    public static void main(String[] args) {
        ConcreteSubject subject = new ConcreteSubject();
        Subject notify = EventHandler.create(Subject.class, subject, "Notify");
        notify.Notify();
    }
}

//观察者类
abstract class Observer{
    public abstract void Update();
}

abstract class Subject{
    private List<Observer> observers = new ArrayList<>();

    public void add(Observer observer){
        observers.add(observer);
    }

    public void remove(Observer observer){
        observers.remove(observer);
    }

    //通知
    public void Notify(){
        for (Observer observer : observers) {
            observer.Update();
        }
    }
}

class ConcreteSubject extends Subject{
    private String subjectStates;

    public String getSubjectStates() {
        return subjectStates;
    }

    public void setSubjectStates(String subjectStates) {
        this.subjectStates = subjectStates;
    }
}

class ConcreteObserver extends Observer{

    private String name;
    private String ObserverState;
    private ConcreteSubject subject;

    public ConcreteObserver(String name, ConcreteSubject subject) {
        this.name = name;
        this.subject = subject;
    }

    @Override
    public void Update() {
        ObserverState = subject.getSubjectStates();
        System.out.println("观察者的新状态"+ObserverState);
    }

    public ConcreteSubject getSubject() {
        return subject;
    }

    public void setSubject(ConcreteSubject subject) {
        this.subject = subject;
    }
}