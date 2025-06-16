import java.util.*;
import java.io.*;
import java.math.*;

public class Main {

    static int R, C, T;

    static int[][] board;
    static int[][] tmp_dust; // 확산되는 먼지를 저장할 공간
    static int[] dr = {-1, 1, 0, 0};
    static int[] dc = {0, 0, -1, 1};

    static int up_r, up_c; // above
    static int down_r, down_c; // under
    static int total_dust = 0;

    static void init() throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer tkn = new StringTokenizer(reader.readLine());
        R = Integer.parseInt(tkn.nextToken()); // 6 ~ 50
        C = Integer.parseInt(tkn.nextToken()); // 6 ~ 50
        T = Integer.parseInt(tkn.nextToken()); // 1 ~ 1천

        up_c = 0;
        down_c = 0;
        board = new int[R][C];
        tmp_dust = new int[R][C];
        boolean flag = true;
        for(int i=0; i<R; i++) {
            tkn = new StringTokenizer(reader.readLine());
            for(int j=0; j<C; j++) {
                board[i][j] = Integer.parseInt(tkn.nextToken());
                total_dust += board[i][j];

                // 청정기 위치 저장
                if (board[i][j] == -1 && flag) {
                    up_r = i;
                    down_r = i+1;
                    flag = false;
                }
            }
        }

        total_dust += 2; // 공기청정기 -1 두번 더해진 것 롤백
    }

    static void clearTmpDust() {
        for(int i=0; i<R; i++)
            Arrays.fill(tmp_dust[i], 0);
    }

    // 인접한 네 방향으로 확산하는 먼지를 tmp_dust에 저장
    static void spread(int r, int c) {
        if (board[r][c] < 5) return;
        int spreads = board[r][c] / 5;
        int nr, nc;
        for(int i=0; i<4; i++) {
            nr = r + dr[i];
            nc = c + dc[i];
            // 범위를 벗어나지 않고, 공기청정기가 없으면
            if (nr >=0 && nc >=0 && nr < R && nc < C && board[nr][nc] != -1) {
                tmp_dust[nr][nc] += spreads; // 먼지가 퍼짐
                tmp_dust[r][c] -= spreads; // 원래 위치에서는 줄어듦
            }
        }
    }

    // 먼지 확산
    static void spreadDust() {
        clearTmpDust();
        // 먼지는 동시에 퍼지므로 퍼지는 먼지의 양을 tmp_dust에 저장
        for(int r=0; r<R; r++) {
            for(int c=0; c<C; c++) {
                spread(r, c);
            }
        }

        // 먼지 변화량을 board에 반영
        for(int r=0; r<R; r++) {
            for(int c=0; c<C; c++) {
                board[r][c] += tmp_dust[r][c];
            }
        }
    }

    // 가로방향
    static void circle(int start_r, int start_c, boolean clock_dir) {
        // 시계방향
        // 시계방향 -> 배열 값 업데이트는 반시계방향으로 해야 함
        if (clock_dir) {
            total_dust -= board[start_r+1][start_c];

            for(int r = start_r+1; r < R-1; r++) {
                board[r][start_c] = board[r+1][start_c];
            }
            for(int c = start_c; c < C-1; c++) {
                board[R-1][c] = board[R-1][c+1];
            }
            for(int r=R-1; r>start_r; r--) {
                board[r][C-1] = board[r-1][C-1];
            }
            for(int c=C-1; c>start_c+1; c--) {
                board[start_r][c] = board[start_r][c-1];
            }
            board[start_r][start_c+1] = 0; // 공기청정기 시작 부분은 0
        }
        // 반시계방향 -> 배열 값 업데이트는 시계방향으로 해야 함
        else {
            total_dust -= board[start_r-1][start_c];
            for(int r=start_r-1; r>0; r--) {
                board[r][start_c] = board[r-1][start_c];
            }
            for(int c=start_c; c<C-1; c++) {
                board[0][c] = board[0][c+1];
            }
            for(int r=0; r<start_r; r++) {
                board[r][C-1] = board[r+1][C-1];
            }
            for(int c=C-1; c > start_c+1; c--) {
                board[start_r][c] = board[start_r][c-1];
            }
            board[start_r][start_c+1] = 0;
        }
    }

    // 공기청정기 작동
    static void circleAir() {
        // 공기청정기 윗부분
        circle(up_r, up_c, false);

        // 공기청정기 아랫부분
        circle(down_r, down_c, true);
    }

    static void calc() {
        // 아래를 T번 반복한다.
        for(int i=0; i<T; i++) {
            // 1. 먼지를 확산시킨다.
            spreadDust();
            // 2. 공기를 순환시킨다.
            circleAir();
        }
    }

    public static void main(String[] args) throws Exception {
        init();
        calc();
        System.out.println(total_dust);
    }

}
