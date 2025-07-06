package snowsan0113.tag.manager;

import org.bukkit.OfflinePlayer;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import snowsan0113.tag.Tag;
import snowsan0113.tag.util.ChatUtil;

import java.util.ArrayList;
import java.util.List;

public class GameManager {

    private static final GameManager instance = new GameManager();

    private BukkitTask task;
    private int game_time;
    private int count_time;
    private GameStatus status;

    private GameManager() {
        this.count_time = 10;
        this.game_time = 60*15;
        this.status = GameStatus.WAIITNG;
    }

    public static GameManager getInstance() {
        return instance;
    }

    public int startGame() {
        if (task == null) {
            this.task = new BukkitRunnable() {
                @Override
                public void run() {
                    if (status == GameStatus.WAIITNG || status == GameStatus.CONNTING) {
                        status = GameStatus.CONNTING;
                        if (count_time == 0) {
                            ChatUtil.sendGlobalMessage("ゲーム開始!");
                            status = GameStatus.RUNNING;
                        }
                        else {
                            String format = String.format("ゲーム開始まであと%d秒", count_time);
                            ChatUtil.sendGlobalMessage(format);
                            count_time--;
                        }
                    }
                    else if (status == GameStatus.RUNNING) {

                        if (game_time == 0) {
                            this.cancel();
                        }

                        game_time--;
                    }
                }
            }.runTaskTimer(Tag.getPlugin(Tag.class), 0L, 20L);

            return 0;
        }
        else {
            return -1;
        }
    }

    public void resetGame() {

    }

    public enum GameStatus {
        WAIITNG(0, "待機中"),
        CONNTING(1, "カウント中"),
        RUNNING(2, "実行中"),
        ENDING(3, "終了");

        private final int status;
        private final String string_status;

        GameStatus(int status, String string_status) {
            this.status = status;
            this.string_status = string_status;
        }

        public int getStatus() {
            return status;
        }

        public String getStringStatus() {
            return string_status;
        }
    }

}
