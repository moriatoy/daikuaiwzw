package com.commonlib.util;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by dmx on 2017/1/3.
 */

public class SerializableMap implements Serializable {
    private Map<String,Object> map;

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }
}
