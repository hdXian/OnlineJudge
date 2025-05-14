import java.util.*;

public class Main {
    static final int MOD = 1000000007;

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();

        long a = 1;
        long b = 1;
        long result = 0;

        for (int i = 3; i <= n; i++) {
            result = (a + b) % MOD;
            a = b;
            b = result;
        }

        System.out.println(result + " " + (n - 2) % MOD);
    }
}