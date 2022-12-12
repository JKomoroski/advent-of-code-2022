package ski.komoro.aoc;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.IntUnaryOperator;
import java.util.function.LongUnaryOperator;
import java.util.stream.Collectors;

public class Day11 {

    public static void main(String[] args) throws Exception {
        List<String> lines = StaticUtils.readFile("./input_11.txt");

        //101115 -- high
        //98908 -- low
        //99540 -- low
        //99852 -- correct

        System.out.println("First Puzzle: " + runInspections(buildMonkeyMap(lines), 20, 3)); //99852
        System.out.println("Second Puzzle: " + runInspections(buildMonkeyMap(lines), 10_000, 1)); //25935263541
    }

    static long runInspections(Map<Integer, Monkey> monkeys, int rounds, int worry) {
        int modulus = monkeys.values().stream().mapToInt(m -> m.testDivisor).reduce(1, Math::multiplyExact);
        // Rounds
        for (int round = 0; round < rounds; round++) {
            // Each Monkey
            for (int monkeyId = 0; monkeyId < monkeys.size(); monkeyId++) {
                final var current = monkeys.get(monkeyId);

                while(!current.items.isEmpty()) {
                    current.totalInspections++;
                    final var aLong = current.items.pop();
                    final var intermediate = current.operation.applyAsLong(aLong);
                    // divide doesn't matter if it's one
                    var result = Math.floorDiv(intermediate, worry);// % modulus; // mod doesn't matter if it's huge
                    if(worry == 1) { // This shit is a hack!
                        result = result % modulus;
                    }
                    final var targetMonkey = result % current.testDivisor == 0
                            ? monkeys.get(current.trueId)
                            : monkeys.get(current.falseId);
                    targetMonkey.items.addLast(result);
                }
            }
        }

        return monkeys.values()
                .stream()
                .map(m -> m.totalInspections)
                .sorted(Comparator.reverseOrder())
                .map(Math::negateExact)
                .limit(2)
                .reduce(Math::multiplyExact)
                .orElseThrow();
    }

    static Map<Integer, Monkey> buildMonkeyMap(List<String> lines) {
        Map<Integer, Monkey> monkeys = new HashMap<>();

        Monkey current = new Monkey();
        for (String line : lines) {
            if(line.equals("")) {
                monkeys.put(current.id, current);
                current = new Monkey();
            } else {
                updateMonkey(line, current);
            }
        }

        monkeys.put(current.id, current);

        return monkeys;
    }

    static class Monkey {

        int id = 0;
        Deque<Long> items = new ArrayDeque<>();
        LongUnaryOperator operation = (x) -> 0;
        int testDivisor = 0;
        int trueId = 0;
        int falseId = 0;
        long totalInspections;
    }

    static void updateMonkey(String s, Monkey m) {
        if (s.contains("Monkey")) {
            m.id = Integer.parseInt(s.split(" ")[1].replaceFirst(":", ""));
        }

        if(s.contains("Starting items:")) {
            m.items = Arrays.stream(s.split(": ")[1].replaceAll(" ", "").split(","))
                    .mapToLong(Long::parseLong)
                    .boxed()
                    .collect(Collectors.toCollection(ArrayDeque::new));
        }

        if(s.contains("Operation")) {
            if (s.contains("+")) {
                final var i = Integer.parseInt(s.split("\\+ ")[1]);
                m.operation = (x) -> x + i;
            }

            if(s.contains("*")) {
                if (s.contains("old * old")) {
                    m.operation = (x) -> x * x;
                } else {
                    final var i = Integer.parseInt(s.split("\\* ")[1]);
                    m.operation = (x) -> x * i;
                }
            }
        }

        if (s.contains("Test:")) {
            m.testDivisor = Integer.parseInt(s.split("by ")[1]);
        }

        if(s.contains("If ")) {
            final var destinationMonkey = Integer.parseInt(s.split("monkey ")[1]);

            if(s.contains("true:")) {
                m.trueId = destinationMonkey;
            }

            if(s.contains("false:")) {
                m.falseId = destinationMonkey;
            }
        }

    }

}
