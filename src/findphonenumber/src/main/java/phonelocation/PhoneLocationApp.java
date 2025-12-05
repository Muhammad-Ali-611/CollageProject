package phonelocation;

import java.util.Scanner;

public class PhoneLocationApp {
    private static final PhoneLocationService locationService = new PhoneLocationService();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        printWelcomeBanner();

        boolean running = true;
        while (running) {
            printMenu();
            int choice = getUserChoice();

            switch (choice) {
                case 1:
                    lookupPhoneNumber();
                    break;
                case 2:
                    validatePhoneNumber();
                    break;
                case 3:
                    showExampleNumbers();
                    break;
                case 4:
                    showAbout();
                    break;
                case 5:
                    running = false;
                    System.out.println("\n👋 Thank you for using Phone Location Tracker!");
                    break;
                default:
                    System.out.println("\n❌ Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }

    private static void printWelcomeBanner() {
        System.out.println("\n╔══════════════════════════════════════════════════════════╗");
        System.out.println("║                                                          ║");
        System.out.println("║        📱 PHONE NUMBER LOCATION TRACKER 📱              ║");
        System.out.println("║                                                          ║");
        System.out.println("║     Get Information about Phone Numbers Legally         ║");
        System.out.println("║                                                          ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝\n");
    }

    private static void printMenu() {
        System.out.println("\n═══════════════ MAIN MENU ═══════════════");
        System.out.println("1. 🔍 Lookup Phone Number");
        System.out.println("2. ✅ Validate Phone Number");
        System.out.println("3. 📋 Show Example Numbers");
        System.out.println("4. ℹ️  About");
        System.out.println("5. 🚪 Exit");
        System.out.println("═══════════════════════════════════════");
        System.out.print("Enter your choice: ");
    }

    private static int getUserChoice() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static void lookupPhoneNumber() {
        System.out.println("\n─────────── PHONE NUMBER LOOKUP ───────────");
        System.out.println("Enter phone number with country code (e.g., +1234567890)");
        System.out.println("Or enter without code and specify country");
        System.out.print("\nPhone Number: ");
        String phoneNumber = scanner.nextLine().trim();

        String regionCode = "US"; // Default
        if (!phoneNumber.startsWith("+")) {
            System.out.print("Country Code (e.g., US, IN, GB, DE): ");
            String inputRegion = scanner.nextLine().trim().toUpperCase();
            if (!inputRegion.isEmpty()) {
                regionCode = inputRegion;
            }
        }

        System.out.println("\n🔄 Processing...\n");
        PhoneNumberInfo info = locationService.getPhoneNumberInfo(phoneNumber, regionCode);
        System.out.println(info.toFormattedString());

        if (!info.isValid()) {
            System.out.println("💡 Tip: Make sure to include the country code (e.g., +1 for US, +44 for UK)");
        }
    }

    private static void validatePhoneNumber() {
        System.out.println("\n─────────── VALIDATE PHONE NUMBER ───────────");
        System.out.print("Phone Number: ");
        String phoneNumber = scanner.nextLine().trim();

        String regionCode = "US";
        if (!phoneNumber.startsWith("+")) {
            System.out.print("Country Code (e.g., US, IN, GB): ");
            String inputRegion = scanner.nextLine().trim().toUpperCase();
            if (!inputRegion.isEmpty()) {
                regionCode = inputRegion;
            }
        }

        boolean isValid = locationService.isValidPhoneNumber(phoneNumber, regionCode);

        if (isValid) {
            System.out.println("\n✅ The phone number is VALID!");
        } else {
            System.out.println("\n❌ The phone number is INVALID!");
        }
    }

    private static void showExampleNumbers() {
        System.out.println("\n─────────── EXAMPLE PHONE NUMBERS ───────────");
        String[] countries = {"US", "GB", "IN", "DE", "FR", "CA", "AU", "JP", "BR", "MX"};
        String[] countryNames = {"United States", "United Kingdom", "India", "Germany",
                "France", "Canada", "Australia", "Japan", "Brazil", "Mexico"};

        for (int i = 0; i < countries.length; i++) {
            String example = locationService.getExampleNumber(countries[i]);
            System.out.printf("%-20s (%s): %s\n", countryNames[i], countries[i], example);
        }
    }

    private static void showAbout() {
        System.out.println("\n╔══════════════════════════════════════════════════════════╗");
        System.out.println("║                    ABOUT THIS APP                        ║");
        System.out.println("╠══════════════════════════════════════════════════════════╣");
        System.out.println("║                                                          ║");
        System.out.println("║  Phone Number Location Tracker v1.0                      ║");
        System.out.println("║                                                          ║");
        System.out.println("║  This application provides legal and ethical phone      ║");
        System.out.println("║  number information lookup including:                    ║");
        System.out.println("║                                                          ║");
        System.out.println("║  • Country and region identification                     ║");
        System.out.println("║  • Phone number validation                               ║");
        System.out.println("║  • Carrier/operator information                          ║");
        System.out.println("║  • Number type (mobile/landline)                         ║");
        System.out.println("║  • Time zone information                                 ║");
        System.out.println("║  • Proper number formatting                              ║");
        System.out.println("║                                                          ║");
        System.out.println("║  ⚠️  PRIVACY NOTICE:                                     ║");
        System.out.println("║  This app only provides publicly available information   ║");
        System.out.println("║  and does NOT track real-time location.                  ║");
        System.out.println("║                                                          ║");
        System.out.println("║  Powered by: Google libphonenumber library               ║");
        System.out.println("║                                                          ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");
    }
}
