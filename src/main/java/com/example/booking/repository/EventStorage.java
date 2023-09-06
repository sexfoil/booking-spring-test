package repository;

import model.Event;
import model.EventImpl;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;

public class EventStorage extends Storage<Event> implements StorageReader {

    private final String storageFilePath;

    public EventStorage(String storageFilePath) {
        this.storageFilePath = storageFilePath;
    }

    @PostConstruct
    private void initStorage() {
        List<String> rows = getStorageData(storageFilePath);
        log.info("Events storage created!");

        if (!rows.isEmpty()) {
            rows.forEach(s -> {
                String[] arr = s.split(",");
                Event event = new EventImpl();
                long id = Long.parseLong(arr[0]);
                event.setId(id);
                event.setTitle(arr[1]);
                event.setDate(new Date(Long.parseLong(arr[2])));
                storageMap.put(id, event);
            });
            log.info("Events data saved in storage!");
        }
    }
}
