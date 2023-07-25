package com.rosed.survivalshowdown.command;

import com.rosed.survivalshowdown.instance.GameState;
import com.rosed.survivalshowdown.instance.Lobby;
import com.rosed.survivalshowdown.manager.InstanceManager;
import com.rosed.survivalshowdown.manager.LobbyManager;
import com.rosed.survivalshowdown.util.NumericUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class LobbyCommand implements CommandExecutor {

    private final LobbyManager lobbyManager;

    public LobbyCommand()   {

        lobbyManager = InstanceManager.INSTANCE.getLobbyManager();

    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player))    { return false; }
        Player player = (Player) sender;

        /*
        (1) /lobby : shows info about /lobby commands
        (2) /lobby list
        (3) /lobby join
        (4) /lobby leave
        (5) wrong command inputs
         */
        if (args.length == 0)   {

            player.sendMessage(ChatColor.RED + "Available lobby commands:");
            player.sendMessage(ChatColor.YELLOW + "/lobby list (Shows list of lobbies)");
            player.sendMessage(ChatColor.YELLOW + "/lobby join (Join a lobby)");
            player.sendMessage(ChatColor.YELLOW + "/lobby leave (Leave your current lobby)");

        } else if (args.length == 1)   {

            if (args[0].equals("list"))   {

                for (Lobby lobby : lobbyManager.getLobbyList())   {
                    int lobbyID = lobby.getLobbyID();
                    String gameState = lobby.getGame().getGameState().toString();

                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6" + lobbyID + " &c(&a" + gameState + "&c)"));
                }

            } else if (args[0].equals("leave")) {

                if (lobbyManager.getPlayerLobby(player) == null) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c(&l&6!&c) You are not in a lobby!"));
                } else {

                    int lobbyID = lobbyManager.getPlayerLobby(player).getLobbyID();

                    if (lobbyManager.getPlayerLobby(player).getGame().getGameState().equals(GameState.COUNTDOWN))   {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c(&l&6!&c) You can not leave during countdown!"));
                    } else {
                        lobbyManager.getLobbyList().get(lobbyManager.getPlayerLobby(player).getLobbyID()).removePlayerFromLobby(player);
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aYou have left &6lobby" + lobbyID));
                    }

                }

            } else {

                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c(&l&6!&c) Invalid input!"));

            }

        } else if (args.length == 2 && args[0].equals("join"))   {

            if (!NumericUtil.isNumeric(args[1]))   {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c(&l&6!&c) Invalid input!"));
                return false;
            }

            int id = Integer.parseInt(args[1]);

            Lobby lobby;
            if (id >= 0 && id < lobbyManager.getLobbyList().size())   {
                lobby = lobbyManager.getLobbyList().get(id);
            } else {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c(&l&6!&c) Invalid lobby!"));
                return false;
            }

            if (lobbyManager.getPlayerLobby(player) != null)   {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c(&l&6!&c) You are already in a lobby!"));
                return false;
            }

            if (lobby.getGame().getGameState() == GameState.RECRUITING)   {
                lobby.addPlayerToLobby(player);
                player.sendMessage(ChatColor.GREEN + "You are now playing in the lobby " + id);
            } else {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c(&l&6!&c) You can not join this lobby right now!"));
            }

        }

        return false;
    }
}
