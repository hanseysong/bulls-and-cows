package bullscows;

import java.util.*;

public class Main {
    public static Scanner scanner = new Scanner(System.in);
    final static String sample = "0123456789abcdefghijklmnopqrstuvwxyz";
    public static String secretCode = "";
    public static String userGuess = "";
    public static int turns = 0;
    public static void main(String[] args) {

        System.out.println("Input the length of the secret code:");
        String input = scanner.nextLine();
        int userInput = 0;
        try {
            userInput = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.printf("Error: \"%s\" isn't a valid number.", input);
            System.exit(0);
        }

        System.out.println("Input the number of possible symbols in the code:");
        int numOfSymbols = scanner.nextInt();



        if (userInput >= 36) {
            System.out.printf("Error: can't generate a secret number with a length of %d because there aren't enough unique digits.", userInput);
        } else if (userInput <= 0) {
            System.out.println("Error: it's not possible");
        } else if (numOfSymbols <= 0) {

            System.exit(0);
        } else if (numOfSymbols > 36) {
            System.out.printf("Error: can't generate a secret number with %d symbols because there aren't enough unique digits.", numOfSymbols);
        }
        else {
            try {
                secretCode = randomGenerator(userInput, numOfSymbols);
            } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                System.out.printf("Error: it's not possible to generate a code with a length of %d with %d unique symbols.", userInput, numOfSymbols);
                System.exit(0);
            }

            System.out.print("The secret is prepared: ");
            for (int i = 0; i < userInput; i++) {
                System.out.print("*");
            }
            if (numOfSymbols <= 10) {
                System.out.print(" (0-9).");
            } else {
                System.out.printf(" (0-9, a-%c).\n", sample.charAt(numOfSymbols-1));
            }
            System.out.println("Okay, let's start the game!");
            menu();
        }


    }

    public static void menu() {
        turns += 1;
        System.out.println();
        System.out.println("Turn " + turns + ":");
        userGuess = scanner.next();
        guessBullCow();
    }

    public static String randomGenerator(int length, int numberOfSymbols) {
        List<Character> randomList = new ArrayList<>();
        for (int i = 0; i < numberOfSymbols; i++) {
            randomList.add(sample.toCharArray()[i]);
        }
        Collections.shuffle(randomList);
        while (randomList.get(0) == 0) {
            Collections.shuffle(randomList);
        }
        StringBuilder result = new StringBuilder();
        for (var ch : randomList.subList(0, length)) {
            result.append(ch);
        }
        return result.toString();
    }

    public static void guessBullCow() {
        char[] secretCodeArr = secretCode.toCharArray();
        char[] inputArr = userGuess.toCharArray();
        int bull = 0;
        int cow = 0;
        for (int i = 0; i < secretCodeArr.length; i++) {
            if (secretCodeArr[i] == inputArr[i]) {
                bull++;
            }
            for (char c : inputArr) {
                if (secretCodeArr[i] == c) {
                    cow++;
                }
            }
        }
        cow -= bull;
        if (bull == 0 && cow == 0) {
            System.out.println("Grade: None");
            menu();
        } else if (bull == secretCode.length()) {
            System.out.printf("Grade: %d bulls", bull);
            System.out.println("Congratulations! You guessed the secret code.");
        } else if (bull <= 1 && cow <= 1) {
            System.out.printf("Grade: %d bull and %d cow", bull, cow);
            menu();
        } else if (bull <= 1) {
            System.out.printf("Grade: %d bull and %d cows", bull, cow);
            menu();
        } else if (cow <= 1) {
            System.out.printf("Grade: %d bulls and %d cow", bull, cow);
            menu();
        } else {
            System.out.printf("Grade: %d bulls and %d cows", bull, cow);
            menu();
        }
    }
}
