package org.example;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Day03 {

    public static void main(String[] args) throws Exception {
        List<String> lines = StaticUtils.readFile("./input_03.txt");

        final var prioritySum = lines.stream()
                .map(s -> new String[]{s.substring(0, s.length() / 2), s.substring(s.length() / 2)})
                .map(p -> findFirstIntersectingCharacter(p[0], p[1]))
                .mapToInt(Day03::charToPriority)
                .sum();

        System.out.println("Part 1 Solution: " + prioritySum);

        int badgePriority = 0;
        for (int i = 0; i < lines.size(); i += 3) {
            int i2 = i + 1;
            int i3 = i + 2;
            final var chars1 = findAllIntersectingCharacters(lines.get(i), lines.get(i2));
            final var chars2 = findAllIntersectingCharacters(lines.get(i2), lines.get(i3));

            chars1.retainAll(chars2);

            final var sum = chars1.stream()
                    .mapToInt(Day03::charToPriority)
                    .sum();

            badgePriority += sum;
        }

        System.out.println("Part 2 Solution: " + badgePriority);

    }

    static Set<Character> findAllIntersectingCharacters(String s1, String s2) {
        return s2.chars()
                .map(s1::indexOf)
                .filter(i -> i > -1)
                .map(s1::charAt)
                .mapToObj(i -> (char) i)
                .collect(Collectors.toSet());
    }

    static char findFirstIntersectingCharacter(String s1, String s2) {
        return (char) s2.chars()
                .map(s1::indexOf)
                .filter(i -> i > -1)
                .map(s1::charAt)
                .findFirst()
                .orElseThrow();
    }

    static int charToPriority(char character) {
        return Character.isLowerCase(character)
                ? (int) character - 96
                : (int) character - 38;
    }
}
