import java.io.*;
import java.util.*;
import java.math.*;

public class Main {

    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    static int N, M;
    static Set<String> comb = new HashSet<>();

    static void init() throws Exception {
        StringTokenizer tkn = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(tkn.nextToken());
        M = Integer.parseInt(tkn.nextToken());
    }

    static void dfs(int pre_i, List<Integer> choice) {
        if (choice.size() == M) {
            StringBuilder sb = new StringBuilder();
            for(int n: choice) sb.append(n).append(' ');
            comb.add(sb.toString());
        }
        else {
            for(int i=pre_i; i<=N; i++) {
                List<Integer> nc = new ArrayList<>(choice);
                nc.add(i);
                dfs(i+1, nc);
            }
        }

    }

    static String calc() {
        dfs(1, new ArrayList<>());
        List<String> resultList = new ArrayList<>(comb);
        resultList.sort(Comparator.naturalOrder());

        StringBuilder sb = new StringBuilder();
        for(String s: resultList) sb.append(s).append('\n');
        return sb.toString();
    }

    public static void main(String[] args) throws Exception {
        init();
        String result = calc();
        System.out.println(result);
    }

}
