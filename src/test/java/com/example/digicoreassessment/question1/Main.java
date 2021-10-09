package com.example.digicoreassessment.question1;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter number of test cases: ");
        int testCases = scanner.nextInt();
        int currentTestCase = 1;
        while (testCases > 0) {
            System.out.println("For test case " + currentTestCase);
            System.out.println("Enter the length: ");
            int length = scanner.nextInt();
            System.out.println("Enter the width: ");
            int width = scanner.nextInt();
            if(length <=0){
                System.out.println("Invalid length value ");
                continue;
            }
            if(width <=0) {
                System.out.println("Invalid width value");
                continue;
            }
            int answer = PerfectSquareCount.calc(length, width);
            String resultString = "For a grid of length " + length + " and width " + width + " we have " + answer + " number of perfect squares";
            System.out.println(resultString);
            currentTestCase++;
            testCases--;
        }
    }
}
