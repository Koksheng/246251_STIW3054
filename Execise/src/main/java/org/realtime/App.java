package org.realtime;


import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;
import org.apache.commons.math3.stat.descriptive.moment.Variance;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * Hello world!
 *
 */
public class App 
{
    public static void main(String[] args) {
        int threads = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = Executors.newFixedThreadPool(threads/2);
        ArrayList<Future<Integer>> futureIntegerArrayList = new ArrayList<>();
        ArrayList<Future<HashMap<Character, Integer>>> futureHashMapArrayList = new ArrayList<>();
        ArrayList<Integer> calculateSdArrayList = new ArrayList<>();



        // Place all the PDF document in a selected File
        ReadDocument readDocument = new ReadDocument("D:\\Users\\User\\Desktop\\Homework\\Sem 5\\Real-Time Programming\\Test File");
        try {
            readDocument.readPDF();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < readDocument.getArrayListPDF().size(); i++) {
            CallableCountWords callableCountWords = new CallableCountWords(readDocument.getArrayListPDF().get(i));
            Future<Integer> futureInteger = executorService.submit(callableCountWords);
            futureIntegerArrayList.add(futureInteger);

            CallableCountCharacters callableCountCharacters = new CallableCountCharacters(readDocument.getArrayListPDF().get(i));
            Future<HashMap<Character, Integer>> futureHashMap = executorService.submit(callableCountCharacters);
            futureHashMapArrayList.add(futureHashMap);
        }

        AtomicInteger totalWords = new AtomicInteger();
        final int[] Number = new int[1];
        Mean mean = new Mean();
        Variance sVariance = new Variance();
        Variance pVariance = new Variance(false);
        StandardDeviation sampleSD = new StandardDeviation();
        StandardDeviation populationSD = new StandardDeviation(false);
        System.out.println("\n$---------- Total Words for Each Document ----------$");
        futureIntegerArrayList.forEach(future -> {
            try {
                System.out.println("Document : " + future.get() + " Words");
                totalWords.addAndGet(future.get());
                Number[0] = future.get();
                calculateSdArrayList.add(Number[0]);

            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });

        System.out.println("Total Words from Documents : " + totalWords);


        //calculate sd

        double[] calculateArray = new double[calculateSdArrayList.size()];
        for (int k = 0; k < calculateArray.length; k++) {
            calculateArray[k] = calculateSdArrayList.get(k);
        }
        System.out.println("Mean : " + mean.evaluate(calculateArray));
        System.out.println("Sample Variance : " + sVariance.evaluate(calculateArray));
        System.out.println("Population Variance : " + pVariance.evaluate(calculateArray));
        System.out.println("Sample SD : " + sampleSD.evaluate(calculateArray));
        System.out.println("Population SD : " + populationSD.evaluate(calculateArray));

//        //add/multiply
//        Manager manager = new Manager();
//        manager.setStrategy(new CountSD());
//        double result = manager.operation();
//        System.out.println(result);

        System.out.println("\n$---------- Total Characters ----------$");

        for (int i = 0; i < futureHashMapArrayList.size(); i++){
            try {
                System.out.println(futureHashMapArrayList.get(i).get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        HashMap<Character, Integer> totalCharactersHashMap = new HashMap<>();

        for (int j = 0; j < futureHashMapArrayList.size(); j++) {
            try {
                futureHashMapArrayList.get(j).get().forEach((key, value) -> {
                    totalCharactersHashMap.merge(key, value, Integer::sum);
                });
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        System.out.println("\n$---------- Total Characters ----------$");
        totalCharactersHashMap.forEach((key, value) -> System.out.println(key + " : " + value + " "));

        executorService.shutdown();
    }


}