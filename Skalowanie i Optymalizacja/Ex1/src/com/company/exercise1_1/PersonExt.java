package com.company.exercise1_1;
import java.io.*;


public class PersonExt implements Externalizable {
    private String surname = null;
    private String firstname = null;
    private String birthday = null;
    private String age = null;


    public PersonExt (String surname, String firstname, String birthday, String age) {
        this.surname = surname;
        this.firstname = firstname;
        this.birthday = birthday;
        this.age = age;
    }

    public PersonExt(){}

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

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(surname);
        out.writeObject(firstname);
        out.writeObject(birthday);
        out.writeObject(age);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        surname = (String) in.readObject();
        firstname = (String) in.readObject();
        birthday = (String) in.readObject();
        age = (String) in.readObject();
    }
}
