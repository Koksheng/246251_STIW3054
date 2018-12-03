package org.testing;
import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;
import org.apache.commons.math3.stat.descriptive.moment.Variance;
import org.apache.commons.math3.stat.descriptive.summary.Sum;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class ReadPDF {
    private String text;
    private ArrayList<Integer> countWordsArrayList = new ArrayList<Integer>();

    public synchronized void extractText(String path) throws IOException {
        // Load File
        File file = new File(path);
        try (PDDocument pdDocument = PDDocument.load(file)) {
            pdDocument.getClass();

            if (!pdDocument.isEncrypted()) {
                PDFTextStripper pdfTextStripper = new PDFTextStripper();
                text = pdfTextStripper.getText(pdDocument);
            }
        }
    }

    public void calculateText() {
        //System.out.println(text);
        System.out.println("Number of Words : " + countWords(text));
        //System.out.println("Number of Characters : " + countCharacters(text));
        countCharacterTypes(text);
    }

    public int countWords(String text) {
        if (text == null || text.isEmpty()) {
            return 0;
        }

        //String [] words = text.split("\\s+");
        StringTokenizer tokenizer = new StringTokenizer(text);
        //return words.length;
        return tokenizer.countTokens();
    }

    public void countCharacterTypes(String text) {
        HashMap<Character, Integer> charactersMap = new HashMap<Character, Integer>();

        char[] charArray = text.toCharArray();

        for (char c : charArray) {
            if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
                if (charactersMap.containsKey(c)) {
                    charactersMap.put(c, charactersMap.get(c) + 1);
                } else {
                    charactersMap.put(c, 1);
                }
            }
        }

        double count = 0;
        double sum = 0;
        for (Map.Entry<Character, Integer> entry : charactersMap.entrySet()) {
            Character key = entry.getKey();
            Integer value = entry.getValue();
            count++;
            sum = sum + value;
            System.out.println(key + " : " + value);
        }
        double mean = sum/count;
        double temp = 0;
        for (Map.Entry<Character, Integer> entry : charactersMap.entrySet()) {
            temp = temp + Math.pow((entry.getValue() - mean),2);
        }
        double SD = Math.sqrt(temp/count);
        System.out.println("SD is: " + SD);
    }


}
