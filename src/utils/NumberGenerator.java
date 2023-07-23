package utils;

import java.util.Random;

public class NumberGenerator {
    private static final Random random = new Random();

    public static int[] generateNumbers(int num, int sum) {
        int[] numbers = new int[num];
        int total = 0;

        for (int i = 0; i < num; i++) {
            numbers[i] = random.nextInt(sum) + 1; // generate random numbers
            total += numbers[i];
        }

        for (int i = 0; i < num; i++) {
            // normalize the numbers to make their sum equal to n
            numbers[i] = Math.round((float)numbers[i] * sum / total);
        }

        // Due to rounding errors, the sum might not be exactly n, adjust the last number to fix this
        int adjust = sum - sum(numbers);
        numbers[num-1] += adjust;

        return numbers;
    }

    public static int sum(int[] numbers) {
        int total = 0;
        for (int number : numbers) {
            total += number;
        }
        return total;
    }
}

