package ca.indigogames.ubloxagps.utility;

import java.util.UUID;

public class Random {
    public static synchronized UUID randomUUID() {
        return UUID.randomUUID();
    }
}
