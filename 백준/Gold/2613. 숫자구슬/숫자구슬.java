import java.util.*;
import java.io.*;
import java.math.*;

public class Main {

    public static int N, M;
    public static int[] balls;

    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer tkn = new StringTokenizer(reader.readLine());

        N = Integer.parseInt(tkn.nextToken()); // 구슬 개수 (1 ~ 300)
        M = Integer.parseInt(tkn.nextToken()); // 그룹 수 (1 ~ N)

        balls = new int[N];
        int max_ball = 0;

        // 꿰어진 구슬 입력받기
        int tmp;
        int total = 0;
        tkn = new StringTokenizer(reader.readLine());
        for(int i=0; i<N; i++) {
            tmp = Integer.parseInt(tkn.nextToken());
            balls[i] = tmp;
            total += tmp;
            if (tmp > max_ball)
                max_ball = tmp;
        }

        binarySearch(max_ball, total);
    }

    // 첫 요소부터 차례대로 붙여서 기준점을 넘지 않을 때까지 이어붙이기
    public static void binarySearch(int start, int end) {
        int left = start;
        int right = end;

        int result = end;

        // 기준점 -> 각 그룹의 합의 최댓값의 기준
        int mid;

        while (left <= right) {
            mid = (left + right) / 2; // 기준점

            int groups = makeGroup(mid);

            // 만들 수 있는 그룹이 M보다 크다 -> 기준점을 높여야 한다.
            if (groups > M) {
                left = mid+1;
            }
            // 만들 수 있는 그룹이 M보다 작거나 같나 -> 조건을 만족한다. 다만, 기준점을 더 낮춰도 조건을 만족할 가능성이 있다.
            else {
                result = mid;
                right = mid-1;
            }

        }

        // 이제 구한 최댓값을 기준으로 구슬을 나누어 그룹을 만들어야 한다.
        List<Integer> ballCounts = new ArrayList<>();
        int group_sum = 0;
        int ball_count = 0;
        int group_count = 0;
        for (int i=0; i<N; i++) {

            if (group_sum + balls[i] > result) {
                ballCounts.add(ball_count);
                group_count++;

                group_sum = balls[i];
                ball_count = 1;
            }
            else {
                group_sum += balls[i];
                ball_count++;
            }

            // 남은 구슬 수와 남은 그룹 수가 같다면, 현재까지의 구슬들을 그룹으로 묶고,
            // 나머지 구슬들을 1개씩 그룹에 추가 후 반복문 종료
            if ((N-i-1) == (M-group_count-1)) {
                ballCounts.add(ball_count);
                group_count++;

                for(int k=i+1; k<N; k++)
                    ballCounts.add(1);

                break;
            }

        }

        if (ballCounts.size() < M)
            ballCounts.add(ball_count);

        StringBuilder sb = new StringBuilder();
        sb.append(result).append("\n");

        for(int e: ballCounts) {
            sb.append(e).append(" ");
        }

        System.out.println(sb);

    }

    // 그룹 만들기 (val 값을 기준으로)
    // M개의 그룹을 만들 수 있는지 확인용
    public static int makeGroup(int val) {
        // 모든 구슬을 돌며 그룹을 만든다.
        int group_count = 0;
        int group_sum = 0;

        for (int i=0; i<N; i++) {
            if (group_sum + balls[i] > val) {
                group_count++;
                group_sum = balls[i];
            }
            else {
                group_sum += balls[i];
            }
        }

        // 남은 구슬들의 합을 계산하고 그룹에 추가
        if (group_sum <= val)
            group_count++;

        return group_count;
    }

}
