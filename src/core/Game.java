package core;

import core.GameLoop;
import core.TurnManager;
import factory.*;
import model.*;
import render.GameRenderer;
import service.*;
import strategy.*;

import java.util.List;
import java.util.Random;

import static utils.Constants.MAX_TURNS;
import static utils.TextUtils.center;

public class Game {

    public static void main(String[] args) {

        Random rand = new Random();

        Mayor mayor = new Mayor(35, 250);

        City city = new City(
                rand.nextInt(21) + 35,
                rand.nextInt(21) + 35,
                rand.nextInt(21) + 35,
                rand.nextInt(21) + 35
        );

        Deck deck = new Deck(new CardFactory().createDefaultDeck());
        //System.out.println(deck.size());

        //List<oldGameFiles.Card> hand = deck.draw();

        RandomEventService eventService =
                new RandomEventService(new RandomEventFactory().createDefaultEvents());

        EventChanceStrategy chanceStrategy =
                new ProgressiveEventChanceStrategy();

        String[] info = {
                center("Year 1  1/8", 60), // magic 60 ??
                "-".repeat(60),
                "",
                ""};

        GameRenderer renderer = new GameRenderer();
        TurnManager turnManager = new TurnManager();

        GameContext ctx = new GameContext(
                city,
                mayor,
                deck,
                eventService,
                chanceStrategy,
                renderer,
                info
        );

        GameLoop loop = new GameLoop();
        loop.run(ctx, turnManager);

        // po loopo
        if(turnManager.getTurn() >= MAX_TURNS && ctx.mayor.getPopularity()>=20)
            ctx.renderer.winMessage();
        else
            ctx.renderer.lostMessage();

    }

}
