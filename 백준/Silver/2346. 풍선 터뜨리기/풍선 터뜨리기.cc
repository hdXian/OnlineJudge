#include <iostream>
#include <map>

using namespace std;

int main() {

    int n;
    cin >> n;

    map<int, int> dict;
    int* nums;
    nums = (int*)calloc(n, sizeof(int)); // 0으로 초기화
    
    int tmp;
    for (int i = 0; i < n; i++) {
        cin >> tmp;
        nums[i] = tmp;
        dict[i] = 1;
        // cout << dict[i] << " ";
    }


    int index = 0;
    int cnt = 0;
    int move;
    while (dict[index] != 0) {
        
        cout << index + 1 << ' ';
        dict[index] = 0;
        cnt++;
        // cout << "count: " << cnt << endl;
        if (cnt == n) break;
        
        move = nums[index];
        // cout << "move: " << move << endl;
        if (move > 0) {
            for (int i = 0; i < move; i++) {
                index++;
                if (index == n)
                    index = 0;

                if (dict[index] == 0) 
                    i--;
                
                
            }
            
        }
        else if (move < 0) {
            move *= -1;
            //cout << "move: " << move << endl;
            for (int i = 0; i < move; i++) {
                index--;
                if (index < 0)
                    index = n - 1;

                if (dict[index] == 0)
                    i--;

                
            }
            if (index < 0) index = n - 1;
        }
        
    }

    free(nums);

}

