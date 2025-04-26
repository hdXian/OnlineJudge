import java.util.*;

class Solution {
    public int solution(String[][] clothes) {
        
        Map<String, Integer> mm = new HashMap<>();
        
        // 1. 각 의상 종류별 개수를 센다.
        String key;
        for(String[] cloth: clothes) {
            key = cloth[1];
            if (!mm.containsKey(key))
                mm.put(key, 1);
            else
                mm.put(key, mm.get(key)+1);
        }
        
        // 2. 모든 의상을 조합해 입는 경우의 수를 센다. (각 의상 수를 곱하면 된다.)
        // 단, 해당 의상 종류를 선택하지 않는 경우 하나를 합쳐 곱해준다.
        // ex) 하의 3벌, 상의 2벌을 조합해 입는 경우의 수 -> (3+1) * (2+1)
        int result = 1;
        for(String k: mm.keySet()) {
            result *= (mm.get(k)+1);
        }
        
        // 모든 종류의 의상을 선택하지 않는 경우 하나를 제외한 경우의 수를 리턴한다.
        int answer = result - 1;
        return answer;
    }
}