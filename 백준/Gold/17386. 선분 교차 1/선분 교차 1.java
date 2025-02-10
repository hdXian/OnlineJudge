import java.util.*;
import java.io.*;
import java.math.*;

public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int x1, y1;
        int x2, y2;
        int x3, y3;
        int x4, y4;

        StringTokenizer tkn = new StringTokenizer(reader.readLine());
        x1 = Integer.parseInt(tkn.nextToken());
        y1 = Integer.parseInt(tkn.nextToken());
        x2 = Integer.parseInt(tkn.nextToken());
        y2 = Integer.parseInt(tkn.nextToken());

        tkn = new StringTokenizer(reader.readLine());
        x3 = Integer.parseInt(tkn.nextToken());
        y3 = Integer.parseInt(tkn.nextToken());
        x4 = Integer.parseInt(tkn.nextToken());
        y4 = Integer.parseInt(tkn.nextToken());

        // CCW (두 벡터와 동시에 수직인 벡터를 구하는 알고리즘. Counter Clock Wise) 개념 선행 필요
        Point A = new Point(x1, y1);
        Point B = new Point(x2, y2);

        Point C = new Point(x3, y3);
        Point D = new Point(x4, y4);

        // 선분 AB 기준에서의 선분 CD에 대한 방향성 검사
        BigInteger dAB = CCW(A, B, C).multiply(CCW(A, B, D)); // 서로 다른 방향이어야 (곱한 값이 0 이하여야) 교차

        // 선분 CD 기준에서의 선분 AD에 대한 방향성 검사
        BigInteger dCD = CCW(C, D, A).multiply(CCW(C, D, B)); // 서로 다른 방향이어야 (곱한 값이 0 이하여야) 교차

        // 양쪽 선분 모두 ccw를 돌린 결과가 0 이하여야 교차하는 것.
        if (dAB.compareTo(BigInteger.ZERO) < 0 && dCD.compareTo(BigInteger.ZERO) < 0) {
            System.out.println(1);
        }
        // 두 선분이 일직선 상에 있는 경우
        else if (dAB.compareTo(BigInteger.ZERO) == 0 && dCD.compareTo(BigInteger.ZERO) == 0) {
            // CD의 최소 x 좌표가 AB의 최대 x좌표보다 작거나 같아야 한다.
            // CD의 최소 y 좌표가 AB의 최대 y좌표보다 작거나 같아야 한다.
            // CD의 최대 x가 AB의 최소 x보다 크거나 같아야 한다.
            // CD의 최대 y가 AB의 최소 y보다 크거나 같아야 한다.
            if (Math.max(A.x, B.x) >= Math.min(C.x, D.x) &&
                    Math.max(C.x, D.x) >= Math.min(A.x, B.x) &&
                    Math.max(A.y, B.y) >= Math.min(C.y, D.y) &&
                    Math.max(C.y, D.y) >= Math.min(A.y, B.y)) {
                System.out.println(1);
            } else {
                System.out.println(0);
            }
        }
        else {
            System.out.println(0);
        }

    }

    // 수직 벡터를 구하는 알고리즘
    public static BigInteger CCW(Point A, Point B, Point C) {
        // (x2-x1)(y3-y1) - (y2-y1)(x3-x1)
        BigInteger n1 = BigInteger.valueOf(B.x - A.x);
        BigInteger n2 = BigInteger.valueOf(C.y - A.y);

        BigInteger n3 = BigInteger.valueOf(B.y - A.y);
        BigInteger n4 = BigInteger.valueOf(C.x - A.x);

        return n1.multiply(n2).subtract(n3.multiply(n4));
    }

    static class Point {
        int x, y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

}
