package render;

import core.TurnManager;
import input.InputHandler;
import jdk.jfr.Event;
import model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static model.Type.*;
import static utils.Constants.*;
import static utils.TextUtils.center;
import static utils.TextUtils.clearTerminal;

public class GameRenderer {

    private final String[] reflections = {
            "Reflecting on the past year's challenges...",
            "Looking back at the hurdles overcome...",
            "Reviewing the trials and triumphs of the year...",
            "Taking stock of the city's ups and downs..."
    };

    private final String[][] tips = {
            {
                    "Citizens’ happiness strongly affects your popularity.",
                    "Keep your citizens happy to stay in power longer."
            },
            {
                    "Security level reduces the chance of negative random events.",
                    "Low security increases risks of crime and disasters."
            },
            {
                    "Environment quality has a moderate impact on city wellbeing,",
                    "but neglecting it can cause long-term problems."
            },
            {
                    "Business boosts your income each round.",
                    "Invest in business to grow your budget steadily."
            }
    };
    //int screenWidth = 168;

    public void renderMainScreen(City city, Mayor mayor, Deck deck, List<Card> cards, String[] info)
    {
        ///  1 Line
        System.out.printf("%-50s", "oldGameFiles.Mayor budget: " + mayor.getBudget() + " -> " + (mayor.getBudget()+((int)(city.get(BUSINESS)*MAYOR_BUDGET_MULTIPLIER))) + " (next turn)");
        System.out.printf("%-87s", center(info[0],60));
        System.out.print("Civilian [");
        System.out.printf("%-20s", "|".repeat(city.get(CIVILIAN) / 5));
        System.out.println("]");
        ///  2 Line
        System.out.printf("%-50s", String.format("oldGameFiles.Mayor Popularity: %.2f", mayor.getPopularity()));
        System.out.printf("%-84s", center(info[1],60));
        System.out.print("Environment [");
        System.out.printf("%-20s", "|".repeat(city.get(ENVIRONMENT) / 5));
        System.out.println("]");
        ///  3 Line
        System.out.printf("%-50s", "__________-\\_________");
        System.out.printf("%-87s", center(info[2],60));
        System.out.print("Business [");
        System.out.printf("%-20s", "|".repeat(city.get(BUSINESS) / 5));
        System.out.println("]");
        ///  4 Line
        System.out.printf("%-50s", "     _---   \\");
        System.out.printf("%-87s", center(info[3],60));
        System.out.print("Security [");
        System.out.printf("%-20s", "|".repeat(city.get(SECURITY) / 5));
        System.out.println("]");

        ////////////////////////////////////////////////////////////////  oldGameFiles.Deck printas
        System.out.println("__---        \\\\          O                        O                                              O                                  O ");
        System.out.printf("%-14s","oldGameFiles.Deck Size: " + deck.size()); // v
        System.out.println(" \\       /|\\       O                /|\\                     O                    /|\\              O                 /|\\                     ");
        //  System.out.println("Buy 5 cards    ///      / \\    /|\\               / \\           O          /|\\                   / \\             /|\\       O        / \\                    ");
        System.out.println("Refill deck    ///      / \\    /|\\               / \\           O          /|\\                   / \\             /|\\       O        / \\                    ");
        System.out.println("by 5 ("+REFILL_COST+"$) ///      O          / \\     O                    /|\\         / \\         O                   O     / \\      /|\\               O            ");
        System.out.println("       ////          /|\\                /|\\                   / \\                  /|\\                 /|\\               / \\              /|\\                ");
        System.out.println("   ////             / \\                / \\                                          / \\                 / \\                              / \\      ");

        ////////////////////////////////////////////////////////////////// Kortu printas
        // kortu dydis, 16 blokeliu kortu laukas
        int width = 21;
        int height = 4;

        if (cards.size() > 0) printCards(cards, width, height, mayor.getBackpack());
            else if (mayor.getBackpack() != null) printBackpack(mayor.getBackpack(),width, height);
            else System.out.print("\n".repeat(16));
        ///////////////////////////////////////////////////////////////////

        System.out.println("Enter card number (to buy), number -b (add to backpack), -b (remove from backpack), n/next (next turn), r/refill (refill deck by 5 cards -"+REFILL_COST+"$), exit (quit):");
        System.out.print("> ");
        ///////////////////////////////////////////////////////////////////   INPUT logika
        //System.out.println(deck.getCards().getLast().getName());












    }

