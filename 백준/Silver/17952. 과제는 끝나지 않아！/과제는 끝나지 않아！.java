import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(reader.readLine());

        // 한 학기동안의 전체 작업을 저장. (시간, 점수)
        Queue<int[]> totalWorks = new LinkedList<>();

        // 밀린 일들을 저장.
        Stack<int[]> workStack = new Stack<>();

        boolean[] isWorkAdd = new boolean[n];
        int totalScore = 0;

        int[] tmp;
        int time, score;
        for(int i=0; i<n; i++) {
            String[] line = reader.readLine().split(" ");

            isWorkAdd[i] = line[0].equals("1");

            // 1이면 작업이 추가 (큐에 추가)
            if (isWorkAdd[i]) {
                score = Integer.parseInt(line[1]);
                time = Integer.parseInt(line[2]);
                tmp = new int[2];
                tmp[0] = time;
                tmp[1] = score;
                totalWorks.add(tmp);
            }

        }

        // current[time, score]
        int[] current = null;
        for (int i=0; i<n; i++) {

            // 남은 일도 없고 하는 일도 없는 경우
            if (totalWorks.isEmpty() && current == null) {
                break;
            }

            boolean isWork = isWorkAdd[i];

            // 일이 추가된 경우
            if (isWork) {
                // 하던 일이 있으면 하던걸 스택에 푸시 (하던 일이 있을 경우 추가되는 작업)
                if (current != null) {
                    workStack.push(current);
                }
                // 추가된 일을 현재 일로 지정 (항상 수행)
                current = totalWorks.poll();
            }
            // 일이 추가되지 않은 경우
            else {
                if (current == null)
                    continue;
                // 하던 일이 없으면 continue
            }

            // 하던 일이 있으면 하던 일의 시간을 감소
            current[0]--;

            // 일을 다 하면
            if (current[0] == 0) {
                totalScore += current[1]; // 점수 추가

                // 밀린 일이 있으면 가져오기
                if (workStack.isEmpty()) {
                    current = null;
                }
                else {
                    current = workStack.pop();
                }
            }

        }

        System.out.println(totalScore);

    }

}
