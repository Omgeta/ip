package omega.storage;

import omega.OmegaException;
import omega.task.Deadline;
import omega.task.Event;
import omega.task.Task;
import omega.task.TaskList;
import omega.task.TaskType;
import omega.task.Todo;

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

    private static Task parseBlock(Map<String, String> block) throws OmegaException {
        if (block.isEmpty()) return null;

        String typeCode = block.get("type");
        String done = block.get("done");
        String desc = block.get("desc");
        if (typeCode == null || done == null || desc == null)
            throw new OmegaException("Failed to read core task fields");
        TaskType type = TaskType.fromCode(typeCode);

        Task t;
        switch (type) {
        case TODO:
            t = new Todo(desc);
            break;
        case DEADLINE:
            String by = block.get("by");
            if (by == null)
                throw new OmegaException("Failed to read by field");
            t = new Deadline(desc, by);
            break;
        case EVENT:
            String from = block.get("from");
            String to = block.get("to");
            if (from == null || to == null)
                throw new OmegaException("Failed to read from or to fields");
            t = new Event(desc, block.get("from"), block.get("to"));
            break;
        default:
            return null; // won't hit this path
        }

        if ("1".equals(done.trim())) t.markDone();
        return t;
    }

    public List<Task> load() throws OmegaException {
        ensureFileExists();

        List<Task> tasks = new ArrayList<>();
        Map<String, String> block = new LinkedHashMap<>();

        try {
            for (String line : Files.readAllLines(this.filePath)) {
                if (!line.trim().isEmpty()) {
                    String[] entry = line.split("=", 2);
                    if (entry.length < 2) continue; // skip corrupted line
                    block.put(entry[0].trim(), entry[1]);
                } else { // at an empty line splitting tasks
                    Task t = parseBlock(block);
                    tasks.add(t);
                    block.clear();
                }
            }
        } catch (IOException e) {
            throw new OmegaException("Failed to read the save file: " + e.getMessage());
        } catch (OmegaException e) {
            throw new OmegaException("Failed to read corrupted save file: " + e.getMessage());
        }

        return tasks;
    }

    public void save(TaskList tasks) throws OmegaException {
        ensureFileExists();

        List<String> lines = new ArrayList<>();
        for (Task t : tasks.toList()) {
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
