import java.util.*;

class Solution {
    
    static Map<String, Integer> total_p = new HashMap<>();
    
    // Comparator 연습
    static class PlaysComparator implements Comparator<String> {
        @Override
        public int compare(String s1, String s2) {
            int n1 = total_p.get(s1);
            int n2 = total_p.get(s2);
            return Integer.compare(n2, n1); // 내림차순 정렬이므로 인자를 반대로 전달
        }
    }
    
    public int[] solution(String[] genres, int[] plays) {
        
        int length = genres.length;
        
        // 1. 장르별 총 재생 횟수를 map으로 저장한다.
        String g_key;
        int p;
        for(int i=0; i<length; i++) {
            g_key = genres[i];
            p = plays[i];
            if (!total_p.containsKey(g_key))
                total_p.put(g_key, p);
            else
                total_p.put(g_key, total_p.get(g_key) + p);
        }
        
        // 3. 총 재생 횟수가 많은 장르부터 순서대로, 각 장르 내에서 가장 재생횟수가 많은 노래를 최대 2개씩 수록한다.
        List<String> keyL = new ArrayList<>(total_p.keySet());
        keyL.sort(new PlaysComparator());
        
        // 2. 장르 안에서 각 노래의 재생 횟수를 map으로 저장한다.
        List<Integer> ans = new ArrayList<>(); // 답
        
        List<Integer> numbers;
        for(String genreName: keyL) {
            numbers = new ArrayList<>();
            
            // genreName에 해당하는 인덱스들을 리스트에 저장
            for(int i=0; i<length; i++)
                if (genres[i].equals(genreName)) numbers.add(i);
            
            // plays[i] 값을 기준으로 인덱스들을 내림차순 정렬
            numbers.sort(new Comparator<Integer>() {
                @Override
                public int compare(Integer n1, Integer n2) {
                    return Integer.compare(plays[n2], plays[n1]);
                }
            });
            
            // 상위 2개를 answer에 추가
            for(int i=0; i<Math.min(numbers.size(), 2); i++)
                ans.add(numbers.get(i));
        }
        
        int[] answer = new int[ans.size()];
        int seq = 0;
        for(Integer i: ans)
            answer[seq++] = i;
        
        return answer;
    }
}