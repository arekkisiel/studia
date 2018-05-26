package com.company.exercise2_2;

import com.company.Benchmark.Benchmark;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static com.company.exercise2_2.ArrayDequeTest.arrayDequeTest;
import static com.company.exercise2_2.ArrayListTest.arrayListTest;
import static com.company.exercise2_2.LinkedListTest.linkedListTest;
import static com.company.exercise2_2.PriorityQueueTest.priorityQueueTest;

public class Utils {

    public static void exercise2_2() throws Exception {
        //warmup
        arrayListTest(10000000);
        linkedListTest(10000000);
        arrayDequeTest(10000000);
        priorityQueueTest(10000000);
        //Lists: ArrayList, LinkedList
        //Queues: ArrayDeque, PriorityQueue
        arrayListTest(10);
        arrayListTest(100);
        arrayListTest(1000);
        arrayListTest(10000);

        linkedListTest(10);
        linkedListTest(100);
        linkedListTest(1000);
        linkedListTest(10000);

        arrayDequeTest(10);
        arrayDequeTest(100);
        arrayDequeTest(1000);
        arrayDequeTest(10000);

        priorityQueueTest(10);
        priorityQueueTest(100);
        priorityQueueTest(1000);
        priorityQueueTest(10000);
    }
}
