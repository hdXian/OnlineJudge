import java.io.*;
import java.util.*;
import java.math.*;

public class Main {

    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    static int N; // 수열 길이 (1 ~ 10만)
    static int[] arr; // 수열

    static void init() throws Exception {
        N = Integer.parseInt(reader.readLine());
        arr = new int[N];

        StringTokenizer tkn = new StringTokenizer(reader.readLine());
        for(int i=0; i<N; i++) {
            arr[i] = Integer.parseInt(tkn.nextToken());
        }
    }

    static int calc() throws Exception {
        int asc_max = 1;
        int desc_max = 1;

        int asc_count = 1;
        int desc_count = 1;
        for (int i=1; i<N; i++) {
            // 계속 증가하거나 감소할 때까지 카운트한다.
            // 연속된 수열이 깨지면 이전 카운트를 비교해 저장하고, 처음부터 다시 카운트한다.
            // 같으면 증가수열, 감소수열 모두 카운트
            if (arr[i] == arr[i-1]) {
                asc_count++;
                desc_count++;
            }
            // 증가했다면 -> 증가수열 카운트, 감소수열 초기화
            else if (arr[i] > arr[i-1]) {
                asc_count++;
                desc_max = Math.max(desc_max, desc_count);
                desc_count = 1;
            }
            // 감소했다면 -> 감소수열 카운트, 증가수열 초기화
            else {
                desc_count++;
                asc_max = Math.max(asc_max, asc_count);
                asc_count = 1;
            }

        }

        asc_max = Math.max(asc_max, asc_count);
        desc_max = Math.max(desc_max, desc_count);

        return Math.max(asc_max, desc_max);
    }

    public static void main(String[] args) throws Exception {
        init();
        int result = calc();
        System.out.println(result);
    }

}
