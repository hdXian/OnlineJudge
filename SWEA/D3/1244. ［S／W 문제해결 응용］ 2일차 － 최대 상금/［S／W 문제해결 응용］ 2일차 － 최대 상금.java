import java.util.*;
import java.io.*;
import java.math.*;

public class Solution {

    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    static int T;
    static int N;
    static Integer result;
    static int[] arr;

    static void swap(int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    static void comb(int start, int depth) {
        int alength = arr.length;

        if (depth == N) {
            int tmp = 0;
            for(int i=0; i<alength; i++) tmp += (int) (arr[i] * Math.pow(10, alength-i-1));
            result = Math.max(result, tmp);
        }
        else {
            for (int i=start; i<alength; i++) {
                for (int j=i+1; j<alength; j++) {
                    swap(i, j);
                    comb(i,depth+1);
                    swap(i, j); // 백트래킹
                }
            }
        }

    }

    static String calc(int[] cards, int n) {
        result = -1;
        N = n;
        arr = cards;
        comb(0, 0);
        return result.toString();
    }

    public static void main(String[] args) throws Exception {

        T = Integer.parseInt(reader.readLine()); // 테스트케이스 개수. 최대 10.

        //System.setIn(new FileInputStream("res/input.txt"));
		/*
		   여러 개의 테스트 케이스가 주어지므로, 각각을 처리합니다.
		*/

        StringBuilder sb = new StringBuilder();
        StringTokenizer tkn;
        String card;
        int n;

        for (int i = 1; i <= T; i++) {
            tkn = new StringTokenizer(reader.readLine());

            card = tkn.nextToken();
            n = Integer.parseInt(tkn.nextToken());

            int clength = card.length();
            int[] cards = new int[clength];
            for(int k=0; k<clength; k++) cards[k] = Character.getNumericValue(card.charAt(k));

            if (n > clength) n = clength;

            String result = calc(cards, n);
            sb.append("#").append(i).append(" ").append(result).append('\n');
        }

        System.out.println(sb);
    }

}
