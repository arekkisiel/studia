package com.company.exercise2_2;

import com.company.Benchmark.Benchmark;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;

public class ArrayListTest {
    public static void arrayListTest(int size) throws Exception {
        ArrayList object = createArrayList(size);

        System.out.println("Size: " + size);
        System.out.println("ArrayList - Add at the beginning");
        System.out.println(new Benchmark().TestMean(() -> {
            object.add(0, ThreadLocalRandom.current().nextInt(10000000, 10000200));
            return null;
        }, 10).time + "ns");

        System.out.println("ArrayList - Remove from the beginning");
        System.out.println(new Benchmark().TestMean(() -> {
            object.remove(0);
            return null;
        }, 10).time + "ns");

        System.out.println("ArrayList - Add at the end");
        System.out.println(new Benchmark().TestMean(() -> {
            object.add(object.size(), ThreadLocalRandom.current().nextInt(10000000, 10000200));
            return null;
        }, 10).time + "ns");

        System.out.println("ArrayList - Remove from the end");
        System.out.println(new Benchmark().TestMean(() -> {
            object.remove(object.size() - 1);
            return null;
        }, 10).time + "ns");

        System.out.println("ArrayList - Add at random place");
        System.out.println(new Benchmark().TestMean(() -> {
            object.add(ThreadLocalRandom.current().nextInt(0, object.size()), ThreadLocalRandom.current().nextInt(10000000, 10000200));
            return null;
        }, 10).time + "ns");

        System.out.println("ArrayList - Remove from the random place");
        System.out.println(new Benchmark().TestMean(() -> {
            object.remove(ThreadLocalRandom.current().nextInt(0, object.size()));
            return null;
        }, 10).time + "ns");

        System.out.println("ArrayList - Browse with indexes");
        System.out.println(new Benchmark().TestMean(() -> {
            for (Object element : object) {
            }
            return null;
        }, 10).time + "ns");

        System.out.println("ArrayList - Browse with iterator");
        Iterator iterator = object.iterator();
        System.out.println(new Benchmark().TestMean(() -> {
            while (iterator.hasNext())
                iterator.next();
            return null;
        }, 10).time + "ns");
    }


    public static ArrayList<Integer> createArrayList ( int size){
        ArrayList object = new ArrayList<Integer>(size);
        for (int index = 0; index < size; index++)
            object.add(index);
        return object;
    }
}