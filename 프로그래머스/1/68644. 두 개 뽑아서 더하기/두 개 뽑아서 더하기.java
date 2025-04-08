import java.util.*;

class Solution {
    static Set<Integer> set = new HashSet<>();
    static PriorityQueue<Integer> pq = new PriorityQueue<>();
    static int length;
    
    public int[] solution(int[] numbers) {
        length = numbers.length;
        
        for(int i=0; i<length; i++) {
            for(int j=i+1; j<length; j++) {
                set.add(numbers[i] + numbers[j]);
            }
        }
        
        pq.addAll(set);
        
        int size = pq.size();
        
        int[] answer = new int[size];
        for(int i=0; i<size; i++)
            answer[i] = pq.poll();
        
        return answer;
    }
}