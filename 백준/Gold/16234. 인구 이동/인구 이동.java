import java.util.*;
import java.math.*;
import java.io.*;

public class Main {

    static BufferedReader reader;

    static int N, L, R;
    static int[][] land;
    static int[] dr = {-1, 1, 0, 0};
    static int[] dc = {0, 0, -1, 1};

    static class Pair {
        public int row;
        public int col;
        public Pair(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }

    static void init() throws Exception {
        StringTokenizer tkn = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(tkn.nextToken()); // 땅 한변 크기 1 ~ 50
        L = Integer.parseInt(tkn.nextToken()); // 1 ~ 100
        R = Integer.parseInt(tkn.nextToken()); // 1 ~ 100

        land = new int[N][N];
        for(int i=0; i<N; i++) {
            tkn = new StringTokenizer(reader.readLine());
            for(int j=0; j<N; j++) {
                land[i][j] = Integer.parseInt(tkn.nextToken());
            }
        }

    }

    static void dfs(int unite_num, int row, int col, int[][] unite, boolean[][] visited) {
        visited[row][col] = true;
        unite[row][col] = unite_num;

        int nr, nc;
        for(int i=0; i<4; i++) {
            nr = row + dr[i];
            nc = col + dc[i];
            // 방문한 적 없고 유효한 곳이라면
            if (nr >= 0 && nc >= 0 && nr < N && nc <N && !visited[nr][nc]) {
                int diff = Math.abs(land[row][col] - land[nr][nc]);
                if (diff >= L && diff <= R) {
                    dfs(unite_num, nr, nc, unite, visited);
                }
            }
        }

    }

    static int calc() {
        // 1. 0, 0부터 시작해서 dfs로 연합에 속한 나라들을 찾는다.
        // 2. dfs를 들리며 연합에 속한 나라들을 서로 다른 번호로 설정해놓는다.
        // 2-1. 설정해놓는 번호는 연합 개수를 카운트하는 데 쓴다.
        // 3. 각 번호별로 속하는 국가를 찾고, 인구수를 나눈 값으로 업데이트한다.

        // 1. 0, 0부터 시작해서 dfs로 연합에 속한 나라들을 찾는다.
        // 2. dfs를 들리며 연합에 속한 나라들을 서로 다른 번호로 설정해놓는다.
        int move_count = 0;

        while(true) {

            boolean[][] visited = new boolean[N][N];
            int[][] unite = new int[N][N];
            for(boolean[] line: visited) Arrays.fill(line, false);
            for(int[] line: unite) Arrays.fill(line, -1);

            int unite_count = 0;
            for(int row=0; row<N; row++) {
                for(int col=0; col<N; col++) {
                    if (!visited[row][col]) {
                        dfs(unite_count, row, col, unite, visited);
                        unite_count++;
                    }
                }
            }

            if (unite_count == N*N) break; // 종료 조건

            // 2-1. 설정해놓는 번호는 연합 개수를 카운트하는 데 쓴다.
            // 3. 각 번호별로 속하는 국가를 찾고, 인구수를 나눈 값으로 업데이트한다.
            List<List<Pair>> uniteList = new ArrayList<>();
            int[] peopleCount = new int[unite_count];
            for(int i=0; i<unite_count; i++) {
                uniteList.add(new ArrayList<>());
                peopleCount[i] = 0;
            }

            // 3-1. 연합에 속한 국가의 row, col을 해당 연합 번호를 인덱스로 가진 List에 저장한다.
            for(int row=0; row<N; row++) {
                for(int col=0; col<N; col++) {
                    int unite_num = unite[row][col];
                    List<Pair> un = uniteList.get(unite_num);
                    un.add(new Pair(row, col));
                    peopleCount[unite_num] += land[row][col];
                }
            }

            // 3-2. 각 연합을 순회하면서 해당 연합에 속하는 국가들의 인구를 나눔
            for(int i=0; i<unite_count; i++) {
                List<Pair> un = uniteList.get(i);
                int people = peopleCount[i] / un.size();
                for(Pair p: un) land[p.row][p.col] = people;
            }

            move_count++;
        }

        return move_count;
    }

    public static void main(String[] args) throws Exception {
        reader = new BufferedReader(new InputStreamReader(System.in));
        init();
        int result = calc();
        System.out.println(result);
    }

}
