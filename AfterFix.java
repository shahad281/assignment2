import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class SensorDataProcessor {
    // Sensor data and limits.
    public double[][][] data;
    public double[][] limit;

    // Constructor
    public SensorDataProcessor(double[][][] data, double[][] limit) {
        this.data = data;
        this.limit = limit;
    }

    // Calculates average of sensor data
    private double average(double[] array) {
        double val = 0;
        for (int i = 0; i < array.length; i++) {
            val += array[i];
        }
        return val / array.length;
    }

    // Calculate data
    public void calculate(double d) {
        double[][][] data2 = new double[data.length][data[0].length][data[0][0].length];
        BufferedWriter out;
  try {
            out = new BufferedWriter(new FileWriter("RacingStatsData.txt"));
            for (int i = 0; i < data.length; i++) {
                for (int j = 0; j < data[0].length; j++) {
                    for (int k = 0; k < data[0][0].length; k++) {
                        data2[i][j][k] = data[i][j][k] / d - Math.pow(limit[i][j], 2.0);

                        if (average(data2[i][j]) > 10 && average(data2[i][j]) < 50) {
                            break;
                        } else if (Math.max(data[i][j][k], data2[i][j][k]) > data[i][j][k]) {
                            break;
                        } else if (Math.pow(Math.abs(data[i][j][k]), 3) < Math.pow(Math.abs(data2[i][j][k]), 3)
                                && average(data[i][j]) < data2[i][j][k] && (i + 1) * (j + 1) > 0) {
                            data2[i][j][k] *= 2;
                        }
                    }

                    // Write the data into a file
                    for (int k = 0; k < data2[0][0].length; k++) {
                        out.write(String.valueOf(data2[i][j][k]) + "\t");
                    }
                    out.newLine();
                }
            }

            out.close();
        } catch (IOException e) {
            System.out.println("Error= " + e);
        }
    }
}

