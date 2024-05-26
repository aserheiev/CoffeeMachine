package machine;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MachineFunctions {

    private final int[] contents;
    MachineStates currentState;
    String mainMenu = "Write action (buy, fill, take, remaining, exit):";
    String coffeeSelection = "What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:";

    public MachineFunctions() {
        currentState = MachineStates.MAIN_MENU;
        contents = new int[]{400, 540, 120, 550, 9};
    }


    public void PrintMenu() {
        switch (currentState) {
            case MAIN_MENU:
                System.out.println(mainMenu);
                break;
            case COFFEE_SELECTION:
                System.out.println(coffeeSelection);
                break;
            default:
                break;
        }
    }

    public void ParseCommand(String input) {
        switch (currentState) {
            case MAIN_MENU:
                switch (input) {
                    case "buy":
                        currentState = MachineStates.COFFEE_SELECTION;
                        break;
                    case "take":
                        Take();
                        break;
                    case "remaining":
                        PrintState(contents);
                        break;
                    case "fill":
                        Fill();
                        break;
                    case "exit":
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid input!");
                        break;
                }
                break;
            case COFFEE_SELECTION:
                switch (input) {
                    case "1", "2", "3":
                        Buy(Integer.parseInt(input) - 1);
                        currentState = MachineStates.MAIN_MENU;
                        break;
                    case "back":
                        currentState = MachineStates.MAIN_MENU;
                        break;
                    default:
                        System.out.println("Invalid input, try again!");
                        break;
                }
                break;
            default:
                break;
        }
    }

    private void PrintState(int[] contents) {
        System.out.println();
        System.out.println("The coffee machine has:");
        System.out.println(contents[0] + " ml of water");
        System.out.println(contents[1] + " ml of milk");
        System.out.println(contents[2] + " g of coffee beans");
        System.out.println(contents[4] + " disposable cups");
        System.out.println("$" + contents[3] + " of money");
        System.out.println();
    }

    private void Fill() {
        Scanner input = new Scanner(System.in);
        int forAnswer;
        System.out.println("Write how many ml of water you want to add:");
        forAnswer = input.nextInt();
        contents[0] += forAnswer;
        System.out.println("Write how many ml of milk you want to add:");
        forAnswer = input.nextInt();
        contents[1] += forAnswer;
        System.out.println("Write how many grams of coffee you want to add:");
        forAnswer = input.nextInt();
        contents[2] += forAnswer;
        System.out.println("Write how many disposable cups you want to add:");
        forAnswer = input.nextInt();
        contents[4] += forAnswer;
        System.out.println();
    }

    private void Take() {
        System.out.println("I gave you $" + contents[3]);
        contents[3] = 0;
        System.out.println();
    }

    private void Buy(int selection) {

        boolean success = true;
        // water, milk, coffee, money
        int[] espresso = {250, 0, 16, 4};
        int[] latte = {350, 75, 20, 7};
        int[] cappuccino = {200, 100, 12, 6};
        List<String> missingResouces = new ArrayList<String>();
        StringBuilder errorMsg = new StringBuilder("Sorry, not enough ");

        int[][] recipes = {espresso, latte, cappuccino};

        for (int i = 0; i < recipes[selection].length - 1; i++) {
            if (contents[i] < recipes[selection][i]) {
                success = false;

                switch (i) {
                    case 0:
                        missingResouces.add("water");
                        break;
                    case 1:
                        missingResouces.add("milk");
                        break;
                    case 2:
                        missingResouces.add("coffee");
                        break;
                }
            }
        }

        // check if there's enough cups
        if (contents[4] < 1) {
            success = false;
            missingResouces.add("cups");
        }

        // remove the ingredients if the recipe works
        if (success) {
            for (int i = 0; i < recipes[selection].length - 1; i++) {
                contents[i] -= recipes[selection][i];
            }

            contents[4]--;  // remove one cup
            contents[3] += recipes[selection][3];

            System.out.println("I have enough resources, making you a coffee!");
            System.out.println();
        } else {
            for (int i = 0; i < missingResouces.size(); i++) {
                if (i != missingResouces.size() - 1) {
                    errorMsg.append(missingResouces.get(i)).append(", ");
                } else {
                    errorMsg.append(missingResouces.get(i)).append("!");
                }
            }
            System.out.println(errorMsg);
            System.out.println();
        }
    }

}
