import java.util.*;

class Solution {
    public int solution(int[] nums) {
        Set<Integer> mm = new HashSet<>();
        for(int n: nums) mm.add(n);
        
        return Math.min(mm.size(), nums.length/2);
    }
}