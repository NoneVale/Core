package net.nighthawkempires.core.settings;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.nighthawkempires.core.CorePlugin;
import net.nighthawkempires.core.datasection.DataSection;
import net.nighthawkempires.core.server.ServerType;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ConfigModel extends SettingsModel {

    private final String key;

    // SERVER SETTINGS
    private final ServerType serverType;
    private final boolean economyBased;
    private final boolean scoreboardEnabled;

    // CONSOLE STUFF //
    private final String consoleDisplayName;
    private final UUID consoleUuid;

    // MONGO INFORMATION //
    private final String mongoHostname;
    private final String mongoDatabase;
    private final String mongoUsername;
    private final String mongoPassword;

    private final boolean useMongo;

    // STARTING SETTINGS //
    private final double defaultBalance;
    private final int defaultTokens;

    private final Map<String, Integer> warmupCommands;

    public ConfigModel() {
        this.key = "config";

        this.serverType = ServerType.SETUP;
        this.economyBased = false;
        this.scoreboardEnabled = true;

        this.consoleDisplayName = "&8&l&o*&7&l&oHawkeye&8&l&o";
        this.consoleUuid = UUID.randomUUID();

        this.mongoHostname = "localhost";
        this.mongoDatabase = "database";
        this.mongoUsername = "u$ername";
        this.mongoPassword = "pa$$word";

        this.useMongo = false;

        this.defaultBalance = 500.0;
        this.defaultTokens = 15;

        warmupCommands = Maps.newHashMap();

        CorePlugin.getSettingsRegistry().register(this);
    }

    public ConfigModel(String key, DataSection data) {
        this.key = key;

        this.serverType = ServerType.valueOf(data.getString("server-type").toUpperCase());
        this.economyBased = data.getBoolean("economy-based", false);

        this.scoreboardEnabled = data.getBoolean("scoreboard-enabled", false);

        DataSection consoleData = data.getSectionNullable("console");
        this.consoleDisplayName = consoleData.getString("display-name");
        this.consoleUuid = UUID.fromString(consoleData.getString("uuid"));

        DataSection mongoData = data.getSectionNullable("mongo");
        this.mongoHostname = mongoData.getString("hostname");
        this.mongoDatabase = mongoData.getString("database");
        this.mongoUsername = mongoData.getString("username");
        this.mongoPassword = mongoData.getString("password");
        this.useMongo = mongoData.getBoolean("enabled", false);

        DataSection defaultData = data.getSectionNullable("defaults");
        this.defaultBalance = defaultData.getDouble("balance");
        this.defaultTokens = defaultData.getInt("tokens");

        this.warmupCommands = Maps.newHashMap();
        if (data.isSet("command-warmups")) {
            DataSection commandData = data.getSectionNullable("command-warmups");
            for (String s : commandData.keySet()) {
                this.warmupCommands.put(s, commandData.getInt(s));
            }
        }

        CorePlugin.getSettingsRegistry().register(this);
    }

    public ServerType getServerType() {
        return this.serverType;
    }

    public boolean isEconomyBased() {
        return economyBased;
    }

    public boolean isScoreboardEnabled() {
        return scoreboardEnabled;
    }

    public String getConsoleDisplayName() {
        return consoleDisplayName;
    }

    public UUID getConsoleUuid() {
        return consoleUuid;
    }

    public String getMongoHostname() {
        return mongoHostname;
    }

    public String getMongoDatabase() {
        return mongoDatabase;
    }

    public String getMongoUsername() {
        return mongoUsername;
    }

    public String getMongoPassword() {
        return mongoPassword;
    }

    public boolean useMongo() {
        return useMongo;
    }

    public double getDefaultBalance() {
        return defaultBalance;
    }

    public int getDefaultTokens() {
        return defaultTokens;
    }

    public ImmutableList<String> getWarmupCommands() {
        return ImmutableList.copyOf(this.warmupCommands.keySet());
    }

    public int getRequiredArgLength(String command) {
        if (this.warmupCommands.containsKey(command)) return this.warmupCommands.get(command);
        return 0;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = Maps.newHashMap();

        map.put("server-type", this.serverType);
        map.put("economy-based", this.economyBased);
        map.put("scoreboard-enabled", this.scoreboardEnabled);

        Map<String, Object> consoleMap = Maps.newHashMap();
        consoleMap.put("display-name", this.consoleDisplayName);
        consoleMap.put("uuid", this.consoleUuid.toString());
        map.put("console", consoleMap);

        Map<String, Object> mongoMap = Maps.newHashMap();
        mongoMap.put("hostname", this.mongoHostname);
        mongoMap.put("database", this.mongoDatabase);
        mongoMap.put("username", this.mongoUsername);
        mongoMap.put("password", this.mongoPassword);
        mongoMap.put("enabled", this.useMongo);
        map.put("mongo", mongoMap);

        Map<String, Object> defaultMap = Maps.newHashMap();
        defaultMap.put("balance", this.defaultBalance);
        defaultMap.put("tokens", this.defaultTokens);
        map.put("defaults", defaultMap);

        map.put("command-warmups", this.warmupCommands);

        return map;
    }
}
