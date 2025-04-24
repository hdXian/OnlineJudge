import java.util.*;

class Solution {
    
    static Map<String, Integer> people = new HashMap<String, Integer>();
    
    public String solution(String[] participant, String[] completion) {
        
        // 1. 참가자들을 이름-참가자 수로 명단을 매핑한다.
        for(String p: participant) {
            if (!people.containsKey(p))
                people.put(p, 1);
            else
                people.put(p, people.get(p)+1);
        }
        
        // 2. 완주자들에 대해 명단에서 카운트를 줄인다.
        String answer = "";
        for(String c: completion) {
            int tmp = people.get(c);
            if (tmp == 1) people.remove(c);
            else people.put(c, tmp-1);
        }
        
        List<String> remains = new ArrayList<>(people.keySet());
        
        return remains.get(0);
    }
}