    private void printBackpack(Card backpack, int width, int height) {
        String[] backpackLines = new String[height];
        String[] parts = backpack.getName().split(" ");
        String[] fixed = new String[height];
        List<Map.Entry<Type, Integer>> cardEffects = backpack.getEffects();

        for (int i = 0; i < height; i++) {
            fixed[i] = (i < parts.length) ? parts[i] : ""; // fill empty lines
        }
        backpackLines = fixed;

        System.out.println("\n".repeat(4));
        System.out.println("            "+center("- Backpack -",width));
        System.out.println("          --"+ "-".repeat(width));
        System.out.println("          |"+ center(backpackLines[0], width)+"|");;
        System.out.println("          |"+ center(backpackLines[1], width)+"|");;
        System.out.println("          |"+ center(backpackLines[2], width)+"|");;
        System.out.println("          |"+ center(backpackLines[3], width)+"|");;
        System.out.println("          | Requirements:"+ " ".repeat(width-14)+"|");
        System.out.printf("          |  >  %-16s|%n",backpack.getCost()+"$");
        System.out.printf("          |  >  %-16s|%n",backpack.getType()+">"+backpack.getRequirement());
        System.out.println("          |"+ " ".repeat(width)+"|");
        System.out.println("          |"+ " ".repeat(width)+"|");
        System.out.printf("          | %-20s",String.format("%-3s","+".repeat((cardEffects.get(0).getValue()/10)+1))+" "+cardEffects.get(0).getKey());
        System.out.printf("          | %-20s",String.format("%-3s","+".repeat((15/10)+1))+" "+"neveikia");
        System.out.println("|");

    }

