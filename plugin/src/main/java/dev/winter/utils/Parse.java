package dev.winter.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class Parse {
    public static final MiniMessage miniMessage = MiniMessage.miniMessage();

    public static Component parse(String message) {
        return miniMessage.deserialize(message);
    }
}