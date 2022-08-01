package com.hz.autoload.impl;

import com.hz.autoload.ConfigFileLoading;
import org.yaml.snakeyaml.reader.UnicodeReader;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class PropertiesAutoLoading implements ConfigFileLoading {
    @Override
    public Map<String, Object> ConfigToMap(String filename, Class reclass) throws IOException {
        Map<String, Object> map = new LinkedHashMap<>();
        BufferedReader reader = new BufferedReader(new UnicodeReader(reclass.getClassLoader().getResourceAsStream(filename)));
        String line;
        while((line = reader.readLine()) != null){
            if(line.charAt(0) == '!') continue;
            String key,value;
            int index = line.indexOf('=');
            key = line.substring(0,index);
            value = line.substring(index+1);
            map.put(key,value);
        }
        return map;
    }

    @Override
    public Map<String, Object> ConfigToMap(String filename) throws IOException {
        Map<String, Object> map = new LinkedHashMap<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
        String line;
        while((line = reader.readLine()) != null){
            if(line.charAt(0) == '!') continue;
            String key,value;
            int index = line.indexOf('=');
            key = line.substring(0,index);
            value = line.substring(index+1);
            map.put(key,value);
        }
        return map;
    }
}
