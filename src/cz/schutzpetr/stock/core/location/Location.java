package cz.schutzpetr.stock.core.location;

import java.io.Serializable;

/**
 * Created by Petr Schutz on 15.02.2017
 *
 * @author Petr Schutz
 * @version 1.0
 */
public abstract class Location implements Serializable {
    /**
     * Jméno lokace
     */
    private final String locationName;


    /**
     * Jméno podskladu
     */
    private final String subStock;

    /**
     * Typ lokace
     */
    private final LocationType locationType;

    Location(String locationName, String subStock, LocationType locationType) {
        this.locationName = locationName;
        this.subStock = subStock;
        this.locationType = locationType;
    }

    /**
     * Metoda, která navrací jméno podskladu
     *
     * @return subStock
     */
    public String getSubStock() {
        return subStock;
    }

    /**
     * Metoda, která navrací jméno lokace
     *
     * @return locationName
     */
    public String getName() {
        return locationName;
    }

    /**
     * Metoda, která navrací výčtový typ, reprezentující typ lokace
     *
     * @return locationType
     */
    public LocationType getType() {
        return locationType;
    }
}
