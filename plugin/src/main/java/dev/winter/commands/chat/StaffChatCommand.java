package dev.winter.commands.chat;

import dev.winter.Winter;
import dev.winter.utils.Parse;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.HashSet;
import java.util.Set;

public class StaffChatCommand implements CommandExecutor, Listener {
    private final Set<Player> staffChatEnabled = new HashSet<>();

    public StaffChatCommand() {
        Winter.getInstance().getServer().getPluginManager().registerEvents(this, Winter.getInstance());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Parse.parse(Winter.getInstance().getManager().getLanguage().getString("CONSOLE-COMMAND", "")));
            return true;
        }

        Player player = (Player) sender;
        if (!player.hasPermission("winter.staffchat") && !player.hasPermission("winter.admin")) {
            player.sendMessage(Parse.parse(Winter.getInstance().getManager().getLanguage().getString("NO-PERMISSIONS", "")));
            return true;
        }

        if (args.length < 1) {
            toggleStaffChat(player);
            return true;
        }

        if (args[0].equalsIgnoreCase("toggle")) {
            toggleStaffChat(player);
            return true;
        }

        StringBuilder message = new StringBuilder();
        for (String arg : args) {
            message.append(arg).append(" ");
        }

        if (isStaffChatEnabled(player)) {
            final String originalFormat = Winter.getInstance().getManager().getLanguage().getString("STAFF-CHAT.FORMAT", "<dark_gray>[<aqua>StaffChat<dark_gray>] <white>⇢ <red>{player}<gray>: <white>{message}");
            String format = originalFormat.replace("{player}", player.getName())
                    .replace("{message}", message.toString());

            Winter.getInstance().getServer().getOnlinePlayers().stream()
                    .filter(p -> p.hasPermission("winter.staffchat") || p.hasPermission("winter.admin"))
                    .forEach(p -> p.sendMessage(Parse.parse(format)));
        } else {
            player.sendMessage(Parse.parse(Winter.getInstance().getManager().getLanguage().getString("STAFF-CHAT.USAGE", "")));
        }

        return true;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        if (isStaffChatEnabled(player)) {
            final String originalFormat = Winter.getInstance().getManager().getLanguage().getString("STAFF-CHAT.FORMAT", "<dark_gray>[<aqua>StaffChat<dark_gray>] <white>⇢ <red>{player}<gray>: <white>{message}");
            String format = originalFormat.replace("{player}", player.getName())
                    .replace("{message}", event.getMessage());

            event.setCancelled(true);
            Winter.getInstance().getServer().getOnlinePlayers().stream()
                    .filter(p -> p.hasPermission("winter.staffchat") || p.hasPermission("winter.admin"))
                    .forEach(p -> p.sendMessage(format));
        }
    }

    private boolean isStaffChatEnabled(Player player) {
        return staffChatEnabled.contains(player);
    }

    private void toggleStaffChat(Player player) {
        if (isStaffChatEnabled(player)) {
            staffChatEnabled.remove(player);
            player.sendMessage(Parse.parse(Winter.getInstance().getManager().getLanguage().getString("STAFF-CHAT.ENABLED", "")));
        } else {
            staffChatEnabled.add(player);
            player.sendMessage(Parse.parse(Winter.getInstance().getManager().getLanguage().getString("STAFF-CHAT.DISABLED", "")));
        }
    }
}