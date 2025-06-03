import java.util.*;
import java.io.*;
import java.math.*;

class Solution {
    static int r_siz;
    static int c_siz;
    static int[] dr = {-1, 1, 0, 0};
    static int[] dc = {0, 0, -1, 1};
    
    static class Node {
        public int row, col;
        public int cost;
        public Node(int row, int col, int cost) {
            this.row = row;
            this.col = col;
            this.cost = cost;
        }
    }
    
    static int bfs(String[] maps, Node src, Node dst) {
        boolean[][] visited = new boolean[r_siz][c_siz];
        for(boolean[] v: visited) Arrays.fill(v, false);
        
        Queue<Node> q = new LinkedList<>();
        visited[src.row][src.col] = true;
        q.add(new Node(src.row, src.col, 0));
        
        Node result = new Node(-1, -1, -1);
        
        while(!q.isEmpty()) {
            Node cur = q.poll();
            if (cur.row == dst.row && cur.col == dst.col) {
                result = cur;
                break;
            }
            
            for(int i=0; i<4; i++) {
                int nr = cur.row + dr[i];
                int nc = cur.col + dc[i];
                if (nr >=0 && nc >= 0 && nr < r_siz && nc < c_siz 
                    && maps[nr].charAt(nc) != 'X' && !visited[nr][nc]) {
                    visited[nr][nc] = true;
                    q.add(new Node(nr, nc, cur.cost+1));
                }
            }
            
        }
        
        return result.cost;
    }
    
    public int solution(String[] maps) {
        // 1. 출발지 -> 레버까지의 최단거리 구하기
        // 2. 레버 -> 도착지까지의 최단거리 구하기
        // 3. 둘이 더하기
        
        int sr = 0; int sc = 0; // 출발지
        int er = 0; int ec = 0; // 도착지
        int lr = 0; int lc = 0; // 레버
        
        r_siz = maps.length;
        c_siz = maps[0].length();
        
        for(int i=0; i<r_siz; i++) {
            for(int j=0; j<c_siz; j++) {
                char ch = maps[i].charAt(j);
                if (ch == 'X' || ch == 'O') continue;
                if (ch == 'S') {
                    sr = i;
                    sc = j;
                }
                else if (ch == 'L') {
                    lr = i;
                    lc = j;
                }
                else {
                    er = i;
                    ec = j;
                }
            }
        }
        
        int c1 = bfs(maps, new Node(sr, sc, 0), new Node(lr, lc, 0));
        if (c1 < 0) return -1;
        
        int c2 = bfs(maps, new Node(lr, lc, 0), new Node(er, ec, 0));
        if (c2 < 0) return -1;
        
        return c1 + c2;
    }
}