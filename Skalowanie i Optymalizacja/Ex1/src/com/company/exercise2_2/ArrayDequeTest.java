package com.company.exercise2_2;

import com.company.Benchmark.Benchmark;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;

public class ArrayDequeTest {
    public static void arrayDequeTest(int size) throws Exception {
        ArrayDeque object = createArrayDeque(size);

        System.out.println("Size: " + size);
        System.out.println("ArrayDeque - Add at the beginning");
        System.out.println(new Benchmark().TestMean(() -> {
            object.addFirst(ThreadLocalRandom.current().nextInt(10000000, 10000200));
            return null;
        }, 10).time + "ns");

        System.out.println("ArrayDeque - Remove from the beginning");
        System.out.println(new Benchmark().TestMean(() -> {
            object.remove(0);
            return null;
        }, 10).time + "ns");

        System.out.println("ArrayDeque - Add at the end");
        System.out.println(new Benchmark().TestMean(() -> {
            object.addLast(ThreadLocalRandom.current().nextInt(10000000, 10000200));
            return null;
        }, 10).time + "ns");

        System.out.println("ArrayDeque - Remove from the end");
        System.out.println(new Benchmark().TestMean(() -> {
            object.removeLast();
            return null;
        }, 10).time + "ns");

        System.out.println("ArrayDeque - Add at random place");
        System.out.println(new Benchmark().TestMean(() -> {
            object.add(ThreadLocalRandom.current().nextInt(10000000, 10000200));
            return null;
        }, 10).time + "ns");

        System.out.println("ArrayDeque - Remove from the random place");
        System.out.println(new Benchmark().TestMean(() -> {
            object.remove(ThreadLocalRandom.current().nextInt(0, object.size()));
            return null;
        }, 10).time + "ns");

        System.out.println("ArrayDeque - Browse with indexes");
        System.out.println(new Benchmark().TestMean(() -> {
            for (Object element : object) {
            }
            return null;
        }, 10).time + "ns");

        System.out.println("ArrayDeque - Browse with iterator");
        Iterator iterator = object.iterator();
        System.out.println(new Benchmark().TestMean(() -> {
            while (iterator.hasNext())
                iterator.next();
            return null;
        }, 10).time + "ns");
    }


    public static ArrayDeque<Integer> createArrayDeque ( int size){
        ArrayDeque object = new ArrayDeque<Integer>(size);
        for (int index = 0; index < size; index++)
            object.add(index);
        return object;
    }
}