package net.nighthawkempires.core;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import net.nighthawkempires.core.announcements.AnnouncementsManager;
import net.nighthawkempires.core.bossbar.BossBarManager;
import net.nighthawkempires.core.chat.format.ChatFormat;
import net.nighthawkempires.core.chat.tag.NameTag;
import net.nighthawkempires.core.command.CommandManager;
import net.nighthawkempires.core.enchantment.EnchantmentManager;
import net.nighthawkempires.core.lang.Messages;
import net.nighthawkempires.core.lang.MessagesManager;
import net.nighthawkempires.core.listener.ChatListener;
import net.nighthawkempires.core.listener.PlayerListener;
import net.nighthawkempires.core.location.player.registry.FPlayerLocationRegistry;
import net.nighthawkempires.core.location.player.registry.PlayerLocationRegistry;
import net.nighthawkempires.core.location.registry.FPublicLocationRegistry;
import net.nighthawkempires.core.location.registry.PublicLocationRegistry;
import net.nighthawkempires.core.scoreboard.ScoreboardManager;
import net.nighthawkempires.core.scoreboard.scoreboards.InfoScoreboard;
import net.nighthawkempires.core.server.ServerType;
import net.nighthawkempires.core.settings.*;
import net.nighthawkempires.core.settings.registry.FSettingsRegistry;
import net.nighthawkempires.core.settings.registry.SettingsRegistry;
import net.nighthawkempires.core.user.registry.MUserRegistry;
import net.nighthawkempires.core.user.registry.UserRegistry;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import static org.bukkit.Bukkit.getPluginManager;

public class CorePlugin extends JavaPlugin {

    private static Plugin plugin;

    private static PlayerLocationRegistry playerLocationRegistry;
    private static PublicLocationRegistry publicLocationRegistry;
    private static SettingsRegistry settingsRegistry;
    private static UserRegistry userRegistry;

    private static MongoDatabase mongoDatabase;

    private static AnnouncementsManager announcementsManager;
    private static BossBarManager bossBarManager;
    private static CommandManager commandManager;
    private static EnchantmentManager enchantmentManager;
    private static MessagesManager messagesManager;
    private static ScoreboardManager scoreboardManager;

    private static ChatFormat chatFormat;

    public void onLoad() {

    }

    public void onEnable() {
        plugin = this;

        settingsRegistry = new FSettingsRegistry();

        if (!getSettingsRegistry().configExists()) {
            getLogger().info("Creating config file for the first time, please standby...");
            getConfigg();
            getLogger().info("Config file has been created, shutting down...");
            getServer().shutdown();
            return;
        }

        if (getConfigg().getServerType() != ServerType.SETUP) {
            getLogger().info("Server Type has been registered as \'" + getConfigg().getServerType().name() + "\'");
            String pluginName = getPlugin().getName();
            try {
                String hostname = getConfigg().getMongoHostname();
                String database = getConfigg().getMongoDatabase().replaceAll("%PLUGIN%", pluginName);
                String username = getConfigg().getMongoUsername().replaceAll("%PLUGIN%", pluginName);
                String password = getConfigg().getMongoPassword();

                ServerAddress serverAddress = new ServerAddress(hostname, 27017);
                MongoCredential mongoCredential = MongoCredential.createCredential(username, database, password.toCharArray());
                mongoDatabase = new MongoClient(serverAddress, mongoCredential, new MongoClientOptions.Builder().build()).getDatabase(database);

                userRegistry = new MUserRegistry(getMongoDatabase());

                getLogger().info("Successfully connected to MongoDB.");

                registerListeners();

                announcementsManager = new AnnouncementsManager();
                bossBarManager = new BossBarManager();
                commandManager = new CommandManager();

                enchantmentManager = new EnchantmentManager();

                messagesManager = new MessagesManager();
                getMessagesManager().addEnumClass(Messages.class);

                scoreboardManager = new ScoreboardManager();
                getScoreboardManager().addScoreboard(new InfoScoreboard());

                chatFormat = new ChatFormat();
                getChatFormat().add(new NameTag());

                playerLocationRegistry = new FPlayerLocationRegistry();
                publicLocationRegistry = new FPublicLocationRegistry();
            } catch (Exception exception) {
                exception.printStackTrace();
                getLogger().warning("Could not connect to MongoDB, shutting down...");
                getServer().shutdown();
            }

        } else {
            getLogger().warning("Plugin is still in SETUP mode, please configure the plugin settings before starting...");
            getServer().shutdown();
        }
    }

    public void onDisable() {
        getEnchantmentManager().unregisterEnchants();
    }

    public void registerListeners() {
        getPluginManager().registerEvents(new PlayerListener(), this);
        getPluginManager().registerEvents(new ChatListener(), this);
    }

    public static Plugin getPlugin() {
        return plugin;
    }

    public static PlayerLocationRegistry getPlayerLocationRegistry() {
        return playerLocationRegistry;
    }

    public static PublicLocationRegistry getPublicLocationRegistry() {
        return publicLocationRegistry;
    }

    public static SettingsRegistry getSettingsRegistry() {
        return settingsRegistry;
    }

    public static ConfigModel getConfigg() {
        return settingsRegistry.getConfig();
    }

    public static MessagesModel getMessages() {
        return settingsRegistry.getMessages();
    }

    public static AnnouncementsModel getAnnouncements() {
        return getSettingsRegistry().getAnnouncements();
    }

    public static MaterialsModel getMaterials() {
        return getSettingsRegistry().getMaterials();
    }

    public static CooldownModel getCooldowns() {
        return getSettingsRegistry().getCooldowns();
    }

    public static UserRegistry getUserRegistry() {
        return userRegistry;
    }

    public MongoDatabase getMongoDatabase() {
        return mongoDatabase;
    }

    public static BossBarManager getBossBarManager() {
        return bossBarManager;
    }

    public static CommandManager getCommandManager() {
        return commandManager;
    }

    public static AnnouncementsManager getAnnouncementsManager() {
        return announcementsManager;
    }

    public static EnchantmentManager getEnchantmentManager() {
        return enchantmentManager;
    }

    public static MessagesManager getMessagesManager() {
        return messagesManager;
    }

    public static ScoreboardManager getScoreboardManager() {
        return scoreboardManager;
    }

    public static ChatFormat getChatFormat() {
        return chatFormat;
    }
}