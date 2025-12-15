package effect;

import model.City;

public interface GameEffect {

    /**
     * Applies this effect to the city.
     */
    void apply(City city);
}