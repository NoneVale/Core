package net.nighthawkempires.core.util;

import com.google.common.collect.Lists;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.List;

public class LocationUtil {

    public static Player getTargetPlayer(final Player player, final double maxRange, final double precision) {
        Validate.notNull(player, "player may not be null");
        Validate.isTrue(maxRange > 0D, "the maximum range has to be greater than 0");
        Validate.isTrue(precision > 0D && precision < 1D, "the precision has to be greater than 0 and smaller than 1");
        final double maxRange2 = maxRange * maxRange;

        final String playerName = player.getName();
        final Location fromLocation = player.getEyeLocation();
        final String playersWorldName = fromLocation.getWorld().getName();
        final Vector playerDirection = fromLocation.getDirection().normalize();
        final Vector playerVectorPos = fromLocation.toVector();

        Player target = null;
        double targetDistance2 = Double.MAX_VALUE;
        for (final Player somePlayer : Bukkit.getServer().getOnlinePlayers()) {
            if (somePlayer.getName().equals(playerName)) {
                continue;
            }
            final Location somePlayerLocation_middle = getMiddleLocationOfPlayer(somePlayer);
            final Location somePlayerLocation_eye = somePlayer.getEyeLocation();
            final Location somePlayerLocation_feed = somePlayer.getLocation();
            if (!somePlayerLocation_eye.getWorld().getName().equals(playersWorldName)) {
                continue;
            }
            final double newTargetDistance2_middle = somePlayerLocation_middle.distanceSquared(fromLocation);
            final double newTargetDistance2_eye = somePlayerLocation_eye.distanceSquared(fromLocation);
            final double newTargetDistance2_feed = somePlayerLocation_feed.distanceSquared(fromLocation);

            if (newTargetDistance2_middle <= maxRange2) {
                final Vector toTarget_middle =
                        somePlayerLocation_middle.toVector().subtract(playerVectorPos).normalize();
                final double dotProduct_middle = toTarget_middle.dot(playerDirection);
                if (dotProduct_middle > precision && player.hasLineOfSight(somePlayer) &&
                        (target == null || newTargetDistance2_middle < targetDistance2)) {
                    target = somePlayer;
                    targetDistance2 = newTargetDistance2_middle;
                    continue;
                }
            }
            if (newTargetDistance2_eye <= maxRange2) {
                final Vector toTarget_eye = somePlayerLocation_eye.toVector().subtract(playerVectorPos).normalize();
                final double dotProduct_eye = toTarget_eye.dot(playerDirection);
                if (dotProduct_eye > precision && player.hasLineOfSight(somePlayer) &&
                        (target == null || newTargetDistance2_eye < targetDistance2)) {
                    target = somePlayer;
                    targetDistance2 = newTargetDistance2_eye;
                    continue;
                }
            }
            if (newTargetDistance2_feed <= maxRange2) {
                final Vector toTarget_feed = somePlayerLocation_feed.toVector().subtract(playerVectorPos).normalize();
                final double dotProduct_feed = toTarget_feed.dot(playerDirection);
                if (dotProduct_feed > precision && player.hasLineOfSight(somePlayer) &&
                        (target == null || newTargetDistance2_feed < targetDistance2)) {
                    target = somePlayer;
                    targetDistance2 = newTargetDistance2_feed;
                }
            }
        }
        return target;
    }

    public static Location getMiddleLocationOfPlayer(final Player player) {
        return player.getLocation().add(0, player.getEyeHeight() / 2, 0);
    }
}
