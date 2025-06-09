import java.io.*;
import java.util.*;

public class Main {

    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    // 12시부터 시계방향
    static int[] dr = {-1, -1, 0, 1, 1, 1, 0, -1};
    static int[] dc = {0, 1, 1, 1, 0, -1, -1, -1};

    static int R, C;

    static int[][] board;
    static int[][] counts;
    static Point[][] table;

    static class Point {
        int row, col;
        public Point(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }

    static void init() throws Exception {
        StringTokenizer tkn = new StringTokenizer(reader.readLine());
        R = Integer.parseInt(tkn.nextToken());
        C = Integer.parseInt(tkn.nextToken());

        board = new int[R][C];
        counts = new int[R][C];
        table = new Point[R][C];

        for(int i=0; i<R; i++) {
            tkn = new StringTokenizer(reader.readLine());
            for(int j=0; j<C; j++) {
                board[i][j] = Integer.parseInt(tkn.nextToken());
                counts[i][j] = 0;
                table[i][j] = new Point(-1, -1);
            }
        }

    }

    static Point dfs(Point cur) {
        int r = cur.row;
        int c = cur.col;
        if (table[r][c].row != -1) return table[r][c];

        int min_r = r;
        int min_c = c;

        // 주변에서 가장 숫자가 작은 좌표를 탐색
        for(int i=0; i<8; i++) {
            int nr = r + dr[i];
            int nc = c + dc[i];
            if (nr >=0 && nc >=0 && nr < R && nc < C) {
                if (board[nr][nc] < board[min_r][min_c]) {
                    min_r = nr;
                    min_c = nc;
                }
            }
        }

        // 주변에 자신보다 작은 값이 존재하지 않으면 자기 자신을 테이블에 저장
        // 재귀호출로 인해 최종적으로 이 조건에 도달하면, 해당 Point 값을 저장하고 리턴함으로써 재귀호출 경로 상의 모든 함수에서 이 경로를 테이블에 저장하게 됨.
        if (min_r == r && min_c == c) {
            table[cur.row][cur.col] = new Point(cur.row, cur.col);
        }
        // 존재하면 dfs 다시 호출
        else {
            table[cur.row][cur.col] = dfs(new Point(min_r, min_c));
        }

        return table[cur.row][cur.col];
    }

    static void calc() {
        // 모든 요소들에 대해 탐색을 돌린다.
        for(int i=0; i<R; i++) {
            for(int j=0; j<C; j++) {
                Point result = dfs(new Point(i, j));
                counts[result.row][result.col]++;
            }
        }

        StringBuilder sb = new StringBuilder();
        for(int i=0; i<R; i++) {
            for(int j=0; j<C; j++) {
                sb.append(counts[i][j]).append(" ");
            }
            sb.append('\n');
        }

        System.out.println(sb);
    }

    public static void main(String[] args) throws Exception {
        init();
        calc();
    }

}
