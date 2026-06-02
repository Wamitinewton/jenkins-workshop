package com.javaguy.springdemo.controller;

import com.javaguy.springdemo.model.CalculatorResponse;
import com.javaguy.springdemo.service.CalculatorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/calculator")
public class CalculatorController {

    private final CalculatorService calculatorService;

    public CalculatorController(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    @GetMapping("/add")
    public CalculatorResponse add(@RequestParam double a, @RequestParam double b) {
        return new CalculatorResponse(calculatorService.add(a, b));
    }

    @GetMapping("/subtract")
    public CalculatorResponse subtract(@RequestParam double a, @RequestParam double b) {
        return new CalculatorResponse(calculatorService.subtract(a, b));
    }

    @GetMapping("/multiply")
    public CalculatorResponse multiply(@RequestParam double a, @RequestParam double b) {
        return new CalculatorResponse(calculatorService.multiply(a, b));
    }

    @GetMapping("/divide")
    public CalculatorResponse divide(@RequestParam double a, @RequestParam double b) {
        return new CalculatorResponse(calculatorService.divide(a, b));
    }
}