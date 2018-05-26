package com.company.exercise2_1;

import static com.company.exercise2_1.HashSetTest.hashSetTests;
import static com.company.exercise2_1.LinkedHashSetTest.linkedHashSetTests;
import static com.company.exercise2_1.TreeSetTest.treeSetTests;

public class Utils {
    public static void exercise2_1() throws Exception {
        //warmup
        treeSetTests(10000000);
        hashSetTests(10000000);
        linkedHashSetTests(10000000);

        //tests
        treeSetTests(10);
        treeSetTests(100);
        treeSetTests(1000);
        treeSetTests(10000);

        hashSetTests(10);
        hashSetTests(100);
        hashSetTests(1000);
        hashSetTests(10000);

        linkedHashSetTests(10);
        linkedHashSetTests(100);
        linkedHashSetTests(1000);
        linkedHashSetTests(1000);
    }





}
