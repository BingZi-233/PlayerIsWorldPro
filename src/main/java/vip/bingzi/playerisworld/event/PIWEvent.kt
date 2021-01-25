package vip.bingzi.playerisworld.event

import io.izzel.taboolib.module.inject.TListener
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerLoginEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.event.player.PlayerTeleportEvent
import vip.bingzi.playerisworld.PlayerIsWorldPro
import vip.bingzi.playerisworld.util.PIWObject.getIntegral
import vip.bingzi.playerisworld.util.PIWObject.getPreloadWorld
import vip.bingzi.playerisworld.util.PIWObject.logger
import vip.bingzi.playerisworld.util.PIWObject.setIntegral

@TListener
object PIWEvent : Listener {
    // 玩家登录事件监听
    @EventHandler
    fun onPlayerLogin(playerLoginEvent: PlayerLoginEvent) {
        val player = playerLoginEvent.player
        // 玩家世界名字
        var integral = getIntegral(player)
        if (integral == null){
            val preloadWorld = getPreloadWorld()
            setIntegral(player,preloadWorld[0])
            integral = getIntegral(player)
        }
        if (integral is String) {
            logger.info("PlayerLoginEvent -> 获取到的世界名称为：$integral")
            PlayerIsWorldPro.BuildWorld.loadWorldSync(integral)
        }
    }

    // 玩家退出事件监听
    @EventHandler
    fun onPlayerQuit(playerQuitEvent: PlayerQuitEvent) {
        val player = playerQuitEvent.player
        val integral = getIntegral(player) as String
        logger.info("PlayerQuitEvent -> 获取到的世界名称为：$integral")
        PlayerIsWorldPro.BuildWorld.unloadWorld(integral)
    }

    // 玩家传送世界监听
    @EventHandler
    fun onPlayerTeleport(playerTeleportEvent: PlayerTeleportEvent) {

    }
}