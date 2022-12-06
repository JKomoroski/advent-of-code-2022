package ski.komoro.aoc;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Day04 {
    public static void main(String[] args) throws Exception {
        final var list = StaticUtils.readFile("./input_04.txt");

        int duplicatePairs = 0;

        for (String s : list) {
            final var split = s.split(",");
            final var s1 = split[0];
            final var s2 = split[1];

            final var range1 = s1.split("-");
            final var range2 = s2.split("-");

            final var nums1 = Arrays.stream(range1)
                    .mapToInt(Integer::parseInt)
                    .sorted()
                    .boxed()
                    .collect(Collectors.toList());

            final var nums2 = Arrays.stream(range2)
                    .mapToInt(Integer::parseInt)
                    .sorted()
                    .boxed()
                    .collect(Collectors.toList());

            final int start1 = nums1.get(0);
            final int start2 = nums2.get(0);
            final int end1 = nums1.get(1);
            final int end2 = nums2.get(1);

            if (start1 <= start2 && end1 >= end2) {
                duplicatePairs++;
            } else if (start2 <= start1 && end2 >= end1) {
                duplicatePairs++;
            }
        }
        System.out.println("Part 1 Solution: " + duplicatePairs);

        int overlapAtAll = 0;

        for (String s : list) {
            final var split = s.split(",");
            final var s1 = split[0];
            final var s2 = split[1];

            final var range1 = s1.split("-");
            final var range2 = s2.split("-");

            final var nums1 = Arrays.stream(range1)
                    .mapToInt(Integer::parseInt)
                    .sorted()
                    .boxed()
                    .collect(Collectors.toList());

            final var nums2 = Arrays.stream(range2)
                    .mapToInt(Integer::parseInt)
                    .sorted()
                    .boxed()
                    .collect(Collectors.toList());

            final int start1 = nums1.get(0);
            final int start2 = nums2.get(0);
            final int end1 = nums1.get(1);
            final int end2 = nums2.get(1);

            if (start1 <= start2 && start2 <= end1) {
                overlapAtAll++;
            } else if (start2 <= start1 && start1 <= end2) {
                overlapAtAll++;
            }
        }

        System.out.println("Part 2 Solution: " + overlapAtAll);


    }
}
