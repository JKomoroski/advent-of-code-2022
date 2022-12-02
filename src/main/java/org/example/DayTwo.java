package org.example;

import static org.example.DayTwo.RPS.M_PAPER;
import static org.example.DayTwo.RPS.M_ROCK;
import static org.example.DayTwo.RPS.M_SCISSORS;
import static org.example.DayTwo.RPS.O_PAPER;
import static org.example.DayTwo.RPS.O_ROCK;
import static org.example.DayTwo.RPS.O_SCISSORS;

import java.util.List;

public class DayTwo {

    static final int WIN = 6;
    static final int DRAW = 3;
    static final int LOSS = 0;

    enum RPS {
        O_ROCK("A", 1),
        O_PAPER("B", 2),
        O_SCISSORS("C", 3),
        M_ROCK("X", 1),
        M_PAPER("Y", 2),
        M_SCISSORS("Z", 3);

        RPS(String value, int score) {
            this.value = value;
            this.score = score;
        }

        private final String value;
        private final int score;

        int getScore() {
            return score;
        }

        String getValue() {
            return value;
        }

        static RPS fromValue(String s) {
            if (s.equals(O_ROCK.getValue())) {
                return O_ROCK;
            }

            if (s.equals(O_PAPER.getValue())) {
                return O_PAPER;
            }

            if (s.equals(O_SCISSORS.getValue())) {
                return O_SCISSORS;
            }

            if (s.equals(M_ROCK.getValue())) {
                return M_ROCK;
            }

            if (s.equals(M_PAPER.getValue())) {
                return M_PAPER;
            }

            if (s.equals(M_SCISSORS.getValue())) {
                return M_SCISSORS;
            }

            return null;
        }
    }

    public static void main(String[] args) throws Exception {
        List<String> lines = StaticUtils.readFile("./input_2.txt");

        var sum = lines.stream()
                .map(s -> s.replace(O_ROCK.getValue(), M_ROCK.getValue()))
                .map(s -> s.replace(O_PAPER.getValue(), M_PAPER.getValue()))
                .map(s -> s.replace(O_SCISSORS.getValue(), M_SCISSORS.getValue()))
                .map(DayTwo::calculateScore)
                .mapToInt(Integer::intValue)
                .sum();

        System.out.println("First Solution: " + sum);

        var sum2 = lines.stream()
                .map(DayTwo::calculateSelectionAndResult)
                .mapToInt(Integer::intValue)
                .sum();

        System.out.println("Second Solution: " + sum2);


    }

    static int calculateSelectionAndResult(String s) {
        int roundScore = 0;
        final var oppScore = RPS.fromValue(s.substring(0, 1)).getScore();


        if (s.contains("X")) {
            roundScore += LOSS;
            if(oppScore == 1) {
                roundScore += 3;
            }
            if(oppScore == 2) {
                roundScore += 1;
            }
            if(oppScore == 3) {
                roundScore += 2;
            }
        }

        if (s.contains("Y")) {
            roundScore += DRAW;
            roundScore += RPS.fromValue(s.substring(0, 1)).getScore();
        }

        if (s.contains("Z")) {
            roundScore += WIN;
            if(oppScore == 1) {
                roundScore += 2;
            }
            if(oppScore == 2) {
                roundScore += 3;
            }
            if(oppScore == 3) {
                roundScore += 1;
            }
        }

        return roundScore;
    }

    static int calculateScore(String s) {
        if (s.equals("X X")) {
            return DRAW + M_ROCK.getScore();
        }

        if (s.equals("Y Y")) {
            return DRAW + M_PAPER.getScore();
        }

        if (s.equals("Z Z")) {
            return DRAW + M_SCISSORS.getScore();
        }

        if (s.equals("X Y")) {
            return WIN + M_PAPER.getScore();
        }

        if (s.equals("Y Z")) {
            return WIN + M_SCISSORS.getScore();
        }

        if (s.equals("Z X")) {
            return WIN + M_ROCK.getScore();
        }

        if (s.equals("X Z")) {
            return LOSS + M_SCISSORS.getScore();
        }

        if (s.equals("Y X")) {
            return LOSS + M_ROCK.getScore();
        }

        if (s.equals("Z Y")) {
            return LOSS + M_PAPER.getScore();
        }

        return 0;

    }
}
