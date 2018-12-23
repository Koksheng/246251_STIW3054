package com.realtime;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.statistics.DefaultBoxAndWhiskerCategoryDataset;

//https://stackoverrun.com/cn/q/9870709
//website
public class DrawBoxplot implements DrawBoxplotInterface {

    private static final String ROW_KEY = "City";


    @Override
    public void display() {
        JFrame f = new JFrame("BoxPlot");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        DefaultBoxAndWhiskerCategoryDataset data = new DefaultBoxAndWhiskerCategoryDataset();
        data.add(getInputData(), ROW_KEY, "Coruscant");
        JFreeChart chart = ChartFactory.createBoxAndWhiskerChart(
                "Box and Whisker Chart", ROW_KEY, "Temperature", data, false);
        f.add(new ChartPanel(chart) {


            public Dimension getPreferredSize() {
                return new Dimension(320, 480);
            }
        });
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }


    private List<Number> getInputData() {
        Scanner s = new Scanner("30 36 46 55 65 76 81 80 71 59 44 34");
        List<Number> list = new ArrayList<>();
        do {
            list.add(s.nextDouble());
        } while (s.hasNext());
        return list;
    }
}
