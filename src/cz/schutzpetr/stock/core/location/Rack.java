package cz.schutzpetr.stock.core.location;

/**
 * Created by Petr Schutz on 15.02.2017
 *
 * @author Petr Schutz
 * @version 1.0
 */
public class Rack extends Location {

    /**
     * Číslo uličky
     */
    private final short aisle;
    /**
     * Číslo buňky regálu
     */
    private final short cell;
    /**
     * Číslo patra regálu
     */
    private final byte floor;

    public Rack(String locationName, String subStock) {
        super(locationName, subStock, LocationType.RACK);

        String[] args = locationName.split("-");

        this.aisle = Short.parseShort(args[0]);
        this.cell = Short.parseShort(args[1]);
        this.floor = Byte.parseByte(args[2]);
    }
}
