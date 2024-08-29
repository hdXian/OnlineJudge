import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    public static List<List<Integer>> graph = new ArrayList<>();
    public static String res_dfs = "";
    public static String res_bfs = "";

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String[] s = reader.readLine().split(" ");

        int n = Integer.parseInt(s[0]); // 노드 개수
        int m = Integer.parseInt(s[1]); // 간선 개수
        int v = Integer.parseInt(s[2]); // 시작 정점

        for (int i = 0; i < n+1; i++)
            graph.add(new ArrayList<Integer>());

        for (int i = 0; i < m; i++) {
            String[] split = reader.readLine().split(" ");
            int src = Integer.parseInt(split[0]);
            int dst = Integer.parseInt(split[1]);
            graph.get(src).add(dst);
            graph.get(dst).add(src);
        }

        for (int i = 0; i < n+1; i++)
            Collections.sort(graph.get(i));
//        System.out.println("graph = " + graph);

        boolean[] visited = new boolean[n+1];
        dfs(visited, v);

        boolean[] visited2 = new boolean[n+1];
        bfs(visited2, v);

        System.out.println(res_dfs);
        System.out.println(res_bfs);

    }

    public static void dfs(boolean[] visited, int v) {
//        System.out.println("v = " + v);
        res_dfs += String.format("%d ", v);
        visited[v] = true;
        for (Integer i : graph.get(v)) {
            if (!visited[i])
                dfs(visited, i);
        }
    }

    public static void bfs(boolean[] visited, int v) {
        List<Integer> queue = new ArrayList<>();
//        System.out.println("v = " + v);
        res_bfs += String.format("%d ", v);
        queue.add(v);
        visited[v] = true;
        while (!queue.isEmpty()) {
            Integer i = queue.get(0);
            for (Integer node : graph.get(i)) {
                if (!visited[node]) {
//                    System.out.println("integer = " + integer);
                    res_bfs += String.format("%d ", node);
                    visited[node] = true;
                    queue.add(node);
                }
            }
            queue.remove(0);
        }

    }

}
