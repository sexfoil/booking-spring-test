package repository;

import model.User;
import model.UserImpl;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

public class UserStorage extends Storage<User> implements StorageReader {

    private final String storageFilePath;

    public UserStorage(String storageFilePath) {
        this.storageFilePath = storageFilePath;
    }

    @PostConstruct
    private void initStorage() throws IOException {
        List<String> rows = getStorageData(storageFilePath);
        log.info("Users storage created!");

        if (!rows.isEmpty()) {
            rows.forEach(s -> {
                String[] arr = s.split(",");
                User user = new UserImpl();
                long id = Long.parseLong(arr[0]);
                user.setId(id);
                user.setName(arr[1]);
                user.setEmail(arr[2]);
                storageMap.put(id, user);
            });
            log.info("Users data saved in storage!");
        }
    }
}
