package com.company.exercise1_1;
import java.io.*;


public class PersonSer implements Serializable {
    private String surname = null;
    private String firstname = null;
    private String birthday = null;
    private String age = null;


    public PersonSer (String surname, String firstname, String birthday, String age) {
        this.surname = surname;
        this.firstname = firstname;
        this.birthday = birthday;
        this.age = age;
    }

    public String getFirstname() {
        return this.firstname;
    }

    public String getSurname() {
        return this.surname;
    }

    public String getBirthday() {
        return this.birthday;
    }

    public String getAge() {
        return this.age;
    }
}
