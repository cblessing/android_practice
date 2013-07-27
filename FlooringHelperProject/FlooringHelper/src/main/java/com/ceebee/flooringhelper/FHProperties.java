package com.ceebee.flooringhelper;

/**
 * Created by gottabcb on 7/22/13.
 */
public class FHProperties {
    private static FHProperties ourInstance = new FHProperties();

    public static FHProperties getInstance() {
        return ourInstance;
    }

    private FHProperties() {}

    public final String DB_NAME = "FLOORING_HELPER_DB";

}
