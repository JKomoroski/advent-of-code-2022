package org.example;

import java.util.HashSet;
import java.util.Set;

public class Day06 {

    public static void main(String[] args) throws Exception {
        final var list = StaticUtils.readFile("./input_06.txt");

        final var dataStream = list.get(0);

        System.out.println("Problem One Solution: " + calculateMarker(dataStream, 4)); // packet marker
        System.out.println("Problem Two Solution: " + calculateMarker(dataStream, 14)); // message marker

    }

    static int calculateMarker(String dataStream, int markerSize) {
        for (int i = 0; i < dataStream.length(); i++) {
            Set<Character> set = new HashSet<>();

            // Unsafe Look Ahead :pray:
            for (int j = i; j < i + markerSize; j++) {
                set.add(dataStream.charAt(j));
            }

            if(set.size() == markerSize) {
                return i + markerSize; // 1 based index for answer
            }

        }
        return 0; // Something went wrong
    }

}
