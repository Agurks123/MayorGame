import java.util.*;

public class RandomEventManager {
    private List<RandomEvent> events = new ArrayList<>();
    private Random random = new Random();
    private RandomEvent currentEvent;

    public RandomEventManager() {

        //events.add(new RandomEvent(
        //        "Business boom",
        //        "Local businesses prosper significantly",
        //        Map.of(Type.BUSINESS, 15)
        //));
        //events.add(new RandomEvent(
        //        "Community festival",
        //        "Citizens enjoy a big festival",
        //        Map.of(Type.CIVILIAN, 15, Type.BUSINESS, 5)
        //));
        //events.add(new RandomEvent(
        //        "Environmental cleanup",
        //        "Volunteers clean parks and rivers",
        //        Map.of(Type.ENVIRONMENT, 10)
        //));
        //events.add(new RandomEvent(
        //        "Neighborhood watch formed",
        //        "Citizens unite to improve security",
        //        Map.of(Type.CIVILIAN, 5, Type.SECURITY, 15)
        //));
        events.add(new RandomEvent(
                "New shopping mall opens",
                "Business gets a boost, some environmental damage",
                Map.of(Type.BUSINESS, 10, Type.ENVIRONMENT, -10)
        ));
        events.add(new RandomEvent(
                "Storm",
                "A powerful storm floods several districts and damages city infrastructure.",
                Map.of(Type.ENVIRONMENT, -15, Type.SECURITY, -10)
        ));

        events.add(new RandomEvent(
                "Crime wave",
                "A sudden crime surge spreads fear among citizens and lowers security morale.",
                Map.of(Type.SECURITY, -20, Type.CIVILIAN, -5)
        ));

        events.add(new RandomEvent(
                "Factory accident",
                "An industrial accident contaminates the air and disrupts production lines.",
                Map.of(Type.ENVIRONMENT, -15, Type.BUSINESS, -10)
        ));

        events.add(new RandomEvent(
                "Tax increase",
                "A sudden tax hike frustrates citizens and businesses alike.",
                Map.of(Type.CIVILIAN, -15, Type.SECURITY, 5, Type.BUSINESS, -15)
        ));

        events.add(new RandomEvent(
                "Power outage",
                "A city-wide blackout halts production and leaves many citizens angry.",
                Map.of(Type.BUSINESS, -15, Type.CIVILIAN, -10)
        ));

        events.add(new RandomEvent(
                "Drought",
                "A long drought hurts the environment and frustrates local citizens.",
                Map.of(Type.ENVIRONMENT, -20, Type.CIVILIAN, -5)
        ));

        events.add(new RandomEvent(
                "Corruption scandal",
                "A corruption scandal involving city officials shakes public trust.",
                Map.of(Type.CIVILIAN, -10, Type.BUSINESS, -5, Type.SECURITY, -10)
        ));

        events.add(new RandomEvent(
                "Pollution spike",
                "Factories dump waste illegally, worsening pollution and angering citizens.",
                Map.of(Type.ENVIRONMENT, -20, Type.CIVILIAN, -5)
        ));

        events.add(new RandomEvent(
                "Cyber attack",
                "Hackers target city systems, causing chaos for businesses and security.",
                Map.of(Type.SECURITY, -15, Type.BUSINESS, -10)
        ));

        events.add(new RandomEvent(
                "Protests",
                "Mass protests erupt due to dissatisfaction, lowering morale and safety.",
                Map.of(Type.CIVILIAN, -10, Type.SECURITY, -15)
        ));

        events.add(new RandomEvent(
                "Economic downturn",
                "A national recession impacts local business and public satisfaction.",
                Map.of(Type.BUSINESS, -15, Type.CIVILIAN, -10)
        ));

        events.add(new RandomEvent(
                "Infrastructure failure",
                "A major bridge collapses due to poor maintenance, causing chaos in the city.",
                Map.of(Type.SECURITY, -10, Type.BUSINESS, -10, Type.CIVILIAN, -5)
        ));
    }

    public void grabNewRandomEvent() {
        currentEvent = events.get(random.nextInt(events.size()));
    }

    public RandomEvent getCurrentEvent() {
        if (currentEvent == null) {
            grabNewRandomEvent(); // initialize if no event yet
        }
        return currentEvent;
    }
}

