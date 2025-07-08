package snowsan0113.tag.manager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * チームを管理するクラス
 */
public class TeamManager {

    private static final TeamManager instance = new TeamManager();

    private final Scoreboard new_scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
    private final Map<GameTeam, Team> team_map = new HashMap<>();

    private TeamManager() {
        Team run = new_scoreboard.registerNewTeam("run");
        run.setColor(GameTeam.RUN.getColor());
        team_map.put(GameTeam.RUN, run);
        Team oni = new_scoreboard.registerNewTeam("oni");
        oni.setColor(GameTeam.ONI.getColor());
        team_map.put(GameTeam.ONI, oni);
        Team prison = new_scoreboard.registerNewTeam("prison");
        prison.setColor(GameTeam.PRISON.getColor());
        team_map.put(GameTeam.PRISON, prison);
    }

    public static TeamManager getInstance() {
        return instance;
    }

    /**
     * ゲームチームから、スコアボードのチームを取得するもの
     * @param team 取得したいチーム
     */
    public Team getTeam(GameTeam team) {
        return team_map.get(team);
    }

    public Scoreboard getScoreboard() {
        return new_scoreboard;
    }

    public enum GameTeam {
        ONI(ChatColor.RED, "鬼"),
        RUN(ChatColor.WHITE, "逃走者"),
        PRISON(ChatColor.GRAY, "牢獄");

        private final ChatColor color;
        private final String team_string;

        GameTeam(ChatColor color, String team_string) {
            this.color = color;
            this.team_string = team_string;
        }

        public ChatColor getColor() {
            return color;
        }

        public String getTeamString() {
            return team_string;
        }
    }
}
