package ru.job4j.dream.store;

import org.junit.Test;
import ru.job4j.dream.model.User;

import static org.junit.Assert.*;

public class MemStoreTest {

    /*@Test
    public void whenSaveUserThenFindByEmailDone() {
        Store store = MemStore.instOf();
        User user1 = new User(0, "User1", "user1@mail.ru", "password");
        store.saveUser(user1);
        assertEquals(store.findUserByEmail("user1@mail.ru"), user1);
    }

    @Test
    public void whenSaveUserThenFindByEmailFailed() {
        Store store = MemStore.instOf();
        User user2 = new User(0, "User2", "user2@mail.ru", "password");
        store.saveUser(user2);
        assertNull(store.findUserByEmail("some@mail"));
    }*/
}