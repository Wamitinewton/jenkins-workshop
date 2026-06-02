package com.javaguy.springdemo.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorServiceTest {

    private CalculatorService calculatorService;

    @BeforeEach
    void setUp() {
        calculatorService = new CalculatorService();
    }

    @Test
    void add_returnsSum() {
        assertEquals(15.0, calculatorService.add(10, 5));
    }

    @Test
    void subtract_returnsDifference() {
        assertEquals(5.0, calculatorService.subtract(10, 5));
    }

    @Test
    void multiply_returnsProduct() {
        assertEquals(50.0, calculatorService.multiply(10, 5));
    }

    @Test
    void divide_returnsQuotient() {
        assertEquals(2.0, calculatorService.divide(10, 5));
    }

    @Test
    void divide_byZero_throwsArithmeticException() {
        assertThrows(ArithmeticException.class, () -> calculatorService.divide(10, 0));
    }

    @Test
    void add_negativeNumbers_returnsCorrectSum() {
        assertEquals(-5.0, calculatorService.add(-10, 5));
    }

    @Test
    void multiply_byZero_returnsZero() {
        assertEquals(0.0, calculatorService.multiply(10, 0));
    }
}
