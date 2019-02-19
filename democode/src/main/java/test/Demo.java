package test;

import java.util.*;

/**
 * 统计字符串的字符数，并按顺序输出
 */
public class Demo {
    public static void main(String[] args) {
       String data = "aabcwdsawdsahfjdhnfkjayuienk";
       Map<Character, Integer> map  = getDataMap(data);
       Map<Character, Integer> sortMap = getSortMap(map);
       sortMap.forEach((k, v) -> System.out.println(k + ":" + v));
    }

    /**
     * 将字符串转换成map
     * @param data
     * @return
     */
    static Map<Character, Integer> getDataMap(String data) {
        Map<Character, Integer> map = new HashMap<>();
        data.chars().mapToObj(c -> (char)c).forEach(c -> {
                    if (map.containsKey(c)) {
                        map.put(c, map.get(c) + 1);
                    } else {
                        map.put(c, 1);
                    }
                });
        return map;
    }

    /**
     * 将Map按value降序排序
     * @param map
     * @return
     */
    static Map<Character, Integer> getSortMap(Map<Character, Integer> map) {
        // 用LinkedHashMap接收，不然放进去还是没有顺序
        Map<Character, Integer> sortMap = new LinkedHashMap<>();
        List<Map.Entry<Character, Integer>> list = new ArrayList<>(map.entrySet());
        Collections.sort(list, new MapValueComparator());
        list.forEach(entry -> sortMap.put(entry.getKey(), entry.getValue()));
        return sortMap;
    }

    static class MapValueComparator implements Comparator<Map.Entry<Character, Integer>> {
        @Override
        public int compare(Map.Entry<Character, Integer> o1, Map.Entry<Character, Integer> o2) {
            return o2.getValue() - o1.getValue();
        }
    }
}
