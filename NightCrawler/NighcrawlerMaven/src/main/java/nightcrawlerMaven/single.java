package nightcrawlerMaven;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class single {

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
            int depth = 2; // Default depth (you can make this configurable if needed)
            bots.add(new Webcrawler(url, depth));
        }
        */
        int numThreads = 1;
		bots.add(new Webcrawler("https://www.apnews.com", 5));
        bots.add(new Webcrawler("https://www.nbcnews.com", 6));
    	bots.add(new Webcrawler("https://www.thehindu.com", 4));
    	bots.add(new Webcrawler("https://www.bbc.com", 7));
    	bots.add(new Webcrawler("https://www.news.google.com", 3));
        long startTime = System.currentTimeMillis();
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

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
