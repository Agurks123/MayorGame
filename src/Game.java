import javax.sound.midi.Soundbank;
import java.sql.SQLOutput;
import java.util.*;

public class Game {

    private Mayor mayor;
    private City city;
    private Deck deck;

    private static int turn;
    private static int maxTurns;
    private static int screenWidth;
    private static int screenHeight;
    private int playerBudgetIncome;
    private boolean gameStatus; // 0 for gameover
    private double randomEventChance;
    private String[] info;

    public Game() {
        Random rand = new Random();

        this.mayor = new Mayor(35,250);
        this.city = new City(rand.nextInt(21) + 35,rand.nextInt(21) + 35,rand.nextInt(21) + 35,rand.nextInt(21) + 35);
        this.deck = new Deck();

        turn = 0;
        maxTurns = 32;
        screenWidth = 168;
        this.playerBudgetIncome = 0;
        this.gameStatus = true;
        this.randomEventChance = 0;
        this.info = new String[4];
    }

    public static void main(String[] args) {

    Game game = new Game();
    game.gameLoop();

        if(turn >= maxTurns && game.mayor.popularity>=20) {
            clearTerminal();
            System.out.println("You won i guess ");
        }
        else{
            clearTerminal();
            System.out.println("You lost! gg loser");
        }


    }

