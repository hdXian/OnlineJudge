import java.util.*;
import java.io.*;
import java.math.*;

public class Main {

    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    static int N, Q;

    static int[][] board;
    static int[] levels;

    static int[] dr = {-1, 1, 0, 0};
    static int[] dc = {0, 0, -1, 1};
    static boolean[][] visited;
    static int[][] ice_count;
    static int total_ice;

    static int linked_ice;
    static int max_ice;

    static void init() throws Exception {
        StringTokenizer tkn = new StringTokenizer(reader.readLine());
        N = (int) Math.pow(2, Integer.parseInt(tkn.nextToken())); // 보드 크기. 2 ~ 6 -> 4 ~ 64
        Q = Integer.parseInt(tkn.nextToken()); // 파이어스톰 시전 횟수. 1 ~ 1천

        // 얼음판 입력받기
        board = new int[N][N];
        for(int i=0; i<N; i++) {
            tkn = new StringTokenizer(reader.readLine());
            for(int j=0; j<N; j++) {
                board[i][j] = Integer.parseInt(tkn.nextToken()); // 얼음 크기. 1 ~ 100
            }
        }

        // 시전 레벨 입력받기
        levels = new int[Q];
        tkn = new StringTokenizer(reader.readLine());
        for(int i=0; i<Q; i++) {
            // 시전 레벨. 1 ~ 2^(N)
            levels[i] = (int) Math.pow(2, Integer.parseInt(tkn.nextToken()));
        }

        visited = new boolean[N][N];
        ice_count = new int[N][N];
    }

    // 시작 row, 시작 col부터 크기 siz 만큼의 board 영역을 시계방향으로 90도 회전시킨다.
    static void tornado(int start_row, int start_col, int siz) {

        int[][] tmpBoard = new int[siz][siz];
        for(int r=0; r<siz; r++) {
            for(int c=0; c<siz; c++) {
                tmpBoard[r][c] = board[start_row+r][start_col+c];
            }
        }

        // 1. 행, 열 바꾸기
        for(int r=0; r<siz; r++) {
            for(int c=r+1; c<siz; c++) {
                int tmp = tmpBoard[r][c];
                tmpBoard[r][c] = tmpBoard[c][r];
                tmpBoard[c][r] = tmp;
            }
        }

        // 2. 좌우 뒤집기
        for(int r=0; r<siz; r++) {
            for(int c=0; c<siz/2; c++) {
                int tmp = tmpBoard[r][c];
                int opposite_col = siz-c-1;
                tmpBoard[r][c] = tmpBoard[r][opposite_col];
                tmpBoard[r][opposite_col] = tmp;
            }
        }

        for(int r=0; r<siz; r++) {
            for(int c=0; c<siz; c++) {
                board[start_row+r][start_col+c] = tmpBoard[r][c];
            }
        }

    }

    // 얼음 녹일 때 쓰는 dfs
    static void dfs_fire(int row, int col) {
        visited[row][col] = true;

        int nr, nc;
        for(int i=0; i<4; i++) {
            nr = row + dr[i];
            nc = col + dc[i];
            if (nr >=0 && nc >=0 && nr < N && nc <N) {
                if (board[nr][nc] > 0) ice_count[row][col]++; // 얼음이 있으면 카운트
                if (!visited[nr][nc]) dfs_fire(nr, nc);
            }
        }

    }

    // 사방에 둘러싼 얼음이 3개 미만인 경우, 해당 얼음의 크기를 1 감소
    static void fireball() {
        // 방문 배열, 녹는 여부 배열 초기화
        for(int i=0; i<N; i++) {
            Arrays.fill(visited[i], false);
            Arrays.fill(ice_count[i], 0);
        }

        // 녹는 얼음 체크
        dfs_fire(0, 0);

        // 얼음 녹이기
        for(int r=0; r<N; r++) {
            for(int c=0; c<N; c++) {
                if (ice_count[r][c] < 3) board[r][c] = Math.max(0, board[r][c]-1);
            }
        }

    }

    static void fireStorm(int level) {
        // 1. 얼음판을 시전 레벨만큼의 부분 행렬로 나눈 다음, 시계방향으로 90도 회전시킨다.
        for(int i=0; i<N; i+=level) {
             for(int j=0; j<N; j+=level) {
                 tornado(i, j, level);
             }
        }

        // 2. 사방에 둘러싼 얼음이 3개 미만인 경우, 해당 얼음의 크기를 1 감소시킨다.
        fireball();
    }

    static void dfs_calc(int row, int col) {
        linked_ice++;
        visited[row][col] = true;

        int nr, nc;
        for(int i=0; i<4; i++) {
            nr = row + dr[i];
            nc = col + dc[i];
            if (nr >=0 && nc >=0 && nr<N && nc<N && !visited[nr][nc]) {
                if (board[nr][nc] > 0) {
                    dfs_calc(nr, nc);
                }
            }
        }
    }

    static String calc() {
        // 1. 지정한 횟수만큼 fireStorm 실행
        for(int i=0; i<Q; i++) {
            fireStorm(levels[i]);
        }

        // 2. A[r][c]의 전체 합 구하기
        // 3. 남은 얼음 중 가장 큰 얼음 덩이 구하기
        // 방문 배열 초기화
        for(int i=0; i<N; i++) Arrays.fill(visited[i], false);
        max_ice = 0;
        total_ice = 0;

        for(int r=0; r<N; r++) {
            for(int c=0; c<N; c++) {
                total_ice += board[r][c];
                if(!visited[r][c] && board[r][c] > 0) {
                    linked_ice = 0;
                    dfs_calc(r, c);
                    max_ice = Math.max(max_ice, linked_ice);
                }
            }
        }

        if (max_ice < 2) max_ice = 0;

        StringBuilder sb = new StringBuilder();
        sb.append(total_ice).append('\n');
        sb.append(max_ice).append('\n');

        return sb.toString();
    }

    public static void main(String[] args) throws Exception {
        init();
        String result = calc();
        System.out.println(result);
    }

}
