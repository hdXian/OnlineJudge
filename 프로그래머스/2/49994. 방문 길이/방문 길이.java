import java.util.*;

class Solution {
    
    public static char[] cmds = {'U', 'D', 'R', 'L'};
    public static int[] dr = {0, 0, 1, -1};
    public static int[] dc = {1, -1, 0, 0};
    
    public int solution(String dirs) {
        
        char[] dir_arr = dirs.toCharArray();
        
        // 각 좌표에 0~121 사이의 번호를 부여한다.
        Set<Route> rs = new HashSet<>();
        int count = 0;
        int row, col;
        int nr, nc;
        int cur_point, next_point;
        
        row = 5;
        col = 5;
        
        for(char ch: dir_arr) {
            for(int i=0; i<4; i++) {
                
                if (ch == cmds[i]) {
                    nr = row + dr[i];
                    nc = col + dc[i];
                    
                    if (nr < 0 || nc < 0 || nr > 10 || nc > 10)
                        break;
                    
                    // 출발 좌표와 도착 좌표를 게산한다.
                    cur_point = (row*11) + col;
                    next_point = (nr*11) + nc;
                    Route tmp1 = new Route(cur_point, next_point);
                    Route tmp2 = new Route(next_point, cur_point);
                    if (!rs.contains(tmp1) && !rs.contains(tmp2)) {
                        rs.add(tmp1);
                        rs.add(tmp2);
                        count++;
                    }
                    
                    row = nr;
                    col = nc;
                    
                    break;
                    
                }
                
            }
            
        }
        
        int answer = count;
        return answer;
    }
    
    // 두 좌표의 번호가 같으면 같은 루트로 취급
    static class Route {
        public int src;
        public int dst;
        
        public Route(int src, int dst) {
            this.src = src;
            this.dst = dst;
        }
        
        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Route))
                return false;
            
            Route r = (Route) o;
            if (this.src == r.src && this.dst == r.dst)
                return true;
           
            return false;
        }
        
        @Override
        public int hashCode() {
            return Objects.hash(src, dst);
        }
        
    }
    
}