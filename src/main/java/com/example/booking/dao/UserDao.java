package dao;

import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import repository.UserStorage;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class UserDao {

    private UserStorage userStorage;

    public User getUserById(long id) {
        return userStorage.getStorageMap().get(id);
    }

    public User getUserByEmail(String email) {
        return userStorage.getStorageMap().values().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst()
                .orElse(null); // it's better to change return type to Optional<User>... but it's not in scope of this task :-)
    }

    public List<User> getUsersByName(String name) {
        return userStorage.getStorageMap().values().stream()
                .filter(user -> user.getName().equals(name))
                .collect(Collectors.toList());
    }

    public User createUser(User user) {
        long id = userStorage.getNextId();
        user.setId(id);
        userStorage.getStorageMap().put(id, user);
        return userStorage.getStorageMap().get(id);
    }

    public User updateUser(User user) {
        User oldUser = getUserById(user.getId());
        if (oldUser == null) {
            return createUser(user);
        }
        userStorage.getStorageMap().put(oldUser.getId(), user);
        return getUserById(oldUser.getId());
    }

    public boolean deleteUser(long userId) {
        User removed = userStorage.getStorageMap().remove(userId);
        return removed != null;
    }

    @Autowired
    public void setUserStorage(UserStorage userStorage) {
        this.userStorage = userStorage;
    }
}
