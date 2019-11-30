package net.nighthawkempires.core.datasection;

import java.util.Map;

public interface Model {

    String getKey();

    Map<String, Object> serialize();
}
