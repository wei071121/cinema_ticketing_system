import java.util.*;
import java.io.*;

public class staffMenu {

    static Scanner input = new Scanner(System.in);

    // 文件路径
    static final String MOVIE_FILE = "movies.txt";

    public static void mainMenu(String staffName) {
        int choice = -1;
        do {
            function.clearScreen();
            System.out.println("=== STAFF MENU ===");
            System.out.println("Welcome, " + staffName);
            System.out.println("1. View Movies & Showtimes");
            System.out.println("2. Add Movie");
            System.out.println("3. Remove Movie");
            System.out.println("4. Add Showtime");
            System.out.println("5. Remove Showtime");
            System.out.println("0. Logout");
            System.out.print("Your choice: ");
            choice = input.nextInt(); input.nextLine();

            switch (choice) {
                case 1: viewMovies(); break;
                case 2: addMovie(); break;
                case 3: removeMovie(); break;
                case 4: addShowtime(); break;
                case 5: removeShowtime(); break;
                case 0: System.out.println("Logging out..."); function.pressEnterToContinue(input); break;
                default: System.out.println("Invalid input!"); function.pressEnterToContinue(input); break;
            }
        } while(choice != 0);
    }

    // Movie 类
    static class Movie {
        String title;
        String genre;
        int duration;
        String ageRating;
        List<String> showtimes;

        public Movie(String t, String g, int d, String ar) {
            title = t;
            genre = g;
            duration = d;
            ageRating = ar;
            showtimes = new ArrayList<>();
        }
    }

    // 读取 movies.txt 文件
    static List<Movie> loadMovies() {
        List<Movie> movies = new ArrayList<>();
        try (Scanner sc = new Scanner(new File(MOVIE_FILE))) {
            Movie current = null;
            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (line.isEmpty()) continue;
                if (line.equals("---")) { current = null; continue; }

                if (current == null) {
                    // 新电影: Title|Genre|Duration|AgeRating
                    String[] parts = line.split("\\|");
                    if (parts.length == 4) {
                        current = new Movie(parts[0], parts[1], Integer.parseInt(parts[2]), parts[3]);
                        movies.add(current);
                    }
                } else {
                    // showtime: Time|Hall
                    current.showtimes.add(line);
                }
            }
        } catch(Exception e) {
            System.out.println("Error reading movie file: " + e.getMessage());
        }
        return movies;
    }

    // 保存 movies 到文件
    static void saveMovies(List<Movie> movies) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(MOVIE_FILE))) {
            for (Movie m : movies) {
                pw.println(m.title + "|" + m.genre + "|" + m.duration + "|" + m.ageRating);
                for (String s : m.showtimes) pw.println(s);
                pw.println("---"); // 分隔电影
            }
        } catch(Exception e) {
            System.out.println("Error saving movie file: " + e.getMessage());
        }
    }

    // 查看电影
    static void viewMovies() {
        List<Movie> movies = loadMovies();
        if (movies.isEmpty()) { System.out.println("No movies available."); function.pressEnterToContinue(input); return; }
        System.out.println("=== Movies & Showtimes ===");
        for (Movie m : movies) {
            System.out.println("Title: " + m.title + " | Genre: " + m.genre + " | Duration: " + m.duration + " | Age: " + m.ageRating);
            if (m.showtimes.isEmpty()) System.out.println("  No showtimes.");
            else {
                System.out.println("  Showtimes:");
                for (String s : m.showtimes) System.out.println("    - " + s);
            }
        }
        function.pressEnterToContinue(input);
    }

    // 添加电影
    static void addMovie() {
        System.out.print("Enter Title: "); String title = input.nextLine();
        System.out.print("Enter Genre: "); String genre = input.nextLine();
        System.out.print("Enter Duration (min): "); int dur = input.nextInt(); input.nextLine();
        System.out.print("Enter Age Rating: "); String ar = input.nextLine();

        List<Movie> movies = loadMovies();
        Movie m = new Movie(title, genre, dur, ar);
        movies.add(m);
        saveMovies(movies);

        System.out.println("Movie added!");
        function.pressEnterToContinue(input);
    }

    // 删除整部电影
    static void removeMovie() {
        List<Movie> movies = loadMovies();
        if (movies.isEmpty()) { System.out.println("No movies to remove."); function.pressEnterToContinue(input); return; }

        System.out.println("Select movie to remove:");
        for (int i = 0; i < movies.size(); i++) System.out.println((i+1) + ". " + movies.get(i).title);
        int choice = input.nextInt(); input.nextLine();
        if (choice < 1 || choice > movies.size()) { System.out.println("Invalid choice."); function.pressEnterToContinue(input); return; }

        System.out.println("Confirm remove \"" + movies.get(choice-1).title + "\"? (Y/N): ");
        String confirm = input.nextLine().trim().toUpperCase();
        if (confirm.equals("Y")) {
            movies.remove(choice-1);
            saveMovies(movies);
            System.out.println("Movie removed.");
        } else System.out.println("Cancelled.");
        function.pressEnterToContinue(input);
    }

    // 添加 showtime
    static void addShowtime() {
        List<Movie> movies = loadMovies();
        if (movies.isEmpty()) { System.out.println("No movies available."); function.pressEnterToContinue(input); return; }

        System.out.println("Select movie to add showtime:");
        for (int i = 0; i < movies.size(); i++) System.out.println((i+1) + ". " + movies.get(i).title);
        int choice = input.nextInt(); input.nextLine();
        if (choice < 1 || choice > movies.size()) { System.out.println("Invalid choice."); function.pressEnterToContinue(input); return; }

        Movie m = movies.get(choice-1);
        System.out.print("Enter Time (HH:mm): "); String time = input.nextLine();
        System.out.print("Enter Hall (Small/Medium/Large): "); String hall = input.nextLine();
        m.showtimes.add(time + "|" + hall);

        saveMovies(movies);
        System.out.println("Showtime added.");
        function.pressEnterToContinue(input);
    }

    // 删除 showtime
    static void removeShowtime() {
        List<Movie> movies = loadMovies();
        if (movies.isEmpty()) { System.out.println("No movies."); function.pressEnterToContinue(input); return; }

        System.out.println("Select movie:");
        for (int i = 0; i < movies.size(); i++) System.out.println((i+1) + ". " + movies.get(i).title);
        int choice = input.nextInt(); input.nextLine();
        if (choice < 1 || choice > movies.size()) { System.out.println("Invalid."); function.pressEnterToContinue(input); return; }

        Movie m = movies.get(choice-1);
        if (m.showtimes.isEmpty()) { System.out.println("No showtimes."); function.pressEnterToContinue(input); return; }

        System.out.println("Select showtime to remove:");
        for (int i = 0; i < m.showtimes.size(); i++) System.out.println((i+1) + ". " + m.showtimes.get(i));
        int tChoice = input.nextInt(); input.nextLine();
        if (tChoice < 1 || tChoice > m.showtimes.size()) { System.out.println("Invalid."); function.pressEnterToContinue(input); return; }

        m.showtimes.remove(tChoice-1);
        saveMovies(movies);
        System.out.println("Showtime removed.");
        function.pressEnterToContinue(input);
    }
}