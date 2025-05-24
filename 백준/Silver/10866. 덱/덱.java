import java.io.*;
import java.util.*;
import java.math.*;

public class Main {

    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    static int N;
    static List<String> cmds = new ArrayList<>();

    static void init() throws Exception {
        N = Integer.parseInt(reader.readLine()); // 명령 개수, 1 ~ 1만
        for(int i=0; i<N; i++) cmds.add(reader.readLine());
    }

    static String calc() {
        Deque<Integer> dq = new LinkedList<>();
        StringTokenizer tkn;

        String command;
        int X;
        StringBuilder sb = new StringBuilder();
        // push_front, push_back, pop_front, pop_back, size, empty, front, back,
        for (String cmd: cmds) {
            tkn = new StringTokenizer(cmd);
            command = tkn.nextToken();
            if (command.equals("push_front")) {
                X = Integer.parseInt(tkn.nextToken());
                dq.addFirst(X);
            }
            else if (command.equals("push_back")) {
                X = Integer.parseInt(tkn.nextToken());
                dq.addLast(X);
            }
            else if (command.equals("pop_front")) {
                if (dq.isEmpty()) sb.append(-1);
                else sb.append(dq.pollFirst());
                sb.append('\n');
            }
            else if (command.equals("pop_back")) {
                if (dq.isEmpty()) sb.append(-1);
                else sb.append(dq.pollLast());
                sb.append('\n');
            }
            else if (command.equals("size")) {
                sb.append(dq.size()).append('\n');

            }
            else if (command.equals("empty")) {
                sb.append((dq.isEmpty()) ? 1 : 0).append('\n');
            }
            else if (command.equals("front")) {
                if (dq.isEmpty()) sb.append(-1);
                else sb.append(dq.peekFirst());
                sb.append('\n');
            }
            else if (command.equals("back")) {
                if (dq.isEmpty()) sb.append(-1);
                else sb.append(dq.peekLast());
                sb.append('\n');
            }

        }

        return sb.toString();
    }

    public static void main(String[] args) throws Exception {
        init();
        String result = calc();
        System.out.println(result);
    }

}
