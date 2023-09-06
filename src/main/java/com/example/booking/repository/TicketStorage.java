package repository;

import model.Ticket;
import model.TicketImpl;

import javax.annotation.PostConstruct;
import java.util.List;

public class TicketStorage extends Storage<Ticket> implements StorageReader {

    private final String storageFilePath;

    public TicketStorage(String storageFilePath) {
        this.storageFilePath = storageFilePath;
    }


    @PostConstruct
    private void initStorage() {
        List<String> rows = getStorageData(storageFilePath);
        log.info("Tickets storage created!");

        if (!rows.isEmpty()) {
            rows.forEach(s -> {
                String[] arr = s.split(",");
                Ticket ticket = new TicketImpl();
                long id = Long.parseLong(arr[0]);
                ticket.setId(id);
                ticket.setEventId(Long.parseLong(arr[1]));
                ticket.setUserId(Long.parseLong(arr[2]));
                ticket.setCategory(Ticket.Category.valueOf(arr[3]));
                ticket.setPlace(Integer.parseInt(arr[4]));
                storageMap.put(id, ticket);
            });
            log.info("Tickets data saved in storage!");
        }
    }
}
