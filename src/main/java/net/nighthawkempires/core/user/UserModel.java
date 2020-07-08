package net.nighthawkempires.core.user;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.nighthawkempires.core.CorePlugin;
import net.nighthawkempires.core.bans.Ban;
import net.nighthawkempires.core.datasection.DataSection;
import net.nighthawkempires.core.datasection.Model;
import net.nighthawkempires.core.kick.Kick;
import net.nighthawkempires.core.mute.Mute;
import net.nighthawkempires.core.server.ServerType;
import net.nighthawkempires.core.warning.Warning;
import org.bukkit.Bukkit;

import java.time.Instant;
import java.util.*;

public class UserModel implements Model {

    private int tokens;

    private List<String> ipAddressList;
    private List<String> playedServersList;

    private HashMap<String, Double> serverBalances;

    private String displayName;
    private String joinDate;
    private String lastJoinDate;
    private String lastLeaveDate;
    private String userName;

    private List<Ban> bans;
    private List<Kick> kicks;
    private List<Mute> mutes;
    private List<Warning> warnings;

    private UUID uuid;

    public UserModel(UUID uuid) {
        this.uuid = uuid;

        this.tokens = CorePlugin.getConfigg().getDefaultTokens();

        this.ipAddressList = Lists.newArrayList();
        /**if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(this.uuid))) {
            String ipAddress = Bukkit.getPlayer(this.uuid).getAddress().getHostString();
            if (!this.ipAddressList.contains(ipAddress))
                this.ipAddressList.add(ipAddress);
        }*/
        this.playedServersList = Lists.newArrayList();

        this.serverBalances = Maps.newHashMap();

        this.displayName = "";
        this.joinDate = "";
        this.lastJoinDate = "";
        this.lastLeaveDate = "";
        this.userName = Bukkit.getOfflinePlayer(this.uuid).getName();

        this.bans = Lists.newArrayList();
        this.kicks = Lists.newArrayList();
        this.mutes = Lists.newArrayList();
        this.warnings = Lists.newArrayList();
    }

    public UserModel(String key, DataSection data) {
        this.uuid = UUID.fromString(key);

        this.tokens = data.getInt("tokens");

        this.ipAddressList = data.getStringList("ip-addresses");
        /**if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(this.uuid))) {
            String ipAddress = Bukkit.getPlayer(this.uuid).getAddress().getHostString();
            if (!this.ipAddressList.contains(ipAddress))
                this.ipAddressList.add(ipAddress);
        }*/
        this.playedServersList = data.getStringList("played-servers");

        this.serverBalances = Maps.newHashMap();
        DataSection balanceSection = data.getSectionNullable("server-balances");
        for (String server : balanceSection.keySet()) {
            this.serverBalances.put(server, balanceSection.getDouble(server));
        }

        this.displayName = data.getString("display-name");
        this.joinDate = data.getString("join-date");
        this.lastJoinDate = data.getString("last-join-date");
        this.lastLeaveDate = data.getString("last-leave-date");
        this.userName = Bukkit.getOfflinePlayer(this.uuid).getName();

        this.bans = Lists.newArrayList();
        if (data.isSet("bans")) {
            for (Map<String, Object> m : data.getMapList("bans")) {
                this.bans.add(new Ban(m));
            }
        }

        this.kicks = Lists.newArrayList();
        if (data.isSet("kicks")) {
            for (Map<String, Object> m : data.getMapList("kicks")) {
                this.kicks.add(new Kick(m));
            }
        }

        this.mutes = Lists.newArrayList();
        if (data.isSet("mutes")) {
            for (Map<String, Object> m : data.getMapList("mutes")) {
                this.mutes.add(new Mute(m));
            }
        }

        this.warnings = Lists.newArrayList();
        if (data.isSet("warnings")) {
            for (Map<String, Object> m : data.getMapList("warnings")) {
                this.warnings.add(new Warning(m));
            }
        }
    }

    public int getTokens() {
        return this.tokens;
    }

    public void addTokens(int tokens) {
        this.tokens += tokens;
        CorePlugin.getUserRegistry().register(this);
    }

    public void removeTokens(int tokens) {
        this.tokens -= tokens;
        CorePlugin.getUserRegistry().register(this);
    }

    public void setTokens(int tokens) {
        this.tokens = tokens;
        CorePlugin.getUserRegistry().register(this);
    }

    public ImmutableList<String> getIpAddressList() {
        return ImmutableList.copyOf(this.ipAddressList);
    }

    public void addIpAddress(String ipAddress) {
        this.ipAddressList.add(ipAddress);
        CorePlugin.getUserRegistry().register(this);
    }

    public ImmutableList<String> getPlayedServerList() {
        return ImmutableList.copyOf(this.playedServersList);
    }

    public void addPlayedServer(ServerType serverType) {
        this.playedServersList.add(serverType.name());
        CorePlugin.getUserRegistry().register(this);
    }

    public ImmutableMap<String, Double> getServerBalances() {
        return ImmutableMap.copyOf(this.serverBalances);
    }

