package snowsan0113.tag;

import org.bukkit.plugin.java.JavaPlugin;
import snowsan0113.tag.command.GameStartCommand;

public final class Tag extends JavaPlugin {

    @Override
    public void onEnable() {
        //cmd
        getCommand("tag_start").setExecutor(new GameStartCommand());

        getLogger().info("プラグインが有効になりました。");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
