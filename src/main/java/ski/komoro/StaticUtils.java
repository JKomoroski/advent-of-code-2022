package ski.komoro;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

class StaticUtils {

    static List<String> readFile(String path) throws IOException {
        return Files.readAllLines(Path.of(path));
    }
}
