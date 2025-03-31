import java.math.*;
import java.io.*;
import java.util.*;

public class Main {

    public static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static int N;
    public static Queue<BigInteger> cards = new PriorityQueue<>();

    public static void init() throws Exception {
        N = Integer.parseInt(reader.readLine()); // 1 ~ 10만

        long val;
        for(int i=0; i<N; i++) {
            val = Integer.parseInt(reader.readLine());
            cards.add(BigInteger.valueOf(val));
        }
    }

    public static BigInteger calc() throws Exception {
        BigInteger result = BigInteger.ZERO;
        BigInteger deck = BigInteger.ZERO;
        BigInteger n1, n2;
        while (cards.size() >= 2) {
            n1 = cards.poll();
            n2 = cards.poll();
            deck = n1.add(n2);
            result = result.add(deck);
            cards.add(deck);
        }

        return result;
    }

    public static void main(String[] args) throws Exception {
        init();
        BigInteger result = calc();
        System.out.println(result);
    }

}
