import java.util.*;
import java.io.*;

public class Main {

    public static int N;
    public static int[][] dmgs =
            { {1, 3, 9}, {1, 9, 3}, {3, 1, 9}, {3, 9, 1}, {9, 1, 3}, {9, 3, 1} };

    public static int[][][] table;

    public static void main(String[] args) throws Exception {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(reader.readLine());

        int[] scvs = new int[3];
        Arrays.fill(scvs, 0);


        // scv 체력 입력받기
        StringTokenizer tkn = new StringTokenizer(reader.readLine());
        for(int i=0; i<N; i++) {
            scvs[i] = Integer.parseInt(tkn.nextToken());
        }

        table = new int[scvs[0]+1][scvs[1]+1][scvs[2]+1];
        for(int i=0; i<=scvs[0]; i++) {
            for(int j=0; j<=scvs[1]; j++) {
                Arrays.fill(table[i][j], -1);
            }
        }

        int result = bfs(scvs[0], scvs[1], scvs[2]);

        System.out.println(result);
    }

    private static int bfs(int scv1, int scv2, int scv3) {
        scv1 = Math.max(scv1, 0);
        scv2 = Math.max(scv2, 0);
        scv3 = Math.max(scv3, 0);

        if (scv1==0 && scv2==0 && scv3==0) {
            return 0;
        }

        // 전에 계산된 적 있는 체력이면 테이블에서 리턴
        if (table[scv1][scv2][scv3] != -1) {
            return table[scv1][scv2][scv3];
        }

        int result = Integer.MAX_VALUE;

        int tmp;
        for (int[] dmg: dmgs) {
            tmp = bfs(scv1-dmg[0], scv2-dmg[1], scv3-dmg[2]) + 1;
            if (tmp < result)
                result = tmp;
        }

        table[scv1][scv2][scv3] = result;

        return table[scv1][scv2][scv3];
    }

}
