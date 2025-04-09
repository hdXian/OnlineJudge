class Solution {
    public int[][] solution(int[][] arr1, int[][] arr2) {
        int row1 = arr1.length;
        int col1 = arr1[0].length;
        
        int row2 = arr2.length;
        int col2 = arr2[0].length;
        
        int[][] answer = new int[row1][col2];
        
        int tmp;
        // 행 개수는 arr1의 행 개수를 가짐
        for(int i=0; i<row1; i++) {
            
            // 열 개수는 arr2의 열 개수를 가짐
            for(int j=0; j<col2; j++) {
                tmp = 0;
                
                // 여기서 반복하는 횟수는 arr1의 열 개수나 arr2의 행 개수나 상관없음.
                for(int k=0; k<col1; k++) {
                    tmp += arr1[i][k] * arr2[k][j];
                }
                answer[i][j] = tmp;
            }
        }
        
        return answer;
    }
}