import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static ArrayList<Point> teachers = new ArrayList<>();
    public static ArrayList<Point> students = new ArrayList<>();
    public static boolean result = false;

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(reader.readLine());

        // 복도
        String[][] road = new String[n][n];
        ArrayList<Point> empty = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            road[i] = reader.readLine().split(" ");
        }

        // 복도를 읽어서 선생님과 학생 위치 확인
        for(int i=0; i<n; i++) {
            for(int j=0; j<n; j++) {
                if (road[i][j].equals("T"))
                    teachers.add(new Point(i, j));
                else if (road[i][j].equals("S"))
                    students.add(new Point(i, j));
                else
                    empty.add(new Point(i, j));
            }
        }

        arrange(road, n, empty, 1);

        if (result)
            System.out.println("YES");
        else
            System.out.println("NO");

    }

    private static void arrange(String[][] road, int n, List<Point> empty, int num) {

        int empty_length = empty.size();
        boolean res = false;

        // 장애물 배치
        for(int i=0; i<empty_length; i++) {
            Point tmp = empty.get(i);
            road[tmp.row][tmp.col] = "O";

            // 아닌 경우 장애물을 배치하고 다음 재귀를 돌림.
            if (num < 3) {
                List<Point> empty_copy = empty.subList(i+1, empty_length);

                // road 배열 깊은 복사
                String[][] road_copy = new String[n][n];
                for(int k=0; k<n; k++) {
                    road_copy[k] = road[k].clone();
                }

                arrange(road, n, empty_copy, num+1);
            }
            // 장애물 3개 배치가 끝난 경우 체크를 돌림
            else if (num == 3) {
                res = check2(road, n);
                if (res) {
                    result = true;
                    break;
                }
                else {
//                    road[tmp.row][tmp.col] = "X";
                }
            }
            road[tmp.row][tmp.col] = "X";

        }

    }

    // 장애물 배치한 다음에 돌릴 함수
    private static boolean check2(String[][] road, int n) {

        boolean res;
        for (Point teacher : teachers) {
            res = check_rc(road, teacher.row, teacher.col, n);
            if (!res) // res == false
                return false;
        }

        // 여기까지 오면 성공
        return true;
    }

    // 선생님 위치에 대해 상하좌우 확인
    private static boolean check_rc(String[][] road, int row, int col, int n) {

        // 열 확인 (위쪽)
        for(int r=row; r>=0; r--) {
            if (road[r][col].equals("O"))
                break;
            else if (road[r][col].equals("S"))
                return false;
        }

        // 열 확인 (아래쪽)
        for(int r=row; r<n; r++) {
            if (road[r][col].equals("O"))
                break;
            else if (road[r][col].equals("S"))
                return false;
        }

        // 행 확인 (왼쪽)
        for(int c=col; c>=0; c--) {
            if (road[row][c].equals("O"))
                break;
            else if (road[row][c].equals("S"))
                return false;
        }

        // 행 확인 (오른쪽)
        for(int c=col; c<n; c++) {
            if (road[row][c].equals("O"))
                break;
            else if (road[row][c].equals("S"))
                return false;
        }

        // 다 통과했다면 true -> 성공
        return true;
    }

    private static void printRoad(String[][] road, int n) {
        for(int i=0; i<n; i++) {
            for(int j=0; j<n; j++) {
                System.out.print(road[i][j] + " ");
            }
            System.out.println();
        }
    }

    static class Point {

        public int row;
        public int col;

        public Point(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public String toString() {
            return String.format("Point(%d %d)", row, col);
        }

    }


}
