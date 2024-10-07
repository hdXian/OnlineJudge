import java.io.*;
import java.math.BigInteger;

public class Main {

    public static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(reader.readLine());

        BigInteger two = new BigInteger("2");

        // 이동 횟수는 공식으로
        BigInteger num = two.pow(n).subtract(BigInteger.valueOf(1));
        System.out.println(num);

        // n이 20 이하면 과정도 출력
        if (n<=20) {
            hanoi(n, 1, 2, 3);
        }

        writer.flush();
        writer.close();

    }

    private static void hanoi(int n, int src, int middle, int dst) throws IOException {
        if (n==0)
            return;

        // 마지막을 제외한 n-1개의 원판을 출발에서 중간으로 이동 (마지막 장대를 경유로 사용)
        hanoi(n-1, src, dst, middle);

        // 마지막 원판을 출발에서 마지막으로 이동
        writer.write(src + " " + dst + "\n");

        // 중간으로 이동한 n-1개의 원판을 중간에서 마지막으로 이동 (출발 장대를 경유로 사용)
        hanoi(n-1, middle, src, dst);
    }

}
