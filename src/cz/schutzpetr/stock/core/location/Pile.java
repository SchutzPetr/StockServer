package cz.schutzpetr.stock.core.location;

/**
 * Created by Petr Schutz on 15.02.2017
 *
 * @author Petr Schutz
 * @version 1.0
 */
public class Pile extends Location {

    /**
     * Cislo ulicky
     */
    private final String pileName;


    public Pile(String locationName, String subStock) {
        super(locationName, subStock, LocationType.PILE);

        this.pileName = locationName.substring(2);

    }

    /**
     * Metoda, která převede parametry na řetězec reprezentující zobrazované jméno lokace
     *
     * @param pileName  jméno hromady
     * @return zobrazované jmeno
     */
    public static String parseDisplayName(String pileName) {
        return "H-" + pileName;
    }

    /**
     * Metoda, která navrací jméno hromady
     *
     * @return pileName
     */
    public String getName() {
        return pileName;
    }
}
