package com.jawollo07.MouseMadness;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.util.Random;

public class MouseMadness extends JavaPlugin {

    private final Random random = new Random();

    @Override
    public void onEnable() {
        // Lade die Konfiguration
        saveDefaultConfig();
    }

    @Override
    public void onDisable() {
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("mousemadness")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;

                // Rotiert die Kamera des Spielers zufällig
                randomizePlayerCamera(player);

                // Nachricht aus der config.yml
                String message = getConfig().getString("messages.camera-rotated", "Deine Kamera wurde zufällig verdreht!");
                player.sendMessage(message);
                return true;
            } else {
                String consoleMessage = getConfig().getString("messages.only-players", "Dieser Befehl kann nur von einem Spieler ausgeführt werden.");
                sender.sendMessage(consoleMessage);
                return true;
            }
        }
        return false;
    }

    private void randomizePlayerCamera(Player player) {
        // Zufällige Werte für Yaw (-180 bis 180) und Pitch (-90 bis 90)
        float randomYaw = -180 + random.nextFloat() * 360;  // Bereich: -180 bis 180 Grad
        float randomPitch = -90 + random.nextFloat() * 180; // Bereich: -90 bis 90 Grad

        // Berechne die neue Blickrichtung basierend auf Yaw und Pitch
        Vector direction = getDirection(randomYaw, randomPitch);

        // Setze die neue Blickrichtung
        player.teleport(player.getLocation().setDirection(direction));
    }

    private Vector getDirection(float yaw, float pitch) {
        // Konvertiere Yaw und Pitch in Radian
        double radYaw = Math.toRadians(yaw);
        double radPitch = Math.toRadians(pitch);

        // Berechne die Blickrichtung (Vector)
        double x = -Math.cos(radPitch) * Math.sin(radYaw);
        double y = -Math.sin(radPitch);
        double z = Math.cos(radPitch) * Math.cos(radYaw);

        return new Vector(x, y, z);
    }
}
