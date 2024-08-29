import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static int[][] matrix;
    public static List<List<Integer>> combs = new ArrayList<>(); // 뽑힌 조합들

    public static void main(String[] args) throws Exception {
        // reads std input
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(reader.readLine());

        matrix = new int[n][n];

        for (int i = 0; i < n; i++) {
            String[] arr = reader.readLine().split(" ");
            for (int j=0; j<n; j++)
                matrix[i][j] = Integer.parseInt(arr[j]);
        }

        ArrayList<Integer> players = new ArrayList<>();
        for (int i = 1; i < n+1; i++)
            players.add(i);

        calcComb(players, new ArrayList<Integer>(), n/2);

        int result = -1;
        // 각 조합에 대해 능력치 차이를 계산 및 비교
        for (List<Integer> comb : combs) {

            // 두 팀으로 나눈다.
            List<Integer> team1 = comb;
            List<Integer> team2 = new ArrayList<>();
            for (Integer i : players) {
                if (!team1.contains(i))
                    team2.add(i);
            }

            // r명씩 나누어진 두 팀의 능력치를 각각 계산한다.
            int point1 = calcPoint(team1, new ArrayList<Integer>());
            int point2 = calcPoint(team2, new ArrayList<Integer>());

            // 각 계산한 능력치의 차이를 구한다.
            int diff = Math.abs(point1-point2);
            if (result==-1) {
                result = diff;
            }
            else { // 이전에 구한 능력치 차이보다 적을 경우, 값을 교체한다.
                result = Math.min(result, diff);
            }
        }
        System.out.println(result);
    }

    // 조합을 구하는 함수
    public static void calcComb(List<Integer> choices, List<Integer> comb, int r) {
        if (comb.size() == r) { // r개를 다 뽑은 경우
            combs.add(comb);
        } else if (choices.isEmpty()) { // 마지막 원소까지 간 경우
            return;
        } else {
//            System.out.println("choices = " + choices);

            // subList가 있네?
            List<Integer> noAddedComb = new ArrayList<>(comb);
//            System.out.println("noAddedComb = " + noAddedComb);

            // 지금 원소 값을 뽑은 경우
            comb.add(choices.get(0)); // 첫 원소를 추가
//            System.out.println("comb added, comb: " + comb);

            // 첫 원소 뺴놓고 다음 재귀로 돌림
            calcComb(choices.subList(1, choices.size()), comb, r);

            // depth 값을 뽑지 않은 경우 (comb.add로 추가하지 않고 넘김)
            calcComb(choices.subList(1, choices.size()), noAddedComb, r);
        }
    }

    // 능력치 합을 구하는 함수 (n combination 2)
    public static int calcPoint(List<Integer> players, List<Integer> comb) {
        if (comb.size() == 2) { // 2개를 다 뽑은 경우 -> 두 사람의 능력치를 더해야 함.
            Integer n1 = comb.get(0);
            Integer n2 = comb.get(1);
            return (matrix[n1-1][n2-1] + matrix[n2-1][n1-1]);
        } else if (players.isEmpty()) { // 마지막 원소까지 간 경우
            return 0;
        } else {
            List<Integer> noAddedComb = new ArrayList<>(comb);

            comb.add(players.get(0)); // 첫 원소를 추가

            return calcPoint(players.subList(1, players.size()), comb) + calcPoint(players.subList(1, players.size()), noAddedComb);
        }
    }

}
