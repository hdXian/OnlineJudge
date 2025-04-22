import java.util.*;

class Solution {
    public int[] solution(long[] numbers) {
        
        // 1. 주어진 수를 이진수로 변환한다.
        int length = numbers.length;
        String[] bins = new String[length];
        for(int i=0; i<length; i++) {
            bins[i] = Long.toString(numbers[i], 2);
        }
        
        // 2. 포화 이진트리의 높이, 총 노드 개수를 구한다.
        // 3. 부모가 0인 노드는 반드시 0이어야 한다.
        int[] answer = new int[length];
        for(int i=0; i<length; i++) {
            answer[i] = calc(bins[i]) ? 1 : 0;
        }
        
        return answer;
    }
    
    // 2. 해당 이진수에 대해 포화 이진트리 표현 가능 여부 판단
    static boolean calc(String bin) {
        // 2-1. 트리의 높이와 노드 개수를 구한다.
        int blength = bin.length();
        int h = (int)Math.floor(Math.log(blength) / Math.log(2)) + 1;
        int nodes = (int)Math.pow(2, h) - 1;
        
        // 2-2. 빈 노드 개수만큼 앞에 0을 붙여서 포화 이진트리로 만든 다음에, 이진탐색을 돌린다.
        String zeros = "";
        for(int i=0; i<nodes-blength; i++) zeros += "0";
        
        String fullBin = zeros + bin;
        if (fullBin.charAt(fullBin.length() / 2) == '0') return false;
        return binarySearch(fullBin, 1, 0, nodes-1); // true or false
    }
    
    static boolean binarySearch(String fullBin, int parent, int start, int end) {
        if (start == end) {
            // 이제부터는 리프도 무조건 안심할 수 없음.
            // 리프가 1이면 -> 부모도 반드시 1
            // 리프가 0이면 -> 상관없음.
            if ((parent == 0) && (fullBin.charAt(start) == '1')) return false;
            else return true;
        }
        
        int mid = (start + end) / 2;
        
        // 부모 노드가 0일 경우 -> 자식 노드도 모두 0이어야 함.
        int cur = (fullBin.charAt(mid) == '1') ? 1 : 0;
        if (parent == 0 && cur == 1) return false;
        
        boolean l_sub = binarySearch(fullBin, cur, start, mid-1);
        boolean r_sub = binarySearch(fullBin, cur,  mid+1, end);
        return l_sub && r_sub;
    }
    
    
}