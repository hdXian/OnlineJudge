import java.util.*;
import java.io.*;
import java.math.*;

public class Main {

    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static int N;
    static int[] t_arr;
    static int[] p_arr;
    static int result;

    static void init() throws Exception {
        N = Integer.parseInt(reader.readLine());
        t_arr = new int[N];
        p_arr = new int[N];

        StringTokenizer tkn;
        for(int i=0; i<N; i++) {
            tkn = new StringTokenizer(reader.readLine());
            t_arr[i] = Integer.parseInt(tkn.nextToken());
            p_arr[i] = Integer.parseInt(tkn.nextToken());
        }

        result = 0;
    }

    static void dfs(int day, int total) {
        if (day >= N) {
            result = Math.max(result, total);
        }
        else {
            // 현재 날짜를 선택하는 경우
            if (day + t_arr[day] < N+1)
                dfs(day + t_arr[day], total+p_arr[day]);

            // 현재 날짜를 선택하지 않는 경우
            dfs(day+1, total);
        }
    }

    public static void main(String[] args) throws Exception {
        init();
        dfs(0, 0);
        System.out.println(result);
    }

}
