import java.util.*;
import java.io.*;
import java.math.*;

class Solution {
    
    static int[] dr = {1, 0, 0, -1};
    static int[] dc = {0, -1, 1, 0};
    static String[] cmds = {"d", "l", "r", "u"};
    
    static String result = "z";
    static int N, M, K;
    
    public String solution(int n, int m, int x, int y, int r, int c, int k) {
        // n, m은 격자 크기
        // x, y는 출발지 좌표
        // r, c는 목적지 좌표
        // k는 이동 횟수
        N = n; M = m; K=k;
        
        String answer = "";
        
        if (isImpossible(x, y, r, c))
            answer = "impossible";
        else {
            dfs(r, c, x, y, 0, "");
            // System.out.println("result = " + result);
            answer = result;
        }
        
        // System.out.println(answer);
        return answer;
    }
    
    static void dfs(int e_r, int e_c, int r, int c, int moves, String cmd) {
        
        // moves + 현재 위치에서 목적지까지 최단거리가 k보다 크면 리턴
        // if (isFailRoute(r, c, e_r, e_c, moves))
        if (isFailRoute(r, c, e_r, e_c, moves) || cmd.compareTo(result) > 0)
            return;
        
        // 제일 먼저 찾은 정답이 가장 빠른 명령어 조합임. cmds 추가하는 순서가 그렇게 되어있음.
        if (moves == K && r == e_r && c == e_c) {
            // result = (result.compareTo(cmd) > 0) ? cmd : result;
            result = cmd;
            return;
        }
        
        int nr, nc;
        for(int i=0; i<4; i++) {
            nc = c + dc[i];
            nr = r + dr[i];
            if (nr > 0 && nc > 0 && nr <= N && nc <= M) {
                dfs(e_r, e_c, nr, nc, moves+1, cmd + cmds[i]);
            }
        }
        
    }
    
    static boolean isImpossible(int r, int c, int e_r, int e_c) {
        int lr = e_r - r;
        int lc = e_c - c;
        
        int distance = Math.abs(lr) + Math.abs(lc);
        if (distance > K || (K - distance) % 2 != 0)
            return true;
        else
            return false;
    }
    
    static boolean isFailRoute(int r, int c, int e_r, int e_c, int moves) {
        int lr = e_r - r;
        int lc = e_c - c;
        
        int distance = Math.abs(lr) + Math.abs(lc);
        return (moves + distance) > K;
    }
    
}