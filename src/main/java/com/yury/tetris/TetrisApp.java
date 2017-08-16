package com.yury.tetris;

import org.springframework.boot.SpringApplication;

public class TetrisApp {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        SpringApplication.run(Tetris.class, args);

        System.out.printf("Execution took %d ms", System.currentTimeMillis() - startTime);
    }
}
