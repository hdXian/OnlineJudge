import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String[] split = reader.readLine().split(" ");

        int n = Integer.parseInt(split[0]); // 트럭 수
        int w = Integer.parseInt(split[1]); // 다리 길이
        int l = Integer.parseInt(split[2]); // 최대 하중

        String[] truck_weight_str = reader.readLine().split(" ");
        List<Integer> trucks = new ArrayList<>();
        for (String s : truck_weight_str)
            trucks.add(Integer.parseInt(s));
//        System.out.println("trucks = " + trucks);

        List<Integer> bridge = new ArrayList<>(w);
        int cross_trucks = 0;
        int time = 0;
        int current_truck;
        int current_idx = 0;

        // 모든 트럭이 다리를 건널 때까지.
        while (cross_trucks < n) {
            if (current_idx < n) {
                current_truck = trucks.get(current_idx);
            }
            else {
                current_truck = 0;
            }

            if (bridge.size() == w) {
//                System.out.println("size_bridge = " + bridge);
                int escape_truck = bridge.get(w-1); // w as index
                bridge.remove(w-1);
                if (escape_truck > 0) {
                    cross_trucks++;
                }
            }

            // 다리에 지탱할 하중이 남으면 트럭을 다리에 올림.
            if (current_truck + calcCurrentBridge(bridge) <= l) {
                bridge.add(0, current_truck);
                current_idx++;
            }
            else { // 하중이 넘치면 트럭을 올리지 않음. (0 추가)
                bridge.add(0, 0);
            }

            time++;
//            System.out.println("bridge = " + bridge);
        }
        System.out.println(time);

    }

    public static int calcCurrentBridge(List<Integer> bridge) {
        int total = 0;
        for (Integer weight : bridge)
            total +=  weight;

        return total;
    }

}
