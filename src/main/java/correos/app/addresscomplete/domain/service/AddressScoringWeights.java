package correos.app.addresscomplete.domain.service;

public class AddressScoringWeights {
    public static final int STREET_EXACT = 100;
    public static final int STREET_HIGH_SIMILARITY = 70;
    public static final int STREET_MEDIUM_SIMILARITY = 40;
    public static final int NUMBER_MATCH = 50;
    public static final int POSTAL_CODE_MATCH = 30;
    public static final int CITY_MATCH = 10;
}
