package net.nighthawkempires.core.command;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import org.bukkit.command.Command;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class CommandManager {

    public Map<String, List<String>> commandPermissions;

    public CommandManager() {
        this.commandPermissions = Maps.newHashMap();
    }

    public void registerCommand(String command, List<String> permissions) {
        if (getCommandPermissions().containsKey(command)) {
            this.commandPermissions.get(command).addAll(permissions);
        } else {
            this.commandPermissions.put(command, permissions);
        }
    }

    public void registerCommands(String command, String[] permissions) {
        this.registerCommand(command, Arrays.asList(permissions));
    }

    public boolean isRegistered(String command) {
        return this.commandPermissions.containsKey(command);
    }

    public ImmutableMap<String, List<String>> getCommandPermissions() {
        return ImmutableMap.copyOf(this.commandPermissions);
    }
}
