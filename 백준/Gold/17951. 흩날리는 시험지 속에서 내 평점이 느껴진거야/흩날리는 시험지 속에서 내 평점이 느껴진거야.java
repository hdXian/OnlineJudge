import java.util.*;
import java.io.*;
import java.math.*;

public class Main {

    public static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static int N, K;
    public static int[] papers;

    public static void main(String[] args) throws Exception {
        init();

        int min_val = 21;
        int total = 0;

        for(int i=0; i<N; i++) {
            if (papers[i] < min_val)
                min_val = papers[i];
            total += papers[i];
        }

        int result = calc(min_val, total);
        System.out.println(result);
    }

    public static int calc(int start, int end) {
        int left = start;
        int right = end;

        int result = end;
        int mid;
        while (left <= right) {
            mid = (left + right) / 2;

            int group_count = makeGroup(mid); // mid값 기준으로 그룹을 나눈다.

            // 만들어진 그룹이 K보다 적거나 같을 경우 -> 그룹을 늘려야 한다 -> 기준값을 줄여야 한다.
            if (group_count < K) {
                right = mid-1;
            }
            // 만들어진 그룹이 K보다 많을 경우 -> 그룹을 줄여야 한다 -> 기준값을 늘려야 한다.
            else {
                result = mid;
                left = mid+1;
            }

        }

        return result;
    }

    // 맞힌 문제들의 합 중 최솟값을 val이라 가정한다.
    // 그럼 val은 최솟값이란 의미. 이걸 기준으로 그룹을 생성하고, 조건을 만족하는지에 따라 val값을 조정해나가야 한다.
    public static int makeGroup(int val) {
        int group_count = 0;
        int sum = 0;

        // 그룹 나누기
        for(int i=0; i<N; i++) {

            // 기준값을 처음 넘을 때까지 그룹에 추가한다.
            if (sum >= val) {
                group_count++;
                sum = 0;
            }
            sum += papers[i];

        }

        // 남은 문제들을 모아 마지막 그룹으로 지정한다.
        if (sum >= val)
            group_count++;

        return group_count;
    }

    public static void init() throws Exception {
        StringTokenizer tkn = new StringTokenizer(reader.readLine());

        N = Integer.parseInt(tkn.nextToken()); // 문제 수
        K = Integer.parseInt(tkn.nextToken()); // 그룹 수
        papers = new int[N];

        tkn = new StringTokenizer(reader.readLine());
        for(int i=0; i<N; i++) {
            papers[i] = Integer.parseInt(tkn.nextToken());
        }

    }

}
