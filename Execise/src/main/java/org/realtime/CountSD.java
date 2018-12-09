package org.realtime;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;
import org.apache.commons.math3.stat.descriptive.moment.Variance;

/**
 *
 * @author Aman
 */
public class CountSD {

    ArrayList<Integer> calculateSdArrayList = new ArrayList<>();
    Mean mean = new Mean();
    Variance sVariance = new Variance();
    Variance pVariance = new Variance(false);
    StandardDeviation sampleSD = new StandardDeviation();
    StandardDeviation populationSD = new StandardDeviation(false);

    //calculate sd
    public void CountSD(ArrayList<Future<Integer>> futureIntegerArrayList){
        futureIntegerArrayList.forEach(future -> {
            try {
                calculateSdArrayList.add(future.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });

        double[] calculateArray = new double [calculateSdArrayList.size()];
        calculateSdArrayList.forEach(num->calculateArray[calculateSdArrayList.indexOf(num)]=num);

        System.out.println("Mean : " + mean.evaluate(calculateArray));
        System.out.println("Sample Variance : " + sVariance.evaluate(calculateArray));
        System.out.println("Population Variance : " + pVariance.evaluate(calculateArray));
        System.out.println("Sample SD : " + sampleSD.evaluate(calculateArray));
        System.out.println("Population SD : " + populationSD.evaluate(calculateArray));
    }
}
