package repository;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public abstract class Storage<T> {

    protected Map<Long, T> storageMap;

    public Storage() {
        this.storageMap = new HashMap<>();
    }

    public Map<Long, T> getStorageMap() {
        return storageMap;
    }

    public long getNextId() {
        return storageMap.keySet().stream()
                .max(Comparator.comparingLong(Long::longValue))
                .orElse(0L) + 1;
    }

}
