package com.hz.autoload;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public interface ConfigFileLoading {
    static final Logger logger = LogManager.getLogger("ConfigLoading");

    Map<String,Object> ConfigToMap(String filename,Class reclass) throws IOException;

    Map<String,Object> ConfigToMap(String filename) throws IOException;

}
