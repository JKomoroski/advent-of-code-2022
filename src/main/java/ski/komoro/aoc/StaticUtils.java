package ski.komoro.aoc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

class StaticUtils {

    private StaticUtils() {
        throw new IllegalStateException("Utility class");
    }

    static List<String> readFile(String path) throws IOException {
        return Files.readAllLines(Path.of(path));
    }
}
