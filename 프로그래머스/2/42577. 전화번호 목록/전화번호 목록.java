import java.util.*;

class Solution {
    
    static Map<String, Integer> pfs = new HashMap<>();
    
    public boolean solution(String[] phone_book) {
        // 해시를 쓴다면
        
        // 각 전화번호를 전화번호-인덱스 형태로 Map에 저장한다.
        int length = phone_book.length;
        for(int i=0; i<length; i++) {
            pfs.put(phone_book[i], i);
        }
        
        // 각 전화번호에 대해 다른 전화번호의 접두어 여부를 확인한다.
        String cur;
        for(int i=0; i<length; i++) {
            cur = phone_book[i];
            for(int j=0; j<cur.length(); j++) {
                if (pfs.containsKey(cur.substring(0, j)))
                    return false;
            }
        }
        
        return true;
    }
}