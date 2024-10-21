import java.util.*;
import java.io.*;
import java.math.*;

public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(reader.readLine());
        char[][] matrix = new char[n+1][n+1];

        StringTokenizer tok;
        // 격자 상태
        for(int i=1; i<n+1; i++) {
            tok = new StringTokenizer(reader.readLine());
            for(int j=1; j<n+1; j++) {
                matrix[i][j] = tok.nextToken().charAt(0);
            }
        }

        // dp 테이블
        Point[][] table = new Point[n+1][n+1];

        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= n; j++) {
                table[i][j] = new Point();  // 각 위치에 새로운 Point 객체 생성
            }
        }

        // 각 인덱스에서, 왼쪽, 왼쪽 위, 위에서 온 경우를 각각 더한다.
        // 단, 왼쪽은 가로+대각선, 왼쪽 위는 가로+세로+대각선, 위는 세로+대각선의 경우를 따진다.

        // 시작은 (1,1), (1,2)에 가로로 놓여있음.
        table[1][2].from_hor = 1;

        for(int row=1; row<n+1; row++) {

            for(int col=1; col<n+1; col++) {

                if (matrix[row][col] == '1')
                    continue;

                // 각 인덱스에 해당하는 포인트
                Point tmp = table[row][col];

                // 왼쪽에서 오는 경우 계산 (가로 + 대각선) -> 이동 후 방향은 가로가 됨
                Point left = table[row][col-1];
                tmp.from_hor += left.from_hor;
                tmp.from_hor += left.from_cross;

                // 왼쪽 위에서 오는 경우 계산 (가로 + 세로 + 대각선) -> 이동 후 방향은 대각선이 됨
                Point cross = table[row-1][col-1];
                if (matrix[row][col-1] == '0' && matrix[row-1][col] == '0') {
                    tmp.from_cross += cross.from_hor;
                    tmp.from_cross += cross.from_vert;
                    tmp.from_cross += cross.from_cross;
                }

                // 위에서 오는 경우 계산 (세로 + 대각선) -> 이동 후 방향은 세로가 됨
                Point above = table[row-1][col];
                tmp.from_vert += above.from_vert;
                tmp.from_vert += above.from_cross;

            }

        }
        
        System.out.println(table[n][n].getTotal());

    }

    static class Point {
        // 가로 배치된 파이프로부터 오는 경우
        int from_hor;

        // 세로 배치된 파이프로부터 오는 경우
        int from_vert;

        // 대각선 배치된 파이프로부터 오는 경우
        int from_cross;

        public Point() {
            from_hor = 0;
            from_vert = 0;
            from_cross = 0;
        }

        public int getTotal() {
            return from_hor + from_vert + from_cross;
        }

        @Override
        public String toString() {
            return String.format("from_hor: %d, from_vert: %d, from_cross: %d, total: %d",
                    from_hor, from_vert, from_cross, getTotal());
        }

    }

}
