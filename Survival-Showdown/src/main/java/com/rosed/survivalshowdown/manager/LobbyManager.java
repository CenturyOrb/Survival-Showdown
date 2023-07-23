package com.rosed.survivalshowdown.manager;

import com.rosed.survivalshowdown.instance.Lobby;
import lombok.Getter;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.List;

@Getter
public class LobbyManager {

    private final ConfigManager configManager;

    private List<Lobby> lobbyList;

    public LobbyManager()   {

        configManager = InstanceManager.INSTANCE.getConfigManager();

        lobbyList = new ArrayList<>();

        createLobbies();

    }

    /**
     * create lobby instances
     */
    private void createLobbies()   {

        for (int i = 0; i < configManager.getNumLobby(); i++)   {
            lobbyList.add(new Lobby(i));
        }

    }

    /**
     * get player's current lobby
     * @param player player to check lobby
     * @return lobby that the player is in
     */
    public Lobby getPlayerLobby(Player player)   {

        for (Lobby lobby : lobbyList)   {
            if (lobby.getPlayerList().contains(player))   {
                return lobby;
            }
        }

        return null;
    }

}
