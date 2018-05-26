package com.company.exercise2_2;

import com.company.Benchmark.Benchmark;

import java.util.PriorityQueue;
import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;

public class PriorityQueueTest {
    public static void priorityQueueTest(int size) throws Exception {
        PriorityQueue object = createPriorityQueue(size);

//        System.out.println("Size: " + size);
//        System.out.println("PriorityQueue - Add at the beginning");
//        System.out.println(new Benchmark().TestMean(() -> {
//            object.add(ThreadLocalRandom.current().nextInt(10000000, 10000200));
//            return null;
//        }, 10).time + "ns");
//
//        System.out.println("PriorityQueue - Remove from the beginning");
//        System.out.println(new Benchmark().TestMean(() -> {
//            object.remove(0);
//            return null;
//        }, 10).time + "ns");

        System.out.println("PriorityQueue - Add at the end");
        System.out.println(new Benchmark().TestMean(() -> {
            object.add(ThreadLocalRandom.current().nextInt(10000000, 10000200));
            return null;
        }, 10).time + "ns");

        System.out.println("PriorityQueue - Remove from the end");
        System.out.println(new Benchmark().TestMean(() -> {
            object.remove();
            return null;
        }, 10).time + "ns");

//        System.out.println("PriorityQueue - Add at random place");
//        System.out.println(new Benchmark().TestMean(() -> {
//            object.add(ThreadLocalRandom.current().nextInt(10000000, 10000200));
//            return null;
//        }, 10).time + "ns");

        System.out.println("PriorityQueue - Remove from the random place");
        System.out.println(new Benchmark().TestMean(() -> {
            object.remove(ThreadLocalRandom.current().nextInt(0, object.size()));
            return null;
        }, 10).time + "ns");

        System.out.println("PriorityQueue - Browse with indexes");
        System.out.println(new Benchmark().TestMean(() -> {
            for (Object element : object) {
            }
            return null;
        }, 10).time + "ns");

        System.out.println("PriorityQueue - Browse with iterator");
        Iterator iterator = object.iterator();
        System.out.println(new Benchmark().TestMean(() -> {
            while (iterator.hasNext())
                iterator.next();
            return null;
        }, 10).time + "ns");
    }


    public static PriorityQueue<Integer> createPriorityQueue ( int size){
        PriorityQueue object = new PriorityQueue<Integer>(size);
        for (int index = 0; index < size; index++)
            object.add(index);
        return object;
    }
}