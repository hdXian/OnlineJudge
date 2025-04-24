import java.util.*;

class Solution {
    public boolean solution(String[] phone_book) {
        // 전화번호 목록을 문자열 사전 순으로 정렬한다.
        // 어떤 번호가 다른 번호의 접두어라면, 정렬했을 때 바로 앞 순서에 위치할 것.
        // ex) 119, 97674223, 1195524421 -> 119, 1195524421, 97674223
        Arrays.sort(phone_book, Comparator.naturalOrder());
        String cur, next;
        for(int i=0; i<phone_book.length-1; i++) {
            cur = phone_book[i];
            next = phone_book[i+1];
            if (cur.length() > next.length()) continue; // 앞 문자열 길이가 더 길면 아래 코드 에러남
            if (cur.equals(next.substring(0, cur.length()))) {
                return false;
            }
        }
        
        return true;
    }
}