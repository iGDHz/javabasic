package com.hz.autoload.impl;

import com.hz.autoload.ConfigFileLoading;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.reader.UnicodeReader;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class YamlAutoLoading implements ConfigFileLoading {

    //TODO 加载YAML配置文件
    @Override
    public Map<String, Object> ConfigToMap(String filename){
        Yaml yaml = new Yaml();
        Map<String, Object> map  = new LinkedHashMap<>();
        Map<String, Object> target  = new LinkedHashMap<>();
        UnicodeReader reader = null;
        try {
            reader = new UnicodeReader(new FileInputStream(filename));
        } catch (FileNotFoundException e) {
            logger.debug("File \""+filename+"\" don't exist");
        }
        for (Object object : yaml.loadAll(reader)) {
            map = (LinkedHashMap<String, Object>)  object;
        }
        ChangeToSimple((LinkedHashMap<String, Object>) map, (LinkedHashMap<String, Object>) target,"");
        return target;
    }


    //通过类获取资源文件加载配置文件
    @Override
    public Map<String, Object> ConfigToMap(String filename,Class reclass) {
        Yaml yaml = new Yaml();
        Map<String, Object> map  = new LinkedHashMap<>();
        Map<String, Object> target  = new LinkedHashMap<>();
        reclass.getResource("/");
        UnicodeReader reader = new UnicodeReader(reclass.getClassLoader().getResourceAsStream(filename));
        for (Object object : yaml.loadAll(reader)) {
            map = (LinkedHashMap<String, Object>)  object;
        }
        ChangeToSimple((LinkedHashMap<String, Object>) map, (LinkedHashMap<String, Object>) target,"");
        return target;
    }

    //TODO 将结果转化为LinkedHashmap
    public Map<String, Object> ChangeToSimple(LinkedHashMap<String,Object> linkedHashMap,LinkedHashMap<String,Object> target,String prefix){
        Set<Map.Entry<String, Object>> entrySet = linkedHashMap.entrySet();
        Iterator<Map.Entry<String, Object>> iterator = entrySet.iterator();
        while(iterator.hasNext()){
            Map.Entry<String, Object> cur = iterator.next();
            String key = cur.getKey();
            Object val = cur.getValue();
            if(val instanceof LinkedHashMap){
                ChangeToSimple((LinkedHashMap<String, Object>) val,target,prefix+key+".");
            }else{
                target.put(prefix+key,val);
            }
        }
        return target;
    }
}
