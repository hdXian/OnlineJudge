import java.util.*;
import java.io.*;

class Solution {

    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    static int clength = 0;
    static int N;
    static String cards;
    static String result;
    static List<Set<String>> visited;

    static void init(String line) {
        StringTokenizer tkn = new StringTokenizer(line);

        cards = tkn.nextToken(); // 숫자 카드 (문자열)
        N = Integer.parseInt(tkn.nextToken()); // 교환 횟수.
        visited = new ArrayList<>();
        for(int i=0; i<N; i++) visited.add(new HashSet<>());

        clength = cards.length();
        result = "";
    }

    static void swap(int a, int b, char[] cardArr) {
        char tmp = cardArr[a];
        cardArr[a] = cardArr[b];
        cardArr[b] = tmp;
    }

    static void dfs(String card, int prei, int depth) {
        if (depth == N) {
            result = (result.compareTo(card) > 0) ? result : card;
            return;
        }

        if (visited.get(depth).contains(card)) return;
        visited.get(depth).add(card);

        for(int i=prei; i<clength; i++) {
            for(int j=i+1; j<clength; j++) {
                char[] cardArr = card.toCharArray();
                swap(i, j, cardArr);
                dfs(new String(cardArr), i, depth+1);
                swap(i, j, cardArr);
            }
        }

    }

    static String calc(int t) {
        dfs(cards, 0, 0);
        return String.format("#%d %s\n", t, result);
    }

    public static void main(String[] args) throws Exception {
        int T;
        T = Integer.parseInt(reader.readLine()); // 테케 개수. 1 ~ 10

        StringBuilder sb = new StringBuilder();
        for (int test_case = 1; test_case <= T; test_case++) {
            String line = reader.readLine();
            init(line); // 카드 배열, 길이, 교환 횟수 초기화
            String result = calc(test_case);
            sb.append(result);
        }
        System.out.println(sb);

    }

}
