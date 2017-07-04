package net.gaminghunter.pokebattle;

import lombok.Getter;
import lombok.Setter;
import net.gaminghunter.pokebattle.coins.CoinsManager;
import net.gaminghunter.pokebattle.coins.PokemonManager;
import net.gaminghunter.pokebattle.commands.SetupCommand;
import net.gaminghunter.pokebattle.commands.StartCommand;
import net.gaminghunter.pokebattle.config.LocationsConfig;
import net.gaminghunter.pokebattle.config.PokeConfig;
import net.gaminghunter.pokebattle.countdowns.gamestates.IngameCountdown;
import net.gaminghunter.pokebattle.countdowns.gamestates.LobbyCountdown;
import net.gaminghunter.pokebattle.countdowns.PokeSpawnCountdown;
import net.gaminghunter.pokebattle.countdowns.WaitingCountdown;
import net.gaminghunter.pokebattle.countdowns.gamestates.ShopCountdown;
import net.gaminghunter.pokebattle.enums.GameState;
import net.gaminghunter.pokebattle.enums.PokemonEntity;
import net.gaminghunter.pokebattle.inventories.ShopInventory;
import net.gaminghunter.pokebattle.listeners.*;
import net.gaminghunter.pokebattle.scoreboards.IngameBoard;
import net.gaminghunter.pokebattle.scoreboards.LobbyBoard;
import net.gaminghunter.pokebattle.teams.TeamInventory;
import net.gaminghunter.pokebattle.teams.TeamManager;
import net.gaminghunter.pokebattle.utils.InventoryPresets;
import net.gaminghunter.pokebattle.utils.LocationUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public final class Pokebattle extends JavaPlugin {

    @Getter
    private static Pokebattle instance;

    //<editor-fold desc="Class initialization">
    @Getter
    private PokeConfig pokeConfig = new PokeConfig();
    @Getter
    private LocationsConfig locationsConfig = new LocationsConfig();
    @Getter
    private LobbyBoard lobbyBoard = new LobbyBoard();
    @Getter
    private IngameBoard ingameBoard = new IngameBoard();
    @Getter @Setter
    private GameState gameState;
    @Getter
    private TeamManager teamManager = new TeamManager();
    @Getter
    private TeamInventory teamInventory = new TeamInventory();
    @Getter
    private InventoryPresets inventoryPresets = new InventoryPresets();
    @Getter
    private CoinsManager coinsManager = new CoinsManager();
    @Getter
    private PokemonManager pokemonManager = new PokemonManager();
    @Getter
    private ShopInventory shopInventory = new ShopInventory();
    //</editor-fold>

    //<editor-fold desc="Config Variables">
    @Getter @Setter
    private String prefix = "§4prefix = null §7| ";
    @Getter @Setter
    private int minPlayers = 404;
    @Getter @Setter
    private String setupPermission;
    @Getter @Setter
    private String startPermission;
    @Getter @Setter
    private Location spawnLocation;
    @Getter @Setter
    private Location shopLocation;
    @Getter @Setter
    private Location shopVillagerLocation;
    //</editor-fold>

    //<editor-fold desc="Countdowns">
    @Getter
    private LobbyCountdown lobbyCountdown = new LobbyCountdown();
    @Getter
    private WaitingCountdown waitingCountdown = new WaitingCountdown();
    @Getter
    private IngameCountdown ingameCountdown = new IngameCountdown();
    @Getter
    private ShopCountdown shopCountdown = new ShopCountdown();
    //</editor-fold>

    //<editor-fold desc="Sonstiges">
    @Getter
    private List<PokemonEntity> pokemons = new ArrayList<>();
    @Getter
    private Map<Player, PokeSpawnCountdown> spawnCountdowns = new HashMap<>();
    //</editor-fold>

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        //<editor-fold desc="Pokemon initialization">
        for(int i = 0; i< 10; i++) {
            pokemons.add(PokemonEntity.COW);
        }
        for(int i = 0; i< 9; i++) {
            pokemons.add(PokemonEntity.PIG);
        }
        for(int i = 0; i<8; i++) {
            pokemons.add(PokemonEntity.SHEEP);
        }
        for(int i = 0; i< 5; i++) {
            pokemons.add(PokemonEntity.CHICKEN);
        }
        for(int i = 0; i< 3; i++) {
            pokemons.add(PokemonEntity.RABBIT);
        }
        pokemons.add(PokemonEntity.BAT);

        Collections.shuffle(pokemons);
        //</editor-fold>

        shopInventory.initShop();

        Bukkit.getPluginManager().registerEvents(new PlayerListeners(), this);
        Bukkit.getPluginManager().registerEvents(new ItemListeners(), this);
        Bukkit.getPluginManager().registerEvents(new InventoryListeners(), this);
        Bukkit.getPluginManager().registerEvents(new EntityListeners(), this);
        Bukkit.getPluginManager().registerEvents(new WorldListeners(), this);
        getCommand("setup").setExecutor(new SetupCommand());
        getCommand("start").setExecutor(new StartCommand());

        teamManager.initTeams();
        teamInventory.updateInventory();
        setGameState(GameState.LOBBY);
        pokeConfig.loadDefaultConfig();
        locationsConfig.loadDefaultConfig();

        setPrefix(String.valueOf(pokeConfig.get("prefix")));
        setMinPlayers((int) pokeConfig.get("minPlayers"));
        setSetupPermission(String.valueOf(pokeConfig.get("setupPermission")));
        if(locationsConfig.get("spawn") != null) {
            setSpawnLocation(LocationUtils.deserializeFully(String.valueOf(locationsConfig.get("spawn"))));
        } else {
            setSpawnLocation(null);
            Bukkit.getConsoleSender().sendMessage("§c§lSpawn wurde nicht gesetzt");
        }
        if(locationsConfig.get("shop") != null) {
            setShopLocation(LocationUtils.deserializeFully(String.valueOf(locationsConfig.get("shop"))));
        } else {
            setShopLocation(null);
            Bukkit.getConsoleSender().sendMessage("§c§lSpieler Spawn am Shop wurde nicht gesetzt");
        }
        if(locationsConfig.get("shopVillager") != null) {
            setShopVillagerLocation(LocationUtils.deserializeFully(String.valueOf(locationsConfig.get("shopVillager"))));
        } else {
            setShopVillagerLocation(null);
            Bukkit.getConsoleSender().sendMessage("§c§lShopvillager wurde nicht gesetzt");
        }
        setStartPermission(String.valueOf(pokeConfig.get("startPermission")));

        Bukkit.getServer().getWorlds().forEach(world ->
            world.getEntities().forEach(entity -> {
                entity.remove();
            })
        );
    }

    @Override
    public void onDisable() {
        Bukkit.getOnlinePlayers().forEach(o ->
        o.kickPlayer("§cDer Server reloadet sich gerade"));
    }
}
