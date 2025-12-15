package core;

import effect.*;
import model.*;
import render.GameRenderer;
import service.RandomEventService;
import strategy.EventChanceStrategy;

import java.util.Scanner;

import static model.Type.*;
import static utils.Constants.*;
import static utils.TextUtils.center;
import static utils.TextUtils.infoPlacer;

public class TurnManager {

    private static int turn = 0;

    public boolean startTurn(GameContext ctx, Scanner scanner) {

        ctx.mayor.addBudget((int) (ctx.city.get(BUSINESS)*MAYOR_BUDGET_MULTIPLIER));
        ctx.city.applyActiveEffects();
        ctx.city.updateGroups();
        ctx.deck.discard(ctx.hand);


        // RANDOM EVENT
        if (ctx.chanceStrategy.shouldTriggerEvent(ctx.city.get(SECURITY))) {
            RandomEvent event = ctx.eventService.nextEvent();
            // DISPLAY
            ctx.renderer.printRandomEvent(event);
            infoPlacer(ctx.info, (event.getSummary()));
            scanner.nextLine();
            //////////
            new EventEffect(event).apply(ctx.city);
          }
        turn++;
        ctx.info[0] = center("Year " + ((turn/ 8) + 1) + "  " + (((turn % 8) + 1)) + "/8", INFO_SCREEN_WIDTH);

        // YEARLY REVIEW
        if(turn % 8 == 0 && turn != 0 ){
            ctx.deck.reshuffle();
            ctx.mayor.setPopularity(((ctx.city.get(CIVILIAN)*0.5)+(ctx.city.get(ENVIRONMENT)*0.3)+(ctx.city.get(BUSINESS)*0.1)+(ctx.city.get(SECURITY)*0.1))* 0.714);
            // DISPLAY     ^ GALIMAI MAGIC NUMBER
            ctx.renderer.printYearlyReview(ctx.city);
            scanner.nextLine();
            //////////
            ctx.city.setPrev();
        }


        if (ctx.deck.size() <= 0) infoPlacer(ctx.info, ("The deck is empty!!")); //// IDK
        ctx.hand = ctx.deck.draw();

        return !ctx.city.isDead() && ctx.mayor.getPopularity() >= MIN_MAYOR_POPULARITY;
    }

    public int getTurn() {
        return turn;
    }
}
