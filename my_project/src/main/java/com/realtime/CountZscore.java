package com.realtime;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

public class CountZscore implements CountZscoreInterface {



        @Override
        public double[] normalize(double[] mathArray,double mean, double sd) {

            DescriptiveStatistics stats = new DescriptiveStatistics();

            // Add the data from the series to stats
            for(double num:mathArray){
                stats.addValue(num);
            }

            // initialize the standardizedSample, which has the same length as the sample
            double[] standardizedSample = new double[mathArray.length];



            for (int i = 0; i < mathArray.length; i++) {
                // z = (x- mean)/standardDeviation
                standardizedSample[i] = (mathArray[i] - mean) / sd;
            }
            return standardizedSample;
        }
    }


