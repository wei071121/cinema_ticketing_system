import java.io.*;
import java.util.*;

public class ReportSystem {
    
    private String bookingFilePath;
    private HashMap<String, Integer> movieCount;
    private HashMap<String, Integer> timeCount;

    // ===== CONSTRUCTOR =====
    public ReportSystem() {
        this("booking.txt");
    }

    public ReportSystem(String bookingFilePath) {
        this.bookingFilePath = bookingFilePath;
        this.movieCount = new HashMap<>();
        this.timeCount = new HashMap<>();
    }

    // ===== PUBLIC METHODS =====
    public void showReports(Scanner input) {
        function.clearScreen();

        displayBestSellingMovies();
        displayPeakHours();

        function.pressEnterToContinue(input);
    }

    // ===== BEST SELLING MOVIES =====
    public void displayBestSellingMovies() {
        System.out.println("===== BEST SELLING MOVIES =====");

        movieCount.clear();
        loadMovieData();

        if (movieCount.isEmpty()) {
            System.out.println("No bookings yet.");
            System.out.println();
            return;
        }

        List<Map.Entry<String, Integer>> sortedList = sortByValue(movieCount);
        displayMovieReport(sortedList);
    }

    private void loadMovieData() {
        try (BufferedReader br = new BufferedReader(new FileReader(bookingFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("Movie:")) {
                    String currentMovie = line.substring(7).trim();
                    movieCount.put(currentMovie,
                            movieCount.getOrDefault(currentMovie, 0) + 1);
                }
            }
        } catch (Exception e) {
            System.out.println("Error reading booking file");
        }
    }

    private void displayMovieReport(List<Map.Entry<String, Integer>> sortedList) {
        for (Map.Entry<String, Integer> entry : sortedList) {
            System.out.println(entry.getKey() + " - " +
                    entry.getValue() + " tickets");
        }
        System.out.println();
    }

    // ===== PEAK HOURS =====
    public void displayPeakHours() {
        System.out.println("===== PEAK HOURS =====");

        timeCount.clear();
        loadTimeData();

        if (timeCount.isEmpty()) {
            System.out.println("No bookings yet.");
            System.out.println();
            return;
        }

        List<Map.Entry<String, Integer>> sortedList = sortByValue(timeCount);
        displayTimeReport(sortedList);
    }

    private void loadTimeData() {
        try (BufferedReader br = new BufferedReader(new FileReader(bookingFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("Show Time:")) {
                    String currentTime = line.substring(10).trim();
                    timeCount.put(currentTime,
                            timeCount.getOrDefault(currentTime, 0) + 1);
                }
            }
        } catch (Exception e) {
            System.out.println("Error reading booking file");
        }
    }

    private void displayTimeReport(List<Map.Entry<String, Integer>> sortedList) {
        for (Map.Entry<String, Integer> entry : sortedList) {
            System.out.println(entry.getKey() + " - " +
                    entry.getValue() + " bookings");
        }
        System.out.println();
    }

    // ===== PRIVATE HELPER METHODS =====
    private List<Map.Entry<String, Integer>> sortByValue(HashMap<String, Integer> map) {
        List<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet());
        list.sort((a, b) -> b.getValue() - a.getValue());
        return list;
    }
}