package dao;

import models.Department;
import models.User;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class Sql2oDeptDaoTest {
    private Sql2oDeptDao deptDao = new Sql2oDeptDao();
    private static Sql2oUserDao userDao = new Sql2oUserDao();

    @Rule
    public DatabaseRule databaseRule = new DatabaseRule();

    private Department newDept(){
        Department department = new Department("Producers","We the best");
        deptDao.add(department);
        return department;
    }

    private Department newDept2(){
        Department department = new Department("Wrestlers","No comment");
        deptDao.add(department);
        return department;
    }

    private User newUser(){
        User user = new User("Kanye West","Manager","Artist","Media");
        userDao.add(user);
        return user;
    }

    private User newUser2(){
        User user = new User("John Cena","Wrestler","Invisible","Wrestler");
        userDao.add(user);
        return user;
    }

}