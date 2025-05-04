import java.util.*;
import java.io.*;
import java.math.*;

public class Main {

    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static int N, M;

    static int[] dr = {-1, 1, 0, 0};
    static int[] dc = {0, 0, -1, 1};
    static int[][] board;
    static int[][] counts;
    static int totalCheese;

    static void init() throws Exception {
        StringTokenizer tkn = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(tkn.nextToken()); // 행 수 (1 ~ 100)
        M = Integer.parseInt(tkn.nextToken()); // 열 수 (1 ~ 100)

        totalCheese = 0;
        board = new int[N][M];
        for(int i=0; i<N; i++) {
            tkn = new StringTokenizer(reader.readLine());
            for(int j=0; j<M; j++) {
                if (tkn.nextToken().equals("1")) {
                    board[i][j] = 1;
                    totalCheese++;
                }
                else board[i][j] = 0;
            }
        }

        counts = new int[N][M];
    }

    static void clearCount() {
        for(int[] line: counts)
            Arrays.fill(line, 0);
    }

    // 상하좌우를 탐색하면서 닿는 면을 카운트
    static void bfs(int r, int c, boolean[][] visited) {
        visited[r][c] = true;
        int nr, nc;
        for(int i=0; i<4; i++) {
            nr = r + dr[i];
            nc = c + dc[i];
            if(nr >= 0 && nc >= 0 && nr < N && nc < M && !visited[nr][nc]) {
                if (board[nr][nc] == 1) counts[nr][nc]++;
                else bfs(nr, nc, visited);
            }
        }
    }

    static int removeCheese() {
        int removed = 0;
        for(int i=0; i<N; i++) {
            for(int j=0; j<M; j++) {
                if (counts[i][j] >= 2) {
                    board[i][j] = 0;
                    removed++;
                }
            }
        }
        return removed;
    }

    static void calc() {
        // 1. (0, 0)부터 시작해서 상하좌우로 bfs를 돌린다.
        // 2. 1인 칸을 만나면 카운트하고 종료한다.
        // 3. bfs가 끝난 뒤 카운트가 2 이상인 칸들을 제거한다.
        // 4. 1~3을 모든 칸이 제거될 때까지 반복한다.
        int hours = 0;
        int removedCheese = 0;
        while (removedCheese < totalCheese) {
            hours++;

            clearCount();

            boolean[][] visited = new boolean[N][M];
            for(boolean[] line: visited) Arrays.fill(line, false);

            // 1. (0, 0)부터 시작해서 상하좌우로 bfs를 돌린다.
            // 2. 1인 칸을 만나면 카운트하고 종료한다.
            bfs(0, 0, visited);

            // 3. bfs가 끝난 뒤 카운트가 2 이상인 칸들을 제거한다.
            removedCheese += removeCheese();
        }

        System.out.println(hours);
    }

    public static void main(String[] args) throws Exception {
        init();
        calc();
    }

}
