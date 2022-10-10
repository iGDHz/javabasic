package com.hz.config;

import com.hz.pojo.HzApplicationContext;
import com.hz.pojo.impl.WebContext;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;

public class ApplicationConfig {
    public static Logger logger;
    public static File ModulePath;

    //TODO 加载配置文件基础信息
    static {
        logger = LogManager.getLogger("webServer"); //加载log4j组件
        ModulePath = new File(".");
    }
}
