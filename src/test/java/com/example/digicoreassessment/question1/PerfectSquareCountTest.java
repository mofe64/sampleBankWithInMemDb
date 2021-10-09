package com.example.digicoreassessment.question1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PerfectSquareCountTest {
    @Test
    void calc(){
       int result = PerfectSquareCount.calc(1,1);
       assertEquals(1, result);
       result = PerfectSquareCount.calc(3,3);
       assertEquals(13, result);
       result = PerfectSquareCount.calc(4,5);
       assertEquals(40, result);
       result = PerfectSquareCount.calc(5,7);
       assertEquals(85,result);
    }


}