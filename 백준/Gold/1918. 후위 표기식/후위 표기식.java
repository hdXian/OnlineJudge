import java.util.*;
import java.io.*;
import java.math.*;

public class Main {

    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    static List<String> process_md(List<String> exp) {
        Stack<String> stk = new Stack<>();
        boolean flag = false;
        for (String s: exp) {
            if (flag) {
                String tmp = stk.pop();
                tmp = (stk.pop()) + s + tmp;
                stk.push(tmp);
                flag = false;
            }
            else
                stk.push(s);

            if (s.equals("*") || s.equals("/"))
                flag = true;

        }
        return new ArrayList<>(stk);
    }

    static List<String> process_pm(List<String> exp) {
        Stack<String> stk = new Stack<>();
        boolean flag = false;
        for (String s: exp) {
            if (flag) {
                String tmp = stk.pop();
                tmp = (stk.pop()) + s + tmp;
                stk.push(tmp);
                flag = false;
            }
            else
                stk.push(s);

            if (s.equals("+") || s.equals("-"))
                flag = true;

        }
        return new ArrayList<>(stk);
    }

    static String calc(String exp) {

        // 1. 괄호를 처리한다.
        Stack<String> stk = new Stack<>();
        for(char ch: exp.toCharArray()) {
            if (ch == ')') {
                Stack<String> tmp = new Stack<>();
                while(!stk.peek().equals("(")) tmp.push(stk.pop());

                stk.pop(); // "(" 제거
                
                List<String> tmpL = new ArrayList<>();
                while(!tmp.isEmpty()) tmpL.add(tmp.pop());

                tmpL = process_md(tmpL);
                tmpL = process_pm(tmpL);
                for(String t: tmpL) stk.push(t);
            }
            else
                stk.push(String.valueOf(ch));

        }

        // 2. *, /를 처리한다.
        List<String> expL = new ArrayList<>(stk);
        expL = process_md(expL);

        // 3. +, -를 처리한다.
        expL = process_pm(expL);

        // 4. 이어붙인다.
        StringBuilder sb = new StringBuilder();
        for(String e: expL) sb.append(e);

        return sb.toString();
    }

    public static void main(String[] args) throws Exception {
        String exp = reader.readLine();
        System.out.print(calc(exp));
    }

}
