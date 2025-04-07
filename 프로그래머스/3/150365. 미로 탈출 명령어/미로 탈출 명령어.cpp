#include <iostream>
#include <cstdlib>
#include <string>
#include <vector>

using namespace std;

static string cmds[4] = {"d", "l", "r", "u"};
static int dr[4] = {1, 0, 0, -1};
static int dc[4] = {0, -1, 1, 0};

static string result = "z";
static int N, M, R, C, K;

void dfs(int, int, int, string);
int calc_distance(int, int, int, int);
bool isImpossible(int, int);

string solution(int n, int m, int x, int y, int r, int c, int k) {
    // n, m: 격자 크기
    // x, y: 출발 위치
    // r, c: 도착 위치
    // k: 이동 횟수
    N = n; M = m; R = r; C = c; K = k;
    
    if (isImpossible(x, y))
        return "impossible";
    
    string command = "";
    dfs(x, y, 0, command);
    
    string answer = result;
    return answer;
}

void dfs(int x, int y, int moves, string command) {
    
    // 현재 이동횟수 + 목적지까지의 최단거리 > k면 종료. 길을 잘못 든 것.
    if ((moves + calc_distance(x, y, R, C)) > K)
        return;
    
    if (command > result)
        return;
    
    if (moves == K && x == R && y == C) {
        result = (result < command) ? result : command;
        return;
    }
    
    int nr, nc;
    for(int i=0; i<4; i++) {
        nr = x + dr[i];
        nc = y + dc[i];
        
        if (nr > 0 && nc > 0 && nr <= N && nc <= M) {
            dfs(nr, nc, moves + 1, command + cmds[i]);
        }
        
    }
    
}

int calc_distance(int x, int y, int r, int c) {
    return std::abs(x-r) + std::abs(y-c);
}

// 목적지까지 최단거리가 k보다 크거나, (k-최단거리)가 짝수가 아니라면 이동 불가
bool isImpossible(int x, int y) {
    int d = calc_distance(x, y, R, C);
    
    if (d > K || (K-d)%2 != 0)
        return true;
    
    return false;
}

