package org.realtime;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

public class CountZScore implements CountZScoreInterface {


    public double[] normalize;

    public double[] normalize(double[] mathArray) {
        DescriptiveStatistics stats = new DescriptiveStatistics();

        // Add the data from the series to stats
        for (int i = 0; i < mathArray.length; i++) {
            stats.addValue(mathArray[i]);
        }

        // Compute mean and standard deviation
        double mean = stats.getMean();
        double standardDeviation = stats.getStandardDeviation();

        // initialize the standardizedSample, which has the same length as the sample
        double[] standardizedSample = new double[mathArray.length];

        for (int i = 0; i < mathArray.length; i++) {
            // z = (x- mean)/standardDeviation
            standardizedSample[i] = (mathArray[i] - mean) / standardDeviation;
        }
        return standardizedSample;
    }
}

