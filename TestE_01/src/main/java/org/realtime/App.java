package org.realtime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;
import org.apache.commons.math3.stat.descriptive.moment.Variance;
import org.apache.commons.math3.stat.descriptive.summary.Sum;
/**
 * Hello world!
 *
 */
public class App 
{
    private static ArrayList<String> pathArrayList = new ArrayList<>();


    public static void main( String[] args ) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        ArrayList<Future<Integer>> futureIntegerArrayList = new ArrayList<>();
        ArrayList<Future<HashMap<Character, Integer>>> futureHashMapArrayList = new ArrayList<>();
        ArrayList<Integer> totalCharactersHashMapList = new ArrayList<Integer>();

        PDFFilePath();

        for (int i = 0; i < pathArrayList.size(); i++) {
            CallableCountWords callableCountWords = new CallableCountWords(pathArrayList.get(i));
            Future<Integer> futureInteger = executorService.submit(callableCountWords);
            futureIntegerArrayList.add(futureInteger);

            CallableCountCharacters callableCountCharacters = new CallableCountCharacters(pathArrayList.get(i));
            Future<HashMap<Character, Integer>> futureHashMap = executorService.submit(callableCountCharacters);
            futureHashMapArrayList.add(futureHashMap);
        }

        AtomicInteger totalWords = new AtomicInteger();
        futureIntegerArrayList.forEach(future -> {
            try {
                System.out.println("Document : " + future.get() + " Words");
                totalWords.addAndGet(future.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });

        System.out.println("Total Words from Documents : " + totalWords);
        System.out.println("\n$---------- Total Characters ----------$");

        for (int i = 0; i < futureHashMapArrayList.size(); i++) {
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

            //start from here I edit
            Sum sum = new Sum();
            Mean mean = new Mean();
            Variance sVariance = new Variance();
            Variance pVariance = new Variance(false);
            StandardDeviation sampleSD = new StandardDeviation();
            StandardDeviation populationSD = new StandardDeviation(false);


        for (Map.Entry<Character, Integer> entry : totalCharactersHashMap.entrySet()) {
            Character key = entry.getKey();
            Integer value = entry.getValue();
            totalCharactersHashMapList.add(value);
            System.out.println(key + " : " + value + " ");
        }

            double[] calculateArray = new double[totalCharactersHashMapList.size()];
            for (int k = 0; k < calculateArray.length; k++) {
                calculateArray[k] = totalCharactersHashMapList.get(k);
            }

            System.out.println("Sum : " + sum.evaluate(calculateArray));
            System.out.println("Mean : " + mean.evaluate(calculateArray));
            System.out.println("Sample Variance : " + sVariance.evaluate(calculateArray));
            System.out.println("Population Variance : " + pVariance.evaluate(calculateArray));
            System.out.println("Sample SD : " + sampleSD.evaluate(calculateArray));
            System.out.println("Population SD : " + populationSD.evaluate(calculateArray));
            System.out.println();

        executorService.shutdown();
        }


//        double count = 0;
//        double sum = 0;
//        for (Map.Entry<Character, Integer> entry : totalCharactersHashMap.entrySet()) {
//            Character key = entry.getKey();
//            Integer value = entry.getValue();
//            count++;
//            sum = sum + value;
//            System.out.println(key + " : " + value + " ");
//        }
//        double mean = sum/26;
//        double temp = 0;
//        for (Map.Entry<Character, Integer> entry : totalCharactersHashMap.entrySet()) {
//            temp = temp + Math.pow((entry.getValue() - mean),2);
//        }
//        double SD = Math.sqrt(temp/count);
//        System.out.println("SD is: " + SD);




    public static void PDFFilePath(){
        // PDF Document Path
        pathArrayList.add("D:/Users/User/Desktop/Homework/Sem 5/JWP Final A181.pdf");
    }

}