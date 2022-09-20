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
        // declare variables

        //value to store score
        int score = 0;

        //API
        final String TEST_URL = "https://jservice.kenzie.academy/api/clues";
        final String TEST_URL2 = "https://jservice.kenzie.academy/api/random-games/6";
        final String TEST_FAIL_URL = "https://jservice.kenzie.academy/api/randommm-games/6";
        String httpResponseStr;

//         Use an HTTP GET request to pull questions from the API
        httpResponseStr = CustomHttpClient.sendGET(TEST_URL);

//          Create an array of random question numbers to select
        int[] questionNumArray = new int[10];
        Random random = new Random();

//         Repeat the Q/A cycle until the total number of questions is reached
        int questionCounter = 0;
        while (questionCounter < questionNumArray.length) {

//         Ensure there are no repeats in array
//         Lines 52 - 67 from -  https://stackoverflow.com/questions/64340800/create-an-array-with-random-integers-but-with-no-duplicates

            for (int i = 0; i < questionNumArray.length; i++) {
                boolean exist = true;//we create a boolean is random number exist and start with true for while loop
                while (exist) {
                    exist = false;//we change it because until we didn't see the same value on array, we accept as non-exist.
                    int x = random.nextInt(99);

                    for (int k = 0; k < i; k++) {//check every number until "i" is hit.
                        if (x == questionNumArray[k]) {//if exist we said same value exist
                            exist = true;
                            break;
                        }
                    }
                    if (!exist) {//if same value not exist we save it in our array
                        questionNumArray[i] = x;
                    }
                }

                try {

                    // 1. instantiate ObjectMapper
                    ObjectMapper objectMapper = new ObjectMapper();
                    // 2. Declare DTO obj
                    QuestionsDTO questionObj;
                    // 3. read data - readValue()
                    questionObj = objectMapper.readValue(httpResponseStr, QuestionsDTO.class);

//         Present a single question to the user. For each question, also display the category.
                    String category = questionObj.getClues().get(questionNumArray[i]).getCategory().getTitle();
                    String question = questionObj.getClues().get(questionNumArray[i]).getQuestion();

                    System.out.println("Category: " + category + " \n" + "Question: " + question);

//         Allow the user to respond to the question
                    System.out.println("ANSWER: ");
                    Scanner scanner = new Scanner(System.in);

//         Store user answer and correct answer
                    String userAnswer = scanner.nextLine();
                    String correctAnswer = questionObj.getClues().get(questionNumArray[i]).getAnswer();


//         Determine if the user's answer was correct
//          - A correct answer awards one (1) point and continues the game
//          - An incorrect answer does not update the score. The game continues.

//         Display a message indicating whether the answer was correct or incorrect.
//         - If the answer is incorrect, display the correct answer.
//         Keep track of and display a user's score

                    //NO ANSWER
                    if (userAnswer.equals("")) {
                        System.out.println("No answer provided. " + "\n" +
                                "The correct answer is: " + correctAnswer + "\n" + "Score: " + score);
                        // CORRECT ANSWER
                    } else if (CheckGivenAnswer(correctAnswer, userAnswer)==true) {
                        score++;
                        System.out.println("You are correct!" + "\n" + "Score: " + score);
                        // WRONG ANSWER
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
        }
        System.out.println("Total Score: " + score);
    }
}
