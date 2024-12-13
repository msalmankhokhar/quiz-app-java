import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class Question {
    private String question;
    private List<String> options;
    private int answer;

    public void display() {
        System.out.println(question);
        for (int i = 0; i < options.size(); i++) {
            System.out.println((i + 1) + ". " + options.get(i));
        }
    }

    public boolean validateAnswer(int userAnswer) {
        return userAnswer == answer;
    }
}

public class Main {
    public static void main(String[] args) {
        // Create a Gson instance to parse the JSON file
        Gson gson = new Gson();

        try (FileReader reader = new FileReader("questions.json")) {
            // Define the type of the data structure to parse
            Type type = new TypeToken<Map<String, List<Question>>>() {}.getType();

            // Parse JSON into a Map with categories and questions
            Map<String, List<Question>> quizzes = gson.fromJson(reader, type);

            // Display available categories
            System.out.println("Available categories:");
            List<String> categories = new ArrayList<>(quizzes.keySet());
            for (int i = 0; i < categories.size(); i++) {
                System.out.println((i + 1) + ". " + categories.get(i));
            }

            // Allow user to select a category
            Scanner scanner = new Scanner(System.in);
            System.out.print("Select a category: ");
            int categoryIndex = scanner.nextInt() - 1;

            if (categoryIndex < 0 || categoryIndex >= categories.size()) {
                System.out.println("Invalid category.");
                return;
            }

            // Start quiz for the selected category
            String selectedCategory = categories.get(categoryIndex);
            List<Question> questions = quizzes.get(selectedCategory);

            System.out.println("\nStarting quiz: " + selectedCategory);
            int score = 0;

            for (Question question : questions) {
                question.display();
                System.out.print("Your answer: ");
                int userAnswer = scanner.nextInt();
                if (question.validateAnswer(userAnswer)) {
                    score++;
                }
            }

            System.out.println("\nQuiz Over! Your score: " + score + "/" + questions.size());
        } catch (Exception e) {
            System.out.println("Error reading JSON file: " + e.getMessage());
        }
    }
}