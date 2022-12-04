package org.example;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Day01 {

    public static void main(String[] args) throws Exception {
        List<Integer> groups = new ArrayList<>();

        final var iterator = StaticUtils.readFile("./input_01.txt").iterator();

        while (iterator.hasNext()) {
            final var food = new ArrayList<Integer>();
            while (iterator.hasNext()) {
                final var next = iterator.next();
                if (next.equals("")) {
                    break;
                }
                food.add(Integer.parseInt(next));
            }
            final var sum = food.stream().mapToInt(Integer::intValue).sum();
            groups.add(sum);
        }
        // Part 1
        final var max = groups.stream().max(Integer::compareTo).orElseThrow();
        System.out.println("Part 1: " + max);

        // Part 2
        final var three = groups.stream().sorted(Comparator.reverseOrder()).limit(3).mapToInt(Integer::intValue).sum();
        System.out.println("Part 2: " + three);
    }

}
