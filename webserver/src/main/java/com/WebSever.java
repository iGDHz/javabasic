package com;

import com.hz.autoload.ConfigFileLoading;
import com.hz.autoload.impl.PropertiesAutoLoading;
import com.hz.autoload.impl.XmlAutoLoading;
import com.hz.autoload.impl.YamlAutoLoading;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.URL;
import java.nio.file.FileSystems;
import java.util.Map;

public class WebSever {
    public static void main(String[] args) throws IOException {

        ServerSocket server = new ServerSocket(8080);
        server.accept();
    }

    @Test
    public void loadYaml(){
        ConfigFileLoading autoLoading = new YamlAutoLoading();
        File file = new File(".");
        URL resource = WebSever.class.getResource("/");
        System.out.println(resource.getFile());
        for (String s : file.list()) {
            System.out.println(s);
        }
        try {
            autoLoading.ConfigToMap("classpath:/application.yaml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
