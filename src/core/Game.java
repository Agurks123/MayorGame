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

import static utils.Constants.*;
import static utils.TextUtils.center;

public class Game {

    public static void main(String[] args) {

        Random rand = new Random();

        Mayor mayor = new Mayor(MAYOR_STARTING_POPULARITY, MAYOR_STARTING_BUDGET);

        City city = new City(
                rand.nextInt(CITY_STARTING_VALUES_FLUCTUATION) + CITY_STARTING_VALUES_CIVIALIAN,
                rand.nextInt(CITY_STARTING_VALUES_FLUCTUATION) + CITY_STARTING_VALUES_BUSINESS,
                rand.nextInt(CITY_STARTING_VALUES_FLUCTUATION) + CITY_STARTING_VALUES_ENVIRONMENT,
                rand.nextInt(CITY_STARTING_VALUES_FLUCTUATION) + CITY_STARTING_VALUES_SECURITY
        );

        Deck deck = new Deck(new CardFactory().createDefaultDeck());

        RandomEventService eventService =
                new RandomEventService(new RandomEventFactory().createDefaultEvents());

        EventChanceStrategy chanceStrategy =
                new ProgressiveEventChanceStrategy();

        String[] info = {
                center("Year 1  1/8", INFO_SCREEN_WIDTH),
                "-".repeat(INFO_SCREEN_WIDTH),
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
        if(turnManager.getTurn() >= MAX_TURNS && ctx.mayor.getPopularity()>=MIN_POPULARITY)
            ctx.renderer.winMessage();
        else
            ctx.renderer.lostMessage();

    }

}
