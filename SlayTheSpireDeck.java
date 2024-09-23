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
     * Main method to read card data from a file and generate a report.
     */
    public static void main(String[] args) {
        // Prompt user for the card file name
        System.out.print("Enter the name of the card file: ");
        Scanner input = new Scanner(System.in);
        String fileName = input.nextLine();

        int totalCost = 0; // Total energy cost of all cards
        int[] costHistogram = new int[7]; // Histogram for costs from 0 to 6
        List<String> invalidCards = new ArrayList<>(); // List of invalid card entries
        int cardCount = 0; // Count of valid cards read

        String deckId = generateDeckId(); // Generate a unique deck ID

        // Try to read the file containing card data
        try {
            File file = new File(fileName);
            Scanner fileScanner = new Scanner(file);

            // Read each line in the file
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                cardCount++; // Increment total card count

                // Split line into card name and cost
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String cardName = parts[0]; // Get card name
                    String costStr = parts[1]; // Get cost as string

                    // Validate the card name and cost
                    if (isValidCard(cardName, costStr)) {
                        int cost = Integer.parseInt(costStr); // Convert cost to integer
                        costHistogram[cost]++; // Update histogram for the cost
                        totalCost += cost; // Add to total cost
                    } else {
                        invalidCards.add(line); // Add invalid card to the list
                    }
                } else {
                    invalidCards.add(line); // Add invalid entry to the list
                }
            }
            fileScanner.close(); // Close the file scanner

        } catch (FileNotFoundException e) {
            // Handle case where the file is not found
            System.out.println("Error reading file: " + fileName);
            return; // Exit the program
        }

        // Check for void report conditions
        if (invalidCards.size() > 10 || cardCount > 1000) {
            System.out.println("Deck ID: " + deckId + "(VOID)");
            System.out.println("VOID"); // Print VOID if conditions are met
            return; // Exit the program
        }

        // Print the report with deck information
        printReport(deckId, totalCost, costHistogram, invalidCards);
    }

    /**
     * Generates a random 9-digit ID for the deck.
     */
    public static String generateDeckId() {
        Random rand = new Random(); // Create random number generator
        int id = rand.nextInt(1000000000); // Generate a random number
        return String.format("%09d", id); // Format as 9-digit string
    }

    /**
     * Checks if the card name and cost are valid.
     *
     * @param cardName The name of the card.
     * @param costStr The cost of the card as a string.
     * @return true if valid, false otherwise.
     */
    public static boolean isValidCard(String cardName, String costStr) {
        // Check if card name is empty
        if (cardName.length() == 0) {
            return false; // Invalid if empty
        }

        try {
            int cost = Integer.parseInt(costStr); // Try to convert cost to integer
            return cost >= 0 && cost <= 6; // Return true if cost is within valid range
        } catch (NumberFormatException e) {
            return false; // Return false if cost is not a number
        }
    }

    /**
     * Prints the report with deck information and invalid cards.
     *
     * @param deckId The unique ID of the deck.
     * @param totalCost The total cost of the cards in the deck.
     * @param costHistogram The histogram of card costs.
     * @param invalidCards The list of invalid card entries.
     */
    public static void printReport(String deckId, int totalCost, int[] costHistogram, List<String> invalidCards) {
        System.out.println("Deck ID: " + deckId); // Print deck ID
        System.out.println("Total Cost: " + totalCost); // Print total cost
        System.out.println("Histogram of Costs:"); // Print header for histogram

        // Print histogram of costs
        for (int i = 0; i < costHistogram.length; i++) {
            System.out.print(i + ": "); // Print cost value
            for (int j = 0; j < costHistogram[i]; j++) {
                System.out.print("*"); // Print asterisk for each occurrence
            }
            System.out.println(); // Move to next line
        }

        // Print invalid cards if any
        if (!invalidCards.isEmpty()) {
            System.out.println("Invalid Cards:");
            for (String invalidCard : invalidCards) {
                System.out.println(invalidCard); // Print each invalid card
            }
        }
    }
}





