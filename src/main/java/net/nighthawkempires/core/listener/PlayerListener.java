package net.nighthawkempires.core.listener;

import net.nighthawkempires.core.CorePlugin;
import net.nighthawkempires.core.lang.Messages;
import net.nighthawkempires.core.settings.ConfigModel;
import net.nighthawkempires.core.user.UserModel;
import org.apache.logging.log4j.core.Core;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import static net.nighthawkempires.core.CorePlugin.*;
import static net.nighthawkempires.core.lang.Messages.*;
import static org.bukkit.ChatColor.*;

public class PlayerListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UserModel userModel = getUserRegistry().getUser(player.getUniqueId());
        userModel.setLastJoinDate(new SimpleDateFormat("MM/dd/yyyy hh:mm:ss").format(new Date()));
        String address = player.getAddress().getHostString();
        if (!userModel.getIpAddressList().contains(address)) {
            userModel.addIpAddress(address);
        }

        if (!userModel.getPlayedServerList().contains(getConfig().getServerType().name())) {
            broadcast(CorePlugin.getMessages().getChatMessage(GRAY + "Welcome " + GREEN + player.getName() + GRAY + " to the server!"));

            userModel.setJoinDate(new SimpleDateFormat("MM/dd/yyyy hh:mm:ss").format(new Date()));
            userModel.addPlayedServer(getConfig().getServerType());
            if (getConfig().isEconomyBased()) {
                userModel.setServerBalance(getConfig().getServerType(), getConfig().getDefaultBalance());
            }

            World world = Bukkit.getWorld("world");

            if (CorePlugin.getPublicLocationRegistry().getPublicLocations().hasSpawn(world)) {
                player.teleport(CorePlugin.getPublicLocationRegistry().getPublicLocations().getSpawn(world));
            }
        }

        if (!userModel.getDisplayName().isEmpty())
            player.setDisplayName(userModel.getDisplayName());


        if (player.hasPermission("ne.staff")) {
            event.setJoinMessage(DARK_GRAY + "[" + DARK_GREEN + "+" + DARK_GRAY + "] "
                    + AQUA + "" + BOLD + "" + ITALIC + "Staff " + GREEN + player.getName() + GRAY + " has joined the game.");
        } else {
            event.setJoinMessage(DARK_GRAY + "[" + DARK_GREEN + "+" + DARK_GRAY + "] "
                    + GREEN + player.getName() + GRAY + " has joined the game.");
        }

        String[] motd = new String[]{
                CorePlugin.getMessages().getMessage(CHAT_HEADER),
                GRAY + "Welcome to " + DARK_GRAY + "" + ITALIC + "Nighthawk "
                        + GRAY + "" + ITALIC + "Empires" + GRAY + ", " + GREEN + player.getName() + GRAY + ".",
                GRAY + "Use " + DARK_AQUA + "/help " + GRAY + "or visit the help section at " +
                        DARK_AQUA + "/warp help" + GRAY + " for help.",
                GRAY + "Toggle scoreboards using " + DARK_AQUA + "/scoreboard",
                GRAY + "There are currently " + GOLD + Bukkit.getOnlinePlayers().size() + DARK_GRAY
                        + "/" + GOLD + Bukkit.getMaxPlayers() + GRAY + " player(s) online.",
                CorePlugin.getMessages().getMessage(CHAT_FOOTER),
        };
        Bukkit.getScheduler().scheduleSyncDelayedTask(CorePlugin.getPlugin(), () -> {
            player.sendMessage(motd);
        }, 5L);

        String header = getMessages().getMessage(CHAT_HEADER) + "\n"
                + DARK_GRAY + "Players Online" + GRAY + ": " + GOLD + Bukkit.getOnlinePlayers().size() + DARK_GRAY + "/" + GOLD + Bukkit.getServer().getMaxPlayers();
        String footer = DARK_GRAY + "Donate" + GRAY + ": " + AQUA + "store.nighthawkempires.net \n" + getMessages().getMessage(CHAT_FOOTER);

        for (Player online : Bukkit.getOnlinePlayers()) {
            online.setPlayerListHeader(header);
            online.setPlayerListFooter(footer);
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        UserModel userModel = getUserRegistry().getUser(player.getUniqueId());
        userModel.setLastLeaveDate(new SimpleDateFormat("MM/dd/yyyy hh:mm:ss").format(new Date()));


        if (player.hasPermission("ne.staff")) {
            event.setQuitMessage(DARK_GRAY + "[" + DARK_RED + "-" + DARK_GRAY + "] "
                    + AQUA + "" + BOLD + "" + ITALIC + "Staff " + RED + player.getName() + GRAY + " has left the game.");
        } else {
            event.setQuitMessage(DARK_GRAY + "[" + DARK_RED + "-" + DARK_GRAY + "] "
                    + RED + player.getName() + GRAY + " has left the game.");
        }

        String header = getMessages().getMessage(CHAT_HEADER) + "\n"
                + DARK_GRAY + "Players Online" + GRAY + ": " + GOLD + (Bukkit.getOnlinePlayers().size() - 1) + DARK_GRAY + "/" + GOLD + Bukkit.getServer().getMaxPlayers();

        for (Player online : Bukkit.getOnlinePlayers()) {
            online.setPlayerListHeader(header);
        }
    }

    @EventHandler
    public void onJoin(AsyncPlayerPreLoginEvent event) {
        UUID uuid = event.getUniqueId();

        if (event.getLoginResult() == AsyncPlayerPreLoginEvent.Result.ALLOWED) {
            if (Bukkit.getOfflinePlayer(uuid).getName() != null) {
                UserModel userModel = getUserRegistry().getUser(uuid);

                if (userModel.isBanned()) {
                    event.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_BANNED);
                    event.setKickMessage(userModel.getActiveBan().getBanInfo());
                } else if (CorePlugin.getIpBanRegistry().isBanned(event.getAddress().getHostAddress())) {
                    event.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_BANNED);
                    event.setKickMessage(CorePlugin.getIpBanRegistry().getBan(event.getAddress().getHostAddress()).getBanInfo());
                    event.setKickMessage(userModel.getActiveBan().getBanInfo());
                }
            }
        }
    }

    private void broadcast(String message) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(message);
        }
    }

    private ConfigModel getConfig() {
        return getConfigg();
    }
}