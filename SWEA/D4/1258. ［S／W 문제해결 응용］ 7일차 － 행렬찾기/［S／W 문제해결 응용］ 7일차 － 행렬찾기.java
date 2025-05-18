import java.util.*;
import java.io.*;
import java.math.*;

class Solution {

    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    static class SubMatrix implements Comparable<SubMatrix> {
        public int row;
        public int col;
        public int size;
        public SubMatrix(int row, int col) {
            this.row = row;
            this.col = col;
            this.size = row * col;
        }

        @Override
        public int compareTo(SubMatrix s) {
            if (this.size == s.size) return Integer.compare(this.row, s.row); // 크기가 같으면 행이 작은 순
            return Integer.compare(this.size, s.size);
        }

    }

    static SubMatrix findMatrix(int r, int c, int[][] matrix, boolean[][] visited, int n) {
        int row_edge = r;
        int col_edge = c;
        while(row_edge < n && matrix[row_edge][c] != 0) row_edge++;
        while(col_edge < n && matrix[r][col_edge] != 0) col_edge++;

        for(int i=r; i<row_edge; i++) {
            for(int j=c; j<col_edge; j++) {
                visited[i][j] = true;
            }
        }

        return new SubMatrix(row_edge-r, col_edge-c);
    }

    static String calc(int[][] matrix, int n) {
        // 1. visited 배열을 만든다.
        boolean[][] visited = new boolean[n][n];
        for(boolean[] v: visited) Arrays.fill(v, false);

        PriorityQueue<SubMatrix> pq = new PriorityQueue<>();

        // 2. 행렬을 순회하다가 방문한 적 없으면서 0이 아닌 요소를 만나면 왼쪽, 아래쪽으로 행렬을 탐색한다.
        for(int i=0; i<n; i++) {

            for(int j=0; j<n; j++) {
                if (matrix[i][j] != 0 && !visited[i][j]) {
                    SubMatrix tmp = findMatrix(i, j, matrix, visited, n);
                    pq.add(tmp);
                }
            }

        }

        // 3. 방문을 마친 행렬을 객체 형태로 만들어 저장한다.
        // 4. 조건에 맞춰서 행렬의 행과 열을 출력한다.
        StringBuilder sb = new StringBuilder();
        sb.append(pq.size()).append(" ");
        while(!pq.isEmpty()) {
            SubMatrix tmp = pq.poll();
            sb.append(tmp.row).append(" ").append(tmp.col).append(" ");
        }

        return sb.toString();
    }

    public static void main(String[] args) throws Exception {
        int T = Integer.parseInt(reader.readLine());

        int n;
        StringTokenizer tkn;

        StringBuilder sb = new StringBuilder();
        for (int test_case = 1; test_case <= T; test_case++) {
            n = Integer.parseInt(reader.readLine());

            int[][] matrix = new int[n][n];
            for(int i=0; i<n; i++) {
                tkn = new StringTokenizer(reader.readLine());
                for(int j=0; j<n; j++) {
                    matrix[i][j] = Integer.parseInt(tkn.nextToken());
                }
            }

            String result = calc(matrix, n);
            sb.append(String.format("#%d %s\n", test_case, result));
        }

        System.out.println(sb);
    }

}
