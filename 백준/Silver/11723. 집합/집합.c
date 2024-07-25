#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int main(int argc, char** argv) {
    int m;
    scanf("%d", &m);
    getchar(); // 개행문자 제거

    int set[20] = {0,};
    // putchar('\n');

    char* line = (char*)calloc(11, sizeof(char));
    // printf("set: %s\n", set);

    char cmd[10];
    int num;
    for(int i=0; i<m; i++) {
        // printf("i: %d\n", i);
        fgets(line, 11, stdin);
        // gets_s(line, 10); g++ compiler doesn't have gets_s()
        // printf("line: %s\n", line);

        sscanf(line, "%s %d", cmd, &num);
        num--;

        switch (cmd[0]) {
            case 'a': // add or all
                {
                int cmp = strncmp(cmd, "add", 3);
                
                if(cmp == 0) {
                    // add
                    if(!set[num]) set[num]++;
                }
                else {
                    // all
                    for(int i=0; i<20; i++) set[i] = 1;
                }
                break;
                }
            case 'r': // remove
                if(set[num]) set[num]--;
                break;

            case 'c': // check
                if(set[num]) puts("1");
                else puts("0");
                // getchar();
                break;

            case 't': // toggle
                if(set[num]) set[num] = 0;
                else set[num] = 1;
                break;

            case 'e': // empty
                for(int i=0; i<20; i++) set[i] = 0;
                break;

            default:
                break;
        }

    }

    free(line);
    
    return 0;
}

