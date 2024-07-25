#include <stdio.h>

int main(int argc, char** argv) {
    int n;
    scanf("%d", &n);

    if(n%2) puts("SK");
    else puts("CY");

    return 0;
}