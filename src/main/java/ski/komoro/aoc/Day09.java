package ski.komoro.aoc;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.IntUnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day09 {

    public static void main(String[] args) throws Exception {
        List<String> lines = StaticUtils.readFile("./input_09.txt");

        List<Move> moves = lines.stream().map(Move::fromString).toList();

        System.out.println("Puzzle One Answer: " + runRopeScenario(2, moves));
        System.out.println("Puzzle Two Answer: " + runRopeScenario(10, moves));

    }

    static int runRopeScenario(int ropeSize, List<Move> moves) {
        List<Point> trailingKnots = IntStream.rangeClosed(1, ropeSize)
                .mapToObj(i -> new Point(0, 0))
                .collect(Collectors.toList());

        final Point headKnot = trailingKnots.get(0);
        final Point tailKnot = trailingKnots.get(trailingKnots.size() - 1);

        Set<Point> visitedPoints = new HashSet<>();
        visitedPoints.add(tailKnot.copy());

        for(Move m : moves) {
            for(int i = 0; i < m.distance(); i++) {
                // move the head one tick of the movement at a time
                headKnot.x = m.direction().xFunc.applyAsInt(headKnot.x);
                headKnot.y = m.direction().yFunc.applyAsInt(headKnot.y);

                // have the trailing knots follow if needed
                for (int k = 0; k < trailingKnots.size() - 1; k++) {
                    moveKnotPosition(trailingKnots.get(k), trailingKnots.get(k + 1));
                }

                visitedPoints.add(tailKnot.copy());
            }
        }
        return visitedPoints.size();

    }




    record Move(Direction direction, int distance) {
        static Move fromString(String s) {
            final var s1 = s.split(" ");
            return new Move(Direction.of(s1[0]), Integer.parseInt(s1[1]));
        }
    }

    public enum Direction {
        UP("U",  a -> a + 1,  a -> a),
        DOWN("D", a -> a - 1,  a -> a),
        LEFT("L",  a -> a, a -> a - 1),
        RIGHT("R",  a -> a,  a -> a + 1);

        private final String letter;
        private final IntUnaryOperator xFunc;
        private final IntUnaryOperator yFunc;

        Direction(String direction, IntUnaryOperator xFunc, IntUnaryOperator yFunc) {
            this.letter = direction;
            this.xFunc = xFunc;
            this.yFunc = yFunc;
        }

        public static Direction of(String letter) {
            return Arrays.stream(Direction.values())
                    .filter(d -> d.letter.equalsIgnoreCase(letter)).findFirst()
                    .orElseThrow(() -> new RuntimeException("Unknown direction"));
        }
    }

    static void moveKnotPosition(Point knotBegin, Point knotEnd) {
        if (Math.abs(knotBegin.x - knotEnd.x) > 1 || Math.abs(knotBegin.y - knotEnd.y) > 1) {
            knotEnd.x = move(knotEnd.x, knotBegin.x);
            knotEnd.y = move(knotEnd.y, knotBegin.y);
        }
    }

    static int move(int a, int b){
        return Math.abs(a - b) > 1 ? Math.min(a, b) + 1 : b;
    }

    static class Point {
        int x, y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        Point copy() {
            return new Point(this.x, this.y);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            Point point = (Point) o;

            if (x != point.x) {
                return false;
            }
            return y == point.y;
        }

        @Override
        public int hashCode() {
            int result = x;
            result = 31 * result + y;
            return result;
        }
    }
}
