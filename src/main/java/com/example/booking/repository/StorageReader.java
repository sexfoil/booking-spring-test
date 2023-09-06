package repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public interface StorageReader {

    Logger log = LoggerFactory.getLogger(StorageReader.class);

    default List<String> getStorageData(String filepath) {
        Path path = Paths.get(filepath);
        try {
            List<String> data = Files.readAllLines(path);
            log.info("SUCCESS! Read data from file: " + filepath);
            return data;
        } catch (IOException e) {
            log.error("No such file: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
