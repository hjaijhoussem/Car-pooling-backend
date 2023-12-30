package com.horizon.carpooling.entities.enums;

public enum Region {
    ARIANA ,
    BEJA,
    BEN_AROUS,
    BIZERTE,
    GABES,
    GAFSA,
    JENDOUBA,
    KAIROUAN,
    KASSERINE,
    KEF,
    MAHDIA,
    MANOUBA,
    MEDENINE,
    MONASTIR,
    NABEUL,
    SFAX,
    SIDI_BOUZID,
    SILIANA,
    SOUSSE,
    TATAOUINE,
    TOZEUR,
    TUNIS,
    ZAGHOUAN;

    public static boolean contains(String departureRegion) {
        for (Region region : Region.values()) {
            if (region.name().equals(departureRegion)) {
                return true;
            }
        }
        return false;
    }
}
