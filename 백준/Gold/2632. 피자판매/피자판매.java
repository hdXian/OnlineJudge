import java.util.*;
import java.math.*;
import java.io.*;

public class Main {

    public static int P;
    public static int M, N;

    // 만들 수 있는 경우의 수를 저장
    public static int[] tableA;
    public static int[] tableB;

    public static int[] piecesA;
    public static int[] piecesB;

    public static void main(String[] args) throws Exception {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer tkn = new StringTokenizer(reader.readLine());

        P = Integer.parseInt(tkn.nextToken()); // 주문 피자 크기 (1 ~ 200만)

        tableA = new int[P+1];
        tableB = new int[P+1];
        tableA[0] = 1;
        tableB[0] = 1;

        tkn = new StringTokenizer(reader.readLine());
        M = Integer.parseInt(tkn.nextToken()); // 피자 A 조각 개수 (3 ~ 1000)
        N = Integer.parseInt(tkn.nextToken()); // 피자 B 조각 개수

        // 피자 A 조각 입력받기
        // ex) 2 2 1 7 2
        piecesA = new int[M];
        int piece;
        for(int i=0; i<M; i++) {
            piece = Integer.parseInt(reader.readLine());
            piecesA[i] = piece;
            if (piece <= P)
                tableA[piece] += 1;
        }

        // 피자 개수별로 나올 수 있는 조합 계산
        calcPizza(piecesA, tableA);

        // 피자 B 조각 입력받기
        piecesB = new int[N];
        for(int i=0; i<N; i++) {
            piece = Integer.parseInt(reader.readLine());
            piecesB[i] = piece;
            if (piece <= P)
                tableB[piece] += 1;
        }

        // 모든 조각을 이어붙이는 경우 계산
        calcPizza(piecesB, tableB);

        long result = 0;
        for(int i=0; i<=P; i++) {
            result += ((long) tableA[i] * tableB[P-i]);
        }

        System.out.println(result);
    }

    public static void calcPizza(int[] pieces, int[] table) {
        int length = pieces.length;

        // 모든 피자 조각을 사용하는 경우 계산
        int tmp = 0;
        for(int k=0; k<length; k++) {
            tmp += pieces[k];
        }
        if (tmp <= P)
            table[tmp] = 1;

        for(int i=2; i<length; i++) {
            int pre_size = 0;

            // 첫번째 조각부터 i개의 조각 이어붙이기
            for(int k=0; k<i; k++) {
                pre_size += pieces[k];
            }
            if (pre_size <= P)
                table[pre_size] += 1;

            for(int start=1; start<length; start++) {
                // j번째 조각부터 i개의 조각 이어붙이기
                int end = (start + i - 1) % length; // 이어붙일 마지막 조각의 인덱스
                int size = pre_size + pieces[end] - pieces[start-1]; // 이전 사이드에서 마지막 인덱스 조각을 더하고, 시작 인덱스의 이전 조각을 뺀다.
                pre_size = size; // 이전 사이드 업데이트
                // 계산된 크기에 해당하는 인덱스에 1 추가
                if (size <= P) {
                    table[size] += 1;
                }

            }

        }

    }
    

}
