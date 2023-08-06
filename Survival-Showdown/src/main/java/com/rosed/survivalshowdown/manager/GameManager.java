package com.rosed.survivalshowdown.manager;

import com.rosed.survivalshowdown.instance.Game;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@Getter
public class GameManager {

    private final ConfigManager configManager;

    private List<Game> gameList;

    public GameManager()   {

        configManager = InstanceManager.INSTANCE.getConfigManager();

        gameList = new ArrayList<>();

    }

    /**
     * get player's current game
     * @param player player to check game
     * @return game that the player is in
     */
    public Game getPlayerGame(Player player)   {

        for (Game game : gameList)   {
            if (game.getPlayerList().contains(player))   {
                return game;
            }
        }
        return null;

    }

}
