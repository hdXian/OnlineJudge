import java.util.*;
import java.io.*;
import java.math.*;

public class Main {

    public static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static int N; // 보드 크기 (2 ~ 100)

    public static boolean[][] apple; // 사과 위치 저장
    public static boolean[][] board; // 뱀 표시할 보드

    // 상, 우, 하, 좌 (0, 1, 2, 3), 시계 방향
    public static int[] dr = {-1, 0, 1, 0};
    public static int[] dc = {0, 1, 0, -1};

    static class Pair {
        public int x, y;
        public Pair(int x, int y) {
            this.x = x;
            this.y =y;
        }
    }

    public static Map<Integer, String> cmds = new HashMap<>();
    public static Deque<Pair> snake = new LinkedList<>(); // 뱀
    // addFirst(), removeFirst, addLast(), removeLast(), getFirst();

    static void init() throws Exception {
        int K, L;
        N = Integer.parseInt(reader.readLine());
        K = Integer.parseInt(reader.readLine());

        board = new boolean[N+1][N+1];
        board[1][1] = true;

        // 사과 위치 입력
        apple = new boolean[N+1][N+1];
        StringTokenizer tkn;
        int r,c;
        for(int i=0; i<K; i++) {
            tkn = new StringTokenizer(reader.readLine());
            r = Integer.parseInt(tkn.nextToken());
            c = Integer.parseInt(tkn.nextToken());
            apple[r][c] = true;
        }

        // 명령어 개수 입력
        L = Integer.parseInt(reader.readLine());
        int key;
        String val;
        for(int i=0; i<L; i++) {
            tkn = new StringTokenizer(reader.readLine());
            key = Integer.parseInt(tkn.nextToken());
            val = tkn.nextToken();
            cmds.put(key, val);
        }

    }

    static int turn(int curDir, String cmd) {
        if (cmd.equals("L")) // 왼쪽으로 회전
            return (curDir+3) % 4;
        else // 오른쪽으로 회전
            return (curDir+1) % 4;
    }

    static int calc() throws Exception {
        // 뱀을 초기화한다.
        int t = 0;
        int curDir = 1;
        int row = 1;
        int col = 1;
        snake.addFirst(new Pair(1, 1));

        Queue<Integer> cmd_times = new PriorityQueue<>(cmds.keySet());
        int next_t = -1;
        String next_cmd = "L";
        if (!cmd_times.isEmpty()) {
            next_t = cmd_times.poll();
            next_cmd = cmds.get(next_t);
        }

        // 시간을 1씩 증가시키면서, cmds에 명시된 시간이 되면 방향을 전환한다.
        Pair tail;
        int nr, nc;
        while (true) {
            t++;
            // 머리를 한 칸 앞으로 움직인다.
            nr = row + dr[curDir];
            nc = col + dc[curDir];

            // 범위를 벗어나면 게임 오버
            if (nr < 1 || nc < 1 || nr > N || nc > N) {
                break;
            }

            // 머리가 움직인 곳에 이미 몸이 있으면 게임 오버
            if (board[nr][nc])
                break;

            snake.addFirst(new Pair(nr, nc));
            board[nr][nc] = true;

            // 사과가 있으면 꼬리를 제거하지 않음. 사과는 제거.
            if (apple[nr][nc]) {
                apple[nr][nc] = false;
            }
            else { // 사과가 없으면 꼬리를 제거.
                tail = snake.getLast();
                board[tail.x][tail.y] = false;
                snake.removeLast();
            }

            // 머리 돌리기
            if (t == next_t) {
                curDir = turn(curDir, next_cmd);
                next_t = (cmd_times.isEmpty()) ? -1 : cmd_times.poll();
                next_cmd = cmds.get(next_t);
            }

            row = nr;
            col = nc;
        }

        // 게임이 종료되면 경과 시간을 리턴하고 종료한다.
        return t;
    }

    public static void main(String[] args) throws Exception {
        init();
        int result = calc();
        System.out.println(result);
    }


}
