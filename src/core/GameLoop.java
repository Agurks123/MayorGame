package core;

import effect.CardEffect;
import factory.CardFactory;
import input.InputHandler;
import input.ParsedCommand;
import model.*;
import render.GameRenderer;
import service.RandomEventService;
import strategy.EventChanceStrategy;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static utils.Constants.MAX_TURNS;
import static utils.Constants.REFILL_COST;
import static utils.TextUtils.infoPlacer;

public class GameLoop {

    private final InputHandler inputHandler = new InputHandler();

    public void run(GameContext ctx, TurnManager turnManager) {

        Scanner scanner = new Scanner(System.in);
        ctx.renderer.introduction();
        scanner.nextLine();

        boolean running = true;
        while (running) {
            ctx.renderer.renderMainScreen(ctx.city, ctx.mayor, ctx.deck, ctx.hand, ctx.info);
            ctx.info[2] = "";
            ctx.info[3] = "";


            ParsedCommand cmd = inputHandler.parse(scanner.nextLine(), ctx.hand, ctx.city, ctx.mayor);


            switch (cmd.command) {
                case EXIT -> running = false;

                case NEXT_TURN -> running = turnManager.startTurn(ctx, scanner);

                case BUY -> {
                    new CardEffect(ctx.hand.get(cmd.index)).apply(ctx.city);
                    ctx.mayor.spend(ctx.hand.get(cmd.index).getCost());
                    infoPlacer(ctx.info, ("You bought "+ctx.hand.get(cmd.index).getName()+"!"));
                    ctx.hand.remove((int)cmd.index);
                }

                case PUT_BACKPACK -> {
                    ctx.mayor.putInBackpack(ctx.hand.get(cmd.index));
                    ctx.hand.remove((int)cmd.index);
                }

                case PUT_BACKPACK_ERROR -> infoPlacer(ctx.info, ("Your backpack is full!"));

                case EMPTY_BACKPACK -> ctx.hand.add(ctx.mayor.removeBackpackCard());

                case EMPTY_BACKPACK_ERROR -> infoPlacer(ctx.info, ("Your backpack is empty!"));

                case REFILL -> {
                    ctx.deck.addCards(new CardFactory().getNewFiveRandomCards());
                    ctx.mayor.spend(REFILL_COST);
                    infoPlacer(ctx.info, ("Added 5 new cards to the deck!"));
                }

                case INVALID -> infoPlacer(ctx.info, ("Invalid command"));

                case NOT_ENOUGH_MONEY -> infoPlacer(ctx.info, ("You don't have enough money!"));

                case REQUIREMENT_NOT_MET -> infoPlacer(ctx.info, ("You don't meet the requirements!"));

                case OUT_OF_RANGE -> infoPlacer(ctx.info, ("Card number out of range!"));



            }
        }


    }
}
