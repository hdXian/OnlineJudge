import java.util.*;

class Solution {
    
    static Map<String, String> nick = new HashMap<>();
    
    public String[] solution(String[] record) {
        
        // 1. uid - 닉네임 형태로 저장하는 Map을 생성한다.
        for(String s: record) {
            String[] args = s.split(" ");
            char head = args[0].charAt(0);
            if (head == 'E' || head == 'C') {
                nick.put(args[1], args[2]);
            }
        }
        
        // 2. record를 순회하면서 answer를 채운다.
        List<String> ans = new ArrayList<>();
        for(String s: record) {
            String[] args = s.split(" ");
            char head = args[0].charAt(0);
            if (head == 'E' || head == 'L') {
                String msg = nick.get(args[1]) + "님이 " + (head == 'E' ? "들어왔습니다." : "나갔습니다.");
                ans.add(msg);
            }
        }
        
        String[] answer = new String[ans.size()];
        int seq = 0;
        for(String s: ans)
            answer[seq++] = s;
        
        return answer;
    }
}