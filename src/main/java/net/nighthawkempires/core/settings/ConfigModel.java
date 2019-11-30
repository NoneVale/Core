package net.nighthawkempires.core.settings;

import com.google.common.collect.Maps;
import net.nighthawkempires.core.CorePlugin;
import net.nighthawkempires.core.datasection.DataSection;
import net.nighthawkempires.core.server.ServerType;

import java.util.Map;
import java.util.UUID;

public class ConfigModel extends SettingsModel {

    private String key;

    // SERVER SETTINGS
    private ServerType serverType;
    private boolean economyBased;

    // CONSOLE STUFF //
    private String consoleDisplayName;
    private UUID consoleUuid;

    // MONGO INFORMATION //
    private String mongoHostname;
    private String mongoDatabase;
    private String mongoUsername;
    private String mongoPassword;

    // STARTING SETTINGS //
    private double defaultBalance;
    private int defaultTokens;

    public ConfigModel() {
        this.key = "config";

        this.serverType = ServerType.SETUP;
        this.economyBased = false;

        this.consoleDisplayName = "&8&l&o*&7&l&oHawkeye&8&l&o";
        this.consoleUuid = UUID.randomUUID();

        this.mongoHostname = "localhost";
        this.mongoDatabase = "database";
        this.mongoUsername = "u$ername";
        this.mongoPassword = "pa$$word";

        this.defaultBalance = 500.0;
        this.defaultTokens = 15;

        CorePlugin.getSettingsRegistry().register(this);
    }

    public ConfigModel(String key, DataSection data) {
        this.key = key;

        this.serverType = ServerType.valueOf(data.getString("server-type").toUpperCase());
        if (data.isSet("economy-based"))
            this.economyBased = data.getBoolean("economy-based");
        else
            this.economyBased = false;

        DataSection consoleData = data.getSectionNullable("console");
        this.consoleDisplayName = consoleData.getString("display-name");
        this.consoleUuid = UUID.fromString(consoleData.getString("uuid"));

        DataSection mongoData = data.getSectionNullable("mongo");
        this.mongoHostname = mongoData.getString("hostname");
        this.mongoDatabase = mongoData.getString("database");
        this.mongoUsername = mongoData.getString("username");
        this.mongoPassword = mongoData.getString("password");

        DataSection defaultData = data.getSectionNullable("defaults");
        this.defaultBalance = defaultData.getDouble("balance");
        this.defaultTokens = defaultData.getInt("tokens");

        CorePlugin.getSettingsRegistry().register(this);
    }

    public ServerType getServerType() {
        return this.serverType;
    }

    public boolean isEconomyBased() {
        return economyBased;
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

    public double getDefaultBalance() {
        return defaultBalance;
    }

    public int getDefaultTokens() {
        return defaultTokens;
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

        Map<String, Object> consoleMap = Maps.newHashMap();
        consoleMap.put("display-name", this.consoleDisplayName);
        consoleMap.put("uuid", this.consoleUuid.toString());
        map.put("console", consoleMap);

        Map<String, Object> mongoMap = Maps.newHashMap();
        mongoMap.put("hostname", this.mongoHostname);
        mongoMap.put("database", this.mongoDatabase);
        mongoMap.put("username", this.mongoUsername);
        mongoMap.put("password", this.mongoPassword);
        map.put("mongo", mongoMap);

        Map<String, Object> defaultMap = Maps.newHashMap();
        defaultMap.put("balance", this.defaultBalance);
        defaultMap.put("tokens", this.defaultTokens);
        map.put("defaults", defaultMap);

        return map;
    }
}
