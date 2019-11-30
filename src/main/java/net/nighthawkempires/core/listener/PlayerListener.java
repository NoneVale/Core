package net.nighthawkempires.core.listener;

import net.nighthawkempires.core.CorePlugin;
import net.nighthawkempires.core.lang.Messages;
import net.nighthawkempires.core.settings.ConfigModel;
import net.nighthawkempires.core.user.UserModel;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class PlayerListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UserModel userModel = CorePlugin.getUserRegistry().getUser(player.getUniqueId());
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
        }

        event.setJoinMessage(CorePlugin.getMessages().getMessage(Messages.JOIN_MESSAGE).replaceAll("%PLAYER%", player.getName()));

        CorePlugin.getScoreboardManager().startScoreboards(player);
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        UserModel userModel = CorePlugin.getUserRegistry().getUser(player.getUniqueId());
        userModel.setLastLeaveDate(new SimpleDateFormat("MM/dd/yyyy hh:mm:ss").format(new Date()));

        //.event.setQuitMessage(ChatColor.DARK_GRAY + "[" + ChatColor.DARK_RED + "-" + ChatColor.DARK_GRAY + "] "
        //        + ChatColor.RED + player.getName() + ChatColor.GRAY + " has left the game.");

        CorePlugin.getScoreboardManager().stopScoreboards(player);
        event.setQuitMessage(CorePlugin.getMessages().getMessage(Messages.QUIT_MESSAGE).replaceAll("%PLAYER%", player.getName()));

    }

    @EventHandler
    public void onJoin(AsyncPlayerPreLoginEvent event) {
        UUID uuid = event.getUniqueId();
        UserModel userModel = CorePlugin.getUserRegistry().getUser(uuid);

        if (userModel.isBanned()) {
            event.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_BANNED);
            event.setKickMessage(userModel.getActiveBan().getBanInfo());
        }
    }

    private void broadcast(String message) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(message);
        }
    }

    private ConfigModel getConfig() {
        return CorePlugin.getConfigg();
    }
}
