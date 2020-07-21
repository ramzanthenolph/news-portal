package models;

import dao.DatabaseRule;
import org.junit.*;

import static org.junit.Assert.*;

public class UserTest {
    @Rule
    public DatabaRule databaseRule = new DatabaseRule();

    private User newUser(){
        return new User("Kanye West","Manager","Artist","Media");
    }

    @Test
    public void initializeCorrectly(){
        User user = newUser();
        assertTrue(user instanceof User);
    }

    @Test
    public void getFunctionsWorkWell(){
        User user = newUser();
        assertEquals(user.getName(),newUser().getName());
        assertEquals(user.getPosition(),newUser().getPosition());
        assertEquals(user.getRole(),newUser().getRole());
        assertEquals(user.getDepartment(),newUser().getDepartment());
    }

}