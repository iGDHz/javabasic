package com.hz.authority;

import java.util.Map;

//Condition用于条件判定使用，通过重写isVaild方法实现
public interface Condition {
    boolean isVaild(Map obj);
}
