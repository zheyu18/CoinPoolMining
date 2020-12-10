package com.bc.bit.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Created by zhengjiyu on 2019/1/3.
 * Describe:
 * Email:3301360040@qq.com
 */

public class MapSort {

    public List<Map.Entry<String, String>> sortMap(final Map<String, String> map) {
        final List<Map.Entry<String, String>> infos = new ArrayList<Map.Entry<String, String>>(map.entrySet());

        // 重写集合的排序方法：按字母顺序
        Collections.sort(infos, new Comparator<Map.Entry<String, String>>() {
            @Override
            public int compare(final Map.Entry<String, String> o1, final Map.Entry<String, String> o2) {
                return (o1.getKey().toString().compareTo(o2.getKey()));
            }
        });

        return infos;
    }
}
