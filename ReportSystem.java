import java.io.*;
import java.util.*;

public class ReportSystem {

    public static void showReports(Scanner input) {
        function.clearScreen();

        bestSellingMovies();
        peakHours();

        function.pressEnterToContinue(input);
    }

    // ===== BEST SELLING =====
    public static void bestSellingMovies() {
        System.out.println("===== BEST SELLING MOVIES =====");

        HashMap<String, Integer> movieCount = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader("booking.txt"))) {

            String line;
            String currentMovie = "";

            while ((line = br.readLine()) != null) {

                if (line.startsWith("Movie:")) {
                    currentMovie = line.substring(7).trim();

                    movieCount.put(currentMovie,
                            movieCount.getOrDefault(currentMovie, 0) + 1);
                }
            }

        } catch (Exception e) {
            System.out.println("Error reading booking file");
        }

        if (movieCount.isEmpty()) {
            System.out.println("No bookings yet.");
            return;
        }

        // 排序
        List<Map.Entry<String, Integer>> list =
                new ArrayList<>(movieCount.entrySet());

        list.sort((a, b) -> b.getValue() - a.getValue());

        for (Map.Entry<String, Integer> entry : list) {
            System.out.println(entry.getKey() + " - " +
                    entry.getValue() + " tickets");
        }

        System.out.println();
    }

    // ===== PEAK HOURS =====
    public static void peakHours() {
        System.out.println("===== PEAK HOURS =====");

        HashMap<String, Integer> timeCount = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader("booking.txt"))) {

            String line;
            String currentTime = "";

            while ((line = br.readLine()) != null) {

                if (line.startsWith("Show Time:")) {
                    currentTime = line.substring(10).trim();

                    timeCount.put(currentTime,
                            timeCount.getOrDefault(currentTime, 0) + 1);
                }
            }

        } catch (Exception e) {
            System.out.println("Error reading booking file");
        }

        if (timeCount.isEmpty()) {
            System.out.println("No bookings yet.");
            return;
        }

        // 排序
        List<Map.Entry<String, Integer>> list =
                new ArrayList<>(timeCount.entrySet());

        list.sort((a, b) -> b.getValue() - a.getValue());

        for (Map.Entry<String, Integer> entry : list) {
            System.out.println(entry.getKey() + " - " +
                    entry.getValue() + " bookings");
        }

        System.out.println();
    }
}