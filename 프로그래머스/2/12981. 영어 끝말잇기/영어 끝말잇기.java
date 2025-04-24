import java.util.*;

class Solution {
    
    static Set<String> s = new HashSet<>();
    
    public int[] solution(int n, String[] words) {
        s.add(words[0]);
        char pre = words[0].charAt(words[0].length()-1);
        
        int[] answer = new int[2];
        for(int i=1; i<words.length; i++) {
            if (words[i].charAt(0) != pre || s.contains(words[i])) {
                answer[0] = i%n + 1;
                answer[1] = i/n + 1;
                break;
            }
            s.add(words[i]);
            pre = words[i].charAt(words[i].length()-1);
        }

        return answer;
    }
}