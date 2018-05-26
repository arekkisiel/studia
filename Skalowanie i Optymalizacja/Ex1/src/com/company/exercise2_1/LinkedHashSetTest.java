package com.company.exercise2_1;

import com.company.Benchmark.Benchmark;

import java.util.LinkedHashSet;
import java.util.concurrent.ThreadLocalRandom;

public class LinkedHashSetTest {

    public static void linkedHashSetTests(int setSize) throws Exception {
        LinkedHashSet linkedHashSet = createLinkedHashSet(setSize);

        System.out.println("Set Size: " + setSize);
        System.out.println("LinkedHashSet - Add");

        System.out.println(new Benchmark().TestMean(() -> {
            linkedHashSet.add(ThreadLocalRandom.current().nextInt(10000000, 10000200));
            return null;
        },10).time + "ns");

        System.out.println("LinkedHashSet - Browse");

        System.out.println(new Benchmark().TestMean(() -> {
            for(Object o : linkedHashSet){}
            return null;
        },10).time + "ns");

        System.out.println("LinkedHashSet - Remove");

        System.out.println(new Benchmark().TestMean(() -> {
            linkedHashSet.remove(ThreadLocalRandom.current().nextInt(1, setSize));
            return null;
        },10).time + "ns");

        System.out.println("LinkedHashSet - Check Existance (Positive)");

        System.out.println(new Benchmark().TestMean(() -> {
            linkedHashSet.contains(1);
            return null;
        },10).time + "ns");

        System.out.println("LinkedHashSet - Check Existance (Negative)");

        System.out.println(new Benchmark().TestMean(() -> {
            linkedHashSet.contains(10000222);
            return null;
        },10).time + "ns");
    }

    private static LinkedHashSet createLinkedHashSet(int setSize) {
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        for(int index = 0; index < setSize; index++)
            linkedHashSet.add(index);
        return linkedHashSet;
    }
}
