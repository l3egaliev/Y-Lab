package kg.rakhim.classes.dao;

//class StorageTest {
//
//
//
//    @Test
//    void testGetUser() {
//        Storage storage = new Storage();
//
//        User existingUser = new User("existingUser", "existingPassword", UserRole.USER);
//        storage.getUsers().add(existingUser);
//
//        User resultUser = storage.getUser("existingUser");
//        assertNotNull(resultUser);
//        assertEquals(existingUser, resultUser);
//
//        User nonExistentUser = storage.getUser("nonexistentUser");
//        assertNull(nonExistentUser);
//    }
//
//    // Проверка конструктора
//    @Test
//    void testConstructor() {
//        Storage storage = new Storage();
//
//        // Проверим что storage не пуст, и в нем создается user с ролью админ.
//        assertFalse(storage.getUsers().isEmpty());
//        assertEquals(storage.getUsers().get(0).getRole(), UserRole.ADMIN);
//    }
//}
