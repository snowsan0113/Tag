package snowsan0113.tag.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import snowsan0113.tag.manager.mission.MissionManager;

public class MissionCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender send, Command cmd, String s, String[] args) {
        if (cmd.getName().equalsIgnoreCase("tag_mission")) {
            MissionManager mission = MissionManager.getInstance();
            if (args[0].equalsIgnoreCase("custom")) {
                mission.addCustomMission(args[1]);
            }
        }
        return false;
    }

}
