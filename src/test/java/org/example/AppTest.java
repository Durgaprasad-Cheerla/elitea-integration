package org.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit test for simple App.
 */
public class AppTest
{
    @Test
    @DisplayName("Sample test")
    public void test1(){
        for (int i=0;i<5;i++){
            System.out.println("hello "+i);
        }
    }
}
