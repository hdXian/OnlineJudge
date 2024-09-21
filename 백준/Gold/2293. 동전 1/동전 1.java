import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int n,k;
        String[] line = reader.readLine().split(" ");
        n = Integer.parseInt(line[0]);
        k = Integer.parseInt(line[1]);

        ArrayList<Integer> coinList = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            Integer value = Integer.valueOf(reader.readLine());
            if (value <= k)
                coinList.add(value);
        }


        int[] table = new int[k+1];
        IntStream.range(0, k+1)
                .forEach((i) -> table[i] = 0);

        Collections.sort(coinList);

        for (Integer coin : coinList) {
            table[coin]++;
            for (int i=coin+1; i<=k; i++) {
                table[i] = table[i-coin] + table[i];
            }

        }

        System.out.println(table[k]);

    }

}
