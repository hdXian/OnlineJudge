import java.util.*;
import java.io.*;
import java.math.*;

public class Main {

    public static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static int N, L, P;

    static class Station {
        public int distance;
        public int gas;
        public Station(int distance, int gas) {
            this.distance = distance;
            this.gas = gas;
        }
    }

    static class StationComparator implements Comparator<Station> {
        @Override
        public int compare(Station s1, Station s2) {
            return Integer.compare(s2.gas, s1.gas); // 가스가 더 많은 순으로
        }
    }

    public static Queue<Station> stations = new PriorityQueue<>((s1, s2) -> Integer.compare(s1.distance, s2.distance));

    public static void init() throws Exception {
        N = Integer.parseInt(reader.readLine()); // 주유소 개수. 1 ~ 1만

        // 주유소 정보 입력받기
        int a, b;
        StringTokenizer tkn;
        for(int i=0; i<N; i++) {
            tkn = new StringTokenizer(reader.readLine());
            a = Integer.parseInt(tkn.nextToken()); // 시작 위치에서 주유소까지의 거리 (1 ~ 100만)
            b = Integer.parseInt(tkn.nextToken()); // 채울 수 있는 연료 양 (1 ~ 100)
            stations.add(new Station(a, b));
        }

        tkn = new StringTokenizer(reader.readLine());
        L = Integer.parseInt(tkn.nextToken()); // 목적지까지 거리 (1 ~ 100만)
        P = Integer.parseInt(tkn.nextToken()); // 처음에 남은 연료 (1 ~ 100만)
    }

    public static int calc() throws Exception {
        int cur = 0; // 현재 위치
        int remain_gas = P; // 남은 연료
        int remain_distance = L; // 목적지까지 남은 거리
        int count = 0; // 방문하는 주유소 개수

        // 도달할 수 있는 주유소들을 저장할 우선순위 큐
        Queue<Station> pq = new PriorityQueue<>(new StationComparator());

        int max_reachable = 0;
        Station next;
        while (true) {
            // 남은 거리가 남은 연료보다 적거나 같을 경우 즉시 종료.
            if (remain_distance <= remain_gas) break;

            // 도달할 수 있는 최대 거리: 현재 위치 + 남은 가스
            max_reachable = cur + remain_gas;

            // 1. 현재 연료를 가지고 도달할 수 있는 주유소들을 스캔한다.
            while (!stations.isEmpty() && stations.peek().distance <= max_reachable) {
                pq.add(stations.poll());
            }

            // 도달할 수 있는 주유소가 없는 경우 -1을 리턴. (목적지까지 못 감)
            if (pq.isEmpty()) {
                count = -1;
                break;
            }

            // 2. 각 주유소를 들렀을 때, 가장 멀리 갈 수 있는 주유소를 선택한다.
            next = pq.poll(); // pq에서 가장 먼저 뽑혀나온 것이 다음에 방문할 주유소
            remain_gas = remain_gas - (next.distance - cur) + next.gas; // 남은 연료 = (현재 연료 - 다음 주유소까지의 거리 + 다음 주유소에서 얻는 연료)
            cur = next.distance; // 현재 위치를 주유소의 위치로 업데이트
            remain_distance = L - cur; // 남은 거리 = (목적지 - 현재위치)
            count++;
        }

        return count;
    }

    public static void main(String[] args) throws Exception {
        init();
        int result = calc();
        System.out.println(result);
    }

}
