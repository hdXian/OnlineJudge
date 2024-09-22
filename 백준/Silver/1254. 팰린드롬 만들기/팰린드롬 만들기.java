import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String line = reader.readLine();
        int result=0;

        // 애초에 팰린드롬인 경우
        if (isPalindrome(line)) {
            result = line.length();
        }
        else { // 팰린드롬이 아닌 경우
            int length = line.length();
            int add_len = 0;
            String str = line;

            while (!isPalindrome(str)) {
                if (add_len >= length)
                    break;

                str = (line + reverseStr(line.substring(0, ++add_len)));
            }

            result = str.length();
        }

        // 정답 출력 부분
        System.out.println(result);

    }

    public static String reverseStr(String s) {
        return new StringBuffer(s).reverse().toString();
    }

    public static boolean isPalindrome(String s) {
        int length = s.length();
        char[] arr = s.toCharArray();

        for(int i=0; i<(length/2); i++) {
            if (arr[i] != arr[length-1-i])
                return false;
        }

        return true;
    }

}
