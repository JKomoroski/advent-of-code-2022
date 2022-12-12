package ski.komoro.aoc;

import java.util.ArrayList;
import java.util.List;

public class Day10 {

    public static void main(String[] args) throws Exception {
        List<String> lines = StaticUtils.readFile("./input_10_sample.txt");

        final var instructions = lines.stream().map(Instruction::fromString).toList();
        final var probeOn = List.of(20, 60, 100, 140, 180, 220);
        final List<Integer> signalStrengths = new ArrayList<>();

        int currentCycle = 0;
        int registerX = 1;
        for (Instruction instruction : instructions) {

            for (int i = 0; i < instruction.getClockCycles(); i++) {
                currentCycle++;

                if (probeOn.contains(currentCycle)) {
                    signalStrengths.add(registerX * currentCycle);
                }

                if (isSpriteVisible(currentCycle, registerX)) {
                    System.out.print("#");
                } else {
                    System.out.print(".");
                }

                if (currentCycle % 40 == 0) {
                    System.out.println("");
                }

                if (i == instruction.getClockCycles() - 1 && instruction instanceof AddX addX) {
                    registerX += addX.value;
                }
            }
        }

        System.out.println("Part 1 answer: " + signalStrengths.stream().mapToInt(Integer::intValue).sum());

    }

    static boolean isSpriteVisible(int cycle, int registerX) {
        final var currentPixel = cycle % 40 - 1;
        final var x = currentPixel - registerX;
        return x == -1 || x == 0 || x == 1;
    }

    static Instruction fromString(String s) {
        final var s1 = s.split(" ");
        return switch (s1[0]) {
            case "noop" -> new Noop();
            case "addx" -> new AddX(Integer.parseInt(s1[1]));
            default -> throw new RuntimeException("failed on " + s);
        };
    }

    static class Noop implements Instruction {

        @Override
        public int getClockCycles() {
            return 1;
        }
    }

    static class AddX implements Instruction {

        final int value;

        AddX(int value) {
            this.value = value;
        }

        @Override
        public int getClockCycles() {
            return 2;
        }
    }

    interface Instruction {

        int getClockCycles();

        static Instruction fromString(String s) {
            final var s1 = s.split(" ");
            return switch (s1[0]) {
                case "noop" -> new Noop();
                case "addx" -> new AddX(Integer.parseInt(s1[1]));
                default -> throw new RuntimeException("failed on " + s);
            };
        }
    }
}
