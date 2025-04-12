import java.util.*;

class Solution {
    
    static Queue<Player> pq = new PriorityQueue<>();
    
    public int[] solution(int N, int[] stages) {
        
        // 스테이지별로 도달한 전체 사람과 실패한 사람을 구한다.
        int[] reached = new int[N+1];
        int[] fails = new int[N+1];
        
        for(int s: stages) {
            for(int i=0; i<s; i++)
                reached[i]++;
            fails[s-1]++;
        }
        
        // 실패율을 구해서 새로운 Player 객체를 생성해 추가한다.
        double rate;
        for(int i=0; i<N; i++) {
            if (reached[i] == 0) rate = 0;
            else rate = (double) fails[i] / reached[i];
            pq.add(new Player(i+1, rate));
        }

        int[] answer = new int[N];
        int seq=0;
        while (!pq.isEmpty()) {
            Player tmp = pq.poll();
            answer[seq++] = tmp.num;
        }
        
        return answer;
    }
    
    static class Player implements Comparable<Player> {
        public int num;
        public double rate;
        public Player(int num, double rate) {
            this.num = num;
            this.rate = rate;
        }
        
        @Override
        public int compareTo(Player p) {
            if (this.rate == p.rate)
                return Integer.valueOf(this.num).compareTo(p.num);
            return Double.valueOf(p.rate).compareTo(this.rate);
        }
        
    }
    
}