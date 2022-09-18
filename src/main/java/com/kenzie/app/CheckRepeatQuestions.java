package com.kenzie.app;

import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;
import java.util.Random;

public class CheckRepeatQuestions {

    public static void main(String[] args) {
//        Check array of question numbers to ensure there are no repeated questions.
        int[] questionNumArray = new int[10];
        int i =0;
        while (i < questionNumArray.length) {
            Random random = new Random();
            int randomNumber = random.nextInt(11);
            for (int j = 0; j < questionNumArray.length; j++) {
                if (!(ArrayUtils.contains(questionNumArray, randomNumber))) {
                    questionNumArray[j] = randomNumber;
                }
            }
            i++;
        }
        System.out.println(Arrays.toString(questionNumArray));
    }
}