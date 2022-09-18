package com.kenzie.app;

// import necessary libraries


import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenzie.app.DTO.QuestionsDTO;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

import static com.kenzie.app.CheckAnswer.CheckGivenAnswer;

public class Main {
    /* Java Fundamentals Capstone project:
       - Define as many variables, properties, and methods as you decide are necessary to
       solve the program requirements.
       - You are not limited to only the class files included here
       - You must write the HTTP GET call inside the CustomHttpClient.sendGET(String URL) method
         definition provided
       - Your program execution must run from the main() method in Main.java
       - The rest is up to you. Good luck and happy coding!

     */


    public static void main(String[] args) {
        //value to store score
        int score = 0;

        //Repeat the Q/A cycle until the total number of questions is reached
        int questionCounter = 0;
        while (questionCounter < 10) {
            try {
                //Write main execution code here
                // API - https://jservice.kenzie.academy/api/clues

                // declare variables
                final String TEST_URL = "https://jservice.kenzie.academy/api/clues";
//              final String TEST_URL = "https://jservice.kenzie.academy/api/random-games/6";
                final String TEST_FAIL_URL = "https://jservice.kenzie.academy/api/randommm-games/6";
                String httpResponseStr;
                String httpResponseStr2;

//         Use an HTTP GET request to pull questions from the API
                httpResponseStr = CustomHttpClient.sendGET(TEST_URL);
                httpResponseStr2 = CustomHttpClient.sendGET(TEST_FAIL_URL);
                //System.out.println(httpResponseStr2);

//         Present a single question to the user. For each question, also display the category.
                // 1. instantiate ObjectMapper
                ObjectMapper objectMapper = new ObjectMapper();
                // 2. Declare DTO obj
                QuestionsDTO questionObj;
                // 3. read data - readValue()
                questionObj = objectMapper.readValue(httpResponseStr, QuestionsDTO.class);

                //Generate random question number
                Random random = new Random();
                int randomNumber = random.nextInt(99);

                String category = questionObj.getClues().get(randomNumber).getCategory().getTitle();
                String question = questionObj.getClues().get(randomNumber).getQuestion();

                System.out.println("Category: " + category + " \n" + "Question: " + question);

//         Allow the user to respond to the question
                System.out.println("ANSWER: ");
                Scanner scanner = new Scanner(System.in);
                String userAnswer = scanner.nextLine();

//         Determine if the user's answer was correct
//          - A correct answer awards one (1) point and continues the game
//          - An incorrect answer does not update the score. The game continues.

                String correctAnswer = questionObj.getClues().get(randomNumber).getAnswer();
                CheckGivenAnswer(correctAnswer, userAnswer);

//         Display a message indicating whether the answer was correct or incorrect.
//         - If the answer is incorrect, display the correct answer.
//         Keep track of and display a user's score

                if (userAnswer.equals("")) {
                    System.out.println("No answer provided. " + "\n" +
                            "The correct answer is: " + correctAnswer + "\n" + "Score: " + score);
                } else if ((CheckGivenAnswer(correctAnswer, userAnswer) == true)) {
                    score++;
                    System.out.println("You are correct!" + "\n" + "Score: " + score);
                } else {
                    System.out.println(userAnswer + " is incorrect. " + "\n" +
                            "The correct answer is: " + correctAnswer + "\n" + "Score: " + score);
                }
                System.out.println();

            }
//            Use appropriate exception handling so the program executes and exits gracefully
            catch (Exception e) {
                System.out.println(e);
            }
            questionCounter++;
        }
        System.out.println("Total Score: " + score);
    }
}

