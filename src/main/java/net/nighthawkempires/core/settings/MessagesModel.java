package net.nighthawkempires.core.settings;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.nighthawkempires.core.CorePlugin;
import net.nighthawkempires.core.datasection.DataSection;
import net.nighthawkempires.core.lang.ServerMessage;
import net.nighthawkempires.core.server.ServerType;
import org.bukkit.ChatColor;

import java.util.HashMap;
import java.util.Map;

public class MessagesModel extends SettingsModel {

    private String key;
    private HashMap<ServerMessage, String> messages;
    private HashMap<ServerType, String> serverTags;

    public MessagesModel() {
        this.key = "messages";

        this.messages = Maps.newHashMap();
        this.serverTags = Maps.newHashMap();
    }

    public MessagesModel(String key, DataSection data) {
        this.key = key;

        DataSection messagesSection = data.getSectionNullable("messages");
        this.messages = Maps.newHashMap();
        for (String s : messagesSection.keySet()) {
            if (CorePlugin.getMessagesManager().isRegistered(s))
                this.messages.put(CorePlugin.getMessagesManager().valueOf(s), messagesSection.getString(s));
        }

        DataSection serverTagsSection = data.getSectionNullable("server-tags");
        this.serverTags = Maps.newHashMap();
        for (String s : serverTagsSection.keySet()) {
            this.serverTags.put(ServerType.valueOf(s), serverTagsSection.getString(s));
        }
    }

    public ImmutableMap<ServerMessage, String> getMessagesMap() {
        return ImmutableMap.copyOf(this.messages);
    }

    public String getMessage(ServerMessage message) {
        if (!getMessagesMap().containsKey(message)) {
            this.messages.put(message, "Please configure the message \'" + message.toString() + "\' in messages.json");
            CorePlugin.getSettingsRegistry().register(this);
        }

        return ChatColor.translateAlternateColorCodes('&', getMessagesMap().get(message));
    }

    public ImmutableMap<ServerType, String> getServerTagsMap() {
        return ImmutableMap.copyOf(this.serverTags);
    }

    public String getServerTag(ServerType serverType) {
        if (!getServerTagsMap().containsKey(serverType)) {
            this.serverTags.put(serverType, serverType.name());
            CorePlugin.getSettingsRegistry().register(this);
        }
        return ChatColor.translateAlternateColorCodes('&', getServerTagsMap().get(serverType));
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = Maps.newHashMap();

        Map<String, String> messagesStringMap = Maps.newHashMap();
        for (ServerMessage m : this.messages.keySet())
            messagesStringMap.put(m.toString(), getMessage(m));
        map.put("messages", messagesStringMap);

        Map<String, String> serverTagsStringMap = Maps.newHashMap();
        for (ServerType st : this.serverTags.keySet())
            serverTagsStringMap.put(st.name(), getServerTag(st));
        map.put("server-tags", serverTagsStringMap);

        return map;
    }
}
