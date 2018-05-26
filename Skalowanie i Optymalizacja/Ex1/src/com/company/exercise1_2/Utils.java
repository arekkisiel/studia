package com.company.exercise1_2;

import com.company.Benchmark.Benchmark;

import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Utils {

    public static void exercise1_2() throws Exception {
        String filepath = "test.dat";
        createFile(filepath);

        System.out.println("Buffered read");

        System.out.println(new Benchmark().TestMean(() -> {
            bufferedRead(filepath);
            return null;
        },10).time + "ns");

        System.out.println("NIO ");

        System.out.println(new Benchmark().TestMean(() -> {
            nioBuffers(filepath);
            return null;
        },10).time + "ns");

        System.out.println("Memory mapped");
        System.out.println(new Benchmark().TestMean(() -> {
            memoryMappedFiles(filepath);
            return null;
        }, 10).time + "ns");
    }

    public static RandomAccessFile createFile(String filepath) throws Exception {
        RandomAccessFile file = new RandomAccessFile(filepath, "rw");
        MappedByteBuffer out = file.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, 0xFFFFFFF);
        for (int i = 0; i < 0xFFFFFFF; i++)
            out.put((byte) 'x');
        return file;
    }

    public static void bufferedRead(String filepath) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filepath));
        bufferedReader.readLine();
        while (bufferedReader.readLine() != null) {
        }
    }

    public static void nioBuffers(String filepath) throws IOException {
        Files.readAllBytes(Paths.get(filepath));
    }

    public static void memoryMappedFiles(String filepath) throws IOException {
        FileChannel fc = new RandomAccessFile(filepath, "r").getChannel();
        MappedByteBuffer buffer = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());

        for (int i = 0; i < buffer.limit(); i++)
        {
            buffer.get();
        }
    }
}
