import java.util.*;
import java.io.*;
import java.math.*;

public class Main {

    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    static int N;
    static boolean[][] board;

    static int total_score;

    static void init() throws Exception {
        N = Integer.parseInt(reader.readLine()); // 블록을 놓은 횟수. 1 ~ 1만.
        board = new boolean[10][10];
        for(int i=0; i<10; i++) Arrays.fill(board[i], false);
        total_score = 0;
    }

    static void moveBlock(int t, int row, int col) {
        // 조건을 만족할 때까지 col을 이동시킨 뒤 블록을 배치시킨다.
        int br, bc; // 파랑 영역 (조건을 만족할 떄까지 col을 이동)
        int gr, gc; // 초록 영역 (조건을 만족랄 때까지 row를 이동)

        br = gr = row;
        bc = gc = col;

        // 한칸짜리
        if (t == 1) {
            // 파랑 처리
            while(bc<10 && !board[br][bc] ) bc++;
            board[br][bc-1] = true;

            // 초록 처리
            while(gr<10 && !board[gr][gc]) gr++;
            board[gr-1][gc] = true;
        }
        // 가로로 긴 블록
        else if (t == 2) {
            // 파랑 처리
            while((bc+1 < 10) && !board[br][bc+1]) bc++;
            bc--;
            board[br][bc] = true;
            board[br][bc+1] = true;

            // 초록 처리
            while(gr<10 && !board[gr][gc] && !board[gr][gc+1]) gr++;
            gr--;
            board[gr][gc] = true;
            board[gr][gc+1] = true;
        }
        // 세로로 긴 블록
        else {
            // 파랑 처리
            while(bc<10 && !board[br][bc] && !board[br+1][bc]) bc++;
            bc--;
            board[br][bc] = true;
            board[br+1][bc] = true;

            // 초록 처리
            while((gr+1<10) && !board[gr+1][gc]) gr++;
            gr--;
            board[gr][gc] = true;
            board[gr+1][gc] = true;
        }

    }

    // 해당 행이 가득 찼는지 체크
    static boolean isFilledCol(int col) {
        for(int r=0; r<4; r++) {
            if(!board[r][col]) return false;
        }
        return true;
    }

    // 연한 파랑 영역 확인 (해당 열에 블록이 하나라도 있으면 true)
    static boolean checkEdgeCol(int col) {
        for(int r=0; r<4; r++) if(board[r][col]) return true;
        return false;
    }

    // 지정한 열의 데이터를 제거하고, 오른쪽으로 한 칸씩 끌어오는 메서드
    static void removeCol(int col) {
        while(col >= 4) {
            for(int r=0; r<4; r++)
                board[r][col] = board[r][col-1];

            col--;
        }
    }

    // 파랑 영역의 각 열에 꽉 차 있는지 확인하고, 꽉 차있을 경우 제거
    static void removeBlueBlock() {
        // 1. 파랑 영역의 블록 제거
        for(int col=9; col>=6; col--) {
            while(isFilledCol(col)) {
                removeCol(col);
                total_score++;
            }
        }

        // 2. 연한 파랑 영역의 블록 확인
        int push_count = 0;
        for(int col=4; col<=5; col++) {
            if (checkEdgeCol(col)) push_count++;
        }

        // 연한 파랑 영역이 차있는 만큼 열을 오른쪽으로 밀어내기 (9열을 지우고 오른쪽으로 당긴다)
        for(int i=0; i<push_count; i++) removeCol(9);
    }

    static boolean isFilledRow(int row) {
        for(int c=0; c<4; c++) if (!board[row][c]) return false;
        return true;
    }

    static boolean checkEdgeRow(int row) {
        for(int c=0; c<4; c++) {
            if (board[row][c]) return true; // 하나라도 있으면 true
        }
        return false;
    }

    static void removeRow(int row) {
        while(row >= 4) {
            for(int c=0; c<4; c++) board[row][c] = board[row-1][c];
            row--;
        }
    }

    static void removeGreenBlock() {
        // 1. 초록 영역의 블록 제거
        for(int row=9; row>=6; row--) {
            while(isFilledRow(row)) {
                removeRow(row);
                total_score++;
            }
        }

        // 2. 연한 초록 영역의 블록 확인
        int push_count = 0;
        for(int row=4; row<=5; row++) {
            if (checkEdgeRow(row)) push_count++;
        }

        for(int i=0; i<push_count; i++) removeRow(9); // 연한 초록 영역이 차있는 만큼 마지막 행을 제거
    }

    static int countBlock() {
        int counts = 0;

        for(int c=6; c<=9; c++) {
            for(int r=0; r<4; r++) if(board[r][c]) counts++;
        }

        for(int r=6; r<=9; r++) {
            for(int c=0; c<4; c++) if (board[r][c]) counts++;
        }

        return counts;
    }

    static void calc(int t, int x, int y) {
        // 1. t에 따라 블록 형태를 정하고, 보드에 배치한다.
        moveBlock(t, x, y);

        // 2. 파랑, 초록 영역의 블록들을 제거한다.
        removeBlueBlock();
        removeGreenBlock();

    }

    public static void main(String[] args) throws Exception {
        init();

        StringTokenizer tkn;
        int t, x, y;
        for(int i=0; i<N; i++) {
            tkn = new StringTokenizer(reader.readLine());
            t = Integer.parseInt(tkn.nextToken());
            x = Integer.parseInt(tkn.nextToken());
            y = Integer.parseInt(tkn.nextToken());
            calc(t, x, y);
        }

        System.out.printf("%d\n%d\n", total_score, countBlock());
    }

}