    public void gameLoop(){
        RandomEventManager eventManager = new RandomEventManager();
        Random random = new Random();
        Scanner scanner = new Scanner(System.in);
        List<Card> cards = null;
        boolean nextTurn = true;
        int[] prevGroupValues = new int[4];
        prevGroupValues[0] = city.citizensHappiness;
        prevGroupValues[1] = city.environmentQuality;
        prevGroupValues[2] = city.businessSatisfaction;
        prevGroupValues[3] = city.securityLevel;

        info[0] = "idk basic ahh rules";
        info[1] = "-".repeat(60);
        info[2] = "";
        info[3] = "";
        mayor.popularity = ((city.citizensHappiness*0.5)+(city.environmentQuality*0.3)+(city.businessSatisfaction*0.1)+(city.securityLevel*0.1))* 0.714;
        String[] reflections = {
                "Reflecting on the past year's challenges...",
                "Looking back at the hurdles overcome...",
                "Reviewing the trials and triumphs of the year...",
                "Taking stock of the city's ups and downs..."
        };

        String[][] tips = {
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

        clearTerminal();
        introduction(scanner);
        while (gameStatus && turn < maxTurns) {

        ///////////////////////////////////////////////// Atlieka logika pries turna
            if (nextTurn) {
                deck.discardCards(cards);
                city.activeEffectManager();

                if(turn % 8 == 0 && turn != 0 ){
                //if(turn == 2){
                    clearTerminal();
                    deck.setCards();
                    mayor.popularity = ((city.citizensHappiness*0.5)+(city.environmentQuality*0.3)+(city.businessSatisfaction*0.1)+(city.securityLevel*0.1))* 0.714;

                    clearTerminal();
                    System.out.println(center("==============================================",screenWidth));
                    System.out.println(center("YEARLY REVIEW & PREPARATION",screenWidth));
                    System.out.println(center("==============================================",screenWidth));
                    System.out.println("\n\n\n");

                    System.out.println(center(reflections[random.nextInt(reflections.length)],screenWidth));
                    //System.out.println("\n\n");

                    System.out.print(" ".repeat((screenWidth/2)-3));
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
                    System.out.println(center("==============================================",screenWidth));
                    System.out.println(center("YEARLY REVIEW & PREPARATION",screenWidth));
                    System.out.println(center("==============================================",screenWidth));
                    System.out.println("\n\n");

                    //String bar = String.format("%-20", "|".repeat(city.citizensHappiness / 5));


                    System.out.println(center("Shuffling cards...",screenWidth));
                    System.out.println(center("Recalculating mayor popularity...",screenWidth));
                    System.out.println("\n");

                    double percentChange = (city.citizensHappiness - prevGroupValues[0]) / (double) prevGroupValues[0] * 100;
                    String percentage = String.format("]          ---- %+03.0f%% ---->          [", percentChange);
                    System.out.println(center("Civilian ["+(String.format("%-20s", "|".repeat(prevGroupValues[0] / 5)))+percentage+String.format("%-20s", "|".repeat(city.citizensHappiness / 5))+"] Civilian",screenWidth));

                    percentChange = (city.environmentQuality - prevGroupValues[1]) / (double) prevGroupValues[1] * 100;
                    percentage = String.format("]          ---- %+03.0f%% ---->          [", percentChange);
                    System.out.println(center("Environment ["+(String.format("%-20s", "|".repeat(prevGroupValues[1] / 5)))+percentage+String.format("%-20s", "|".repeat(city.environmentQuality / 5))+"] Environment",screenWidth));

                    percentChange = (city.businessSatisfaction - prevGroupValues[2]) / (double) prevGroupValues[2] * 100;
                    percentage = String.format("]          ---- %+03.0f%% ---->          [", percentChange);
                    System.out.println(center("Business ["+(String.format("%-20s", "|".repeat(prevGroupValues[2] / 5)))+percentage+String.format("%-20s", "|".repeat(city.businessSatisfaction / 5))+"] Business",screenWidth));

                    percentChange = (city.securityLevel - prevGroupValues[3]) / (double) prevGroupValues[3] * 100;
                    percentage = String.format("]          ---- %+03.0f%% ---->          [", percentChange);
                    System.out.println(center("Security ["+(String.format("%-20s", "|".repeat(prevGroupValues[3] / 5)))+percentage+String.format("%-20s", "|".repeat(city.securityLevel / 5))+"] Security",screenWidth));



                    System.out.println("\n\n\n\n\n");

                    System.out.println(center("tip:",screenWidth));
                    int index = random.nextInt(tips.length);
                    for (String line : tips[index]) {
                        System.out.println(center(line, screenWidth));
                    }

                    System.out.println("\n");
                    System.out.println(center("   Press Enter to continue...", screenWidth));
                    String input = scanner.nextLine();
                    clearTerminal();


                    prevGroupValues[0] = city.citizensHappiness;
                    prevGroupValues[1] = city.environmentQuality;
                    prevGroupValues[2] = city.businessSatisfaction;
                    prevGroupValues[3] = city.securityLevel;
                    // idet mero popularity checkus galimai win galimai lose
                }

                if (deck.getCardsSize() <= 0) {info[2] = "The deck is empty!!";} //// IDK
                cards = deck.drawCards();
                mayor.budget += playerBudgetIncome;

                info[0] = center("Year " + ((turn / 8) + 1) + "  " + (((turn % 8) + 1)) + "/8", 60);
                turn++;
                nextTurn = false;

            }
        /////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////// logic for updating when screen refreshes

            playerBudgetIncome = (int) (city.businessSatisfaction*1.2);

        ///  1 Line
            System.out.printf("%-50s", "Mayor budget: " + mayor.budget + " -> " + (mayor.budget+playerBudgetIncome) + " (next turn)");
            System.out.printf("%-87s", center(info[0],60));
            System.out.print("Civilian [");
            System.out.printf("%-20s", "|".repeat(city.citizensHappiness / 5));
            System.out.println("]");
        ///  2 Line
            System.out.printf("%-50s", String.format("Mayor Popularity: %.2f", mayor.popularity));
            System.out.printf("%-84s", center(info[1],60));
            System.out.print("Environment [");
            System.out.printf("%-20s", "|".repeat(city.environmentQuality / 5));
            System.out.println("]");
        ///  3 Line
            System.out.printf("%-50s", "__________-\\_________");
            System.out.printf("%-87s", center(info[2],60));
            System.out.print("Business [");
            System.out.printf("%-20s", "|".repeat(city.businessSatisfaction / 5));
            System.out.println("]");
        ///  4 Line
            System.out.printf("%-50s", "     _---   \\");
            System.out.printf("%-87s", center(info[3],60));
            System.out.print("Security [");
            System.out.printf("%-20s", "|".repeat(city.securityLevel / 5));
            System.out.println("]");


            info[2] = "";
            info[3] = "";


        ////////////////////////////////////////////////////////////////  Deck printas
            System.out.println("__---        \\\\          O                        O                                              O                                  O ");
            System.out.printf("%-14s","Deck Size: " + deck.getCardsSize()); // v
                         System.out.println(" \\       /|\\       O                /|\\                     O                    /|\\              O                 /|\\                     ");
        //  System.out.println("Buy 5 cards    ///      / \\    /|\\               / \\           O          /|\\                   / \\             /|\\       O        / \\                    ");
            System.out.println("Refill deck    ///      / \\    /|\\               / \\           O          /|\\                   / \\             /|\\       O        / \\                    ");
            System.out.println("by 5 (150$) ///      O          / \\     O                    /|\\         / \\         O                   O     / \\      /|\\               O            ");
            System.out.println("       ////          /|\\                /|\\                   / \\                  /|\\                 /|\\               / \\              /|\\                ");
            System.out.println("   ////             / \\                / \\                                          / \\                 / \\                              / \\      ");

        ////////////////////////////////////////////////////////////////// Kortu printas
        // kortu dydis, 16 blokeliu kortu laukas
            int width = 21;
            int height = 4;

            if (cards.size() > 0) printCards(cards, width, height, mayor.backpack);
                else if (mayor.backpack != null) printBackpack(mayor.backpack,width, height);
                else System.out.print("\n".repeat(16));
        ///////////////////////////////////////////////////////////////////

            System.out.println("Enter card number (to buy), number -b (add to backpack), -b (remove from backpack), n/next (next turn), r/refill (refill deck by 5 cards -150$), exit (quit):");
            System.out.print("> ");
        ///////////////////////////////////////////////////////////////////   INPUT logika
            String input = scanner.nextLine();
            String result = handleInput(input, cards, city, mayor);
            if (result.startsWith("buy")) {                      ////////////   Korta   ////////////
                int index = Integer.parseInt(result.split(" ")[1]);

                if (cards.get(index).duration > 0) {
                    for (Map.Entry<Type, Integer> effect : cards.get(index).effects) {
                        city.setEffectDuration(effect.getKey().ordinal(), cards.get(index).duration);
                        city.setEffectStrength(effect.getKey().ordinal(), effect.getValue());
                    }
                } else {
                    for (Map.Entry<Type, Integer> effect : cards.get(index).effects) {
                        city.effectManager(effect.getKey(), effect.getValue());
                    }

                }
                mayor.budget -= cards.get(index).cost;
                infoPlacer(info, ("You bought "+cards.get(index).name+"!"));
                cards.remove(index);


            }
            else if (result.startsWith("backpack")) {     ////////////  backpack idejima  ////////////
                int index = Integer.parseInt(result.split(" ")[1]);

                if (mayor.backpack == null) {
                    mayor.backpack = cards.get(index);
                    cards.remove(index);
                } else {

                    infoPlacer(info, "Your backpack is full!");
                }

            }
            else if (result.equals("empty")) {          ////////////  backpack isejimas ////////////

                if (mayor.backpack == null) {

                    infoPlacer(info, "Your backpack is empty!");
                } else {
                    cards.add(mayor.backpack);
                    mayor.backpack = null;
                }
            }
            else if (result.equals("next")) nextTurn = true;////////////  NEXT turn ////////////

            else if (result.equals("refill")){                 ////////////  refill 5 cards ////////////
                deck.getFiveRandomCards();
                mayor.budget-=150;
            }
            else if (result.equals("exit")) gameStatus = false; ////////////  exit game ////////////
            else if (result.equals("money")){
                infoPlacer(info, "You don't have enough money!");
            }
            else if (result.equals("requirement")){
                infoPlacer(info, "You don't meet the requirements!");
            }
            else if (result.equals("range")) {
                infoPlacer(info, "Card number out of range!");
            }
            else {
                infoPlacer(info, "Invalid input.");
            }

        ///////////////////////////////////////////////////////////////////      LOGIC kitam turnui
            if(nextTurn) { // jei nera klaidos po inputo vyksta game logic
                if (checkEvent(randomEventChance, city)) { // event
                    eventManager.grabNewRandomEvent();
                    RandomEvent current = eventManager.getCurrentEvent();
                    String[] effectList = current.getEffectDescriptions();


                    for (Map.Entry<Type, Integer> entry : current.getPointsChanges().entrySet()) {
                        //System.out.println(entry.getKey() + " changes by " + entry.getValue());
                        city.effectManager(entry.getKey(), entry.getValue());
                    }

                    //print
                    clearTerminal();
                    System.out.println(center("!!!   RANDOM EVENT   !!!",150));
                    System.out.println();
                    System.out.println(center("Current event: " + current.getName(),150));
                    System.out.println();
                    System.out.println(center(current.getDescription(),150));
                    System.out.println();
                    for(int i=0; i<4; i++){
                        System.out.println(center(effectList[i],150));
                    }
                    System.out.println("\n\n\n\n");
                    System.out.println(center("Press Enter to continue...", 150));
                    System.out.println();
                    infoPlacer(info, (current.getSummary()));
                    input = scanner.nextLine();

                    randomEventChance = 0;
                } else {
                    randomEventChance += 0.1;
                    if (randomEventChance > 1) randomEventChance = 1;

                }

                city.updateGroups();


            }
        /////////////////////////////////////////////////////////////////////   Checkas pabaigoj ciklo

            // probmlema ta kad jei yra efektas ir jis numusa iki 0 po jo dar vyksta input part ir tik paskui sitas
            if(city.checkStatus() || mayor.popularity<20) {
                gameStatus = false;
            }

            clearTerminal();

        }

    }

    private void introduction(Scanner scanner) {
        System.out.println("   [" + "The game lol" + "]");
        System.out.println();
        System.out.println();
        System.out.println("   Objective:");
        System.out.println("   Survive 4 years (32 turns) without letting any group's satisfaction drop to 0,");
        System.out.println("   and keep the mayor's popularity above 20%.");
        System.out.println("\n");
        System.out.println("   How to play:");
        System.out.println("   - Type a card number to buy that card.");
        System.out.println("   - Use 'number -b' to save a card to your backpack without buying it.");
        System.out.println("   - Use '-b' to remove the card from your backpack.");
        System.out.println("   - Use 'refill' to refill deck by 5 cards (-150$).");
        System.out.println("   - Use 'next' or 'n' to proceed to the next turn.");
        System.out.println("   - Use 'exit' to quit the game anytime.");
        System.out.println("\n");
        System.out.println("   Manage your city wisely and keep everyone happy!\n\n\n\n");
        System.out.println("   Press Enter to continue...\n");

        scanner.nextLine();
        clearTerminal();
    }

    private void infoPlacer(String[] info, String text){
        if(info[2].isEmpty()) info[2] = text;
        else info[3] = text;
    }

    private void printBackpack(Card backpack, int width, int height) {
        String[] backpackLines = new String[height];
        String[] parts = backpack.name.split(" ");
        String[] fixed = new String[height];

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
        System.out.printf("          |  >  %-16s|%n",backpack.cost+"$");
        System.out.printf("          |  >  %-16s|%n",backpack.type+">"+backpack.requirement);
        System.out.println("          |"+ " ".repeat(width)+"|");
        System.out.println("          |"+ " ".repeat(width)+"|");
        System.out.printf("          | %-20s",String.format("%-3s","+".repeat((backpack.effects.get(0).getValue()/10)+1))+" "+backpack.effects.get(0).getKey());
        System.out.println("|");

    }

    public static boolean checkEvent(double randomEventChance, City city) {
        Random random = new Random();

            // random ahh function
            double Chance = randomEventChance * (1 + (50 - city.getStatus(Type.SECURITY)) / 100.0);

            if (Chance < 0) Chance = 0;
            if (Chance > 1) Chance = 1;

            if (random.nextDouble() < Chance) {
                return true;
            } else {
                return false;

            }
    }


    public static void printCards(List<Card> cards, int width, int height, Card backpack) {
        // Preprocess: split each name into at most 'height' words
        List<String[]> cardLines = new ArrayList<>();
        for (Card card : cards) {
            String[] parts = card.name.split(" ");
            String[] fixed = new String[height];
            for (int i = 0; i < height; i++) {
                fixed[i] = (i < parts.length) ? parts[i] : ""; // fill empty lines
            }
            cardLines.add(fixed);        }
        String[] backpackLines = new String[height];
        if(backpack != null){
            String[] parts = backpack.name.split(" ");
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
            System.out.printf("|  >  %-16s",card.cost+"$");
        }
        if(backpack == null)System.out.println("|");
        else System.out.println("|  |"+ center(backpackLines[0], width)+"|");;

        System.out.print(" ".repeat(10));
        for (Card card : cards) {
            if (card.requirement != 0){
                System.out.printf("|  >  %-16s",card.type+">"+card.requirement);
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

            System.out.printf("| %-20s",String.format("%-3s","+".repeat((card.effects.get(0).getValue()/10)+1))+" "+card.effects.get(0).getKey());

        }
        if(backpack == null)System.out.println("|");
        else System.out.println("|  | Requirements: "+ " ".repeat(width-15)+"|");

        System.out.print(" ".repeat(10));
        for (Card card : cards) {
            System.out.printf("| %-20s",String.format("%-3s","-".repeat((Math.abs(card.effects.get(1).getValue())/10)+1))+" "+card.effects.get(1).getKey());
        }
        if(backpack == null)System.out.println("|");
        else System.out.printf("|  |  >  %-16s|%n",backpack.cost+"$");

        System.out.print(" ".repeat(10));
        for (Card card : cards) {
            if (card.effects.size()>2)
                System.out.printf("| ?   %-16s",". . . .");
            else System.out.print("|" + " ".repeat(width));
        }
        if(backpack == null)System.out.println("|");
        else System.out.printf("|  |  >  %-16s|%n",backpack.type+">"+backpack.requirement);

        System.out.print(" ".repeat(10));
        for (Card card : cards) {
            if (card.duration>0)
                System.out.printf("|%21s",card.duration+" turns ");
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
            System.out.printf("  | %-20s",String.format("%-3s","+".repeat((backpack.effects.get(0).getValue()/10)+1))+" "+backpack.effects.get(0).getKey());
            System.out.print("|");
        }

        //if(backpack != null)System.out.print("  |"+ "+".repeat(width)+"|");
        System.out.println();
    }

    public static String center(String text, int width) {
        if (text.length() >= width) return text.substring(0, width);
        int leftPadding = (width - text.length()) / 2;
        int rightPadding = width - text.length() - leftPadding;
        return " ".repeat(leftPadding) + text + " ".repeat(rightPadding);
    }


    public String handleInput(String input, List<Card> cards, City city, Mayor mayor) {
        input = input.trim().toLowerCase();

        // handle single number buy
        if (input.matches("\\d+")) {
            int num = Integer.parseInt(input);
            if (num < 1 || num > cards.size()) {
                return "range"; // invalid range
            }

            Card card = cards.get(num - 1);

            if (card.requirement > 0 && city.getStatus(card.type) < card.requirement) {
                return "requirement"; // not enough group satisfaction
            }

            if (card.cost > mayor.budget) {
                return "money"; // not enough money
            }

            return "buy " + (num - 1); // return command with card index
        }

        // "x -b" → put card into backpack
        if (input.matches("\\d+\\s+-b")) {
            String[] parts = input.split("\\s+");
            int num = Integer.parseInt(parts[0]);
            if (num < 1 || num > cards.size()) {
                return "range";
            }
            return "backpack " + (num - 1);
        }

        // "-b" → empty backpack
        if (input.equals("-b")) return "empty";

        // "next" → end month
        if (input.equals("next") || input.equals("n")) return "next";

        // "refill" → Refill deck by 5 cards
        if (input.equals("refill") || input.equals("r"))
            if(mayor.budget >= 150) return "refill";
                else return "money";

        // "exit" → quit
        if (input.equals("exit")) return "exit";

        // unknown command
        return "invalid";
    }

    public static void clearTerminal() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }

}

