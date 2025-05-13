import java.util.*;
import java.io.*;
import java.math.*;

public class Main {

    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    static int T; // 테스트케이스 개수. 1 ~ 50

    // comparator 연습
    static class StrComparator implements Comparator<String> {
        @Override
        public int compare(String s1, String s2) {
            return s1.compareTo(s2);
        }
    }

    static String calc(List<String> book) throws Exception {
        // 1. 전화번호들을 오름차순으로 정렬한다.
        // 2. 정렬한 상태에서 어떤 전화번호 A가 다른 전화번호의 접두어이려면,
        // A의 바로 다음에 오는 문자열이 A로 시작해야 한다.
        // 해당 여부를 체크해서 리턴한다.

        book.sort(new StrComparator());

        int length = book.size();

        String cur, next;
        for(int i=0; i<length-1; i++) {
            cur = book.get(i);
            next = book.get(i+1);
            if (next.startsWith(cur)) return "NO";
        }

        return "YES";
    }

    public static void main(String[] args) throws Exception {
        T = Integer.parseInt(reader.readLine()); // 테케 개수 (1 ~ 50)

        StringBuilder sb = new StringBuilder();

        int n;
        for(int i=0; i<T; i++) {
            n = Integer.parseInt(reader.readLine()); // 전화번호 수 (1 ~ 1만)
            List<String> book = new ArrayList<>();
            for(int k=0; k<n; k++) book.add(reader.readLine());
            sb.append(calc(book)).append('\n');
        }

        System.out.print(sb);
    }

}
