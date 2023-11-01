import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class AnimatedBarChart extends JFrame implements Runnable {

    private static final long serialVersionUID = 1L;
    private DefaultCategoryDataset dataset;
    private JFreeChart chart;

    public AnimatedBarChart(String title) {
        super(title);
        dataset = createDataset();

        chart = ChartFactory.createBarChart(
                "Visits Over Time",
                "Platform",
                "Visits",
                dataset,
                PlotOrientation.HORIZONTAL,
                true,
                true,
                false
        );

        CategoryPlot plot = chart.getCategoryPlot();
        plot.setRangeGridlinePaint(Color.GRAY);
        plot.setBackgroundPaint(new Color(240,240,240));
        
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, new Color(255, 103, 103));
        renderer.setSeriesPaint(1, new Color(255, 180, 103));
        renderer.setSeriesPaint(2, new Color(255, 226, 103));
        renderer.setSeriesPaint(3, new Color(136, 255, 103));
        renderer.setSeriesPaint(4, new Color(103, 173, 255));

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(900, 500));
        setContentPane(chartPanel);
    }

    private DefaultCategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        dataset.addValue(158, "Shopee", "2023");
        dataset.addValue(117, "Tokopedia", "2023");
        dataset.addValue(83.2, "Lazada", "2023");
        dataset.addValue(25.4, "Blibli", "2023");
        dataset.addValue(18.1, "Bukalapak", "2023");

        return dataset;
    }

    @Override
    public void run() {
        Random rand = new Random();
        while (true) {
            try {
                Thread.sleep(500); // Delay for 0.5 second for smoother animation
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (String platform : new String[]{"Shopee", "Tokopedia", "Lazada", "Blibli", "Bukalapak"}) {
                double currentValue = dataset.getValue(platform, "2023").doubleValue();
                double change = rand.nextDouble() * 5 - 2.5; // Random change between -2.5 and 2.5
                double newValue = Math.max(0, currentValue + change);
                dataset.setValue(newValue, platform, "2023");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AnimatedBarChart chart = new AnimatedBarChart("Animated Bar Chart Example");
            chart.pack();
            chart.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            chart.setVisible(true);
            
            Thread animationThread = new Thread(chart);
            animationThread.start();
        });
    }
}
