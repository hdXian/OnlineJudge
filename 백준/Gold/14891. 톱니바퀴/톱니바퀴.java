import java.util.*;
import java.io.*;
import java.math.*;

public class Main {

    public static void main(String[] args) throws Exception {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        Gear[] gears = new Gear[4];

        char[] line;
        for (int i=0; i<4; i++) {
            line = reader.readLine().toCharArray();
            gears[i] = new Gear(line);
        }

        // k번 톱니를 돌림
        int k = Integer.parseInt(reader.readLine());
        int num, direction;
        boolean[] turned = new boolean[4];
        StringTokenizer tok;
        for(int i=0; i<k; i++) {
            tok = new StringTokenizer(reader.readLine());
            num = Integer.parseInt(tok.nextToken());
            direction = Integer.parseInt(tok.nextToken());

            Arrays.fill(turned, false);
            // 톱니 번호와 방향을 입력받아 돌리기
            turnGear(num, direction, gears, turned);
        }

        Gear tmp;
        int res = 0;
        for(int i=0; i<4; i++) {
            tmp = gears[i];
            if (tmp.circle[0] == 1) {
                res += (1 << i);
            }
        }

        System.out.println(res);

    }

    private static void printGear(Gear[] gears) {
        for(int i=0; i<4; i++) {
            System.out.println("gears\n" + gears[i]);
        }
    }

    private static void turnGear(int num, int direction, Gear[] gears, boolean[] turned) {
        // 돌리기로 한 바퀴 양옆의 톱니가 같은지 확인한다.
        boolean left = false;
        boolean right = false;
        int idx = num-1;
        if (num == 1) {
            // right만 확인 (1번 기어와 2번 기어의 맞물리는 부분)
            right = (gears[idx].circle[2] != gears[idx+1].circle[6]);
        }
        else if (num == 4) {
            // left만 확인 (3번 기어와 4번 기어의 맞물리는 부분)
            left = (gears[idx-1].circle[2] != gears[idx].circle[6]);
        }
        else {
            // num=2 or 3 양옆 모두 확인
            left = (gears[idx-1].circle[2] != gears[idx].circle[6]);
            right = (gears[idx].circle[2] != gears[idx+1].circle[6]);
        }

        // 돌리기로 한 바퀴를 돌린다.
        if (direction == 1)
            gears[idx].turn();
        else {
            gears[idx].reverse();
        }
        turned[idx] = true;

        // 톱니가 다르면 direction의 양옆의 바퀴에 대해 반대 방향으로 turnGear() 호출
        if (left) {
            if (!turned[idx-1])
                turnGear(num-1, (direction * -1), gears, turned);
        }

        if (right) {
            if (!turned[idx+1])
                turnGear(num+1, (direction * -1), gears, turned);
        }

    }

    static class Gear {

        int[] circle = new int[8];

        public Gear(char[] line) {
            for(int i=0; i<8; i++) {
                circle[i] = Integer.parseInt(String.valueOf(line[i]));
            }
        }

        // 시계방향 회전
        public void turn() {
            int tmp = circle[7];
            for(int i=7; i>0; i--) {
                circle[i] = circle[i-1];
            }
            circle[0] = tmp;
        }

        // 반시계방향 회전
        public void reverse() {
            int tmp = circle[0];
            for(int i=0; i<7; i++) {
                circle[i] = circle[i+1];
            }
            circle[7] = tmp;
        }

        @Override
        public String toString() {
            StringBuilder res = new StringBuilder();
            for(int i=0; i<8; i++) {
                res.append(i).append(" ");
            }
            res.append('\n');
            for(int c: circle) {
                res.append(c).append(" ");
            }
            return res.toString();
        }
    }

}
