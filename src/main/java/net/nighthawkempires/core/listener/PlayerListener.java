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
            broadcast("Welcome " + player.getName() + " to the " + getConfig().getServerType().name() + " server.");

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


        /*String header = "" +
                getMessages().getMessage(CHAT_HEADER) + "\n\n" +
                DARK_GRAY + "Players Online" + GRAY + ": " + GOLD + Bukkit.getServer().getOnlinePlayers().size() + DARK_GRAY + "/" + GOLD + Bukkit.getServer().getMaxPlayers() + "\n" +
                RED + "\n    ";
        player.setPlayerListHeader(header);
        player.setPlayerListFooter(getMessages().getMessage(CHAT_FOOTER));*/

        if (player.hasPermission("ne.staff")) {
            event.setJoinMessage(DARK_GRAY + "[" + DARK_GREEN + "+" + DARK_GRAY + "] "
                    + AQUA + "" + BOLD + "" + ITALIC + "Staff " + GREEN + player.getName() + GRAY + " has joined the game.");
        } else {
            event.setJoinMessage(ChatColor.DARK_GRAY + "[" + DARK_GREEN + "+" + DARK_GRAY + "] "
                    + GREEN + player.getName() + GRAY + " has joined the game.");
        }

        if (getConfig().isScoreboardEnabled()) {
            if (userModel.isScoreboardEnabled()) {
                getScoreboardManager().startScoreboards(player);
            }
        }

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
            event.setQuitMessage(ChatColor.DARK_GRAY + "[" + ChatColor.DARK_RED + "-" + ChatColor.DARK_GRAY + "] "
                    + AQUA + "" + BOLD + "" + ITALIC + "Staff " + ChatColor.RED + player.getName() + ChatColor.GRAY + " has left the game.");
        } else {
            event.setQuitMessage(ChatColor.DARK_GRAY + "[" + ChatColor.DARK_RED + "-" + ChatColor.DARK_GRAY + "] "
                    + ChatColor.RED + player.getName() + ChatColor.GRAY + " has left the game.");
        }

        //getScoreboardManager().stopScoreboards(player);
        //event.setQuitMessage(getMessages().getMessage(QUIT_MESSAGE).replaceAll("%PLAYER%", player.getName()));
        String header = getMessages().getMessage(CHAT_HEADER) + "\n"
                + DARK_GRAY + "Players Online" + GRAY + ": " + GOLD + (Bukkit.getOnlinePlayers().size() - 1) + DARK_GRAY + "/" + GOLD + Bukkit.getServer().getMaxPlayers();
        String footer = DARK_GRAY + "Donate" + GRAY + ": " + AQUA + "store.nighthawkempires.net \n" + getMessages().getMessage(CHAT_FOOTER);

        for (Player online : Bukkit.getOnlinePlayers()) {
            online.setPlayerListHeader(header);
            online.setPlayerListFooter(footer);
        }
    }

    @EventHandler
    public void onJoin(AsyncPlayerPreLoginEvent event) {
        UUID uuid = event.getUniqueId();
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

    private void broadcast(String message) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(message);
        }
    }

    private ConfigModel getConfig() {
        return getConfigg();
    }
}