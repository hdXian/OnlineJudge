import java.util.*;
import java.io.*;
import java.math.*;

public class Main {

    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static int N, C, M;

    static List<Map<Integer, Integer>> town = new ArrayList<>(); // 마을
    static int[] posts; // 현재 트럭의 목적지별 용량

    static void init() throws Exception {
        StringTokenizer tkn = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(tkn.nextToken()); // 마을 수 (2 ~ 2000)

        C = Integer.parseInt(tkn.nextToken()); // 트럭 용량 (1 ~ 10000)

        M = Integer.parseInt(reader.readLine()); // 정보 개수 (1 ~ 10000)

        posts = new int[N+1]; // 인덱스 1부터 쓰기
        // 각 마을의 택배 정보를 저장할 map 초기화
        for(int i=0; i<N-1; i++) {
            town.add(new HashMap<>());
        }

        // 마을의 택배 정보 저장
        int src, dst, amount;
        for(int i=0; i<M; i++) {
            tkn = new StringTokenizer(reader.readLine());
            src = Integer.parseInt(tkn.nextToken());
            dst = Integer.parseInt(tkn.nextToken());
            amount = Integer.parseInt(tkn.nextToken());
            town.get(src-1).put(dst, amount);
        }
    }

    static void calc() throws Exception {
        int cap = 0;
        int count = 0; // 총 배송 수

        // 1. 가장 가까운 마을 순으로 최대한 상자를 담는다.
        Map<Integer, Integer> t;

        // 각 마을을 순회
        for(int i=1; i<=N-1; i++) {
            // 현재 마을이 목적지인 짐을 모두 내린다.
            count += posts[i];
            cap -= posts[i];
            posts[i] = 0;

            // 현재 마을의 택배 목록을 가져온다.
            t = town.get(i-1); // (2, 10), (3, 20), ...

            // 현재 마을의 목적지 배열
            List<Integer> keyL = new ArrayList<>(t.keySet());

            // 목적지들을 가까운 순 (오름차순)으로 정렬
            keyL.sort(Comparator.naturalOrder());

            // 목적지들에 대해 트럭에 담을 수 있을만큼 담기
            for (Integer key: keyL) {
                int amount = t.get(key);
                if (cap + amount <= C) {
                    cap += amount;
                    posts[key] += amount;
                }
                else { // 넘치면 가득 찰 만큼만 트럭에 담음
                    posts[key] += C - cap;
                    cap = C;
                }
            }

        }

        // 마지막 마을에 짐을 배송한다.
        count += posts[N];
        System.out.println(count);
    }

    public static void main(String[] args) throws Exception {
        init();
        calc();
    }

}
