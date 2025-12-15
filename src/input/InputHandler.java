package input;

import model.*;

import java.util.List;

import static utils.Constants.REFILL_COST;

public class InputHandler {

    public ParsedCommand parse(String input, List<Card> cards, City city, Mayor mayor) {
        input = input.trim().toLowerCase();

        if (input.matches("\\d+")) {
            int index = Integer.parseInt(input) - 1;
            if (index < 0 || index >= cards.size())
                return new ParsedCommand(Command.OUT_OF_RANGE);

            Card card = cards.get(index);

            if (card.getRequirement() > 0 &&
                    city.get(card.getType()) < card.getRequirement())
                return new ParsedCommand(Command.REQUIREMENT_NOT_MET);

            if (!mayor.canAfford(card.getCost()))
                return new ParsedCommand(Command.NOT_ENOUGH_MONEY);

            return new ParsedCommand(Command.BUY, index);
        }

        if (input.matches("\\d+\\s+-b")) {
            if (!mayor.hasBackpackCard()) {
                int index = Integer.parseInt(input.split("\\s+")[0]) - 1;
                return new ParsedCommand(Command.PUT_BACKPACK, index);
            }
            else return new ParsedCommand(Command.PUT_BACKPACK_ERROR);
        }

        if (input.equals("-b")){
            if (mayor.hasBackpackCard()) return new ParsedCommand(Command.EMPTY_BACKPACK);
                else  return new ParsedCommand(Command.EMPTY_BACKPACK_ERROR);
        }
        if (input.equals("next") || input.equals("n")) return new ParsedCommand(Command.NEXT_TURN);
        if (input.equals("exit")) return new ParsedCommand(Command.EXIT);

        if (input.equals("refill") || input.equals("r")) {
            if (mayor.canAfford(REFILL_COST)) return new ParsedCommand(Command.REFILL);
            return new ParsedCommand(Command.NOT_ENOUGH_MONEY);
        }

        return new ParsedCommand(Command.INVALID);
    }
}

