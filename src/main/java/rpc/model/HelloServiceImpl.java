package rpc.model;

public class HelloServiceImpl implements HelloService {
    @Override
    public String hello(String string) {
        System.out.println("Serer runing "+string);
        return "hello "+string;
    }
}
