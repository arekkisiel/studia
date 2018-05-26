package com.company.exercise2_2;

import com.company.Benchmark.Benchmark;

import java.util.LinkedList;
import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;

public class LinkedListTest {
    public static void linkedListTest(int size) throws Exception {
        LinkedList object = createLinkedList(size);

        System.out.println("Size: " + size);
        System.out.println("LinkedList - Add at the beginning");
        System.out.println(new Benchmark().TestMean(() -> {
            object.add(0, ThreadLocalRandom.current().nextInt(10000000, 10000200));
            return null;
        }, 10).time + "ns");

        System.out.println("LinkedList - Remove from the beginning");
        System.out.println(new Benchmark().TestMean(() -> {
            object.remove(0);
            return null;
        }, 10).time + "ns");

        System.out.println("LinkedList - Add at the end");
        System.out.println(new Benchmark().TestMean(() -> {
            object.add(object.size(), ThreadLocalRandom.current().nextInt(10000000, 10000200));
            return null;
        }, 10).time + "ns");

        System.out.println("LinkedList - Remove from the end");
        System.out.println(new Benchmark().TestMean(() -> {
            object.remove(object.size() - 1);
            return null;
        }, 10).time + "ns");

        System.out.println("LinkedList - Add at random place");
        System.out.println(new Benchmark().TestMean(() -> {
            object.add(ThreadLocalRandom.current().nextInt(0, object.size()), ThreadLocalRandom.current().nextInt(10000000, 10000200));
            return null;
        }, 10).time + "ns");

        System.out.println("LinkedList - Remove from the random place");
        System.out.println(new Benchmark().TestMean(() -> {
            object.remove(ThreadLocalRandom.current().nextInt(0, object.size()));
            return null;
        }, 10).time + "ns");

        System.out.println("LinkedList - Browse with indexes");
        System.out.println(new Benchmark().TestMean(() -> {
            for (Object element : object) {
            }
            return null;
        }, 10).time + "ns");

        System.out.println("LinkedList - Browse with iterator");
        Iterator iterator = object.iterator();
        System.out.println(new Benchmark().TestMean(() -> {
            while (iterator.hasNext())
                iterator.next();
            return null;
        }, 10).time + "ns");
    }


    public static LinkedList<Integer> createLinkedList ( int size){
        LinkedList object = new LinkedList<Integer>();
        for (int index = 0; index < size; index++)
            object.add(index);
        return object;
    }
}