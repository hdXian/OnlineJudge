class Solution
{
    public int solution(int n, int a, int b) {
        // 자신의 번호에서 1을 더한 뒤 2로 나눈 몫을 취하면 다음 라운드 진출 시 자신의 번호가 나옴
        int na = a;
        int nb = b;
        int count = 0;
        while(na != nb) {
            na = (na+1) >> 1;
            nb = (nb+1) >> 1;
            count++;
        }
        
        return count;
    }
}