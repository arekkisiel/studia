package com.company.exercise1_1;

import java.io.*;

public class Utils {
    public static void exercise1_1() throws IOException, ClassNotFoundException {
        serializableObjectTest();
        externalizableObjectTest();
    }

    public static void serializableObjectTest() throws IOException, ClassNotFoundException {
        PersonSer new_person = new PersonSer("Kisiel", "Arkadiusz", "02-08-1992", "25");
        serialize(new_person, "ser.txt");
        PersonSer read_person = deserialize("ser.txt");
        System.out.println(read_person);
    }

    public static void externalizableObjectTest() throws IOException, ClassNotFoundException {
        PersonExt new_person = new PersonExt("Kisiel", "Arkadiusz", "02-08-1992", "25");
        serializeExt(new_person, "ext.txt");
        PersonExt read_person = deserializeExt("ext.txt");
        System.out.println(read_person);
    }

    private static void serialize(PersonSer object, String filename) throws IOException {
        FileOutputStream fileOutputStream= new FileOutputStream(filename);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        //Serialize object of this type to a file
        objectOutputStream.writeObject(object);
    }

    private static PersonSer deserialize(String source) throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream= new FileInputStream(source);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        //Serialize object of this type to a file
        return (PersonSer) objectInputStream.readObject();
    }

    private static void serializeExt(PersonExt object, String filename) throws IOException {
        FileOutputStream fileOutputStream= new FileOutputStream(filename);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        //Serialize object of this type to a file
        objectOutputStream.writeObject(object);
    }

    private static PersonExt deserializeExt(String source) throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(source);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        //Serialize object of this type to a file
        return (PersonExt) objectInputStream.readObject();
    }
}
