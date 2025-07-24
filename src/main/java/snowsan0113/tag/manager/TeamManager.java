package snowsan0113.tag.manager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.checkerframework.checker.nullness.qual.NonNull;

import javax.xml.validation.Validator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * チームを管理するクラス
 */
public class TeamManager {

    private static final TeamManager instance = new TeamManager();

    private final Scoreboard new_scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
    private final Map<GameTeam, Team> team_map = new HashMap<>();

    private TeamManager() {
        //逃走者
        Team run = new_scoreboard.registerNewTeam("run");
        run.setColor(GameTeam.RUN.getColor());
        run.setPrefix(GameTeam.RUN.getColor() + "");
        team_map.put(GameTeam.RUN, run);

        //鬼
        Team oni = new_scoreboard.registerNewTeam("oni");
        oni.setColor(GameTeam.ONI.getColor());
        oni.setPrefix(GameTeam.ONI.getColor() + "");
        team_map.put(GameTeam.ONI, oni);

        //牢獄
        Team prison = new_scoreboard.registerNewTeam("prison");
        prison.setColor(GameTeam.PRISON.getColor());
        prison.setPrefix(GameTeam.PRISON.getColor() + "");
        team_map.put(GameTeam.PRISON, prison);
    }

    public static TeamManager getInstance() {
        return instance;
    }

    public boolean joinTeam(@NonNull GameTeam team, @NonNull Player player) {
        if (!isJoinTeam(player)) {
            Team board_team = getConvertBoardTeam(team);
            board_team.addEntry(player.getName());
            player.setPlayerListName(team.getColor() + player.getName());
            return true;
        }
        return false;
    }

    public boolean leaveTeam(@NonNull OfflinePlayer player) {
        if (isJoinTeam(player)) {
            Team board_team = getJoinBoardTeam(player);
            board_team.removeEntry(player.getName());
            if (player.isOnline()) {
                player.getPlayer().setPlayerListName(ChatColor.RESET + player.getName());
            }
            return true;
        }
        return false;
    }

    public boolean isJoinTeam(@NonNull OfflinePlayer player) {
        for (Map.Entry<GameTeam, Team> entry : team_map.entrySet()) {
            GameTeam game_team = entry.getKey();
            Team board_team = entry.getValue();
            if (board_team.getEntries().contains(player.getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * ゲームチームから、スコアボードのチームを取得するもの
     * @param team 取得したいチーム
     */
    public Team getConvertBoardTeam(GameTeam team) {
        return team_map.get(team);
    }

    /**
     * スコアボードのチームから、ゲームチームを取得するもの
     * @param team 取得したいチーム
     */
    public GameTeam getConvertGameTeam(Team team) {
        for (Map.Entry<GameTeam, Team> entry : team_map.entrySet()) {
            GameTeam game_team = entry.getKey();
            Team board_team = entry.getValue();
            if (board_team.getName().equalsIgnoreCase(team.getName())) {
                return game_team;
            }
        }
        return null;
    }

    /**
     * プレイヤーがどのチームに所属しているかを返す
     * @return スコアボードのチームを返す。見つからない場合はnull
     */
    public Team getJoinBoardTeam(OfflinePlayer player) {
        for (Map.Entry<GameTeam, Team> entry : team_map.entrySet()) {
            GameTeam game_team = entry.getKey();
            Team board_team = entry.getValue();

            Set<String> team_player_set = board_team.getEntries();
            for (String team_player_name : team_player_set) {
                OfflinePlayer team_player = Bukkit.getOfflinePlayer(team_player_name);
                if (player.equals(team_player)) {
                    return board_team;
                }
            }
        }
        return null;
    }

    /**
     * プレイヤーがどのチームに所属しているかを返す
     * @return ゲームチームを返す。見つからない場合はnull
     */
    public GameTeam getJoinGameTeam(OfflinePlayer player) {
        Team board_team = getJoinBoardTeam(player);
        if (board_team != null) {
            return getConvertGameTeam(board_team);
        }

        return null;
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
