package cz.schutzpetr.stock.core.location;

/**
 * Created by Petr Schutz on 15.02.2017
 *
 * @author Petr Schutz
 * @version 1.0
 */
public class Aisle extends Location {

    /**
     * Cislo ulicky
     */
    private final int aisle;


    public Aisle(String locationName, String subStock) {
        super(locationName, subStock, LocationType.AISLE);

        this.aisle = Integer.parseInt(locationName.substring(2));
    }

    /**
     * Metoda, která převede parametry na řetězec reprezentující zobrazované jméno lokace
     *
     * @param aisle číslo uličky
     * @return zobrazované jmeno
     */
    public static String parseDisplayName(int aisle) {
        return "HU" + aisle;
    }


}