    private void printCards(List<Card> cards, int width, int height, Card backpack) {
    // Preprocess: split each name into at most 'height' words
        List<String[]> cardLines = new ArrayList<>();
        for (Card card : cards) {
            String[] parts = card.getName().split(" ");
            String[] fixed = new String[height];
            for (int i = 0; i < height; i++) {
                fixed[i] = (i < parts.length) ? parts[i] : ""; // fill empty lines
            }
            cardLines.add(fixed);        }
        String[] backpackLines = new String[height];
        if(backpack != null){
            String[] parts = backpack.getName().split(" ");
            String[] fixed = new String[height];

            for (int i = 0; i < height; i++) {
                fixed[i] = (i < parts.length) ? parts[i] : ""; // fill empty lines
            }
            backpackLines = fixed;
        }

        // Print all lines row by row
        System.out.print(" ".repeat(10));
        System.out.println("-".repeat(width*cardLines.size()) + "-".repeat(cardLines.size()) + "-");
        for (int line = 0; line < height; line++) {
            System.out.print(" ".repeat(10));
            for (String[] lines : cardLines) {
                System.out.print("|" + center(lines[line], width));
            }
            if(line < height-1) System.out.println("|");
            else if (backpack != null) System.out.println("|   "+center("- Backpack -",width));
            else System.out.println("|");
        }

        // mine addition
        System.out.print(" ".repeat(10));
        for (Card card : cards) {
            //System.out.print("|" + " ".repeat(width));
            System.out.printf("| %-20s","Requirements:");
        }
        if(backpack == null)System.out.println("|");
        else System.out.println("|  --"+ "-".repeat(width));



        System.out.print(" ".repeat(10));
        for (Card card : cards) {
            System.out.printf("|  >  %-16s",card.getCost()+"$");
        }
        if(backpack == null)System.out.println("|");
        else System.out.println("|  |"+ center(backpackLines[0], width)+"|");;

        System.out.print(" ".repeat(10));
        for (Card card : cards) {
            if (card.getRequirement() != 0){
                System.out.printf("|  >  %-16s",card.getType()+">"+card.getRequirement());
            }
            else System.out.print("|" + " ".repeat(width));
        }
        if(backpack == null)System.out.println("|");
        else System.out.println("|  |"+ center(backpackLines[1], width)+"|");;

        System.out.print(" ".repeat(10));
        for (Card card : cards) {
            System.out.print("|" + " ".repeat(width));
        }
        if(backpack == null)System.out.println("|");
        else System.out.println("|  |"+ center(backpackLines[2], width)+"|");;

        System.out.print(" ".repeat(10));
        for (Card card : cards) {
            System.out.print("|" + " ".repeat(width));
        }
        if(backpack == null)System.out.println("|");
        else System.out.println("|  |"+ center(backpackLines[3], width)+"|");;

        System.out.print(" ".repeat(10));
        for (Card card : cards) {

            System.out.printf("| %-20s",String.format("%-3s","+".repeat((card.getEffects().get(0).getValue()/10)+1))+" "+card.getEffects().get(0).getKey());
            //System.out.printf("| %-20s",String.format("%-3s","+".repeat((15/10)+1))+" "+"neveikia");

        }
        if(backpack == null)System.out.println("|");
        else System.out.println("|  | Requirements: "+ " ".repeat(width-15)+"|");

        System.out.print(" ".repeat(10));
        for (Card card : cards) {
            System.out.printf("| %-20s",String.format("%-3s","-".repeat((Math.abs(card.getEffects().get(1).getValue())/10)+1))+" "+card.getEffects().get(1).getKey());
            //System.out.printf("| %-20s",String.format("%-3s","-".repeat((Math.abs(15)/10)+1))+" "+"neveikia");
        }
        if(backpack == null)System.out.println("|");
        else System.out.printf("|  |  >  %-16s|%n",backpack.getCost()+"$");

        System.out.print(" ".repeat(10));
        for (Card card : cards) {
            if (card.getEffects().size()>2)
                System.out.printf("| ?   %-16s",". . . .");
            else System.out.print("|" + " ".repeat(width));
        }
        if(backpack == null)System.out.println("|");
        else System.out.printf("|  |  >  %-16s|%n",backpack.getType()+">"+backpack.getRequirement());

        System.out.print(" ".repeat(10));
        for (Card card : cards) {
            if (card.getDuration()>0)
                System.out.printf("|%21s",card.getDuration()+" turns ");
            else System.out.print("|"+" ".repeat(width));
        }
        if(backpack == null)System.out.println("|");
        else System.out.println("|  |"+ " ".repeat(width)+"|");

        System.out.print(" ".repeat(10));
        for (int i = 1; i <= cardLines.size(); i++) {
            System.out.print("|"+ " ".repeat(width/2)+ i+ " ".repeat(width/2));
        }
        if(backpack == null)System.out.println("|");
        else System.out.println("|  |"+ " ".repeat(width)+"|");


        System.out.print(" ".repeat(10));
        System.out.print("-".repeat(width*cardLines.size()) + "-".repeat(cardLines.size()) + "-");
        if(backpack != null){
            System.out.printf("  | %-20s",String.format("%-3s","+".repeat((backpack.getEffects().get(0).getValue()/10)+1))+" "+backpack.getEffects().get(0).getKey());
            //System.out.printf("  | %-20s",String.format("%-3s","+".repeat((15/10)+1))+" "+"neveikia");
            System.out.print("|");
        }

        //if(backpack != null)System.out.print("  |"+ "+".repeat(width)+"|");
        System.out.println();
    }

    public void printYearlyReview(City city){

        Random random = new Random();
        clearTerminal();
        System.out.println(center("==============================================",SCREEN_WIDTH));
        System.out.println(center("YEARLY REVIEW & PREPARATION",SCREEN_WIDTH));
        System.out.println(center("==============================================",SCREEN_WIDTH));
        System.out.println("\n\n\n");

        System.out.println(center(reflections[random.nextInt(reflections.length)],SCREEN_WIDTH));
        //System.out.println("\n\n");

        System.out.print(" ".repeat((SCREEN_WIDTH/2)-3));
        for(int i = 0; i < 3; i++) {
            System.out.print(".  ");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // optional: restore interrupt status
                break; // or just break the loop if interrupted
            }
        }
        clearTerminal();
        System.out.println(center("==============================================",SCREEN_WIDTH));
        System.out.println(center("YEARLY REVIEW & PREPARATION",SCREEN_WIDTH));
        System.out.println(center("==============================================",SCREEN_WIDTH));
        System.out.println("\n\n");

        //String bar = String.format("%-20", "|".repeat(city.citizensHappiness / 5));


        System.out.println(center("Shuffling cards...",SCREEN_WIDTH));
        System.out.println(center("Recalculating mayor popularity...",SCREEN_WIDTH));
        System.out.println("\n");