    public double getServerBalance(ServerType serverType) {
        if (!this.serverBalances.containsKey(serverType.name())) return -1;

        return this.serverBalances.get(serverType.name());
    }

    public double getCurrentServerBalance() {
        return getServerBalance(CorePlugin.getConfigg().getServerType());
    }

    public void setServerBalance(ServerType serverType, double balance) {
        this.serverBalances.put(serverType.name(), balance);
        CorePlugin.getUserRegistry().register(this);
    }

    public void addServerBalance(ServerType serverType, double balance) {
        this.serverBalances.put(serverType.name(), getServerBalance(serverType) + balance);
        CorePlugin.getUserRegistry().register(this);
    }

    public void removeServerBalance(ServerType serverType, double balance) {
        this.serverBalances.put(serverType.name(), getServerBalance(serverType) - balance);
        CorePlugin.getUserRegistry().register(this);
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
        CorePlugin.getUserRegistry().register(this);
    }

    public String getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
        CorePlugin.getUserRegistry().register(this);
    }

    public String getLastJoinDate() {
        return lastJoinDate;
    }

    public void setLastJoinDate(String lastJoinDate) {
        this.lastJoinDate = lastJoinDate;
        CorePlugin.getUserRegistry().register(this);
    }

    public String getLastLeaveDate() {
        return lastLeaveDate;
    }

    public void setLastLeaveDate(String lastLeaveDate) {
        this.lastLeaveDate = lastLeaveDate;
        CorePlugin.getUserRegistry().register(this);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
        CorePlugin.getUserRegistry().register(this);
    }

    public ImmutableList<Ban> getBans() {
        return ImmutableList.copyOf(this.bans);
    }

    public boolean isBanned() {
        for (Ban b : getBans()) {
            if (b.isBanActive()) return true;
        }

        return false;
    }

    public Ban getActiveBan() {
        for (Ban b : getBans()) {
            if (b.isBanActive()) return b;
        }
        return null;
    }

    public void unban() {
        for (Ban b : getBans()) {
            if (b.isBanActive()) b.setBanActive(false);
        }
        CorePlugin.getUserRegistry().register(this);
    }

    public void ban(Ban ban) {
        this.bans.add(ban);
        CorePlugin.getUserRegistry().register(this);
    }

    public void ban(Map<String, Object> banMap) {
        ban(new Ban(banMap));
    }

    public ImmutableList<Kick> getKicks() {
        return ImmutableList.copyOf(this.kicks);
    }

    public void clearKicks() {
        this.kicks.clear();
    }

    public void kick(Kick kick) {
        this.kicks.add(kick);
    }

    public void kick(Map<String, Object> kickMap) {
        kick(new Kick(kickMap));
    }

    public ImmutableList<Mute> getMutes() {
        return ImmutableList.copyOf(this.mutes);
    }

    public boolean isMuted() {
        for (Mute m : getMutes()) {
            if (m.isMuteActive()) return true;
        }

        return false;
    }

    public Mute getActiveMute() {
        for (Mute m : getMutes()) {
            if (m.isMuteActive()) return m;
        }
        return null;
    }

    public void unmute() {
        for (Mute m : getMutes()) {
            if (m.isMuteActive()) m.setMuteActive(false);
        }
        CorePlugin.getUserRegistry().register(this);
    }

    public void mute(Mute mute) {
        this.mutes.add(mute);
        CorePlugin.getUserRegistry().register(this);
    }

    public void mute(Map<String, Object> muteMap) {
        mute(new Mute(muteMap));
    }

    public ImmutableList<Warning> getWarnings() {
        return ImmutableList.copyOf(this.warnings);
    }

    public void clearWarnings() {
        this.warnings.clear();
    }

    public void warn(Warning warning) {
        this.warnings.add(warning);
    }

    public void warn(Map<String, Object> warningMap) {
        warn(new Warning(warningMap));
    }

    @Override
    public String getKey() {
        return uuid.toString();
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = Maps.newHashMap();
        map.put("tokens", this.tokens);

        map.put("ip-addresses", this.ipAddressList);
        map.put("played-servers", this.playedServersList);

        map.put("server-balances", this.serverBalances);

        map.put("display-name", this.displayName);
        map.put("join-date", this.joinDate);
        map.put("last-join-date", this.lastJoinDate);
        map.put("last-leave-date", this.lastLeaveDate);
        map.put("username", this.userName);

        List<Map<String, Object>> bans = Lists.newArrayList();
        for (Ban b : this.bans) {
            bans.add(b.serialize());
        }
        map.put("bans", bans);

        List<Map<String, Object>> kicks = Lists.newArrayList();
        for (Kick k : this.kicks) {
            kicks.add(k.serialize());
        }
        map.put("kicks", kicks);

        List<Map<String, Object>> mutes = Lists.newArrayList();
        for (Mute m : this.mutes) {
            mutes.add(m.serialize());
        }
        map.put("mutes", mutes);

        List<Map<String, Object>> warnings = Lists.newArrayList();
        for (Warning w : this.warnings) {
            warnings.add(w.serialize());
        }
        map.put("warnings", warnings);

        return map;
    }
}