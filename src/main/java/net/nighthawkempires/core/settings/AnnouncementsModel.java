package net.nighthawkempires.core.settings;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.nighthawkempires.core.announcements.Announcement;
import net.nighthawkempires.core.datasection.DataSection;

import java.util.List;
import java.util.Map;

public class AnnouncementsModel extends SettingsModel {

    private String key;

    private boolean announcementsEnabled;
    private long announcementsInterval;

    private List<String[]> announcementsList;
    private List<Announcement> announcements;

    public AnnouncementsModel() {
        this.key = "announcements";

        this.announcementsEnabled = false;
        this.announcementsInterval = 600;

        this.announcementsList = Lists.newArrayList();
        this.announcements = Lists.newArrayList();
    }

    @SuppressWarnings("unchecked")
    public AnnouncementsModel(String key, DataSection data) {
        this.key = key;

        this.announcementsEnabled = data.getBoolean("announcements-enabled");
        this.announcementsInterval = data.getLong("announcements-interval");

        List<String[]> announcements = Lists.newArrayList();
        this.announcements = Lists.newArrayList();
        for (Object o : data.getList("announcements")) {
            announcements.add((String[]) o);
            this.announcements.add(new Announcement((String[]) o));
        }
        this.announcementsList = announcements;
    }

    public boolean areAnnouncementsEnabled() {
        return this.announcementsEnabled;
    }

    public long getAnnouncementsInterval() {
        return this.announcementsInterval;
    }

    public ImmutableList<String[]> getAnnouncementsList() {
        return ImmutableList.copyOf(this.announcementsList);
    }

    public ImmutableList<Announcement> getAnnouncements() {
        return ImmutableList.copyOf(this.announcements);
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = Maps.newHashMap();

        map.put("announcements-enabled", this.announcementsEnabled);
        map.put("announcements-interval", this.announcementsInterval);

        map.put("announcements", this.announcementsList);
        return map;
    }
}