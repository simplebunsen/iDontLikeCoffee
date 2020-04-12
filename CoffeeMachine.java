package machine;

import java.util.Scanner;

enum State{
    MAIN_MENU, CHOOSING_BEVERAGE, FILL_WATER, FILL_MILK, FILL_BEANS, FILL_CUPS;
}


public class CoffeeMachine {

    private int supplyWater = 400;
    private int supplyMilk = 540;
    private int supplyBeans = 120;
    private int supplyCups = 9;
    private int balance = 550;

    private State currentState = State.MAIN_MENU;

    public static void main(String[] args) {
        boolean flag = true;
        CoffeeMachine coffeeMachine = new CoffeeMachine();
        Scanner scanner = new Scanner(System.in);

        coffeeMachine.printMainMenu();

        while(flag){
            flag = coffeeMachine.processInput(scanner.nextLine());
        }
    }

    public boolean processInput(String input) {
        boolean flag = true;
        //System.out.println("processing input");

        switch (currentState) {
            case MAIN_MENU:
                //System.out.println("calling prcessmainmenuchoice");
                flag = processMainMenuChoice(input);
                break;
            case CHOOSING_BEVERAGE:
                processBeverageChoice(input);
                break;
            case FILL_WATER:
                fillWater(input);
                break;
            case FILL_MILK:
                fillMilk(input);
                break;
            case FILL_BEANS:
                fillBeans(input);
                break;
            case FILL_CUPS:
                fillCups(input);
                break;
            default:
                System.out.println("unsupported State, going back to main menu");
                currentState = State.MAIN_MENU;
                break;
        }
        return flag;
    }

    private void processBeverageChoice(String input) {
        if (!hasResourcesForType(supplyWater, supplyMilk, supplyBeans, supplyCups, input)) {
            System.out.println("Sorry, not enough water!");
            //TODO: This is hardcoded cause I'm too stubborn to deal with every possible outcome
            System.out.println();
            printMainMenu();
            currentState = State.MAIN_MENU;
            return;
        }

        System.out.println("I have enough resources, making you a coffee!");

        switch (input) {
            case "1":
                supplyWater -= 250;
                supplyBeans -= 16;
                supplyCups--;
                balance += 4;
                break;
            case "2":
                supplyWater -= 350;
                supplyMilk -= 75;
                supplyBeans -= 20;
                supplyCups--;
                balance += 7;
                break;
            case "3":
                supplyWater -= 200;
                supplyMilk -= 100;
                supplyBeans -= 12;
                supplyCups--;
                balance += 6;
                break;
            case "back":

                break;
        }

        System.out.println();
        printMainMenu();
        currentState = State.MAIN_MENU;
    }

    public void printMainMenu(){
        System.out.println("Write action (buy, fill, take, remaining, exit):");
    }

    public boolean processMainMenuChoice(String input) {
        switch (input) {
            case "buy":
                System.out.println();
                System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino:");
                currentState = State.CHOOSING_BEVERAGE;
                break;
            case "fill":
                System.out.println();
                System.out.println("Write how many ml of water do you want to add: ");
                currentState = State.FILL_WATER;
                break;
            case "take":
                System.out.println();
                System.out.println("I gave you $" + balance);
                balance = 0;

                System.out.println();
                printMainMenu();
                break;
            case "remaining":
                printInventory(supplyWater, supplyMilk, supplyBeans, supplyCups, balance);
                break;
            case "exit":
                return false;
        }
        return true;
    }

    private void fillWater(String input) {
        supplyWater += Integer.parseInt(input);
        System.out.println("Write how many ml of milk do you want to add:");
        currentState = State.FILL_MILK;
    }

    private void fillMilk(String input) {
        supplyMilk += Integer.parseInt(input);
        System.out.println("Write how many grams of coffee beans do you want to add:");
        currentState = State.FILL_BEANS;
    }

    private void fillBeans(String input) {
        supplyBeans += Integer.parseInt(input);
        System.out.println("Write how many disposable cups of coffee do you want to add:");
        currentState = State.FILL_CUPS;
    }

    private void fillCups(String input) {
        supplyCups += Integer.parseInt(input);

        System.out.println();
        printMainMenu();
        currentState = State.MAIN_MENU;
    }

    private void printInventory(int supplyWater, int supplyMilk, int supplyBeans, int supplyCups, int balance) {
        System.out.println();
        System.out.println("The coffee machine has:\n" +
                supplyWater + " of water\n" +
                supplyMilk +" of milk\n" +
                supplyBeans + " of coffee beans\n" +
                supplyCups + " of disposable cups\n" +
                "$" + balance + " of money");

        System.out.println();
        printMainMenu();
    }

    private static boolean hasResourcesForType(int supplyWater, int supplyMilk, int supplyBeans, int cups, String type) {
        int waterx = 1;
        int milkx = 1;
        int beansx = 1;

        switch(type){
            case "1":
                waterx = 250;
                milkx = -1;
                beansx = 16;
                break;
            case "2":
                waterx = 350;
                milkx = 75;
                beansx = 20;
                break;
            case "3":
                waterx = 200;
                milkx = 100;
                beansx = 12;
                break;
            default:
                return false;
        }
        int waterCups = supplyWater / waterx;
        int milkCups;
        if(milkx == -1){
            milkCups = Integer.MAX_VALUE;
        }else {
            milkCups = supplyMilk / milkx;
        }
        int beansCups = supplyBeans / beansx;

        if(waterCups >= 1 && beansCups >=1 && milkCups >= 1 && cups >= 1){
            //System.out.println("has enough for " + type);
            return true;
        }else {
            //System.out.println("has NOT enough for " + type);
            return false;
        }
    }
}

