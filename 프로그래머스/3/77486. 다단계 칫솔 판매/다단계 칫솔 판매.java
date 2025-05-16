import java.util.*;

class Solution {
    
    static Map<String, String> parents = new HashMap<>(); // 이름 - 추천인
    static Map<String, Integer> incomes = new HashMap<>(); // 이름 - 수익금
    
    public int[] solution(String[] enroll, String[] referral, String[] seller, int[] amount) {
        // 1. 추천인 계층 구조를 만든다.
        int elength = enroll.length; // 1 ~ 1만
        for(int i=0; i<elength; i++) {
            parents.put(enroll[i], referral[i]);
            incomes.put(enroll[i], 0);
        }
            
        // 2. seller를 순회하면서 이익금을 계산한다.
        int slength = seller.length; // 1 ~ 10만
        String cur, parent;
        int income;
        int tenp;
        for(int i=0; i<slength; i++) {
            cur = seller[i];
            parent = parents.get(cur);
            income = amount[i] * 100;
            
            // 부모가 "-"이 될 때까지 수익금 배분
            while(!cur.equals("-") && income != 0) {
                // 10% 계산, 원단위 절삭
                tenp = (int) (income * 0.1);
                
                // 1원 미만이면 컷
                if (tenp < 1) tenp = 0;
                
                // 수익금 계산
                income -= tenp;
                incomes.put(cur, incomes.get(cur) + income);
                
                // 부모로 넘김
                cur = parent;
                parent = parents.get(cur);
                income = tenp;
            }
            
        }
        
        // 3. enroll에 명시된 순서대로 수익금을 배열에 담아 리턴한다.
        int[] answer = new int[elength];
        for(int i=0; i<elength; i++) {
            answer[i] = incomes.get(enroll[i]);
        }
        
        return answer;
    }
}