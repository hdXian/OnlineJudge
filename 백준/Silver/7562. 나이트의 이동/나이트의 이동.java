import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;

public class Main {

    public static ArrayList<Point> directions = new ArrayList<>();
    public static int level;

    public static void main(String[] args) throws IOException {
        // 나이트의 이동 방향 초기화
        initDirections();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int cases = Integer.parseInt(reader.readLine());
        int[] res = new int[cases];
//        System.out.println("cases = " + cases);

        for (int i = 0; i < cases; i++) {
            int l = Integer.parseInt(reader.readLine());
            String[] knightPoint = reader.readLine().split(" ");
            String[] destPoint = reader.readLine().split(" ");

            Point knight = new Point(knightPoint);
            Point destination = new Point(destPoint);

            // 시작점과 도착점이 애초에 같을 경우
            if (knight.equals(destination)) {
                res[i] = 0;
                continue;
            }

            boolean[][] visited = new boolean[l][l];
//
//            System.out.println("l = " + l);
//            System.out.println("knight = " + knight);
//            System.out.println("destination = " + destination);
//
//            System.out.println("directions = " + directions);

            ArrayList<Point> queue = new ArrayList<>();
            queue.add(knight);

            level = 0;
            bfs2(destination, queue, visited, l);
            res[i] = level;
//            System.out.println("level = " + level);
        }

        for (int result : res) {
            System.out.println(result);
        }

    }

    // 얘도 circle만 해결하면 될 것 같은데.. 아, visited?
    static void bfs2(Point end, ArrayList<Point> queue, boolean[][] visited, int l) {

        level++;
//        System.out.println("level added = " + level);
        ArrayList<Point> nextQueue = new ArrayList<>();

        while (!queue.isEmpty()) {
            Point curPoint = queue.get(0);

            visited[curPoint.row][curPoint.col] = true;
//            System.out.println("dequeued curPoint = " + curPoint);

            // 8가지 방향으로 이동
            for (Point direction : directions) {
//                System.out.println("fori direction: " + direction);
                Point next = new Point(curPoint.row, curPoint.col);
                next.addPoints(direction);

                // 범위를 벗어나거나 제자리에 돌아오면 (방문한 적 있으면) 건너뜀 (큐에 추가 x)
                if (next.isOutBound(l) || visited[next.row][next.col]) {
                    continue;
                }

                // 도착한 경우
                if (next.equals(end)) {
//                    System.out.println("도착: " + next);
                    return;
                }
                else { // 아니면 큐에 추가 (더 탐색해야 함)
//                    System.out.println("next = " + next + " added");
                    visited[next.row][next.col] = true;
                    nextQueue.add(next);
//                    queue.add(next);
                }
            }
            queue.remove(0); // 뽑아내기
        }

        // 트리 레벨을 구하기 위한 재귀 호출
        bfs2(end, nextQueue, visited, l);
    }

    // 나이트 이동 방향 초기화
    static void initDirections() {
        // 왼쪽 위
        directions.add(new Point(-1, -2));
        directions.add(new Point(-2, -1));

        // 오른쪽 위
        directions.add(new Point(-1, 2));
        directions.add(new Point(-2, 1));

        // 왼쪽 아래
        directions.add(new Point(1, -2));
        directions.add(new Point(2, -1));

        // 오른쪽 아래
        directions.add(new Point(1, 2));
        directions.add(new Point(2, 1));
    }

    // Point 객체
    public static class Point {
        int row;
        int col;

        public Point(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public Point(String[] point) {
            this.row = Integer.parseInt(point[0]);
            this.col = Integer.parseInt(point[1]);
        }

        public void addPoints(Point p) {
            this.row += p.row;
            this.col += p.col;
        }

        public boolean isOutBound(int n) {
            return (row < 0) || (col < 0) || (row >= n) || (col >= n);
        }

        @Override
        public String toString() {
            return String.format("Point[%d][%d]", row, col);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return row == point.row && col == point.col;
        }

        @Override
        public int hashCode() {
            return Objects.hash(row, col);
        }

    }

}
