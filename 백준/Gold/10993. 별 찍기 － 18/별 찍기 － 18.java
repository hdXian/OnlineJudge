import java.util.*;
import java.io.*;
import java.math.*;

public class Main {

    public static int N;
    public static int[] rows = new int[11]; // 단계별 높이
    public static int[] cols = new int[11]; // 단계별 한 변 길이
    public static boolean[][] star;

    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(reader.readLine());

        rows[1] = 1;
        for(int i=2; i<=10; i++) {
            rows[i] = rows[i-1] * 2 + 1;
        }

        cols[1] = 1;
        for(int i=2; i<=10; i++) {
            cols[i] = cols[i-1] * 2 + 3;
        }

        int row = rows[N];
        int col = cols[N];
        star = new boolean[row+1][col+1];
        for(int i=1; i<=row; i++) {
            Arrays.fill(star[i], false);
        }

        fillStar(N, 1, row, 1);

        printStar();

    }

    // n번째에 대해 (시작, 끝) 범위에서 별 채우기
    private static void fillStar(int n, int rowStart, int rowEnd, int colStart) {
        if (n<1)
            return;

        int lineLength = cols[n];
        // col_start = 전에꺼의 col_start에 + 지금 lineLength/2를 더한 인덱스
        // col_end = col_start + lineLength -1
        int col_start = colStart;
        int col_end = colStart + lineLength - 1;

        // 순서가 짝수라면 아래 -> 위로 넓어짐 (마지막 줄에서 시작해 하나 채우기, 그다음에 옆에 채우다가 가장 첫 줄에 한 변 채우기) -> 끝나고 start는 +1, end가 위로 올라옴(idx--)
        if (n%2 == 0) {

            // 제일 윗 변을 꽉 채우기
            for(int c=col_start; c<=col_end; c++) {
                star[rowStart][c] = true;
            }

            // 별 채우기
            for(int row=rowStart; row<=rowEnd; row++) {
                star[row][col_start] = true;
                star[row][col_end] = true;
                col_start++;
                col_end--;
            }

            int nextRowEnd = (rowStart+rowEnd)/2;
            int nextColStart = (colStart +1) + (cols[n-1]/2)+1;
            fillStar(n-1, rowStart+1, nextRowEnd, nextColStart);
        }
        // 순서가 홀수라면 위 -> 아래로 넓어짐 (처음에 하나 채우기, 그다음에 옆에 채우다가 마지막에 한 변 채우기) -> 끝나고 end는 -1, start가 아래로 내려옴(idx++)
        else {
            // 마지막 줄 꽉 채우기
            for(int c=col_start; c<=col_end; c++) {
                star[rowEnd][c] = true;
            }

            // 별 채우기
            for (int row=rowEnd; row>=rowStart; row--) {
                star[row][col_start] = true;
                star[row][col_end] = true;
                col_start++;
                col_end--;
            }

            int nextRowStart = (rowStart+rowEnd)/2;
            int nextColStart = (colStart +1) + (cols[n-1]/2)+1;
            fillStar(n-1, nextRowStart, rowEnd-1, nextColStart);
        }

    }

    private static void printStar() {
        int row = rows[N];
        int col = cols[N];

        StringBuilder totalStar = new StringBuilder();

        StringBuilder starLine;
        int lastIdx;
        for(int r=1; r<=row; r++) {

            starLine = new StringBuilder();
            lastIdx = 1;

            for(int c=1; c<=col; c++) {
                if (star[r][c]) {
                    starLine.append("*");
                    lastIdx = c;
                }
                else
                    starLine.append(" ");
            }

            starLine.delete(lastIdx, col); // 마지막 별이 찍힌 인덱스 이후부터 뒤를 모두 삭제

            starLine.append("\n");
            totalStar.append(starLine);
        }
        totalStar.deleteCharAt(totalStar.length()-1); // 마지막 개행문자 제거

        System.out.print(totalStar);
    }

}
