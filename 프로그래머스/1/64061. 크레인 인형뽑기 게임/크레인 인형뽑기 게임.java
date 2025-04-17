import java.io.*;
import java.util.*;

class Solution {
    public int solution(int[][] board, int[] moves) {
        int N = board.length; // 격자 크기
        
        // 1. 각 번호별로 스택을 선언하고 인형을 채운다.
        List<Stack<Integer>> sts = new ArrayList<>();
        for(int i=0; i<N; i++)
            sts.add(new Stack<Integer>());
        
        int[] tmp;
        for(int i=N-1; i>=0; i--) {
            tmp = board[i];
            for(int j=0; j<N; j++) {
                if (tmp[j] != 0)
                    sts.get(j).push(tmp[j]);
            }
        }
        
        // 2. 바구니를 선언하고 moves에 대해 시뮬레이션을 돌린다.
        Stack<Integer> bucket = new Stack<>();
        int count = 0;
        int idx, cur;
        for(int m: moves) {
            idx = m-1;
            // 뽑을 인형이 있다면 (해당 스택이 비어있지 않다면)
            if (!sts.get(idx).isEmpty()) {
                // 일단 인형을 뽑는다.
                cur = sts.get(idx).pop();
                // 바구니의 top 요소와 같다면 바구니의 인형을 제거한다.
                if (!bucket.isEmpty() && (cur == bucket.peek())) {
                    bucket.pop();
                    count += 2;
                }
                else {
                    bucket.push(cur);
                }
            }
            // 뽑을 인형이 없다면 아무것도 하지 않는다.
        }
        
        
        int answer = count;
        return answer;
    }
}