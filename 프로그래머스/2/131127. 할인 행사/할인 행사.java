import java.util.*;

class Solution {
    
    static Map<String, Integer> dm = new HashMap<>();
    
    public int solution(String[] want, int[] number, String[] discount) {
        
        // 1. 할인하는 물건 목록의 Map을 생성하고, 첫날~10일치를 저장한다.
        String cur;
        for(int i=0; i<9; i++) {
            cur = discount[i];
            if (!dm.containsKey(cur))
                dm.put(cur, 1);
            else
                dm.put(cur, dm.get(cur)+1);
        }
        
        // 2. 날짜가 바뀜에 따라 할인 목록을 갱신하고, 원하는 물건 목록과 비교한다.
        int count = 0;
        for(int i=9; i<discount.length; i++) {
            count++;
            cur = discount[i];
            
            if (!dm.containsKey(cur)) dm.put(cur, 1);
            else dm.put(cur, dm.get(cur)+1);
            
            for(int k=0; k<want.length; k++) {
                if (!dm.containsKey(want[k]) || (dm.get(want[k]) != number[k])) {
                    count--;
                    break;
                }
            }
            
            // 현재로부터 9일 전 상품을 뺀다. (다음 순회에서의 10일 전 상품에 해당)
            dm.put(discount[i-9], dm.get(discount[i-9])-1);
        }

        int answer = count;
        return answer;
    }
}