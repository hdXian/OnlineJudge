import java.util.*;
import java.io.*;
import java.math.*;

public class Main {

    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static int K;
    static int[] cases;

    static String[] min_dp;

    static String sortStr(String s) {
        char[] tmp = s.toCharArray();
        Arrays.sort(tmp);
        return new String(tmp);
    }

    static String moveZero(String s) {
        int seq = 0;

        while(seq < s.length() && s.charAt(seq) == '0') seq++;

        String zeros;
        char head;

        if(seq == s.length()) {
            zeros = s.substring(0, seq-1);
            return '6' + zeros;
        }
        else {
            head = s.charAt(seq);
            zeros = s.substring(0, seq);
            return head + zeros + s.substring(seq+1);
        }

    }

    static void init() throws Exception {
        K = Integer.parseInt(reader.readLine()); // 테스트케이스 개수 (1 ~ 100)

        cases = new int[K];
        for(int i=0; i<K; i++)
            cases[i] = Integer.parseInt(reader.readLine()); // 성냥개비 개수 (2 ~ 100)

        min_dp = new String[101]; // 인덱스 1부터 쓰기
        char[] tmp = "174208".toCharArray(); // {"1", "7", "4", "2", "0", "8"}
        for(int i=2; i<=7; i++) {
            min_dp[i] = String.valueOf(tmp[i-2]);
        }

        min_dp[8] = "10";
        // 9번 인덱스부터 계산해서 집어넣기
        String cur, sorted;
        for(int i=9; i<=100; i++) {
            // (현재 인덱스) - (7 ~ 2) 문자열을 붙여 만든다.
            // 7과의 조합을 먼저 계산
            cur = sortStr(min_dp[7] + min_dp[i-7]);
            if (cur.startsWith("0")) cur = moveZero(cur);

            // 6부터 조합 비교
            for(int k=6; k>=1; k--) {
                // 사전 순으로 정렬한다.
                if (k == 1) // 6개 성냥으로 6을 만드는 경우의 수도 계산 (k 숫자와 무관, 그냥 하나 더 체크)
                    sorted = sortStr("6" + min_dp[i-6]);
                else
                    sorted = sortStr(min_dp[k] + min_dp[i-k]);

                // 길이가 길다면 (자릿수가 더 많다면) 건너뜀
                if (sorted.length() > cur.length()) continue;

                // 앞에 0이 있다면 뒤로 밀어낸다.
                if (sorted.startsWith("0"))
                    sorted = moveZero(sorted);

                // 자릿수가 더 적으면 바로 업데이트하고 다음으로 넘어감.
                if (sorted.length() < cur.length()) {
                    cur = sorted;
                    continue;
                }

                // 비교해서 저장한다.
                cur = cur.compareTo(sorted) > 0 ? sorted : cur;
            }

            // 6 부분에 0대신 6을 넣는 경우도 고려해야 함.

            min_dp[i] = cur;
        }

        min_dp[6] = "6"; // 6번 인덱스 값을 6으로 수정한다. (0으로 시작할 수 없으므로)
    }

    static String calc(int n) {
        StringBuilder sb = new StringBuilder();

        // 1. 가장 작은 수 구하기
        sb.append(min_dp[n]).append(" ");

        // 2. 가장 큰 수 구하기 (오름차순 그리디)
        // 사실 2, 3개 성냥으로 다 해결될 것 같음.
        int ones = n / 2;
        int threes = 0;
        if (n%2 == 1) {
            ones--;
            threes++;
        }
        for(int i=0; i<threes; i++) sb.append("7");
        for(int i=0; i<ones; i++) sb.append("1");

        return sb.toString();
    }

    public static void main(String[] args) throws Exception {
        init();
        StringBuilder result = new StringBuilder();
        for(int c: cases) {
            result.append(calc(c)).append("\n");
        }
        System.out.println(result);
    }

}
