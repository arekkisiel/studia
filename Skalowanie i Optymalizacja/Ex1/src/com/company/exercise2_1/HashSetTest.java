package com.company.exercise2_1;

import com.company.Benchmark.Benchmark;

import java.util.HashSet;
import java.util.concurrent.ThreadLocalRandom;

public class HashSetTest {

    public static void hashSetTests(int setSize) throws Exception {
        HashSet hashSet = createHashSet(setSize);

        System.out.println("Set Size: " + setSize);
        System.out.println("HashSet - Add");

        System.out.println(new Benchmark().TestMean(() -> {
            hashSet.add(ThreadLocalRandom.current().nextInt(10000000, 10000200));
            return null;
        },10).time + "ns");

        System.out.println("HashSet - Browse");

        System.out.println(new Benchmark().TestMean(() -> {
            for(Object o : hashSet){}
            return null;
        },10).time + "ns");

        System.out.println("HashSet - Remove");

        System.out.println(new Benchmark().TestMean(() -> {
            hashSet.remove(ThreadLocalRandom.current().nextInt(1, setSize));
            return null;
        },10).time + "ns");

        System.out.println("HashSet - Check Existance (Positive)");

        System.out.println(new Benchmark().TestMean(() -> {
            hashSet.contains(1);
            return null;
        },10).time + "ns");

        System.out.println("HashSet - Check Existance (Negative)");

        System.out.println(new Benchmark().TestMean(() -> {
            hashSet.contains(10000222);
            return null;
        },10).time + "ns");
    }

    private static HashSet createHashSet(int setSize) {
        HashSet hashSet = new HashSet();
        for(int index = 0; index < setSize; index++)
            hashSet.add(index);
        return hashSet;
    }
}
