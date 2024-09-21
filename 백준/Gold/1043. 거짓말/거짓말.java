import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.IntStream;

public class Main {

    static List<List<Integer>> parties = new ArrayList<>();
    static int max_lie = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        // 사람 수, 파티 수
        int n, m;

        // 사람 수, 파티 수 입력받기 (1번째 줄 입력)
        StringTokenizer st = new StringTokenizer(reader.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        // 진실을 들은 사람들
        int[] isKnown = new int[n+1];
        int[] confused = new int[n+1];
        IntStream.range(0, n+1)
                .forEach((i) -> {
                    isKnown[i] = 0;
                    confused[i] = 0;
                });

        // 진실을 아는 사람 세팅하기 (2번째 줄 입력)
        st = new StringTokenizer(reader.readLine());
        int num = Integer.parseInt(st.nextToken());
        // 사람 수가 0이 아니면 -> 진실을 아는 사람들 번호를 받아와서 배열에 1로 세팅
        if (num != 0) {
            int tmp;
            while (st.hasMoreTokens()) {
                tmp = Integer.parseInt(st.nextToken());
                isKnown[tmp] = 1;
            }
        }

        // 파티 입력받기 (3번째 줄 이후)
        int people;
        for(int i=0; i<m; i++) {

            st = new StringTokenizer(reader.readLine());
            people = Integer.parseInt(st.nextToken());

            List<Integer> party = new ArrayList<>();
            for(int x=0; x<people; x++) {
                party.add(Integer.valueOf(st.nextToken()));
            }

            parties.add(party);

        }
        
        dfs(0, parties.size(), isKnown, confused, 0);
        System.out.println(max_lie);

    }

    static void dfs(int idx, int length, int[] isKnown, int[] confused, int lieNum) {

        if (idx == length) {
            if (lieNum > max_lie)
                max_lie = lieNum;
            return;
        }

        List<Integer> party = parties.get(idx);

        boolean canTruth = checkToTell(party, confused);
        boolean canLie = checkToTell(party, isKnown);

        if(canTruth) {
            int[] next_known = isKnown.clone();
            updateArr(party, next_known);
            dfs(idx+1, length, next_known, confused, lieNum);
        }

        if (canLie) {
            int[] next_confused = confused.clone();
            updateArr(party, next_confused);
            dfs(idx+1, length, isKnown, next_confused, lieNum+1); // 거짓말하면 lieNum 1 증가
        }

    }

    static void updateArr(List<Integer> party, int[] arr) {
        for (Integer p : party)
            arr[p] = 1;
    }

    static boolean checkToTell(List<Integer> party, int[] arr) {
        for (Integer p : party) {
            if (arr[p] == 1)
                return false;
        }
        return true;
    }

}
