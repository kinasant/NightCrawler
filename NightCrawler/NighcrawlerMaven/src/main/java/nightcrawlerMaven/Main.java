package nightcrawlerMaven;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Webcrawler> bots = new ArrayList<>();

        /*
        System.out.println("Enter website URLs:");
        while (true) {
            String url = scanner.nextLine();
            if (url.isEmpty()) {
                break;
            }
            int depth = 2; 
            bots.add(new Webcrawler(url, depth));
        }
        
        System.out.print("Enter the desired number of threads: ");
        int numThreads = scanner.nextInt();
        scanner.close(); 
        */
        int numThreads = 5;
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        Thread audioThread = new Thread(() -> {
            AudioPlayer audioPlayer = new AudioPlayer();
            audioPlayer.playAudio();
        });
    	System.out.print("\nProject Nightcrawler Activated\n");
        audioThread.start();
        try {
            Thread.sleep(7000); 
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    		bots.add(new Webcrawler("https://www.apnews.com", 5));
            bots.add(new Webcrawler("https://www.nbcnews.com", 6));
        	bots.add(new Webcrawler("https://www.thehindu.com", 4));
        	bots.add(new Webcrawler("https://www.bbc.com", 7));
        	bots.add(new Webcrawler("https://www.news.google.com", 3));

        long startTime = System.currentTimeMillis();


        for (Webcrawler w : bots) {
            executor.submit(w);
        }

        executor.shutdown(); 

        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        System.out.println("Execution time: " + executionTime + " milliseconds");
        System.out.println("Thread count: " + numThreads);
        System.exit(0);
    }
}