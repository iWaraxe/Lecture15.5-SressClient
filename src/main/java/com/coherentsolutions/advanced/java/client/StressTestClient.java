package com.coherentsolutions.advanced.java.client;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StressTestClient {

    public static void main(String[] args) throws InterruptedException {
        int numRequests = 1000;  // Number of requests to send
        ExecutorService executorService = Executors.newFixedThreadPool(10);  // You can adjust this for testing

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < numRequests; i++) {
            final int requestId = i;
            executorService.submit(() -> sendRequest(requestId));
        }

        executorService.shutdown();
        while (!executorService.isTerminated()) {
            // wait for all tasks to complete
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Completed " + numRequests + " requests in " + (endTime - startTime) + " ms.");
    }

    private static void sendRequest(int requestId) {
        try {
            URL url = new URL("http://localhost:8080");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            System.out.println("Request " + requestId + " handled by server with response code: " + responseCode);

            connection.disconnect();
        } catch (IOException e) {
            System.err.println("Request " + requestId + " failed: " + e.getMessage());
        }
    }
}
