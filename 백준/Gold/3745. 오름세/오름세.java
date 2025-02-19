import java.util.*;
import java.io.*;
import java.math.*;

public class Main {

    public static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    public static StringTokenizer tkn;
    public static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws Exception {

        while (true) {
            String line = reader.readLine();
            if (line == null || line.isBlank()) break;

            tkn = new StringTokenizer(line);
            int N = Integer.parseInt(tkn.nextToken());

            calc(N);

        }

        System.out.println(sb);
    }

    public static void calc(int n) throws Exception {
        // 주가 변동 추이 입력받기
        tkn = new StringTokenizer(reader.readLine());

        // 저장해놓고 꺼내 쓸 리스트 선언
        List<Integer> arr = new ArrayList<>();
        for(int i=0; i<n; i++) {
            arr.add(Integer.parseInt(tkn.nextToken()));
        }

        // 길이를 잴 table 배열 선언
        int[] table = new int[n];
        Arrays.fill(table, 0);
        table[0] = 100001; // 처음에는 무조건 숫자 하나 들어가도록 최댓값+1로 초기화.
        int cur = 1; // 다음 수가 들어가야 할 인덱스를 지정.

        for (int cost: arr) {
            // 1. cost가 배열의 최솟값보다 작을 경우
            if (cost < table[0])
                table[0] = cost;

            // 2. cost가 배열의 최댓값보다 클 경우
            else if (cost > table[cur-1]) {
                table[cur] = cost;
                cur++;
            }

            // 3. cost가 배열의 최소 ~ 최대값 사이의 크기일 경우
            // cost를 cost보다 큰 원소가 가장 처음 나오는 인덱스에 갈아끼워버린다.
            else {
                int bs = binarySearch(table, cost, cur);
                int idx = (bs < 0) ? bs*-1 : bs;
                table[idx] = cost;
            }
        }

        sb.append(cur).append("\n");
    }

    public static int binarySearch(int[] arr, int val, int length) {
        int left = 0;
        int right = length-1;

        while (left <= right) {
            int mid = (left + right) / 2;
            int midVal = arr[mid];

            if (midVal < val)
                left = mid+1;
            else if (midVal > val)
                right = mid-1;
            else
                return mid;
        }
        // 여기에 왔으면 값을 못 찾은거임.
        // 여기에서의 left는 찾는 값(val)보다 큰 값이 가장 처음 나오는 인덱스임.
        return (-left);
    }

}
