#include <stdio.h>
#include <stdlib.h>

#define _X 0
#define _Y 1

int main() {

    int n;
    scanf("%d", &n);
    getchar(); // remove newline char

    // mem allocate
    char** matrix = (char**)calloc(n, sizeof(char*));
    for(int i=0; i<n; i++)
        matrix[i] = (char*)calloc(n+1, sizeof(char));

    // input matrix
    for(int i=0; i<n; i++) {
        fgets(matrix[i], n+1, stdin);
        getchar();
        // printf("matrix[%d]: %s\n", i, matrix[i]);
    }

    int head[2];
    int heart[2];
    int find_head = 0;

    // find head
    for(int i=0; i<n; i++) {
        for (int j=0; j<n; j++) {
            if (!find_head && matrix[i][j] == '*') {
                head[_X] = i;
                head[_Y] = j;
                // point of heart is head[x+1][y]
                heart[_X] = i+1;
                heart[_Y] = j;
                find_head = 1;
                break;
            }
        }
        if(find_head) break;
    }

    // 머리 찾으면
    // 한줄 아래 심장 좌표 찍고
    // 그 아래줄 * 찍혀있는 처음 인덱스 ~ 마지막 인덱스 구해서 심장 좌표랑 빼서 양쪽 팔 길이 구하고
    // 심장 아래좌표부터 * 찍혀있는 동안 계속 카운트하고
    // 심장 양쪽 한칸 띄워서 아래부터 찾아가면서 길이 카운트하기

    int heart_row = heart[_X];
    int heart_col = heart[_Y];

    int left_arm_col = 0;
    int right_arm_col = n-1;

    while(matrix[heart_row][left_arm_col] != '*')
        left_arm_col++;
    
    while(matrix[heart_row][right_arm_col] != '*')
        right_arm_col--;

    
    int middle_row = heart_row+1;
    while (matrix[middle_row][heart_col] == '*')
        middle_row++;
    
    middle_row--;
    

    int left_leg_row = middle_row+1;
    int left_leg_col = heart_col-1;

    int right_leg_row = middle_row+1;
    int right_leg_col = heart_col+1;

    while (left_leg_row < n && matrix[left_leg_row][left_leg_col] == '*')
        left_leg_row++;

    while (right_leg_row < n && matrix[right_leg_row][right_leg_col] == '*')
        right_leg_row++;
    
    left_leg_row--;
    right_leg_row--;
    

    int left_arm_len = heart_col - left_arm_col;
    int right_arm_len = right_arm_col - heart_col;

    int middle_len = middle_row - heart_row;

    int left_leg_len = left_leg_row - middle_row;
    int right_leg_len = right_leg_row - middle_row;

    printf("%d %d\n", heart[_X]+1, heart[_Y]+1);
    printf("%d %d %d %d %d\n", left_arm_len, right_arm_len, middle_len, left_leg_len, right_leg_len);

    
    // free mem
    for(int i=0; i<n; i++)
        free(matrix[i]);
    free(matrix);

    return 0;

}