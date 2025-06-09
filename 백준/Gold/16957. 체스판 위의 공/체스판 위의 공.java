import java.io.*;
import java.util.*;
import java.math.*;

public class Main {

    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    // 12시부터 시계방향
    static int[] dr = {-1, -1, 0, 1, 1, 1, 0, -1};
    static int[] dc = {0, 1, 1, 1, 0, -1, -1, -1};

    static int R, C;

    static int[][] board;
    static int[] parents;

    static void init() throws Exception {
        StringTokenizer tkn = new StringTokenizer(reader.readLine());
        R = Integer.parseInt(tkn.nextToken());
        C = Integer.parseInt(tkn.nextToken());

        board = new int[R][C];
        for(int i=0; i<R; i++) {
            tkn = new StringTokenizer(reader.readLine());
            for(int j=0; j<C; j++) {
                board[i][j] = Integer.parseInt(tkn.nextToken());
            }
        }

        parents = new int[R*C];
        for(int i=0; i<R*C; i++) {
            parents[i] = i;
        }

    }

    static int findParent(int n) {
        if (parents[n] != n) parents[n] = findParent(parents[n]);
        return parents[n];
    }

    static void union(int n1, int n2) {
        int p1 = findParent(n1);
        int p2 = findParent(n2);

        int r1 = p1 / C; // 행 번호는 번호를 (열 길이)로 나눈 몫.
        int c1 = p1 % C; // 열 번호는 번호를 (열 길이)로 나눈 나머지.

        int r2 = p2 / C;
        int c2 = p2 % C;

        if (board[r1][c1] < board[r2][c2]) parents[p2] = p1;
        else parents[p1] = p2;
    }

    static void search(int r, int c) {
        int cur = r * C + c;

        int min_num = board[r][c];
        int min_r = r;
        int min_c = c;

        for(int i=0; i<8; i++) {
            int nr = r + dr[i];
            int nc = c + dc[i];
            if (nr >=0 && nc >=0 && nr < R && nc < C) {
                if (board[nr][nc] < min_num) {
                    min_num = board[nr][nc];
                    min_r = nr;
                    min_c = nc;
                }
            }
        }

        int next = min_r * C + min_c;
        union(cur, next);
    }

    static void calc() {
        // 모든 요소들에 대해 탐색을 돌린다.
        for(int i=0; i<R; i++) {
            for(int j=0; j<C; j++) {
                search(i, j);
            }
        }

        int[] counts = new int[R*C];
        for(int i=0; i<R*C; i++) {
            int p = findParent(i);
            counts[p]++;
        }

        for(int i=0; i<R; i++) {
            for(int j=0; j<C; j++) {
                System.out.print(counts[i*C + j] + " ");
            }
            System.out.println();
        }

    }

    public static void main(String[] args) throws Exception {
        init();
        calc();
    }

}
