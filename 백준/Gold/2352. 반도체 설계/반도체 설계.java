import java.util.*;
import java.io.*;
import java.math.*;

public class Main {

    public static int N;
    public static int[] ports;

    public static int[] table;

    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(reader.readLine()); // 포트 개수
        ports = new int[N];

        // ports 배열 입력받기
        StringTokenizer tkn = new StringTokenizer(reader.readLine());
        int seq=0;
        int n;
        while (tkn.hasMoreTokens()) {
            n = Integer.parseInt(tkn.nextToken());
            ports[seq++] = n;
        }

        table = new int[N];
        Arrays.fill(table, 0);
        int tail = 0;

        // 첫 요소 초기화
        table[0] = ports[0];
        tail++;

        int cur;
        for (int i=1; i<N; i++) {
            cur = ports[i];

            // 1. 배열 요소가 dp 테이블의 최솟값 (0번 요소)와 작으면, 0번 요소를 교체한다.
            if (cur < table[0]) {
                table[0] = cur;
            }
            // 2. 현재 요소가 dp 테이블의 최댓값 (tail이 가리키는 요소)보다 크면, 해당 요오슬 dp 테이블의 끝에 추가한다.
            else if (cur > table[tail-1]) {
                table[tail] = cur;
                tail++;
            }
            // 3. 배열 요소가 dp 테이블의 최소, 최댓값의 중간 크기라면, binarySearch를 통해 들어갈 인덱스를 찾아 교체한다.
            else {
                int res = binarySearch(cur, 0, tail-1);
                int idx = -(res+1); // cur가 들어갈 인덱스 (이진 탐색을 통해 인덱스를 찾음)
                table[idx] = cur;
            }

        }

        int result = tail;
        System.out.println(result);
    }

    // 이진 탐색 - 찾아서 인덱스를 반환
    public static int binarySearch(int n, int start, int end) {
        int low = start;
        int high = end;

        int mid;
        int midVal;
        while (low <= high) {
            mid = (low + high) / 2;
            midVal = table[mid];
            if (midVal < n) {
                low = mid+1;
            }
            else if (midVal > n) {
                high = mid-1;
            }
            else{
                return mid;
            }
        }

        return -(low + 1);
    }
  
}
