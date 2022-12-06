package ski.komoro;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.stream.IntStream;

public class Day05 {

    public static void main(String[] args) throws Exception {
        final var list = StaticUtils.readFile("./input_05.txt");
        final List<Deque<String>> deques = new ArrayList<>();
        IntStream.range(0, 10)
                .forEach(i -> deques.add(new ArrayDeque<>()));

        final var moves = new ArrayList<Move>();
        for (String line : list) {
            if(line.startsWith("move")) {
                moves.add(processMoveLine(line));
            }

            if(line.startsWith("[")) {
                final var s = processStackData(line);
                for (int i = 0; i < s.toCharArray().length; i++) {
                    final var item = s.charAt(i) + "";
                    if(!item.equals(" ")) {
                        deques.get(i + 1).addLast(item);
                    }
                }
            }
        }

        for (Move move : moves) {
            for (int i = 0; i < move.count; i++) {
                deques.get(move.dest).addFirst(deques.get(move.src).removeFirst());
            }
        }
        System.out.println("Problem One Solution: ");
        for (Deque<String> deque : deques) {
            if(deque.isEmpty()) {
                continue;
            }
            System.out.print(deque.removeFirst());
        }
        System.out.println(""); //WZWSGLNGJ


        final List<Deque<String>> deques2 = new ArrayList<>();
        IntStream.range(0, 10)
                .forEach(i -> deques2.add(new ArrayDeque<>()));

        for (String line : list) {

            if(line.startsWith("[")) {
                final var s = processStackData(line);
                for (int i = 0; i < s.toCharArray().length; i++) {
                    final var item = s.charAt(i) + "";
                    if(!item.equals(" ")) {
                        deques2.get(i + 1).addLast(item);
                    }
                }
            }
        }

        for (Move move : moves) {
            final var strings = new ArrayDeque<String>();
            for (int i = 0; i < move.count; i++) {
                strings.addFirst(deques2.get(move.src).removeFirst());
            }

            strings.forEach(deques2.get(move.dest)::addFirst);
        }

        System.out.println("Problem Two Solution: ");
        for (Deque<String> deque : deques2) {
            if(deque.isEmpty()) {
                continue;
            }
            System.out.print(deque.removeFirst());
        }
        System.out.println("");

    }

    static String processStackData(String line) {
        return new String(new char[]{line.charAt(1),
                line.charAt(5),
                line.charAt(9),
                line.charAt(13),
                line.charAt(17),
                line.charAt(21),
                line.charAt(25),
                line.charAt(29),
                line.charAt(33)});
    }

    static Move processMoveLine(String line) {
        final var s = line.split(" ");
        return new Move(Integer.parseInt(s[1]), Integer.parseInt(s[3]), Integer.parseInt(s[5]));
    }

    static class Move {
        int count;
        int src;
        int dest;

        Move(int count, int src, int dest) {
            this.count = count;
            this.src = src;
            this.dest = dest;
        }
    }
}
