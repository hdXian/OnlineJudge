import java.util.*;
import java.io.*;
import java.math.*;

public class Main {

    public static void main(String[] args) throws Exception {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int n, k;
        StringTokenizer tkn = new StringTokenizer(reader.readLine());
        n = Integer.parseInt(tkn.nextToken()); // 물건 개수
        k = Integer.parseInt(tkn.nextToken()); // 최대 무게

        int[] weights = new int[n+1];
        int[] values = new int[n+1];

        weights[0] = 0;
        values[0] = 0;

        int weight;
        int value;
        for(int i=1; i<=n; i++) {
            tkn = new StringTokenizer(reader.readLine());
            weights[i] = Integer.parseInt(tkn.nextToken());
            values[i] = Integer.parseInt(tkn.nextToken());
        }

        int[][] table = new int[n+1][k+1];

        // 첫 줄은 0으로 채움 (아이템 0번)
        Arrays.fill(table[0], 0);

        int res; // 어떤 아이템을 가방에 담고 남는 용량

        for(int i=1; i<=n; i++) {

            // j가 가방의 용량이 될 것
            for (int j=0; j<=k; j++) {
                res = j - weights[i];

                // 용량이 남았다 -> 이 물건을 담을 수 있다
                if (res >= 0) {
                    int choice1 = values[i] + table[i-1][res]; // 이 물건을 담고 나머지 물건들로 남은 무게를 채울 경우의 가치
                    int choice2 = table[i-1][j]; // 이 물건을 담지 않는 경우의 가치

                    table[i][j] = (choice1 > choice2) ? choice1 : choice2; // 이 값을 이제부터 정해야 함.
                }
                // 용량이 남지 않는다 -> 이 물건을 담을 수 없다.
                else {
                    // 이 물건을 못 담으면 그냥 이전 물건들의 구성을 그대로 가져옴
                    table[i][j] = table[i-1][j];
                }

            }

        }

        System.out.println(table[n][k]);

    }


}
