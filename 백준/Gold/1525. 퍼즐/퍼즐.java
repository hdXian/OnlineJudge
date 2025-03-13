import java.util.*;
import java.io.*;
import java.math.*;

// 최적화 2
public class Main {

    public static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static int[][] puzzle;
    public static int idx;
    public static Set<String> codeSet = new HashSet<>();

    public static int[] dx = {-3, 3, -1, 1};

    public static void init() throws Exception {
        puzzle = new int[3][3];
        StringTokenizer tkn;
        for(int i=0; i<3; i++) {
            tkn = new StringTokenizer(reader.readLine());
            for(int j=0; j<3; j++) {
                puzzle[i][j] = Integer.parseInt(tkn.nextToken());
                if (puzzle[i][j] == 0)
                    idx = i*3 + j;
            }
        }

    }

    public static void main(String[] args) throws Exception {
        init();
        String code = makeCode(puzzle);
        int result = bfs(idx, code);
        System.out.println(result);
    }

    // 재귀를 써야하나?
    public static int bfs(int idx, String code) {
        int depth = 0;

        if (isCorrect(code))
            return depth;

        Queue<Puzzle> q = new LinkedList<>();
        depth++;
        // 상하좌우 이동 (초기 큐 데이터 추가)
        for (int k=0; k<4; k++) {
            int next_idx = idx + dx[k];

            if (idx % 3 == 2 && (next_idx - idx) == 1) continue;
            if (idx % 3 == 0 && (idx - next_idx) == 1) continue;

            // 배열 범위를 벗어나지 않고, 방문한 적 없는 칸인 경우 칸 이동
            if (next_idx >= 0 && next_idx < 9) {

                // 칸 이동 (코드 내에서 문자열 인덱스 변경)
                char[] seq = code.toCharArray();
                seq[idx] = seq[next_idx];
                seq[next_idx] = '0';

                // 위치가 바뀐 코드를 새로운 String으로 생성
                String update_code = new String(seq);

                // 옮긴 상태가 조건을 만족하면 depth를 리턴하고 종료
                if (isCorrect(update_code))
                    return depth;
                // 만족하지 않을 경우 현재 상태를 code로 만들어 hashSet에 저장
                else {
                    Puzzle p = new Puzzle(next_idx, update_code);
                    q.add(p);
                    codeSet.add(update_code);
                }
            }

        }

        // 큐가 빌 때까지 반복
        while(!q.isEmpty()) {
            depth++;

            int length = q.size();

            // 처음에 큐에 들어있던 노드들을 대상으로 반복 (이전에 계산된 length)
            for(int i=0; i<length; i++) {
                Puzzle p = q.poll(); // 여기서 꺼내진 퍼즐은 조건을 만족하지 않는 것들이므로 상하좌우를 탐색한다.

                // 상하좌우 탐색
                for (int k=0; k<4; k++) {
                    int crnt_idx = p.idx;
                    String crnt_code = p.code;

                    int next_idx = p.idx + dx[k];
                    int nr = next_idx / 3;
                    int nc = next_idx % 3;

                    if (crnt_idx % 3 == 2 && (next_idx - crnt_idx) == 1) continue;
                    if (crnt_idx % 3 == 0 && (crnt_idx - next_idx) == 1) continue;

                    // 배열 범위를 벗어나지 않고, 방문한 적 없는 칸인 경우 칸 이동
                    if (nr >= 0 && nc >= 0 && nr < 3) {

                        // 칸 이동 (코드 내에서 문자열 인덱스 변경)
                        char[] seq = crnt_code.toCharArray();
                        seq[crnt_idx] = seq[next_idx];
                        seq[next_idx] = '0';

                        // 위치가 바뀐 코드를 새로운 String으로 생성
                        String update_code = new String(seq);

                        // 옮긴 상태가 조건을 만족하면 depth를 리턴하고 종료
                        if (isCorrect(update_code))
                            return depth;
                            // 만족하지 않을 경우 현재 상태를 Puzzle 객체로 만들어 큐에 저장
                        else {
                            if (!codeSet.contains(update_code)) {
                                Puzzle new_p = new Puzzle(next_idx, update_code);
                                q.add(new_p);
                                codeSet.add(update_code);
                            }
                        }
                    }

                }

            }

        }

        return -1;
    }

    public static String makeCode(int[][] puzzle) {
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<3; i++)
            for(int j=0; j<3; j++)
                sb.append(puzzle[i][j]);
        return sb.toString();
    }

    // 조건을 만족하는지 체크
    public static boolean isCorrect(String code) {
        return code.equals("123456780");
    }

    static class Puzzle {
        public int idx;
        public String code;

        public Puzzle(int idx, String code) {
            this.idx = idx;
            this.code = code;
        }

        @Override
        public String toString() {
            return String.format("idx = %d, code = %s\n", idx, code);
        }

    }

}
