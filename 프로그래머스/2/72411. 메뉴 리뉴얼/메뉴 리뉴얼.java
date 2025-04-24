import java.util.*;

class Solution {
    
    static Map<String, Integer> counts = new HashMap<>();
    
    static void comb(String prefix, String postfix, int length) {
        // 목표한 길이에 도달하면 map에 카운트
        if (prefix.length() == length) {
            if (!counts.containsKey(prefix)) counts.put(prefix, 1);
            else counts.put(prefix, counts.get(prefix)+1);
        }
        else {
            for(int i=0; i<postfix.length(); i++) {
                comb(prefix + postfix.charAt(i), postfix.substring(i+1), length);
            }
        }
    }
    
    static String sortStr(String s) {
        char[] buf = s.toCharArray();
        Arrays.sort(buf);
        return new String(buf);
    }
    
    public String[] solution(String[] orders, int[] course) {
        // 1. 각 order를 사전 순으로 정렬한다.
        List<String> orderL = new ArrayList<>();
        for(String s: orders)
            orderL.add(sortStr(s));
        
        // 2. 각 order를 통해 만들 수 있는 조합을 문자열로 표현하고, 그 빈도를 Map에 저장한다. (static Map)
        List<String> ans = new ArrayList<>(); // 답
        
        List<String> ranking; // 문자열로 표현된 각 조합을 빈도 순으로 정렬할 리스트
        for(int length: course) {
            
            // 각 order에 대해 length 길이로가능한 조합들을 map에 저장
            for(String o: orderL) {
                if (o.length() < length) continue; // 길이가 짧은 것은 건너 뜀
                comb("", o, length);
            }
            
            // counts 값 순으로 정렬
            ranking = new ArrayList<>(counts.keySet());
            ranking.sort(new Comparator<String>() {
                @Override
                public int compare(String s1, String s2) {
                    int n1 = counts.get(s1);
                    int n2 = counts.get(s2);
                    return Integer.compare(n2, n1); // 내림차순
                }
            });
            
            if (ranking.size() == 0) continue; // 해당 length에 해당하는 조합이 아예 없을 수도 있음
            int top = counts.get(ranking.get(0));
            if (top < 2) continue; // 2번 이상 나온 조합에 대해서만 추가
            for(String s: ranking) {
                if (counts.get(s) == top) ans.add(s);
                else break;
            }
            
            counts.clear(); // 다음 length에 대해 카운트하기 위해 map 초기화
        }
        
        String[] answer = new String[ans.size()];
        int seq = 0;
        for(String s: ans)
            answer[seq++] = s;
        
        Arrays.sort(answer);
        return answer;
    }
}