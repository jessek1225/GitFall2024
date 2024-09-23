import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Random;

/**
 * Class to calculate total energy cost of a Slay the Spire deck.
 */
public class SlayTheSpireDeck {

    /**
     * Main method to read card data and generate a report.
     */
    public static void main(String[] args) {
        System.out.print("Enter the name of the card file: ");
        Scanner input = new Scanner(System.in);
        String fileName = input.nextLine();

        int totalCost = 0;
        int[] costHistogram = new int[7];
        List<String> invalidCards = new ArrayList<>();
        int cardCount = 0;

        String deckId = generateDeckId();

        try {
            File file = new File(fileName);
            Scanner fileScanner = new Scanner(file);

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                cardCount++;

                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String cardName = parts[0];
                    String costStr = parts[1];

                    if (isValidCard(cardName, costStr)) {
                        int cost = Integer.parseInt(costStr);
                        costHistogram[cost]++;
                        totalCost += cost;
                    } else {
                        invalidCards.add(line);
                    }
                } else {
                    invalidCards.add(line);
                }
            }
            fileScanner.close();

        } catch (FileNotFoundException e) {
            System.out.println("Error reading file: " + fileName);
            return;
        }

        if (invalidCards.size() > 10 || cardCount > 1000) {
            System.out.println("Deck ID: " + deckId + "(VOID)");
            System.out.println("VOID");
            return;
        }

        printReport(deckId, totalCost, costHistogram, invalidCards);
    }

    /**
     * Generates a random 9-digit ID.
     */
    public static String generateDeckId() {
        Random rand = new Random();
        int id = rand.nextInt(1000000000);
        return String.format("%09d", id);
    }

    /**
     * Checks if the card name and cost are valid.
     */
    public static boolean isValidCard(String cardName, String costStr) {
        if (cardName.length() == 0) {
            return false;
        }

        try {
            int cost = Integer.parseInt(costStr);
            return cost >= 0 && cost <= 6;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Prints the report with deck info and invalid cards.
     */
    public static void printReport(String deckId, int totalCost, int[] costHistogram, List<String> invalidCards) {
        System.out.println("Deck ID: " + deckId);
        System.out.println("Total Cost: " + totalCost);
        System.out.println("Histogram of Costs:");

        for (int i = 0; i < costHistogram.length; i++) {
            System.out.print(i + ": ");
            for (int j = 0; j < costHistogram[i]; j++) {
                System.out.print("*");
            }
            System.out.println();
        }

        if (!invalidCards.isEmpty()) {
            System.out.println("Invalid Cards:");
            for (String invalidCard : invalidCards) {
                System.out.println(invalidCard);
            }
        }
    }
}




