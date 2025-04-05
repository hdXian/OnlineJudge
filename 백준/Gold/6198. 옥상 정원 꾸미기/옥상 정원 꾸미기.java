import java.util.*;
import java.io.*;
import java.math.*;

public class Main {

    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    static int N;
    static int[] hs;
    static Stack<Integer> stack = new Stack<>();

    static void init() throws Exception {
        N = Integer.parseInt(reader.readLine()); // 빌딩 수. 1 ~ 8만
        hs = new int[N+1];

        // 빌딩 높이 입력. 1 ~ 10억
        for(int i=1; i<=N; i++) hs[i] = Integer.parseInt(reader.readLine());
    }

    static void calc() throws Exception {
        long result = 0;
        for (int h: hs) {
            while (!stack.isEmpty() && stack.peek() <= h)
                stack.pop();

            result += stack.size();

            stack.push(h);
        }

        System.out.println(result);
    }

    public static void main(String[] args) throws Exception {
        init();
        calc();
    }

}
