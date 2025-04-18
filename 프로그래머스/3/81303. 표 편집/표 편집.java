import java.util.*;

class Solution {
    
    static Stack<Integer> del_history = new Stack<>();
    
    static class Node {
        public int prev = 0;
        public int next = 0;
        public Node() {}
        public Node(int prev, int next) {
            this.prev = prev;
            this.next = next;
        }
    }
    
    static Node[] rows;
    
    public String solution(int n, int k, String[] cmd) {
        // n: 전체 행
        // k: 커서 위치
        // cmd: 명령어 배열
        int cur = k;
        
        // 1. 표의 행을 구성하는 Node 배열을 선언한다.
        rows = new Node[n];
        for(int i=0; i<n; i++) {
            rows[i] = new Node(i-1, i+1);
        }
        
        // 2. 차례대로 명령어를 실행한다.
        for(String s: cmd) {
            cur = exec_cmd(cur, s);
        }
        
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<n; i++) sb.append("O");
        
        while (!del_history.isEmpty()) {
            int del = del_history.pop();
            sb.replace(del, del+1, "X"); // del <= (idx) < del+1
        }
        
        String answer = sb.toString();
        return answer;
    }
    
    int exec_cmd(int cur, String command) {
        // U, D, C, Z
        String[] args = command.split(" ");
        String arg1 = args[0];
        int arg2;
        
        if (arg1.equals("U")) {
            arg2 = Integer.parseInt(args[1]);
            for(int i=0; i<arg2; i++) cur = rows[cur].prev;
        }
        else if (arg1.equals("D")) {
            arg2 = Integer.parseInt(args[1]);
            for(int i=0; i<arg2; i++) cur = rows[cur].next;
        }
        else if (arg1.equals("C")) {
            del_history.push(cur);
            int next_idx = rows[cur].next;
            int prev_idx = rows[cur].prev;

            if (next_idx >= rows.length) cur = prev_idx;
            else {
                cur = next_idx;
                rows[next_idx].prev = prev_idx;
            }
            
            if (prev_idx >= 0) {
                rows[prev_idx].next = next_idx;
            }
        }
        else { // 'Z'
            if (!del_history.isEmpty()) {
                int del = del_history.pop();
                int next_idx = rows[del].next;
                int prev_idx = rows[del].prev;
                if (next_idx < rows.length) rows[next_idx].prev = del;
                if (prev_idx >= 0)rows[prev_idx].next = del;
            }
        }
        
        return cur;
    }
    
    
}