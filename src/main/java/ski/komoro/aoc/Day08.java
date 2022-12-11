package ski.komoro.aoc;


import java.util.Arrays;
import java.util.stream.IntStream;

public class Day08 {

    public static void main(String[] args) throws Exception {
        final var list = StaticUtils.readFile("./input_08.txt");
        final var width = list.get(0).length();
        final var height = list.size();

        final int[][] grid = list.stream()
                .map(Day08::stringToIntArray)
                .toArray(int[][]::new);

        final var ans1 = checkTreeVisibilty(grid);

        System.out.println("Part One Answer: " + ans1);

        final var ans2 = findHighestScenicScore(grid);

        System.out.println("Part Two Answer: " + ans2);
    }

    static int[] stringToIntArray(String s) {
        return s.chars().map(Character::getNumericValue).toArray();
    }

    private static int checkTreeVisibilty(int[][] grid) {

        var numRows = grid.length;
        var numCols = grid[0].length;
        var visibleGrid = new int[numRows][numCols];

        // iterate horizontally
        for (var row = 0; row < numRows; row++) {

            // Visible from left edge
            var treeLineFromVantage = -1;
            for (var col = 0; col < numCols; col++) {
                final var treeHeight = grid[row][col];
                if (treeHeight > treeLineFromVantage) {
                    visibleGrid[row][col] = 1;
                    treeLineFromVantage = grid[row][col];
                }
            }

            // Reset and check if Visible from right edge
            treeLineFromVantage = -1;
            for (var col = numCols - 1; col >= 0; col--) {
                if (grid[row][col] > treeLineFromVantage) {
                    visibleGrid[row][col] = 1;
                    treeLineFromVantage = grid[row][col];
                }
            }
        }

        // iterate vertically
        for (var col = 0; col < numCols; col++) {
            // Reset and Check if Visible from top edge
            var treeLineFromVantage = -1;
            for (var row = 0; row < numRows; row++) {
                if (grid[row][col] > treeLineFromVantage) {
                    visibleGrid[row][col] = 1;
                    treeLineFromVantage = grid[row][col];
                }
            }
            // Reset and check if visible from bottom edge
            treeLineFromVantage = -1;
            for (var row = numRows - 1; row >= 0; row--) {
                if (grid[row][col] > treeLineFromVantage) {
                    visibleGrid[row][col] = 1;
                    treeLineFromVantage = grid[row][col];
                }
            }
        }

        // Count the number of visibleGrid trees
        return Arrays.stream(visibleGrid)
                .flatMapToInt(IntStream::of)
                .sum();

    }

    private static int findHighestScenicScore(int[][] grid) {

        var numRows = grid.length;
        var numCols = grid[0].length;
        var scoreGrid = new int[numRows][numCols];

        // iterate horizontally, skip edges
        for (var row = 1; row < numRows - 1; row++) {
            // iterate vertically, skip edges
            for (var col = 1; col < numCols - 1; col++) {
                int up = 0;
                int left = 0;
                int down = 0;
                int right = 0;
                int currentHeight = grid[row][col];

                // Look Up
                for(int i = row - 1; i >= 0; i--) {
                    up++;
                    if (grid[i][col] >= currentHeight) {
                        break;
                    }
                }

                // Look Left
                for(int i = col - 1; i >= 0; i--) {
                    left++;
                    if (grid[row][i] >= currentHeight) {
                        break;
                    }
                }

                // Look Down
                for(int i = row + 1; i < numRows; i++) {
                    down++;
                    if (grid[i][col] >= currentHeight) {
                        break;
                    }
                }

                // Look Right
                for(int i = col + 1; i < numRows; i++) {
                    right++;
                    if (grid[row][i] >= currentHeight) {
                        break;
                    }
                }

                scoreGrid[row][col] = multiplyViews(up, left, down, right);
            }
        }

        return Arrays.stream(scoreGrid)
                .flatMapToInt(IntStream::of)
                .max()
                .orElseThrow();
    }

    private static int multiplyViews(int up, int left, int down, int right) {
        return up * left * down * right;
    }
}
