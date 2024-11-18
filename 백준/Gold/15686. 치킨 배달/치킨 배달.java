import java.util.*;
import java.io.*;

public class Main {

    public static int result = 9999999;
    public static boolean[] opened;

    public static void main(String[] args) throws Exception {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int n,m;

        StringTokenizer tkn = new StringTokenizer(reader.readLine());
        n = Integer.parseInt(tkn.nextToken());
        m = Integer.parseInt(tkn.nextToken());

        // matrix 초기화
        char[][] matrix = new char[n+1][n+1];

        String[] line;
        for (int i=1; i<=n; i++) {
            tkn = new StringTokenizer(reader.readLine());

            for(int j=1; j<=n; j++) {
                matrix[i][j] = tkn.nextToken().charAt(0);
            }

        }

        // Home, Chicken 리스트 초기화
        ArrayList<Home> homes = new ArrayList<>();
        ArrayList<Chicken> chickens = new ArrayList<>();

        for(int i=1; i<=n; i++) {
            for(int j=1; j<=n; j++) {
                if (matrix[i][j] == '1')
                    homes.add(new Home(i, j));
                else if (matrix[i][j] == '2')
                    chickens.add(new Chicken(i, j));
            }
        }

        opened = new boolean[chickens.size()];
        Arrays.fill(opened, false);

        // 전체 치킨집 중에서 n개를 제거했을 때 치킨 거리가 최소가 되는 경우를 찾아야 함. -> 브루트 포스
        dfs(homes, chickens, 0,0, m);

        System.out.println(result);

    }

    private static void dfs(List<Home> homes, List<Chicken> chickens, int start, int depth, int m) {
        // 종료 조건
        if (depth == m) {
            // 각 Home들에 대한 치킨 거리 계산
            List<Chicken> openedChicken = new ArrayList<>();
            for(int i=0; i< chickens.size(); i++) {
                if (opened[i]) {
                    openedChicken.add(chickens.get(i));
                }
            }

            for (Home h: homes) {
                h.calcChickenDist(openedChicken);
            }

            // 전체 치킨 거리를 계산해 업데이트
            int total = calcTotalChickenDist(homes);
            if (total < result)
                result = total;

            return;
        }

        for (int i=start; i<chickens.size(); i++) {
            opened[i] = true;
            dfs(homes, chickens, i+1, depth+1, m);
            opened[i] = false;
        }

    }

    // 도시의 치킨 거리를 계산
    private static int calcTotalChickenDist(List<Home> homes) {
        int res = 0;
        for(Home h: homes) {
            res += h.chickenDist;
        }
        return res;
    }

    static class Home {
        public int row;
        public int col;
        public int chickenDist;

        public Home(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public int calcDist(Chicken ch) {
            int n1 = Math.abs(this.row - ch.row);
            int n2 = Math.abs(this.col - ch.col);
            return n1 + n2;
        }

        public void calcChickenDist(List<Chicken> cs) {
            this.chickenDist = 999999;
            int tmp = 0;

            for (Chicken chicken: cs) {
                tmp = calcDist(chicken);

                if (tmp < this.chickenDist) {
                    this.chickenDist = tmp;
                }

            }
        }

    }

    static class Chicken {
        public int row;
        public int col;

        public Chicken(int row, int col) {
            this.row = row;
            this.col = col;
        }

    }

}
