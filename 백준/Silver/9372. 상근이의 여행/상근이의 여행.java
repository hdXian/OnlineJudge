import java.io.*;
import java.util.*;

public class Main {

    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    static int T;

    static int calc() throws Exception {
        int n, m;
        StringTokenizer tkn = new StringTokenizer(reader.readLine());
        n = Integer.parseInt(tkn.nextToken());
        m = Integer.parseInt(tkn.nextToken());

        for(int i=0; i<m; i++) reader.readLine();

        return n-1;
    }

    public static void main(String[] args) throws Exception {
        T = Integer.parseInt(reader.readLine());
        StringBuilder sb = new StringBuilder();

        int result;
        for(int i=0; i<T; i++) {
            result = calc();
            sb.append(result).append('\n');
        }

        System.out.println(sb);
    }

}
