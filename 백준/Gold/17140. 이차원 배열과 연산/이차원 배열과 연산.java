import java.io.*;
import java.util.*;
import java.math.*;

public class Main {

    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    static int R, C, K;

    static int[][] matrix;
    static int N, M;

    static class NumCount implements Comparable<NumCount> {
        public int number;
        public int count;
        public NumCount(int number, int count) {
            this.number = number;
            this.count = count;
        }

        @Override
        public int compareTo(NumCount nc) {
            if (this.count == nc.count) return Integer.compare(this.number, nc.number);
            return Integer.compare(this.count, nc.count);
        }

    }

    static void init() throws Exception {
        StringTokenizer tkn = new StringTokenizer(reader.readLine());
        R = Integer.parseInt(tkn.nextToken()); // 1 ~ 100
        C = Integer.parseInt(tkn.nextToken()); // 1 ~ 100
        K = Integer.parseInt(tkn.nextToken()); // 1 ~ 100
        // A[r][c] = k가 되는 가장 빠른 시간 구하기

        matrix = new int[100][100]; // 행, 열 최대 길이는 100
        for(int i=0; i<3; i++) {
            tkn = new StringTokenizer(reader.readLine());
            for(int j=0; j<3; j++) {
                matrix[i][j] = Integer.parseInt(tkn.nextToken());
            }
        }
        N = M = 3;
    }

    static int[] sortLine(Map<Integer, Integer> countMap) {
        PriorityQueue<NumCount> pq = new PriorityQueue<>();
        for(int key: countMap.keySet()) {
            pq.add(new NumCount(key, countMap.get(key)));
        }

        int[] result = new int[100];
        Arrays.fill(result, 0);

        int seq = 0;
        while(!pq.isEmpty()) {
            NumCount nc = pq.poll();
            result[seq++] = nc.number;
            result[seq++] = nc.count;
            if (seq>=100) break; // 100 이후의 행이나 열은 버림
        }

        return result;
    }

    // 행 정렬하기
    static int[][] calc_r() {
        Map<Integer, Integer> countMap;

        int col_max = M;
        // 각 행에서 각 숫자의 출현 갯수를 세고, 등장 횟수 혹은 숫자 순으로 정렬해서 새로이 행을 만든다.
        for(int i=0; i<N; i++) {

            int[] line = matrix[i];
            countMap = new HashMap<>();

            for(int j=0; j<M; j++) {
                int num = line[j];
                if (num == 0) continue;

                if (!countMap.containsKey(num)) countMap.put(num, 1); // 없으면 새로 생성
                else countMap.put(num, countMap.get(num)+1); // 있으면 1 증가
            }

            int[] new_line = sortLine(countMap);
            matrix[i] = new_line;

            col_max = Math.max(col_max, countMap.size()*2); // 행 최대 길이 업데이트
            col_max = Math.min(col_max, 100);
        }

        M = col_max;

        return matrix;
    }

    // 열 정렬하기
    static int[][] calc_c() {
        Map<Integer, Integer> countMap;

        int row_max = N;
        // 각 열에서 각 숫자의 출현 갯수를 세고, 등장 횟수 혹은 숫자 순으로 정렬해서 새로이 열을 만든다.
        for(int i=0; i<M; i++) {

            int[] col_line = new int[100];

            // 행 번호를 늘려가며 col_line 채우기
            for(int r=0; r<N; r++) {
                col_line[r] = matrix[r][i];
            }
            countMap = new HashMap<>();

            // col_line의 크기는 행의 크기
            for(int j=0; j<N; j++) {
                int num = col_line[j];
                if (num == 0) continue;

                if (!countMap.containsKey(num)) countMap.put(num, 1); // 없으면 새로 생성
                else countMap.put(num, countMap.get(num)+1); // 있으면 1 증가
            }

            int[] new_line = sortLine(countMap);

            for(int r=0; r<100; r++) {
                matrix[r][i] = new_line[r];
            }

            row_max = Math.max(row_max, countMap.size()*2); // 행 최대 길이 업데이트
            row_max = Math.min(row_max, 100);
        }

        N = row_max;

        return matrix;
    }

    static int calc() {

        int time_count = 0;

        for(int i=0; i<=100; i++) {
            if (matrix[R-1][C-1] == K) return time_count;

            if (N >= M) {
                matrix = calc_r();
            }
            else {
                matrix = calc_c();
            }

            time_count++;
        }

        return -1;

    }

    public static void main(String[] args) throws Exception {
        init();
        int result = calc();
        System.out.println(result);
    }

}
