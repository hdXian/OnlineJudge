import java.util.*;
import java.io.*;
import java.math.*;

class Solution {

    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static String[] codes = {
            "0001101", // 0
            "0011001",
            "0010011",
            "0111101",
            "0100011",
            "0110001",
            "0101111",
            "0111011",
            "0110111",
            "0001011" // 9
    };

    static int parse(String code) {
        for(int i=0; i<=9; i++) {
            if (codes[i].equals(code)) return i;
        }
        return -1;
    }

    static int calc(String codeLine) {
//        System.out.println("codeLine = " + codeLine);
//        System.out.println("codeLine.length() = " + codeLine.length());
        int[] num = new int[8];
        int seq = 0;
        int total = 0;
        String code;
        for(int i=0; i<56; i+=7) {
            code = codeLine.substring(i, i+7);
            num[seq] = parse(code);
            total += num[seq];
            seq++;
        }

        int odd = 0;
        int even = 0;
        for(int i=0; i<8; i++) {
            if (i%2==0) odd += num[i]; // 인덱스가 짝수면 자리 번호는 홀수
            else even += num[i];
        }

        return ((odd*3 + even) % 10 == 0) ? total : 0;
    }

    public static void main(String[] args) throws Exception {

        int T;
        T = Integer.parseInt(reader.readLine());

        int N, M; // 세로, 가로
        StringTokenizer tkn;
        StringBuilder sb = new StringBuilder();

        String line;
        char[] tmp;
        int last_idx; // '1'이 등장하는 마지막 인덱스 저장용
        for(int test_case = 1; test_case <= T; test_case++) {
            tkn = new StringTokenizer(reader.readLine());
            N = Integer.parseInt(tkn.nextToken()); // 세로
            M = Integer.parseInt(tkn.nextToken()); // 가로

            // 하나의 테케동안 돌린다.
            last_idx = -1;
            for(int i=0; i<N; i++) {
                // 1. 각 라인을 받는다.
                line = reader.readLine();

                // 2. 받은 라인을 char 배열로 표현한 다음, '1'의 여부를 판단한다.
                tmp = line.toCharArray();

                // 3. '1'이 있는 라인이 암호 코드. '1'이 위치하는 가장 마지막 인덱스를 찾는다.
                for(int k=0; k<M; k++) {
                    if (tmp[k] == '1') last_idx = k;
                }

                // last_idx가 업데이트됐다면 해당 라인이 암호문이 있는 라인.
                if (last_idx != -1) {
//                    System.out.println("라인 찾음: last_idx = " + last_idx);
                    // 4. '1'이 있는 라인에서 ('1'의 마지막 인덱스 ~ 56번 뒤까지 substring으로 뽑는다.)
                    // 5. 뽑은 문자열을 암호 코드로 파싱하고 유효 여부를 검사하여 결과를 리턴한다. (calc)
                    int result = calc(line.substring(last_idx-55, last_idx+1));
                    sb.append(String.format("#%d %d\n", test_case, result));

                    // 남은건 버리기
                    for(int k=i+1; k<N; k++) {
                        reader.readLine();
                        i++;
                    }

                }

            }

        }

        System.out.println(sb);

    }
}
