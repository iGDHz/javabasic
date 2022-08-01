package com.hz.autoload.impl;

import com.hz.autoload.ConfigFileLoading;
import org.yaml.snakeyaml.reader.UnicodeReader;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Deprecated
public class XmlAutoLoading implements ConfigFileLoading {
    @Override
    public Map<String, Object> ConfigToMap(String filename, Class reclass) {
        Map<String, Object> map = new HashMap<>();
        Properties properties = new Properties();
        try {
            properties.loadFromXML(reclass.getClassLoader().getResourceAsStream(filename));
        } catch (IOException e) {
            logger.debug("There is something error in the file: \""+filename+"\"" );
        }
        return map;
    }

    @Override
    public Map<String, Object> ConfigToMap(String filename) {
        Map<String, Object> map = new HashMap<>();
        Properties properties = new Properties();
        try {
            properties.loadFromXML(new FileInputStream(filename));
        } catch (IOException e) {
            logger.debug("File \""+filename+"\" don't exist");
        }
        return map;
    }


}
