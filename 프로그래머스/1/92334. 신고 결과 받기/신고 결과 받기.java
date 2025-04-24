import java.util.*;

class Solution {
    
    static Map<String, Set<Integer>> reported = new HashMap<>();
    static Map<String, Integer> uids = new HashMap<>(); // 닉네임별 인덱스 저장
    
    public int[] solution(String[] id_list, String[] report, int k) {
        
        // 1. 각 유저와 해당 유저를 신고한 유저들을 매핑하는 Map을 생성한다.
        int seq = 0;
        for(String id: id_list) {
            reported.put(id, new HashSet<>());
            uids.put(id, seq++);
        }
        
        // 2. 신고 기록을 바탕으로 해당 유저를 신고한 유저들을 취합한다.
        String[] args;
        for (String r: report) {
            args = r.split(" ");
            // args[0]이 args[1]을 신고함
            int uid = uids.get(args[0]);
            reported.get(args[1]).add(uid);
        }
        
        // 3. 신고횟수가 k번을 넘은 유저들에 대해 해당 유저를 신고한 유저들이 받는 메일 개수를 카운트한다.
        int[] mails = new int[id_list.length];
        
        Set<Integer> report_uids;
        for(int i=0; i<id_list.length; i++) {
            report_uids = reported.get(id_list[i]);
            if (report_uids.size() >= k) {
                for(int uid: report_uids)
                    mails[uid]++;
            }
        }
        
        return mails;
    }
}