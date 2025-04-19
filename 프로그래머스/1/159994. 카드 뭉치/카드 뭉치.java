import java.util.*;

class Solution {
    public String solution(String[] cards1, String[] cards2, String[] goal) {
        Queue<String> c1 = new LinkedList<>();
        for(String s: cards1) c1.add(s);
        Queue<String> c2 = new LinkedList<>();
        for(String s: cards2) c2.add(s);
        
        String answer = "Yes";
        for(String s: goal) {
            if (!c1.isEmpty() && c1.peek().equals(s)) c1.poll();
            else if (!c2.isEmpty() && c2.peek().equals(s)) c2.poll();
            else {
                answer = "No";
                break;
            }
        }
        
        return answer;
    }
}