import java.util.*;

class Solution {
    
    static int N; // 외벽 길이
    static int P; // 사람 수
    static List<Integer> weak = new ArrayList<>();
    static int[] dist;
    static int weak_points; // 취약지점 개수
    static final int INF = 999;
    static int result = INF;
    
    // start를 시작 지점으로 해서 사람을 pos 순서대로 배치해보기
    static int countPeople(List<Integer> pos, int start) {
        Queue<Integer> q = new LinkedList<>();
        for(int i=start; i<weak.size(); i++) q.add(weak.get(i));
        
        // 큐 내용을 2배로 넣던가 해야 함. 한바퀴 돌고 넘어갈 때 뭔가 해야 함.
        int people_idx = 0;
        int checked_weaks = 0;
        
        // 사람들 배치해보기
        while(!q.isEmpty() && checked_weaks < weak_points && people_idx < P) {
            int cur = q.poll();
            checked_weaks++;
            
            int p = pos.get(people_idx);
            int reachable = cur + dist[p];
            while(!q.isEmpty() && q.peek() <= reachable) {
                q.poll();
                checked_weaks++;
            }
            
            people_idx++;
        }
        
        // 모든 취약지점이 체크되었다 -> 배치 가능, 투입된 사람 수 리턴
        if (checked_weaks == weak_points) return people_idx; 
        // 아니다 -> 모든 사람을 투입해도 취약지점 커버가 불가능하다. INF 리턴.
        else return INF;
    }
    
    static int check(List<Integer> pos) {
        int people_count = INF;
        // 각 취약지점을 시작 지점으로 pos 순서대로 사람을 배치해서 모든 지점이 커버되는지 확인
        for(int start=0; start<weak_points; start++) {
            people_count = Math.min(people_count, countPeople(pos, start));
        }
        
        return people_count;
    }
    
    static void dfs(List<Integer> pos, boolean[] visited) {
        if (pos.size() == P) {
            // 구한 순열 가지고 사람들 배치해보기
            // System.out.println(pos);
            result = Math.min(result, check(pos));
            return;
        }
        
        for(int i=0; i<P; i++) {
            if (visited[i]) continue;
            pos.add(i);
            visited[i] = true;
            
            dfs(pos, visited);
            
            pos.remove(pos.size()-1);
            visited[i] = false;
        }
    }
    
    public int solution(int n, int[] _weak, int[] _dist) {
        // 외벽 길이 n: 1 ~ 200
        // 취약점 위치 배열 weak: 길이 1 ~ 15, 요소는 0 ~ n-1
        // 각 사람들이 1시간동안 커버칠수 있는 거리 dist: 길이 1 ~ 8, 요소 1 ~ 100
        N = n;
        for(int w: _weak) weak.add(w);
        for(int w: _weak) weak.add(w+n);
        
        dist = _dist;
        weak_points = _weak.length;
        
        // 1. 사람을 배치하는 모든 경우를 순열로 구한다.
        // 2. 각 배치 순서대로 weak의 각 지점을 시작으로 삼아 배치했을 때 모든 취약점을 커버칠 수 있는지 확인한다.
        // 3. 커버칠 수 있는 경우, 배치한 사람이 더 적은 경우인지 비교해서 업데이트한다.
        
        P = dist.length;
        
        List<Integer> pos = new ArrayList<>();
        boolean[] visited = new boolean[P];
        
        dfs(pos, visited);
        
        if (result == INF) return -1;
        else return result;
    }
}