package snowsan0113.tag;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import snowsan0113.tag.command.GameStartCommand;
import snowsan0113.tag.command.MissionCommand;
import snowsan0113.tag.command.TeamCommand;
import snowsan0113.tag.listener.PlayerDamageListener;
import snowsan0113.tag.manager.ScoreboardManager;

public final class Tag extends JavaPlugin {

    @Override
    public void onEnable() {
        //cmd
        getCommand("tag_start").setExecutor(new GameStartCommand());
        getCommand("tag_team").setExecutor(new TeamCommand());
        getCommand("tag_mission").setExecutor(new MissionCommand());

        //listener
        PluginManager plm = getServer().getPluginManager();
        plm.registerEvents(new PlayerDamageListener(), this);

        for (Player player : Bukkit.getOnlinePlayers()) {
            ScoreboardManager.getInstance(player.getUniqueId()).setScoreboard();
        }

        getLogger().info("プラグインが有効になりました。");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
