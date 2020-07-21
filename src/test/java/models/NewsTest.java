package models;

import dao.DatabaseRule;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class NewsTest {
    @Rule
    public DatabaseRule databaseRule = new DatabaseRule();

    private News simpleNews(){
        return new News("Simple News","today is sunny");
    }
    private News altNews(){
        return new News("New Album","burna boy releases new album");
    }
}