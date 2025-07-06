package snowsan0113.tag.command;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.scoreboard.Team;
import snowsan0113.tag.manager.TeamManager;
import snowsan0113.tag.util.ChatUtil;

public class TeamCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (cmd.getName().equalsIgnoreCase("tag_team")) {
            TeamManager team = TeamManager.getInstance();
            Team oni = team.getTeam(TeamManager.GameTeam.ONI);
            Team run = team.getTeam(TeamManager.GameTeam.RUN);
            if (args[0].equalsIgnoreCase("list")) {
                StringBuilder builder = new StringBuilder();

                builder.append("---鬼チーム--- \n");
                oni.getEntries().stream().map(oni_player -> oni_player + ",").forEach(builder::append);
                builder.append("\n");
                builder.append("---逃走者チーム--- \n");
                run.getEntries().stream().map(run_player -> run_player + ",").forEach(builder::append);

                ChatUtil.sendMessage(sender,
                        "==============" + "\n" +
                        builder.toString() + "\n" +
                        "==============");
            }
            else if (args[0].equalsIgnoreCase("join")) {
                if (args[1].equalsIgnoreCase("oni")) {
                    oni.addEntry(args[2]);
                    ChatUtil.sendMessage(sender, "鬼チームに参加させました。");
                }
                else if (args[1].equalsIgnoreCase("run")) {
                    run.addEntry(args[2]);
                    ChatUtil.sendMessage(sender, "逃走者チームに参加させました。");
                }
            }
        }
        return false;
    }

}
