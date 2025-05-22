import java.util.*;
import java.io.*;
import java.math.*;

class Solution {

    static BufferedReader reader;

    static String[] binMap = {
            "0000", "0001", "0010", "0011",
            "0100", "0101", "0110", "0111",
            "1000", "1001", "1010", "1011",
            "1100", "1101", "1110", "1111"
    };

    static int[][][][] codeMap;

    static String toBinaryLine(String line) {
        StringBuilder sb = new StringBuilder();

        char[] carr = line.toCharArray();
        for(char ch: carr) {
            int i = Integer.valueOf("" + ch, 16);
            sb.append(binMap[i]);
        }

        return sb.toString();
    }

    static List<String> findCode(int n, int m) throws Exception {

        Set<String> codes = new HashSet<>();

        String line;
        String pre_line = "";
        String binary_line;
        char[] line_arr;
        int ptrn1, ptrn2, ptrn3, ptrn4;
        int blength; // 이진수로 표현된 한 줄의 길이

        for (int i=0; i<n; i++) {
            // 16진수들을 2진수로 바꾼다.
            line = reader.readLine();
            
            // 이전의 라인과 같으면 건너뛴다.
            if (line.equals(pre_line)) continue;

            binary_line = toBinaryLine(line);
            line_arr = binary_line.toCharArray();
            blength = line_arr.length; // 이거 쓰면 m이 필요없긴 하네.

            for(int k = blength-1; k>=0; k--) {

                // 지금부터 각 줄마다 0, 1의 변하는 횟수를 찾아야 함.
                // 뒤에서부터 처음 1이 등장하는 인덱스를 찾는다.
                if (line_arr[k] == '1') {
                    int[] code = new int[8];

                    // 모든 암호 코드의 2~3번째 영역에는 길이 1짜리 영역이 포함되어있음.
                    // 그 말은 즉, 어떤 암호 코드가 K배 두꺼워진 상태라고 했을 때, 위에서 구한 세 영역중 가장 작은 값은
                    // 길이 1짜리 영역이 K배 두꺼워진, K 길이의 영역을 의미함.
                    // 즉, 우리는 위에서 구한 세 영역 중 가장 작은 값이 K라는 사실을 이용해
                    // 각 영역 길이를 K로 나누면 두께 1짜리의 암호 코드를 구할 수 있음.
                    
                    // 뒤에서부터 코드를 찾아 넣으므로 거꾸로 코드 채워넣기
                    for(int seq = 7; seq>=0; seq--) {
                        ptrn4 = ptrn3 = ptrn2 = 0;
                        while(line_arr[k] == '1') {ptrn4++; k--;}; // 4번째 영역의 길이
                        while(line_arr[k] == '0') {ptrn3++; k--;}; // 3번째 영역의 길이
                        while(line_arr[k] == '1') {ptrn2++; k--;}; // 2번째 영역의 길이

                        // 즉, 우리는 위에서 구한 세 영역 중 가장 작은 값이 K라는 사실을 이용해
                        // 각 영역 길이를 K로 나누면 두께 1짜리의 암호 코드를 구할 수 있음.
                        int depth = Math.min(Math.min(ptrn4, ptrn3), ptrn2);
                        ptrn4 /= depth;
                        ptrn3 /= depth;
                        ptrn2 /= depth;
                        ptrn1 = 7 - (ptrn2 + ptrn3 + ptrn4); // ptrn1 영역의 길이는 다른 영역들을 통해 구하기
                        k -= ptrn1 * depth; // k는 두꺼워진 암호 코드 두께만큼 앞으로 당겨야 함
                        code[seq] = codeMap[ptrn1][ptrn2][ptrn3][ptrn4];
                    }

                    StringBuilder sb = new StringBuilder();
                    for(int c: code) sb.append(c);
                    codes.add(sb.toString());
                }

            }

        }

        return new ArrayList<>(codes);
    }

    static int calc(List<String> codes) {
        int result = 0;

        for(String code: codes) {
            int[] intCode = new int[8];
            char[] chCode = code.toCharArray();
            for(int i=0; i<8; i++) intCode[i] = chCode[i] - '0';

            int odd = 0;
            int even = 0;
            for(int i=0; i<8; i++) {
                if (i%2==0) odd += intCode[i];
                else even += intCode[i];
            }

            result += ((odd*3) + even) % 10 == 0 ? (odd + even) : 0;

        }

        return result;
    }

    public static void main(String[] args) throws Exception {

        // System.setIn(new FileInputStream("res/input.txt"));
        reader = new BufferedReader(new InputStreamReader(System.in));

        codeMap = new int[5][5][5][5];
        codeMap[3][2][1][1] = 0;
        codeMap[2][2][2][1] = 1;
        codeMap[2][1][2][2] = 2;
        codeMap[1][4][1][1] = 3;
        codeMap[1][1][3][2] = 4;
        codeMap[1][2][3][1] = 5;
        codeMap[1][1][1][4] = 6;
        codeMap[1][3][1][2] = 7;
        codeMap[1][2][1][3] = 8;
        codeMap[3][1][1][2] = 9;

        int T = Integer.parseInt(reader.readLine());

        // 1. 한 줄씩 읽어가며 라인을 2진수로 변환한다.
        // 2. 모든 암호문은 '1'로 끝나므로 뒤에서부터 '1'을 탐색한다.
        // 3. 암호 코드의 영역별 길이를 측정한 다음 두께 1짜리로 줄여 코드로 변환한다.
        // 4. 암호 코드의 정상 여부를 판단한 다음, 정상 암호코드에 적혀있는 숫자들의 합을 출력한다.
        StringBuilder sb = new StringBuilder();

        StringTokenizer tkn;
        int N, M; // 세로, 가로
        for (int test_case = 1; test_case <= T; test_case++) {
            tkn = new StringTokenizer(reader.readLine()); // N, M 입력받기
            N = Integer.parseInt(tkn.nextToken());
            M = Integer.parseInt(tkn.nextToken());
            List<String> codes = findCode(N, M);

            int result = calc(codes);
            sb.append(String.format("#%d %d\n", test_case, result));
        }

        System.out.println(sb);
    }
}
