package vip.bingzi.playerisworld.event

import io.izzel.taboolib.module.inject.TListener
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerLoginEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.event.player.PlayerTeleportEvent
import vip.bingzi.playerisworld.PlayerIsWorldPro
import vip.bingzi.playerisworld.util.PIWObject.getIntegral

@TListener
object PIWEvent : Listener {
    // 玩家登录事件监听
    @EventHandler
    fun onPlayerLogin(playerLoginEvent: PlayerLoginEvent) {
        val player = playerLoginEvent.player
        // 玩家世界名字
        var integral: String = getIntegral(player) as String

        PlayerIsWorldPro.BuildWorld.loadWorldSync(integral)
    }

    // 玩家退出事件监听
    @EventHandler
    fun onPlayerQuit(playerQuitEvent: PlayerQuitEvent) {

    }

    // 玩家传送世界监听
    @EventHandler
    fun onPlayerTeleport(playerTeleportEvent: PlayerTeleportEvent) {

    }
}