        double percentChange = (city.get(CIVILIAN) - city.getPrev(CIVILIAN)) / (double) city.getPrev(CIVILIAN) * 100;
        String percentage = String.format("]          ---- %+03.0f%% ---->          [", percentChange);
        System.out.println(center("Civilian ["+(String.format("%-20s", "|".repeat(city.getPrev(CIVILIAN) / 5)))+percentage+String.format("%-20s", "|".repeat(city.get(CIVILIAN) / 5))+"] Civilian",SCREEN_WIDTH));

        percentChange = (city.get(ENVIRONMENT) - city.getPrev(ENVIRONMENT)) / (double) city.getPrev(ENVIRONMENT) * 100;
        percentage = String.format("]          ---- %+03.0f%% ---->          [", percentChange);
        System.out.println(center("Environment ["+(String.format("%-20s", "|".repeat(city.getPrev(ENVIRONMENT) / 5)))+percentage+String.format("%-20s", "|".repeat(city.get(ENVIRONMENT) / 5))+"] Environment",SCREEN_WIDTH));

        percentChange = (city.get(BUSINESS) - city.getPrev(BUSINESS)) / (double) city.getPrev(BUSINESS) * 100;
        percentage = String.format("]          ---- %+03.0f%% ---->          [", percentChange);
        System.out.println(center("Business ["+(String.format("%-20s", "|".repeat(city.getPrev(BUSINESS) / 5)))+percentage+String.format("%-20s", "|".repeat(city.get(BUSINESS) / 5))+"] Business",SCREEN_WIDTH));

        percentChange = (city.get(SECURITY) - city.getPrev(SECURITY)) / (double) city.getPrev(SECURITY) * 100;
        percentage = String.format("]          ---- %+03.0f%% ---->          [", percentChange);
        System.out.println(center("Security ["+(String.format("%-20s", "|".repeat(city.getPrev(SECURITY) / 5)))+percentage+String.format("%-20s", "|".repeat(city.get(SECURITY) / 5))+"] Security",SCREEN_WIDTH));



        System.out.println("\n\n\n\n\n");

        System.out.println(center("tip:",SCREEN_WIDTH));
        int index = random.nextInt(tips.length);
        for (String line : tips[index]) {
            System.out.println(center(line, SCREEN_WIDTH));
        }

        System.out.println("\n");
        System.out.println(center("   Press Enter to continue...", SCREEN_WIDTH));

        // idet mero popularity checkus galimai win galimai lose!!!!!!!!!!!!!!!!!
    }

    public void printRandomEvent(RandomEvent event){

        String[] effectList = event.getEffectDescriptions();

        clearTerminal();
        System.out.println(center("!!!   RANDOM EVENT   !!!",SCREEN_WIDTH));
        System.out.println();
        System.out.println(center("Current event: " + event.getName(),SCREEN_WIDTH));
        System.out.println();
        System.out.println(center(event.getDescription(),SCREEN_WIDTH));
        System.out.println();
        for(int i=0; i<4; i++){
            System.out.println(center(effectList[i],SCREEN_WIDTH));
        }
        System.out.println("\n\n\n\n");
        System.out.println(center("Press Enter to continue...", SCREEN_WIDTH));
        System.out.println();


    }

    public void winMessage() {
        clearTerminal();
        System.out.println("You won i guess ");
    }

    public void lostMessage() {
        clearTerminal();
        System.out.println("You lost! gg loser");
    }

    public void introduction() {
        System.out.println("   [" + "The game lol" + "]");
        System.out.println();
        System.out.println();
        System.out.println("   Objective:");
        System.out.println("   Survive 4 years (32 turns) without letting any group's satisfaction drop to 0,");
        System.out.println("   and keep the mayor's popularity above 20%.");
        System.out.println("\n");
        System.out.println("   How to play:");
        System.out.println("   - oldGameFiles.Type a card number to buy that card.");
        System.out.println("   - Use 'number -b' to save a card to your backpack without buying it.");
        System.out.println("   - Use '-b' to remove the card from your backpack.");
        System.out.println("   - Use 'refill' to refill deck by 5 cards (-150$).");
        System.out.println("   - Use 'next' or 'n' to proceed to the next turn.");
        System.out.println("   - Use 'exit' to quit the game anytime.");
        System.out.println("\n");
        System.out.println("   Manage your city wisely and keep everyone happy!\n\n\n\n");
        System.out.println("   Press Enter to continue...\n");

    }
}
