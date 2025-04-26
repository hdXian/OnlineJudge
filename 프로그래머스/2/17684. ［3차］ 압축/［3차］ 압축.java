import java.util.*;

class Solution {
    public int[] solution(String msg) {
        
        Map<String, Integer> dict = new HashMap<>();
        // 1. 길이가 1인 모든 단어 (알파벳)을 포함하도록 사전을 초기화한다.
        char ch = 'A';
        for(int i=0; i<26; i++) {
            dict.put(String.valueOf(ch), i+1);
            ch++;
        }
        
        int mlength = msg.length();
        
        List<Integer> ans = new ArrayList<>();
        String remain = new String(msg);
        int cur = 0;
        int seq = 27;
        while(true) {
            
            // 2. 사전에 존재하는 가장 긴 문자열을 찾는다.
            int n_idx = cur+1;
            while(n_idx <= mlength && dict.containsKey(remain.substring(cur, n_idx))) {
                n_idx++;
            }
            
            // 3. w에 해당하는 인덱스를 출력하고, 입력에서 w를 제거한다.
            String w;
            // w가 한 글자라면 그대로 자르기
            if(n_idx == cur+1)
                w = remain.substring(cur, n_idx);
            // w가 한 글자 이상이라면 한 글자 빼고 자르기 (n_idx는 가장 긴 문자열+1 만큼 가 있음)
            else
                w = remain.substring(cur, --n_idx);
            
            // w에 해당하는 인덱스 출력
            ans.add(dict.get(w));
            
            // 입력에서 w 제거 (포인터 이동)
            cur = n_idx;
            
            // 포인트가 끝까지 이동했다면 멈춤
            if (cur >= mlength) break;
            
            // 뒤에 글자가 남은 경우 (포인터가 끝까지 이동하지 않았다면) w+c를 사전에 등록
            dict.put(w + remain.charAt(cur), seq++);
        }
        
        int[] answer = new int[ans.size()];
        int idx = 0;
        for(int a: ans) answer[idx++] = a;
        return answer;
    }
}