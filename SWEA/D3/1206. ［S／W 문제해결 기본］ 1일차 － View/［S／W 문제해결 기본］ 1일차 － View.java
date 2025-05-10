import java.util.Scanner;
import java.io.FileInputStream;
import java.io.*;
import java.util.*;
import java.math.*;

/*
   사용하는 클래스명이 Solution 이어야 하므로, 가급적 Solution.java 를 사용할 것을 권장합니다.
   이러한 상황에서도 동일하게 java Solution 명령으로 프로그램을 수행해볼 수 있습니다.
 */
class Solution {

    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String args[]) throws Exception {
        StringBuilder sb = new StringBuilder();
        int result;
        for(int test_case = 1; test_case <= 10; test_case++) {
            result = calc();
            sb.append(String.format("#%d %d\n", test_case, result));
        }

        System.out.println(sb);
    }


    static int calc() throws Exception {
        int N = Integer.parseInt(reader.readLine()); // 건물 개수 (4 ~ 1000)

        // 빌딩 배열
        int[] bs = new int[N];
        StringTokenizer tkn = new StringTokenizer(reader.readLine());
        for(int i=0; i<N; i++) bs[i] = Integer.parseInt(tkn.nextToken());

        // 좌우 2개에 존재하는 빌딩들 중 가장 높은 빌딩의 높이를 뺀다.
        int result = 0;
        int l_max, r_max, t_max;
        for(int i=2; i<N-2; i++) {
            l_max = Math.max(bs[i-1], bs[i-2]);
            r_max = Math.max(bs[i+1], bs[i+2]);
            t_max = Math.max(l_max, r_max);
            result += Math.max(0, bs[i] - t_max);
        }

        return result;
    }

}