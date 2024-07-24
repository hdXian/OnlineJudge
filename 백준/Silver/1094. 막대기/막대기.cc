#include <iostream>

int main() {

    int x;
    std::cin >> x;

    int count = 0;
    while (x > 0) {
        // std::cout << x << " / 2 =  " << x / 2 << std::endl;
        count += x % 2;
        x /= 2;
    }

    std::cout << count;

}