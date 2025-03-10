import java.util.*;
import java.io.*;
import java.math.*;

public class Main {

    public static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    public static int N;
    public static int[][] forest;
    public static int[] dr = {-1, 1, 0, 0};
    public static int[] dc = {0, 0, -1, 1};

    public static int result;
    public static int[][] table;

    public static void init() throws Exception {
        N = Integer.parseInt(reader.readLine());

        forest = new int[N][N];
        StringTokenizer tkn;
        for (int i=0; i<N; i++) {
            tkn = new StringTokenizer(reader.readLine());

            for(int j=0; j<N; j++) {
                forest[i][j] = Integer.parseInt(tkn.nextToken());
            }

        }

        table = new int[N][N];
        for(int i=0; i<N; i++)
            Arrays.fill(table[i], -1);

        result = -1;
    }

    public static void main(String[] args) throws Exception {
        init();
        for(int r=0; r<N; r++) {
            for(int c=0; c<N; c++) {
                dfs(r, c);
            }
        }

        for(int r=0; r<N; r++)
            for(int c=0; c<N; c++)
                result = Math.max(result, table[r][c]);

        System.out.println(result);

    }

    public static int dfs(int row, int col) {
        if (table[row][col] != -1) {
            return table[row][col];
        }

        // 시작 점을 정하고, 상하좌우로 이동한다.
        table[row][col] = 1;

        // 상, 하, 좌, 우 좌표 이동
        for(int n=0; n<4; n++) {
            // 움직일 다음 칸의 좌표
            int nr = row + dr[n];
            int nc = col + dc[n];

            if ((nr >= 0) && (nc >= 0) && (nr <= N-1) && (nc <= N-1) && (forest[nr][nc] > forest[row][col])) {
                table[row][col] = Math.max(table[row][col], dfs(nr, nc)+1);
            }

        }

        return table[row][col];
    }

}
