import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Storage {
    private final Path filePath;

    public Storage(Path filePath) {
        this.filePath = filePath;
    }

    public void load(List<Task> tasks) throws OmegaException {
        ensureFileExists();

        tasks.clear();
        Map<String, String> block = new LinkedHashMap<>();

        try {
            for (String line : Files.readAllLines(this.filePath)) {
                if (!line.trim().isEmpty()) {
                    String[] entry = line.split("=", 2);
                    block.put(entry[0], entry[1]);
                } else { // at an empty line splitting tasks
                    tasks.add(parseBlock(block));
                    block.clear();
                }
            }
        } catch(IOException e) {
            throw new OmegaException("Failed to read the save file.");
        }
    }

    public void save(List<Task> tasks) throws OmegaException {
        ensureFileExists();

        List<String> lines = new ArrayList<>();
        for (Task t : tasks) {
            for (Map.Entry<String, String> e : t.toSerializationMap().entrySet()) {
                lines.add(e.getKey() + "=" + e.getValue());
            }
            lines.add("");
        }

        try {
            Files.write(filePath, lines);
        } catch (IOException e) {
            throw new OmegaException("Failed to write to the save file.");
        }
    }

    private static Task parseBlock(Map<String, String> block) {
        if (block.isEmpty()) return null;

        String type = block.get("type");
        String done = block.get("done");
        String desc = block.get("desc");

        Task t;
        switch (type) {
            case "T":
                t = new Todo(desc);
                break;
            case "D":
                t = new Deadline(desc, block.get("by"));
                break;
            case "E":
                t = new Event(desc, block.get("from"), block.get("to"));
                break;
            default:
                return null;
        }

        if ("1".equals(done.trim())) t.markDone();
        return t;
    }

    private void ensureFileExists() throws OmegaException {
        try {
            Files.createDirectories(filePath.getParent());
            if (!Files.exists(filePath))
                Files.createFile(filePath);
        } catch (IOException e) {
            throw new OmegaException("Could not set up save file.");
        }
    }
}
