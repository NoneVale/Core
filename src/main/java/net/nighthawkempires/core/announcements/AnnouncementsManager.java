package net.nighthawkempires.core.announcements;

import net.nighthawkempires.core.CorePlugin;
import net.nighthawkempires.core.settings.AnnouncementsModel;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class AnnouncementsManager {

    private int taskId;
    private int currentAnnouncement;

    public AnnouncementsManager() {
        this.taskId = this.currentAnnouncement = -1;

        startAnnouncements();
    }

    public void startAnnouncements() {
        if (getAnnouncementsModel().areAnnouncementsEnabled() && getAnnouncementsModel().getAnnouncements().size() != 0) {
            if (this.taskId != -1)
                Bukkit.getScheduler().cancelTask(this.taskId);

            this.taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(CorePlugin.getPlugin(), () -> {
                this.currentAnnouncement++;
                if (this.currentAnnouncement >= getAnnouncementsModel().getAnnouncements().size()){
                    this.currentAnnouncement = 0;
                }

                Announcement announcement = getAnnouncementsModel().getAnnouncements().get(this.currentAnnouncement);

                for (Player player : Bukkit.getOnlinePlayers()) {
                    player.sendMessage(announcement.getLines());
                }

            }, getAnnouncementsModel().getAnnouncementsInterval(), getAnnouncementsModel().getAnnouncementsInterval());
        }
    }

    public void stopAnnouncements() {
        if (this.taskId == -1) return;

        Bukkit.getScheduler().cancelTask(this.taskId);
        this.taskId = this.currentAnnouncement = -1;
    }

    private AnnouncementsModel getAnnouncementsModel() {
        return CorePlugin.getAnnouncements();
    }
}