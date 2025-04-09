#include <string>
#include <vector>

using namespace std;

vector<vector<int>> solution(vector<vector<int>> arr1, vector<vector<int>> arr2) {
    
    int row_1 = arr1.size();
    int col_1 = arr1[0].size();
    
    int row_2 = arr2.size();
    int col_2 = arr2[0].size();
    
    // arr1의 각 행 * arr2의 각 열
    // answer[arr1.c][arr2.r]
    // 곱셈 결과 행렬은 a1의 i번째 행과 a2의 j번째 열의 스칼라곱.
    // a1의 열 개수와 a2의 행 개수가 같아야 함.
    vector<vector<int>> answer;
    int tmp;
    for(int i=0; i<row_1; i++) {
        vector<int> ans_row;
        // i번째 행과 j번째 열의 행렬 곱 수행
        for(int j=0; j<col_2; j++) {
            tmp = 0;
            // 여기 반복은 a1의 열 개수든, a2의 행 개수든 상관없음
            for(int k=0; k<col_1; k++) {
                tmp += arr1[i][k] * arr2[k][j];
            }
            ans_row.push_back(tmp);
        }
        answer.push_back(ans_row);
    }
    
    return answer;
}