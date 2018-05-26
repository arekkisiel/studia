package com.company.exercise2_1;

import com.company.Benchmark.Benchmark;

import java.util.TreeSet;
import java.util.concurrent.ThreadLocalRandom;

public class TreeSetTest {
    public static void treeSetTests(int setSize) throws Exception {
        TreeSet treeSet = createTreeSet(setSize);

        System.out.println("Set Size: " + setSize);
        System.out.println("TreeSet - Add");

        System.out.println(new Benchmark().TestMean(() -> {
            treeSet.add(ThreadLocalRandom.current().nextInt(10000000, 10000200));
            return null;
        },10).time + "ns");

        System.out.println("TreeSet - Browse");

        System.out.println(new Benchmark().TestMean(() -> {
            for(Object o : treeSet){}
            return null;
        },10).time + "ns");

        System.out.println("TreeSet - Remove");

        System.out.println(new Benchmark().TestMean(() -> {
            treeSet.remove(treeSet.last());
            return null;
        },10).time + "ns");

        System.out.println("TreeSet - Check Existance (Positive)");

        System.out.println(new Benchmark().TestMean(() -> {
            treeSet.contains(1);
            return null;
        },10).time + "ns");

        System.out.println("TreeSet - Check Existance (Negative)");

        System.out.println(new Benchmark().TestMean(() -> {
            treeSet.contains(10000222);
            return null;
        },10).time + "ns");
    }

    private static TreeSet createTreeSet(int setSize) {
        TreeSet treeSet = new TreeSet();
        for(int index = 0; index < setSize; index++)
            treeSet.add(index);
        return treeSet;
    }
}
