package com.xcloudeye.stats.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ListUtil {

    /**
     * <p>Title: disdinctList</p>
     * <p>Description: list去重</p>
     *
     * @param list
     * @return 去重后的List
     */
    public static List<String> disdinctList(List<String> list) {
        List<String> listTemp = new ArrayList<String>();
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            String a = it.next();
            if (listTemp.contains(a)) {
                it.remove();
            } else {
                listTemp.add(a);
            }
        }
        return list;
    }

}
