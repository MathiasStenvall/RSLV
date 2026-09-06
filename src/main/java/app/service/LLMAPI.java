package app.service;


import app.DTO.GeminiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

public class LLMAPI {

    String apiKey = System.getenv("GEMINI_API_KEY");

    public void askLlm() throws IOException, InterruptedException {

        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException(
                    "GEMINI_API_KEY is not configured"
            );
        }

        ObjectMapper mapper = new ObjectMapper();

        String model = "gemini-3.5-flash-lite";

        String endpoint =
                "https://generativelanguage.googleapis.com/v1beta/models/"
                        + model
                        + ":generateContent";

        Map<String, Object> quiz = Map.of("contents", List.of(
                        Map.of("parts", List.of(
                                        Map.of("text", """
                                                Generate one multiple-choice question about a fitness topic.
                                                
                                                The question must have exactly four possible answers.
                                                
                                                Return only JSON with these properties:
                                                
                                                question
                                                answers
                                                correctAnswer
                                                
                                                correctAnswer must be the zero-based index of the correct answer.
                                                
                                                Do not include Markdown or explanations.""")
                                )
                        )
                ),
                "generationConfig", Map.of("responseMimeType", "application/json")
        );

        String jsonQuiz = mapper.writeValueAsString(quiz);

        HttpRequest requestQuiz = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .header("Content-Type", "application/json")
                .header("x-goog-api-key", apiKey)
                .POST(HttpRequest.BodyPublishers.ofString(jsonQuiz))
                .build();

        HttpResponse<String> responseQuiz;

        try (HttpClient clientQuiz = HttpClient.newHttpClient()) {

            responseQuiz = clientQuiz.send(
                    requestQuiz,
                    HttpResponse.BodyHandlers.ofString()
            );
        }

        GeminiResponse geminiQuiz = mapper.readValue(responseQuiz.body(), GeminiResponse.class);

        String quizJson = geminiQuiz.getCandidates().get(0).getContent().getParts().get(0).getText();

        GeminiResponse.Candidate.Content.Part.QuizQuestion quizQuestion =
                mapper.readValue(quizJson, GeminiResponse.Candidate.Content.Part.QuizQuestion.class);



        System.out.println(quizQuestion.getQuestion());
        for (String s: quizQuestion.getAnswers()){
            System.out.println("\t" + s);
        }
        System.out.println("Correct answer: " + (1+quizQuestion.getCorrectAnswer()));
    }
}