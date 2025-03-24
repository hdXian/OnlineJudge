import java.util.*;
import java.io.*;
import java.math.*;

public class Main {
    
    public static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static int N, M; // 추 개수, 구슬 개수

    public static int[] rims; // 무게추 배열
    public static int[] balls; // 구슬 배열

    public static Set<Integer> ableSet = new HashSet<>(); // 현재까지 가능한 무게들을 저장하는 집합

    public static void init() throws Exception {
        N = Integer.parseInt(reader.readLine()); // 무게추 개수 (1~30), 각 추의 무게 1~500
        rims = new int[N];

        StringTokenizer tkn;
        tkn = new StringTokenizer(reader.readLine());
        for(int i=0; i<N; i++)
            rims[i] = Integer.parseInt(tkn.nextToken());

        M = Integer.parseInt(reader.readLine()); // 대상 구슬 개수
        balls = new int[M];

        tkn = new StringTokenizer(reader.readLine());
        for(int i=0; i<M; i++)
            balls[i] = Integer.parseInt(tkn.nextToken());

    }

    public static void calc() throws Exception {
        // 1. 무게추들을 가져와 가능한 무게들의 집합을 생성한다.
        Set<Integer> tmp = new HashSet<>();
        for (int rim: rims) {
            tmp.add(rim);

            for (int n: ableSet) {
                tmp.add(n + rim);
                tmp.add(Math.abs(n-rim));
            }

            ableSet.addAll(tmp);
            tmp.clear();
        }

        // 2. 그 구슬들이 집합에 속하는지 확인한다.
        StringBuilder sb = new StringBuilder();

        for(int b:balls) {
            if (ableSet.contains(b))
                sb.append("Y ");
            else
                sb.append("N ");
        }

        System.out.println(sb);

    }

    public static void main(String[] args) throws Exception {
        init();
        calc();
    }
    
}
