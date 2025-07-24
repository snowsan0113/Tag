package snowsan0113.tag.listener;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.scheduler.BukkitRunnable;
import snowsan0113.tag.Tag;
import snowsan0113.tag.manager.GameManager;
import snowsan0113.tag.manager.TeamManager;
import snowsan0113.tag.util.ChatUtil;

public class PlayerDamageListener implements Listener {

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        GameManager game = GameManager.getInstance();
        TeamManager team = TeamManager.getInstance();
        Entity damage = event.getEntity(); //ダメージを受けた人
        Entity damager = event.getDamager(); //ダメージを与えた人
        if (game.getStatus() == GameManager.GameStatus.RUNNING) {
            if (damage instanceof Player damage_player && damager instanceof Player damager_player) {
                if (team.getJoinGameTeam(damage_player) == TeamManager.GameTeam.RUN
                        && team.getJoinGameTeam(damager_player) == TeamManager.GameTeam.ONI) { //受けた人は逃走者、与えた人は鬼だったら
                    event.setDamage(0);
                    team.leaveTeam(damage_player);
                    team.joinTeam(TeamManager.GameTeam.PRISON, damage_player);

                    ChatUtil.sendMessage(damage_player, "あなたは捕まってしまいました。3秒後に牢屋に飛ばされます。");
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            ChatUtil.sendMessage(damage_player, "あなたは牢獄に飛ばされました。");
                        }
                    }.runTaskLater(Tag.getPlugin(Tag.class), 20*3L);
                }
                else {
                    event.setDamage(0);
                    event.setCancelled(true);
                }
            }
        }
    }
    
}
