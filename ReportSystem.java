import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

public class ReportSystem {

    public static void showReports(){

        bestSellingMovie();
        peakHours();

    }

    public static void bestSellingMovie(){

        System.out.println("\n===== BEST SELLING MOVIE =====");

        HashMap<String,Integer> movieCount =
                new HashMap<>();

        try{

            BufferedReader br =
                    new BufferedReader(
                            new FileReader("booking.txt")
                    );

            String line;

            while((line = br.readLine()) != null){

                String[] data = line.split(",");

                String movie = data[1];

                movieCount.put(
                        movie,
                        movieCount.getOrDefault(movie,0) + 1
                );

            }

            br.close();

            for(String movie : movieCount.keySet()){

                System.out.println(
                        movie + " - " +
                        movieCount.get(movie) + " tickets"
                );

            }

        }catch(Exception e){

            System.out.println(e);

        }

    }

    public static void peakHours(){

        System.out.println("\n===== PEAK HOURS =====");

        HashMap<String,Integer> timeCount =
                new HashMap<>();

        try{

            BufferedReader br =
                    new BufferedReader(
                            new FileReader("booking.txt")
                    );

            String line;

            while((line = br.readLine()) != null){

                String[] data = line.split(",");

                String time = data[2];

                timeCount.put(
                        time,
                        timeCount.getOrDefault(time,0) + 1
                );

            }

            br.close();

            for(String time : timeCount.keySet()){

                System.out.println(
                        time + " - " +
                        timeCount.get(time) + " bookings"
                );

            }

        }catch(Exception e){

            System.out.println(e);

        }

    }

}