package com.kenzie.app;
import com.kenzie.app.DTO.QuestionsDTO;
import com.kenzie.app.DTO.Clues;
import com.kenzie.app.DTO.Category;
import java.util.Arrays;

public class CheckAnswer {

//    Determine if the user's answer was correct
    public static boolean CheckGivenAnswer(String correctAnswer, String userAnswer) {

        if (userAnswer.replaceAll("\\s+", " ").trim().equalsIgnoreCase(correctAnswer)) {
            return true;

        } else {
            return false;
        }

    }
}
