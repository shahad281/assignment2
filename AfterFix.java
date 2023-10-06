import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class SensorDataProcessor {
    // Sensor data and limits.
    public double[][][] sensorData;
    public double[][] limits;

    // Constructor
    public SensorDataProcessor(double[][][] sensorData, double[][] limits) {
        this.sensorData = sensorData;
        this.limits = limits;
    }

    // Calculates average of sensor data
    private double calculateAverage(double[] array) {
        double sum = 0;
        for (int i = 0; i < array.length; i++) {
            sum += array[i];
        }
        return sum / array.length;
    }

    // Calculate processed data
    public void calculateProcessedData(double divisor) {
        double[][][] processedData = new double[sensorData.length][sensorData[0].length][sensorData[0][0].length];
        BufferedWriter writer;

   try {
            writer = new BufferedWriter(new FileWriter("RacingStatsData.txt"));
            for (int i = 0; i < sensorData.length; i++) {
                for (int j = 0; j < sensorData[0].length; j++) {
                    for (int k = 0; k < sensorData[0][0].length; k++) {
                        processedData[i][j][k] = sensorData[i][j][k] / divisor - Math.pow(limits[i][j], 2.0);

                        if (calculateAverage(processedData[i][j]) > 10 && calculateAverage(processedData[i][j]) < 50) {
                            break;
                        } else if (Math.max(sensorData[i][j][k], processedData[i][j][k]) > sensorData[i][j][k]) {
                            break;
                        } else if (Math.pow(Math.abs(sensorData[i][j][k]), 3) < Math.pow(Math.abs(processedData[i][j][k]), 3)
                                && calculateAverage(sensorData[i][j]) < processedData[i][j][k] && (i + 1) * (j + 1) > 0) {
                            processedData[i][j][k] *= 2;
                        }
                    }

                    // Write the processed data into a file
                    for (int k = 0; k < processedData[0][0].length; k++) {
                        writer.write(String.valueOf(processedData[i][j][k]) + "\t");
                    }
                    writer.newLine();
                }
            }

            writer.close();
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }
}
