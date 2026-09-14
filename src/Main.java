// Main.java
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PaymentGateway gateway = new PaymentGateway();
        int nextId = 1001;

        // Seed with three sample payments so the menu has data right away.
        gateway.add(new GCashPayment(nextId++, "Ana", 1500.00, "0917-555-0134"));
        gateway.add(new MayaPayment(nextId++, "Jerome", 899.50, "jerome@liceo.edu.ph"));
        gateway.add(new CashPayment(nextId++, "Liza", 250.00));

        boolean running = true;
        while (running) {
            System.out.println();
            System.out.println("=========================================");
            System.out.println("            L I C E O   P A Y           ");
            System.out.println("=========================================");
            System.out.println("1. Make a Payment");
            System.out.println("2. Show All Receipts");
            System.out.println("3. Find Payment by ID");
            System.out.println("4. Show Total Collected");
            System.out.println("5. Refund All Eligible Payments");
            System.out.println("6. Show Service Fees");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");

            String input = scanner.nextLine().trim();
            int choice;
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number from the menu.");
                continue;
            }

            switch (choice) {
                case 1:
                    makePayment(scanner, gateway, nextId);
                    nextId++;
                    break;
                case 2:
                    System.out.println();
                    gateway.processAll();
                    break;
                case 3:
                    findPayment(scanner, gateway);
                    break;
                case 4:
                    System.out.printf("Total collected: PHP %.2f%n", gateway.totalCollected());
                    break;
                case 5:
                    System.out.println();
                    System.out.println("Refunding every payment that can be refunded:");
                    gateway.refundAll();
                    break;
                case 6:
                    System.out.println();
                    System.out.println("Service fees (the two serviceFee methods):");
                    gateway.showServiceFees();
                    break;
                case 0:
                    running = false;
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("That option does not exist. Try again.");
            }
        }

        scanner.close();
    }

    private static void makePayment(Scanner scanner, PaymentGateway gateway, int id) {
        System.out.println();
        System.out.println("Payment type: 1) GCash  2) Maya  3) Cash");
        System.out.print("Choose type: ");
        String typeInput = scanner.nextLine().trim();

        System.out.print("Payer name: ");
        String name = scanner.nextLine().trim();

        double amount;
        while (true) {
            System.out.print("Amount: ");
            try {
                amount = Double.parseDouble(scanner.nextLine().trim());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number for the amount.");
            }
        }

        switch (typeInput) {
            case "1":
                System.out.print("Mobile number: ");
                String mobile = scanner.nextLine().trim();
                gateway.add(new GCashPayment(id, name, amount, mobile));
                System.out.println();
                gateway.findById(id).printReceipt();
                gateway.findById(id).printThankYou();
                break;
            case "2":
                System.out.print("Email: ");
                String email = scanner.nextLine().trim();
                gateway.add(new MayaPayment(id, name, amount, email));
                System.out.println();
                gateway.findById(id).printReceipt();
                gateway.findById(id).printThankYou();
                break;
            case "3":
                gateway.add(new CashPayment(id, name, amount));
                System.out.println();
                gateway.findById(id).printReceipt();
                gateway.findById(id).printThankYou();
                break;
            default:
                System.out.println("That is not a valid payment type. Nothing was added.");
        }
    }

    private static void findPayment(Scanner scanner, PaymentGateway gateway) {
        System.out.print("Enter the ID to find: ");
        String idInput = scanner.nextLine().trim();
        try {
            int id = Integer.parseInt(idInput);
            Payment found = gateway.findById(id);
            if (found == null) {
                System.out.println("No payment found with ID " + id + ".");
            } else {
                System.out.println("Payment found:");
                found.printReceipt();
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid numeric ID.");
        }
    }
}