import java.io.*;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        // 수열 길이
        int n = Integer.parseInt(reader.readLine());

        // 입력받은 수열 문자 배열로 나누기
        String[] split = reader.readLine().split(" ");

        // 인덱스 1부터 시작하도록 int 배열 새로 선언하기
        int[] arr = new int[n+1];

        for (int i=0; i<n; i++) {
            arr[i+1] = Integer.parseInt(split[i]);
        }

        // s부터 e까지가 팰린드롬인지 저장할 dp 테이블 선언
        boolean[][] table = new boolean[n+1][n+1];

        // 한글자 초기화
        for(int i=1; i<n+1; i++){
            table[i][i] = true;
        }

        // 두글자 초기화
        for(int i=1; i<n; i++) {
            table[i][i+1] = (arr[i] == arr[i+1]);
        }

        // 나머지 계산
        for(int i=3; i<n+1; i++) {
            for(int j=1; j<=i-2; j++) {
                table[j][i] = ((arr[j] == arr[i]) && table[j+1][i-1]);
            }
        }

        int m = Integer.parseInt(reader.readLine());
        StringTokenizer tokenizer;
        int start, end;

        for(int i=0; i<m; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            start = Integer.parseInt(tokenizer.nextToken());
            end = Integer.parseInt(tokenizer.nextToken());
            if(table[start][end])
                writer.write(1 + "\n");
            else
                writer.write(0 + "\n");

        }

        writer.flush();
        writer.close();
    }

}
