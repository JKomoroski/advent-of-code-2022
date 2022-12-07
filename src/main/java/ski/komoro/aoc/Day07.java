package ski.komoro.aoc;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Stack;
import java.util.stream.Stream;

public class Day07 {

    public static void main(String[] args) throws Exception {
        final var list = StaticUtils.readFile("./input_07.txt");


        // Today we traverse a tree without using a tree data structure to track the state
        Map<String, Integer> counts = new HashMap<>();
        final Stack<String> currentDir = new Stack<>();
        for(String line : list) {
            if (line.startsWith("$")) {
                if (line.contains("cd")) {
                    if (line.contains("..")) {
                        currentDir.pop();
                    } else {
                        final var dir = line.split(" cd ")[1];
                        currentDir.push(dir);
                    }
                }
            }

            if (line.matches("^\\d+ .*")) {
                final var file = line.split(" ");

                for (int i = 1; i <= currentDir.size(); i++) {

                    final var incDir = currentDir.subList(0, i);
                    final var integer = counts.get(String.join("", incDir));

                    if(integer == null) {
                        counts.put(String.join("", incDir), Integer.parseInt(file[0]));
                    } else {
                        counts.put(String.join("", incDir), Integer.parseInt(file[0]) + integer);
                    }
                }

            }
        }

        final var ans1 = counts.values().stream().filter(i -> i <= 100_000).reduce(Integer::sum).orElse(0);

        final var totalSpace = 70_000_000;
        final var usedSpace = counts.get("/");
        final var desiredFreeSpace = 30000000;
        final var currentFreeSpace = totalSpace - usedSpace;
        final var minDirDeleteSize = desiredFreeSpace - currentFreeSpace;

        final var ans2 = counts.entrySet()
                .stream()
                .filter(e -> e.getValue() >= minDirDeleteSize)
                .min(Comparator.comparingInt(Entry::getValue))
                .map(Entry::getValue)
                .orElseThrow();


        System.out.println("Problem One Solution: " + ans1);
        System.out.println("Problem Two Solution: " + ans2);
    }

}
