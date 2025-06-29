import java.util.*;

class Solution {
    
    static int[] appech;
    
    static int[] result;
    static int max_diff = -1;
    
    static int calcScore(int[] cur) {
        int s1 = 0;
        int s2 = 0;
        for(int i=0; i<11; i++) {
            if (cur[i] == 0 && appech[i] == 0) continue;
            
            if(cur[i] > appech[i]) s1 += (10-i);
            else s2 += (10-i);
        }
        
        return s1 - s2;
    }
    
    static void dfs(int target, int[] cur, int remains) {
        
        // 모든 과녁에 대해 화살 배분을 끝냈거나, 더 쏠 화살이 없을 경우 종료하고 점수 계산
        if (target == -1 || remains == 0) {
            cur[10] = remains; // 화살이 남았다면 cur[10]번째 과녁에 모든 화살을 배분, 화살이 안 남았다면 영향 x
            
            int diff = calcScore(cur);
            
            // dfs 순서에 의해 더 낮은 점수를 많이 맞춘 경우가 나중에 고려되어 답을 덮어씀.
            // 따라서 "점수 차가 같은 경우, 더 낮은 점수를 많이 맞힌 경우를 리턴한다" 조건을 자동으로 만족하게 됨.
            if (diff > 0 && diff > max_diff) {
                max_diff = diff;
                result = cur.clone();
            }
            
            cur[10] = 0; // 백트래킹
            return;
        }
        
        // 남은 화살을 투자하면 cur_target점짜리 과녁의 점수를 획득할 수 있을 경우
        if (remains > appech[target]) {
            int shoot = appech[target]+1; // 어피치가 쏜 것보다 1발 더 쏘는 것이 최선
            cur[target] = shoot;
            dfs(target-1, cur, remains - shoot);
            
            cur[target] = 0; // 백트래킹
        }
        
        // 현재 taget 과녁에 화살을 쏘지 않고 넘어가는 경우
        dfs(target-1, cur, remains);
    }
    
    
    public int[] solution(int n, int[] info) {
        appech = info;
        
        int[] cur = new int[11];
        Arrays.fill(cur, 0);
        
        dfs(10, cur, n);
        
        if (max_diff == -1) {
            result = new int[1];
            result[0] = -1;
        }
        
        return result;
    }
    
}