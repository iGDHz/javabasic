package com.hz.config;

import java.io.File;
import java.io.FilenameFilter;
import java.util.regex.Pattern;

public class FileFilter implements FilenameFilter {

    private Pattern pattern; //正则过滤器过滤文件

    public FileFilter(){

    }

    public FileFilter(String regex){
        pattern = Pattern.compile(regex);
    }

    @Override
    public boolean accept(File dir, String name) {
        return pattern.matcher(name).matches();
    }
}
