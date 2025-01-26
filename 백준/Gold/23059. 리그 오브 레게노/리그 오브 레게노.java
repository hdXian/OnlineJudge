import java.util.*;
import java.io.*;

public class Main {

    public static int N;
    public static Map<String, Integer> map = new HashMap<>();
    public static Map<Integer, String> reverseMap = new HashMap<>();

    public static List<List<Integer>> edges = new ArrayList<>();
    public static int[] degree;
    public static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws Exception {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(reader.readLine()); // 1 ~ 20만
        degree = new int[N*2+1];
        Arrays.fill(degree, 0);

        StringTokenizer tkn;
        String A, B;
        int seq = 0; // 사람 번호는 1부터 시작

        // 선후관계 입력받기
        for(int i=0; i<N; i++) {
            tkn = new StringTokenizer(reader.readLine());

            A = tkn.nextToken();
            B = tkn.nextToken();

            // 처음 등장한 아이템이면 map에 추가, 새로운 edges (인접 노드 리스트) 추가
            if (!map.containsKey(A)) {
                map.put(A, ++seq);
                edges.add(new ArrayList<>()); // 1번 사람의 인접 노드들 List -> edges.get(0);
            }

            if (!map.containsKey(B)) {
                map.put(B, ++seq);
                edges.add(new ArrayList<>());
            }

            // A를 먼저 구매해야 B를 구매할 수 있다. -> A의 인접 노드에 B 추가, B의 진입 차수 1 증가
            Integer na = map.get(A);
            Integer nb = map.get(B);

            edges.get(na-1).add(nb); // A의 인접 노드에 B 추가
            degree[nb]++; // B의 진입 차수 1 증가
        }

        // 번호를 key로 가진 반대 Map 생성
        for(String key: map.keySet()) {
            Integer val = map.get(key);
            reverseMap.put(val, key);
        }

        topologySort();

        boolean flag = true;
        for(Integer val: map.values()) {
            if (degree[val] != 0) {
                System.out.println(-1);
                flag = false;
                break;
            }
        }

        if (flag)
            System.out.println(sb);

    }

    // map -> Key: String, Value: Integer
    // reverseMap -> Key: Integer, Value: String
    // 위상 정렬
    public static void topologySort() {
        // 진입 차수가 0인 노드들끼리의 순서는 사전 순이다.

        // 진입 차수가 0인 노드들을 처음에 받는다.
        List<String> zlist = new ArrayList<>();
        for (Integer value : map.values()) {
            if (degree[value] == 0)
                zlist.add(reverseMap.get(value));
        }

        Collections.sort(zlist);
        Queue<String> q = new LinkedList<>(); // pq로 정렬한 노드들로 큐를 구성
        for(String item: zlist) {
            q.add(item);
        }

        int length;
        while (!q.isEmpty()) {
            length = q.size();

            List<String> sortList = new ArrayList<>();

            // 진입 차수가 0인 요소를 꺼낸다.
            for(int i=0; i<length; i++) {
                String item = q.poll();
                sb.append(item).append("\n");

                Integer num = map.get(item); // 해당 아이템의 번호 가져오기
                List<Integer> nodes = edges.get(num - 1); // 해당 아이템 번호의 인접 노드들 번호 가져오기

                // 해당 요소와 연결된 노드들의 진입 차수를 1 감소시킨다.
                for (Integer n: nodes) {
                    degree[n]--;
                    if (degree[n] == 0) {
                        sortList.add(reverseMap.get(n)); // key가 번호인 map에서 아이템 이름을 가져온다.
                    }
                }
            }

            Collections.sort(sortList);

            for(String item: sortList) {
                q.add(item);
            }

        }

    }


}
