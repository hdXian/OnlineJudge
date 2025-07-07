import java.util.*;
import java.io.*;
import java.math.*;

public class Main {

    static int[][] board = new int[4][4];
    static int[][] directions = new int[4][4];

    static int[] dr = {-1, -1, 0, 1, 1, 1, 0, -1};
    static int[] dc = {0, -1, -1, -1, 0, 1, 1, 1};

    static final int SHARK = -1;

    static int result = 0;

    static void init() throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int n, d;
        for(int i=0; i<4; i++) {
            StringTokenizer tkn = new StringTokenizer(reader.readLine());
            for(int j=0; j<4; j++) {
                n = Integer.parseInt(tkn.nextToken());
                d = Integer.parseInt(tkn.nextToken());
                board[i][j] = n;
                directions[i][j] = d-1;
            }
        }

    }

    static void swapFish(int r, int c, int[][] board, int[][] directions) {
        // 8군데 확인하면서 이동 가능한 칸이 있을 경우 이동
        for(int k=0; k<8; k++) {
            int d = (directions[r][c] + k) % 8;
            int nr = r + dr[d];
            int nc = c + dc[d];
            // 해당 방향이 물고기거나 빈 칸이면 스왑하고 break
            if (nr >= 0 && nc >= 0 && nr < 4 && nc < 4 && board[nr][nc] != SHARK) {
                // System.out.printf("지금부터 (%d, %d) 위치의 %d번 물고기와 (%d, %d) 위치의 %d번 물고기의 위치를 바꿉니다.\n", r, c, board[r][c], nr, nc, board[nr][nc]);
                int tmp = board[r][c];
                board[r][c] = board[nr][nc];
                board[nr][nc] = tmp;
                directions[r][c] = directions[nr][nc];
                directions[nr][nc] = d;
                break;
            }
        }
    }

    static void moveFish(int[][] board, int[][] directions) {
        for(int i=1; i<=16; i++) {
            boolean flag = true;
            for(int r=0; r<4; r++) {
                for(int c=0; c<4; c++) {
                    if (board[r][c] == i && flag) {
                        swapFish(r, c, board, directions);
                        flag = false;
                    }
                }
            }

        }
    }

    static int[][] copyArr(int[][] arr) {
        int[][] copy = new int[4][4];
        for(int i=0; i<4; i++) {
            for(int j=0; j<4; j++) {
                copy[i][j] = arr[i][j];
            }
        }

        return copy;
    }

    static void dfs(int[][] board, int[][] directions, int row, int col, int total) {
        // 현재 위치의 물고기 먹기
        total += board[row][col];
        board[row][col] = SHARK;

        // 물고기 이동시키기
        moveFish(board, directions);

        // 현재 방향 저장
        int cur_d = directions[row][col];

        // 상어 이동하기 - 현재 위치와 방향에서 방문할 수 있는 모든 칸을 dfs
        int nr = row + dr[cur_d];
        int nc = col + dc[cur_d];
        boolean end_flag = true;
        while(nr >=0 && nc >=0 && nr < 4 && nc < 4) {
            if (board[nr][nc] == 0) {
                nr += dr[cur_d];
                nc += dc[cur_d];
                continue;
            }

            // 이거 백트래킹 에바임. 그냥 배열 새거 만들어서 넘기기
            int[][] nb = copyArr(board);
            int[][] nd = copyArr(directions);
            nb[row][col] = 0; // 상어가 있던 자리는 빈칸으로 설정

            // 다음 위치로 상어 이동
            dfs(nb, nd, nr, nc, total);

            end_flag = false;
            nr += dr[cur_d];
            nc += dc[cur_d];
        }

        // 더이상 움직이지 못했다면 지금까지 먹은 번호 업데이트
        if (end_flag) result = Math.max(result, total);
    }

    static void calc() {
        // 0, 0 칸의 물고기를 먹고 시작
        dfs(board, directions, 0, 0, 0);
    }

    public static void main(String[] args) throws Exception {
        // 1. 상어를 0, 0에 놓는다.
        // 2. 상어의 선택지별로 dfs를 돌린다.
        // 3. 가장 많이 먹은 물고기 번호 합을 출력한다.
        init();
        calc();
        System.out.println(result);
    }

}
