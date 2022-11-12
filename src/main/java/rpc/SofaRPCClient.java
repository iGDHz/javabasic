package rpc;

import com.alipay.sofa.rpc.config.ConsumerConfig;
import rpc.model.HelloService;

public class SofaRPCClient {
    public static void main(String[] args) {
        ConsumerConfig<HelloService> consumerConfig = new ConsumerConfig<HelloService>()
                .setInterfaceId(HelloService.class.getName()) // Specify the interface
                .setProtocol("bolt") // Specify the protocol.setDirectUrl
                .setDirectUrl("bolt://127.0.0.1:12200"); // Specify the direct connection address
        // Generate the proxy class
        HelloService helloService = consumerConfig.refer();
        while (true) {
            System.out.println(helloService.hello("world"));
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
            }
        }
    }
}
