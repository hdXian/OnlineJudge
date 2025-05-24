import java.io.*;
import java.util.*;
import java.math.*;

public class Main {

    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    static int N, M;
    static boolean[] visited;

    static Set<String> comb = new HashSet<>();

    static void init() throws Exception {
        StringTokenizer tkn = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(tkn.nextToken());
        M = Integer.parseInt(tkn.nextToken());
        visited = new boolean[N+1];
        Arrays.fill(visited, false);
    }

    static void dfs(List<Integer> choice) {
        if (choice.size() == M) {
            StringBuilder sb = new StringBuilder();
            for(int n: choice) sb.append(n).append(' ');
            comb.add(sb.toString());
        }
        else {
            for(int i=1; i<=N; i++) {
                if (!visited[i]) {
                    List<Integer> nc = new ArrayList<>(choice);
                    nc.add(i);
                    visited[i] = true;
                    dfs(nc);
                    visited[i] = false;
                }
            }
        }

    }

    static String calc() {
        dfs(new ArrayList<>());

        List<String> result = new ArrayList<>(comb);
        result.sort(Comparator.naturalOrder());

        StringBuilder sb = new StringBuilder();

        for(String s: result) sb.append(s).append('\n');
        return sb.toString();
    }

    public static void main(String[] args) throws Exception {
        init();
        String result = calc();
        System.out.println(result);
    }

}
