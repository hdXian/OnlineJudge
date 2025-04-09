import java.util.*;

class Solution {
    
    static int[] pattern1 = {1, 2, 3, 4, 5}; // 5
    static int[] pattern2 = {2, 1, 2, 3, 2, 4, 2, 5}; // 8
    static int[] pattern3 = {3, 3, 1, 1, 2, 2, 4, 4, 5, 5}; // 10
    
    public int[] solution(int[] answers) {
        int length = answers.length;
        
        int[] scores = {0, 0, 0};
        
        int val;
        for(int i=0; i<length; i++) {
            val = answers[i];
            
            if(val == pattern1[i%5]) scores[0]++;
            if(val == pattern2[i%8]) scores[1]++;
            if(val == pattern3[i%10]) scores[2]++;
        }
        
        int max_val = -1;
        for(int i=0; i<3; i++)
            max_val = (max_val < scores[i]) ? scores[i] : max_val;
        
        List<Integer> ans = new ArrayList<>();
        for(int i=0; i<3; i++)
            if (scores[i] == max_val) ans.add(i+1);
        
        
        int[] answer = new int[ans.size()];
        int seq=0;
        for(int i: ans)
            answer[seq++] = i;
        
        return answer;
    }
}