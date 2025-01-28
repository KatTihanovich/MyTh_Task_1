package com.sdc.reader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class TaskReader {
    private static final Logger logger = LogManager.getLogger(TaskReader.class);

    public static List<String> readTasksFromFile(String file) {
        URL filePath = TaskReader.class.getResource(file);
        List<String> parsedTasks = new ArrayList<>();
        if (filePath == null) {
            String errorMessage = "Invalid file path: " + file;
            logger.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }

        try (Stream<String> lines = Files.lines(Paths.get(filePath.toURI()))) {
            lines.forEach(parsedTasks::add);
        } catch (IOException | URISyntaxException e) {
            logger.error(e);
            throw new RuntimeException("Error parsing file", e);
        }
        return parsedTasks;
    }
}