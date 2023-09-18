package org.example;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        validatePhoneNumbers();
        userToJson();
        wordsCounter();
    }

    public static void validatePhoneNumbers() {
        try (BufferedReader reader = new BufferedReader(new FileReader("file.txt"))) {
            String line;
            System.out.println("-----------------------------------");
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                Pattern pattern = Pattern.compile("^\\(\\d{3}\\) \\d{3}-\\d{4}$|^\\d{3}-\\d{3}-\\d{4}$");
                Matcher matcher = pattern.matcher(line);
                if (matcher.matches()) {
                    System.out.println(line);
                }
            }

            System.out.println("-----------------------------------");
        } catch (IOException e) {
         e.printStackTrace();
        }
    }

    public static void userToJson() {
        String filePath = "users.txt";

        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String header = reader.readLine();
            List<User> users = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(" ");
                String name = data[0];
                int age = Integer.parseInt(data[1]);
                User user = new User(name, age);
                users.add(user);
            }
            reader.close();

            FileWriter writer = new FileWriter("user.json");

            Gson gson = new Gson();
            String json = gson.toJson(users);

            writer.write(json);
            writer.close();

            System.out.println("JSON file created");
            System.out.println("-----------------------------------");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void wordsCounter() {
        String filePath = "words.txt";

        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));

            Map<String, Integer> frequencyMap = new HashMap<>();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] words = line.split("\\s+");

                for (String word : words) {
                    if (frequencyMap.containsKey(word)) {
                        frequencyMap.put(word, frequencyMap.get(word) + 1);
                    }
                    else {
                        frequencyMap.put(word, 1);
                    }
                }
            }

            reader.close();

            Map<String, Integer> sortedFrequencyMap = new TreeMap<>((word1, word2) -> {
                int frequency1 = frequencyMap.get(word1);
                int frequency2 = frequencyMap.get(word2);
                return Integer.compare(frequency2, frequency1);
            });
            sortedFrequencyMap.putAll(frequencyMap);

            for (String word : sortedFrequencyMap.keySet()) {
                int frequency = sortedFrequencyMap.get(word);
                System.out.println(word + " " + frequency);
            }

            System.out.println("-----------------------------------");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}