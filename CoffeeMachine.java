package machine;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CoffeeMachine {
    // water, milk, coffee, money, cups

    public static void main(String[] args) {
        MachineFunctions machineFunctions = new MachineFunctions();
        Scanner input = new Scanner(System.in);
        String command;

        while (true) {
            machineFunctions.PrintMenu();
            command = input.nextLine();
            machineFunctions.ParseCommand(command);
        }
    }

}