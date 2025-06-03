import java.io.*;
import java.util.*;
import java.math.*;

public class Main {

    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

    static int N, M;
    static Map<String, Integer> wordMap = new HashMap<>();

    static class WordComparator implements Comparator<String> {
        @Override
        public int compare(String s1, String s2) {
            int c1 = wordMap.get(s1);
            int c2 = wordMap.get(s2);
            if (c1 == c2) {
                if (s1.length() == s2.length()) return s1.compareTo(s2); // 사전 순 오름차순
                else return s2.length() - s1.length(); // 단어 길이 내림차순
            }
            else return c2 - c1; // 빈도 내림차순
        }
    }

    static void init() throws Exception {
        StringTokenizer tkn = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(tkn.nextToken()); // 단어 개수. 1 ~ 10만
        M = Integer.parseInt(tkn.nextToken()); // 기준 단어 길이. 1 ~ 10

        String word;
        for(int i=0; i<N; i++) {
            word = reader.readLine();
            if (word.length() < M) continue;

            if (!wordMap.containsKey(word)) wordMap.put(word, 1);
            else wordMap.put(word, wordMap.get(word)+1);
        }
    }

    public static void main(String[] args) throws Exception {
        init();

        List<String> arr = new ArrayList<>(wordMap.keySet());
        arr.sort(new WordComparator());

        for(String s: arr) writer.write(s + "\n");
        writer.flush();
    }

}
