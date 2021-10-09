package com.example.digicoreassessment.question1;

public class PerfectSquareCount {
    public static int calc(int len, int width) {
        if (len == 1 && width == 1) {
            return 1;
        }
        int numberOfPerfectSquares = 0;
        for (int i = len, j = width; i > 1 || j > 1; i--, j--) {
            numberOfPerfectSquares += (i * j);
        }
        return numberOfPerfectSquares;

    }
}
