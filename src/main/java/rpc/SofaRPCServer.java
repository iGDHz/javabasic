package rpc;

import com.alipay.sofa.rpc.config.ProviderConfig;
import com.alipay.sofa.rpc.config.ServerConfig;
import rpc.model.HelloService;
import rpc.model.HelloServiceImpl;

public class SofaRPCServer{
    public static void main(String[] args) {
        ServerConfig serverConfig = new ServerConfig()
                .setProtocol("bolt") // Set a protocol, which is bolt by default
                .setPort(12200) // set a port, which is 12200 by default
                .setDaemon(false); // non-daemon thread

        ProviderConfig<HelloService> providerConfig = new ProviderConfig<HelloService>()
                .setInterfaceId(HelloService.class.getName()) // Specify the interface
                .setRef(new HelloServiceImpl()) // Specify the implementation
                .setServer(serverConfig); // Specify the server

        providerConfig.export (); // Publish service
    }
}
