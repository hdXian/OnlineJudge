import java.util.*;
import java.io.*;
import java.math.*;

public class Main {

    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    static int N, M, D;
    static int[][] board;
    static List<PriorityQueue<Enemy>> eList = new ArrayList<>();
    static Set<Integer> kills = new HashSet<>();
    static Queue<Enemy> killed = new LinkedList<>();

    static List<List<Integer>> combs = new ArrayList<>();

    static int result = -1;

    static class Enemy implements Comparable<Enemy> {
        public int id;
        public int row, col;
        public int distance;

        public Enemy(int id, int row, int col, int distance) {
            this.id = id;
            this.row = row;
            this.col = col;
            this.distance = distance;
        }

        @Override
        public int compareTo(Enemy e) {
            if (this.distance == e.distance) return (this.col - e.col); // 거리가 같으면 왼쪽
            else return (this.distance - e.distance); // 거리 기준 오름차순
        }
    }

    static void init() throws Exception {
        StringTokenizer tkn = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(tkn.nextToken()); // 행
        M = Integer.parseInt(tkn.nextToken()); // 열 (1 ~ 15)
        D = Integer.parseInt(tkn.nextToken()); // 사거리 (1 ~ 10)

        board = new int[N][M];
        int seq = 1;
        for(int i=0; i<N; i++) {
            tkn = new StringTokenizer(reader.readLine());
            for(int j=0; j<M; j++) {
                // 적이면 해당 위치에 id 부여, 아니면 0 저장
                board[i][j] = tkn.nextToken().equals("1") ? seq++ : 0;
            }
        }

        for(int i=0; i<3; i++) eList.add(new PriorityQueue<>());
    }

    static int[][] copyBoard() {
        int[][] tmp = new int[N][M];
        for(int i=0; i<N; i++)
            for(int j=0; j<M; j++) tmp[i][j] = board[i][j];

        return tmp;
    }

    static int calcDistance(int r1, int c1, int r2, int c2) {
        return Math.abs(r1-r2) + Math.abs(c1-c2);
    }

    // Enemy 큐 초기화
    static void clearQueue() {
        for(int k=0; k<3; k++) {
            eList.get(k).clear();
        }
    }

    // 한 줄씩 지우기
    static void nextTurn(int[][] tmpBoard) {
        for(int row=N-1; row>=1; row--) {
            tmpBoard[row] = tmpBoard[row-1];
        }
        tmpBoard[0] = new int[M];
    }

    // 사거리를 계산하여 각 열의 궁수 사정권에 있는 적을 추가한다.
    static void addEnemy(List<Integer> archers, int[][] tmpBoard) {
        for(int i=0; i<N; i++) {
            for(int j=0; j<M; j++) {
                int id = tmpBoard[i][j];

                // 해당 위치에 적이 존재하면
                if (id != 0) {
                    // 각 열에 위치한 궁수로부터 사거리가 되는 경우 큐에 추가
                    for(int k=0; k<3; k++) {
                        int d = calcDistance(i, j, N, archers.get(k));
                        if (d <= D) eList.get(k).add(new Enemy(id, i, j, d));
                    }
                }

            }
        }
    }

    static void removeEnemy(int[][] tmpBoard) {
        // 각 열의 궁수들에 대해 처치할 적을 추가
        for(int k=0; k<3; k++) {
            PriorityQueue<Enemy> pq = eList.get(k);
            // 처치할 적이 존재한다면 제거
            if (!pq.isEmpty()) {
                Enemy killed_enemy = pq.poll();
                kills.add(killed_enemy.id);
                killed.add(killed_enemy);
            }
        }

        // 이번 턴에 죽은 적들을 board에서 제거
        while(!killed.isEmpty()) {
            Enemy e = killed.poll();
            tmpBoard[e.row][e.col] = 0;
        }
    }

    static void comb(List<Integer> cur, int start, int depth) {
        if (depth == 3) {
            combs.add(new ArrayList<>(cur));
        }
        else {
            for(int i=start; i<M-(2-depth); i++) {
                cur.add(i);
                comb(cur, i+1, depth+1);
                cur.remove(cur.size()-1);
            }
        }
    }

    static void calc() {
        // 1. 궁수를 배치할 3개 열의 조합을 구한다.
        comb(new ArrayList<>(), 0, 0);

        // 2. 각 궁수 배치에 대해 시뮬레이션을 돌린다.
        for(List<Integer> archers: combs) {
            int[][] tmpBoard = copyBoard();

            for(int i=0; i<N; i++) {
                addEnemy(archers, tmpBoard); // 적들의 목록을 각자의 큐에 추가
                removeEnemy(tmpBoard); // 각자의 큐에서 가장 가까운 적을 제거
                nextTurn(tmpBoard); // 턴이 지남에 따라 성벽이 부딪혀 없어지는 적 제거
                clearQueue(); // 각자의 큐를 초기화
            }

            result = Math.max(result, kills.size());
            kills.clear();
        }
    }

    public static void main(String[] args) throws Exception {
        init();
        calc();
        System.out.println(result);
    }

}
