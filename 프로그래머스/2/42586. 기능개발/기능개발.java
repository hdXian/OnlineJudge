import java.util.*;

class Solution {
    public int[] solution(int[] progresses, int[] speeds) {
        // 1. 각 작업의 완료일을 계산한다.
        int length = progresses.length;
        int[] days = new int[length];
        for(int i=0; i<length; i++) {
            double div = (double)(100-progresses[i]) / speeds[i];
            days[i] = (int)Math.ceil(div);
        }
        
        // 2. 앞에서 pop하면서, pop한 작업일 수보다 적은 것들을 함께 pop한다.
        List<Integer> ans = new ArrayList<>();
        int count = 1;
        int max_days = days[0];
        for(int i=1; i<length; i++) {
            if (days[i] <= max_days) count++;
            else {
                ans.add(count);
                max_days = days[i];
                count = 1;
            }
        }
        ans.add(count); // 마지막 배포 추가
        
        length = ans.size();
        int[] answer = new int[length];
        int seq = 0;
        for(int i: ans)
            answer[seq++] = i;
        
        return answer;
    }
}