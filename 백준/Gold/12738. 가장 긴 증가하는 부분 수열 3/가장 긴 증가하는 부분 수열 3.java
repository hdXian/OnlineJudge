import java.util.*;
import java.io.*;
import java.math.*;

public class Main {

    public static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static int N; // 1 ~ 100만
    public static int[] arr;
    public static int result;

    public static void init() throws Exception {
        N = Integer.parseInt(reader.readLine());

        arr = new int[N];

        StringTokenizer tkn = new StringTokenizer(reader.readLine());
        for(int i=0; i<N; i++) {
            arr[i] = Integer.parseInt(tkn.nextToken());
        }

    }

    public static void main(String[] args) throws Exception {
        init();
        result = calc();
        System.out.println(result);
    }

    public static int calc() {
        int[] table = new int[N];
        Arrays.fill(table, 0);
        table[0] = arr[0];
        int cur = 1;

        for(int i=1; i<N; i++) {

            if (arr[i] < table[0])
                table[0] = arr[i];
            else if (arr[i] > table[cur-1])
                table[cur++] = arr[i];
            else {
                int idx = binarySearch(table, 0, cur-1, arr[i]);
                if (idx < 0)
                    table[-idx] = arr[i];
            }

        }

        return cur;
    }

    public static int binarySearch(int[] table, int start, int end, int n) {
        int left = start;
        int right = end;

        int mid;
        int val;
        while (left <= right) {
            mid = (left + right) / 2;
            val = table[mid];

            if (n > val)
                left = mid+1;
            else if (n < val)
                right = mid-1;
            else
                return mid;
        }

        return -left;
    }


}
