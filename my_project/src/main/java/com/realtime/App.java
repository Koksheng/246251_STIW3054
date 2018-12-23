package com.realtime;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public class App {

    public static void main(String[] args) {
        int processor = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = Executors.newFixedThreadPool(processor);
        ArrayList<Future<ObjectPDF>> futureArrayList;
        File[] pdfFile;

        // File Directory
        File directoryPath = new File("D:\\Users\\User\\Desktop\\Homework\\Sem 5\\Real-Time Programming\\Test");
        pdfFile = directoryPath.listFiles(pathname -> pathname.getName().endsWith(".pdf"));
        System.out.println("$---------- PDF File Found ----------$");
        if (pdfFile != null) {
            Arrays.stream(pdfFile).map(File::getName).forEach(System.out::println);
        } else {
            System.out.println("No PDF File Detected");
            return;
        }
        // Start Reading PDF using Executor
        futureArrayList = Arrays.stream(pdfFile).map(file ->
                new ProcessPDF(file.toString(), file.getName())).map(executorService::submit).collect(Collectors.toCollection(ArrayList::new));
        /*
        for (File file : pdfFile) {
            ProcessPDF processPDF = new ProcessPDF(file.toString(), file.getName());
            Future<ObjectPDF> futureObjectPDF = executorService.submit(processPDF);
            futureArrayList.add(futureObjectPDF);
        }
        */
        executorService.shutdown();
        /*
        System.out.println("$---------- Each File Detail ----------$");
        try {
            for (Future<ObjectPDF> objectPDFFuture : futureArrayList) {
                System.out.println("File Name : " + objectPDFFuture.get().getFileName());
                System.out.println("Words : " + objectPDFFuture.get().getWordsNumber());
                System.out.println("ArrayList of Words Length : " + objectPDFFuture.get().getWordsLengthArrayList().size());
                System.out.println("Characters : " + objectPDFFuture.get().getCharactersNumber());
                System.out.println("List : " + objectPDFFuture.get().getCharacterHashMap());
                //System.out.println(objectPDFFuture.get().getWordsLengthArrayList());
                System.out.println();
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        */
        CountTotalPDF countTotalPDF = new CountTotalPDF(futureArrayList);
        System.out.println("\n$---------- Total Detail ----------$");
        System.out.println("Number of Files : " + pdfFile.length);
        System.out.println("Total Words : " + countTotalPDF.calculateTotalWords());
        System.out.println("ArrayList of Total Words Length : " + countTotalPDF.countCharactersLengthList().size());
        System.out.println("Total Characters : " + countTotalPDF.calculateTotalCharacters());
        countTotalPDF.charactersHashMap().forEach((key, value) -> System.out.print(key + ":" + value + " "));

        CountCommonsMath countCommonsMath = new CountCommonsMath(countTotalPDF.countCharactersLengthList());
        double mean = countCommonsMath.countMean();
        double variance = countCommonsMath.countVariance();
        double standardDeviation = countCommonsMath.countSD();

        System.out.println("\n\n$---------- Commons Math ----------$");
        System.out.printf("Mean : %.2f", mean);
        System.out.printf("%nVariance : %.4f", variance);
        System.out.printf("%nStandard Deviation : %.4f", standardDeviation);

        CountZscore countZscore = new CountZscore();
        double [] zScore = countZscore.normalize(countCommonsMath.getMathArray(),countCommonsMath.countMean(),countCommonsMath.countSD());
        //ystem.out.printf("%nZ score : ", zScore);
        for(int i=0;i<zScore.length;i++){
            System.out.printf("%nZ-Score of word[s] " + (i+1) + ": " + zScore[i]);

        }




        GraphNormalization graphNormalization = new GraphNormalization();
        //DrawBoxplot drawBoxplot = new DrawBoxplot();

        graphNormalization.normalizationGraph();
        
        EventQueue.invokeLater(new DrawBoxplot()::display);
        
        
        
    }

}
