import java.io.*;
import java.util.*;
import java.math.*;

public class Main {

    static int N, M;

    static int[][] board;

    static int[] dr = {-1, 1, 0, 0};
    static int[] dc = {0, 0, -1, 1};

    static int total_score = 0;
    static final int _BLANK = -99;

    static class Point implements Comparable<Point> {
        public int row, col;
        public Point(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public int compareTo(Point p) {
            if (this.row == p.row) {
                return this.col - p.col; // 행이 같으면 열 기준 오름차순
            }
            else return this.row - p.row; // 행 기준 오름차순
        }
    }

    static class BlockGroup implements Comparable<BlockGroup> {
        Point root = null;
        List<Point> blocks = new ArrayList<>();
        int rainbowBlockCount;

        public BlockGroup(Point p) {
            this.root = p;
            blocks.add(p);
            rainbowBlockCount = 0;
        }

        public void addBlock(Point p) {
            // 일반 블록이라면 기준 블록과 비교해서 업데이트
            if (board[p.row][p.col] > 0) {
                if (root.compareTo(p) > 0) root = p;
            }
            else rainbowBlockCount++; // 일반 블록이 아니면 무지개블록 카운트 +1
            blocks.add(p);
        }

        public int getTotalBlocks() {
            return this.blocks.size();
        }

        @Override
        public int compareTo(BlockGroup bg) {
            // 총 블록 개수가 같으면
            if (this.getTotalBlocks() == bg.getTotalBlocks()) {
                // 무지개 블록 개수마저 같으면
                if (this.rainbowBlockCount == bg.rainbowBlockCount) {
                    // 기준 블록으로 비교
                    return -(this.root.compareTo(bg.root));
                }
                else return -(this.rainbowBlockCount - bg.rainbowBlockCount);
            }
            else return -(this.getTotalBlocks() - bg.getTotalBlocks());
        }

    }

    static void init() throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer tkn = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(tkn.nextToken()); // 격자 크기. 1 ~ 20
        M = Integer.parseInt(tkn.nextToken()); // 일반 블록 종류. 1 ~ 5

        board = new int[N][N];
        for(int i=0; i<N; i++) {
            tkn = new StringTokenizer(reader.readLine());
            for(int j=0; j<N; j++) {
                board[i][j] = Integer.parseInt(tkn.nextToken());
            }
        }
    }

    // bfs로 하나의 BlockGroup을 찾아서 리턴하는 함수
    static BlockGroup findGroup(int row, int col, boolean[][] visited) {
        Queue<Point> q = new LinkedList<>();
        q.add(new Point(row, col));
        visited[row][col] = true;
        int blockNum = board[row][col];

        BlockGroup bg = new BlockGroup(new Point(row, col));

        List<Point> rainbows = new ArrayList<>();

        int r, c;
        int nr, nc;
        while(!q.isEmpty()) {
            Point cur = q.poll();
            r = cur.row;
            c = cur.col;
            // 인접 네 방향을 살펴보면서, 조건에 맞으면 큐에 추가한다.
            for(int i=0; i<4; i++) {
                nr = r + dr[i];
                nc = c + dc[i];
                // 조건에 맞으면 큐에 추가
                if (nr >=0 && nc >=0 && nr < N && nc < N
                        && !visited[nr][nc] && (board[nr][nc] == blockNum || board[nr][nc] == 0)) {
                    q.add(new Point(nr, nc));
                    visited[nr][nc] = true;

                    bg.addBlock(new Point(nr, nc));
                    if (board[nr][nc] == 0) rainbows.add(new Point(nr, nc));
                }
            }
        }

        // 무지개블록은 각 블록 그룹에서 공유할 수 있음. 블록 그룹을 찾은 뒤 visited를 다시 해제해야 함.
        for(Point p: rainbows) visited[p.row][p.col] = false;

        return bg;
    }

    // 가장 큰 블록 그룹 찾기
    static BlockGroup findMaxBlock() {
        // 일단 bfs로 조건 걸어서 블록을 찾고, 찾은 블록 그룹을 별도의 BlockGroup 클래스로 저장.
        boolean[][] visited = new boolean[N][N];
        PriorityQueue<BlockGroup> group_pq = new PriorityQueue<>();

        for(int i=0; i<N; i++) {
            for(int j=0; j<N; j++) {
                if (board[i][j] > 0) {
                    BlockGroup group = findGroup(i, j, visited);
                    if (group.getTotalBlocks() > 1) {
                        group_pq.add(group);
                    }
                }
            }
        }

        // 가장 큰 블록 그룹의 조건을 걸어서 sort 때리고 리턴.
        if (group_pq.isEmpty()) return null;
        else return group_pq.peek();
    }

    static void removeBlock(BlockGroup bg) {
        for(Point p: bg.blocks) {
            board[p.row][p.col] = -99;
        }
    }

    static void gravity() {
        // 제일 아래 행에서부터 진행
        for(int i=N-2; i>=0; i--) {
            for(int j=0; j<N; j++) {
                // 일반 블록과 무지개 블록을 아래쪽으로 이동
                if (board[i][j] >= 0) {
                    int row = i+1; // 아랫줄부터 확인
                    while(row < N && board[row][j] == _BLANK) row++;
                    // 아래에 빈 공간이 있어 row가 내려갔을 경우
                    if (row > i+1) {
                        row--;
                        board[row][j] = board[i][j];
                        board[i][j] = _BLANK;
                    }
                }
            }
        }
    }

    // 90도 반시계방향으로 회전
    static void rotate() {
        int[][] tmpBoard = new int[N][N];
        for(int i=0; i<N; i++) {
            int[] row = board[i];
            for(int j=0; j<N; j++) {
                tmpBoard[N-1-j][i] = row[j];
            }
        }

        for(int i=0; i<N; i++) {
            for(int j=0; j<N; j++) {
                board[i][j] = tmpBoard[i][j];
            }
        }

    }

    static void calc() {
        // 0. 아래 과정을 블록 그룹이 더 이상 없을 때까지 반복한다.

        // 1. 크기가 가장 큰 블록 그룹을 찾는다.
        // 크기가 같다면 무지개 블록의 수가 가장 많은 블록 그룹, 그것도 여러개라면 기준 블록의 행, 열이 가장 큰 그룹
        BlockGroup maxBlock = findMaxBlock();

        while(maxBlock != null) {
            // 2. 1에서 찾은 블록 그룹의 모든 블록을 제거한다.
            // (제거한 블록의 수)^2 점을 획득한다.
            removeBlock(maxBlock);
            total_score += (int) Math.pow(maxBlock.getTotalBlocks(), 2);

            // 3. 격자에 중력이 작용한다.
            gravity();
            // 4. 격자가 90도 반시계 방향으로 회전한다.
            rotate();
            // 5. 다시 격자에 중력이 작용한다.
            gravity();

            maxBlock = findMaxBlock();
        }

    }

    public static void main(String[] args) throws Exception {
        init();
        calc();
        System.out.println(total_score);
    }

}
