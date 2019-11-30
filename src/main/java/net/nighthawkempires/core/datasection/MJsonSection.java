package net.nighthawkempires.core.datasection;

import org.bson.Document;

import java.util.Map;

public class MJsonSection extends Document implements DataSection {

    private MJsonSection() {
    }

    public MJsonSection(Map<String, Object> data) {
        this(null, data);
    }

    public MJsonSection(String key, Map<String, Object> data) {
        if (data != null) {
            clear();
            putAll(data);
        } else {
            throw new NullPointerException("Section data cannot be null, is this a valid section?");
        }
        if (key != null) {
            put("key", key);
        }
    }

    public MJsonSection getSectionNullable(String s) {
        try {
            MJsonSection section = new MJsonSection();
            section.putAll((Map) get(s));
            return section;
        } catch (Exception ignored) {
        }
        return null;
    }

    public MJsonSection createSection(String s) {
        if (!s.equals("key")) {
            MJsonSection section = new MJsonSection();
            put(s, section);
            return section;
        }
        throw new IllegalArgumentException("Cannot set a section as 'key'.");
    }

    public MJsonSection createSection(String s, Map<String, Object> map) {
        if (!s.equals("key")) {
            MJsonSection section = new MJsonSection(map);
            put(s, section);
            return section;
        }
        throw new IllegalArgumentException("Cannot set a section as 'key'.");
    }

    @Override
    public FJsonSection toFJsonSection() {
        return new FJsonSection(this);
    }

    @Override
    public MJsonSection toMJsonSection() {
        return this;
    }
}
