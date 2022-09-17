package com.hz.pojo.impl;

import com.hz.pojo.HzApplicationContext;

import java.util.HashMap;

public class WebContext implements HzApplicationContext<String> {
    public HashMap<String,String> map;

    @Override
    public String getValue(String key) {
        return map.get(key);
    }

    @Override
    public String getOrDefault(String key, String defval) {
        return map.getOrDefault(key,defval);
    }
}
