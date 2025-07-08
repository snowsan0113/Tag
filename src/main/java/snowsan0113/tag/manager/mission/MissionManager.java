package snowsan0113.tag.manager.mission;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import snowsan0113.tag.util.ChatUtil;

import java.util.ArrayList;
import java.util.List;

public class MissionManager {

    private static final MissionManager instance = new MissionManager();
    private final List<GameMission> mission_list;

    private MissionManager() {
        this.mission_list = new ArrayList<>();
    }

    public static MissionManager getInstance() {
        return instance;
    }

    public GameMission addCustomMission(String message) {
        GameMission mission = new GameMission(this, GameMissionType.CUSTOM, message);
        mission_list.add(mission);

        //ミッション処理
        Bukkit.getOnlinePlayers().forEach(online -> online.playSound(online.getLocation(), Sound.ENTITY_ENDER_DRAGON_AMBIENT, 50, 1));
        ChatUtil.sendGlobalMessage("==============" + "\n" +
                "ミッションが発動されました。" + "\n" +
                message + "\n" +
                "==============");

        return mission;
    }

    public enum GameMissionType {
        CUSTOM
    }

    public static class GameMission implements Mission {
        private boolean valid_mission;
        private final GameMissionType type;
        private final MissionManager manager;
        private final String message;

        public GameMission(MissionManager manager, GameMissionType type, String message) {
            this.manager = manager;
            this.type = type;
            this.message = message;
            this.valid_mission = true;
        }

        public void stopMission(MissionResult result, String message) {
            manager.mission_list.remove(this);

            if (result == MissionResult.SUCCES) {
                ChatUtil.sendGlobalMessage("=========" + "\n" +
                        "ミッション成功!" + "\n" +
                        message + "\n" +
                        "=========");
            }
        }

        public String getMessage() {
            return message;
        }

        public GameMissionType getType() {
            return type;
        }
    }

    public enum MissionResult {
        SUCCES,
        FALT,
        CANSEL,
        UNKNWON
    }
}
