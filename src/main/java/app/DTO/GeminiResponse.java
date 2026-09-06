package app.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeminiResponse {

    @JsonProperty("candidates")
    List<Candidate> candidates;

    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Candidate {

        @JsonProperty("content")
        Content content;

        @Getter
        @Setter
        @NoArgsConstructor
        @ToString
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Content {
            @JsonProperty("parts")
            List<Part> parts;

            @Getter
            @Setter
            @NoArgsConstructor
            @ToString
            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class Part {
                @JsonProperty("text")
                String text;

                @Getter
                @Setter
                @NoArgsConstructor
                @ToString
                @JsonIgnoreProperties(ignoreUnknown = true)
                public static class QuizQuestion {

                    String question;
                    List<String> answers;
                    int correctAnswer;

                }
            }

        }

    }

}
