package vip.bingzi.playerisworld.event

import io.izzel.taboolib.module.inject.TListener
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerLoginEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.event.player.PlayerTeleportEvent
import vip.bingzi.playerisworld.PlayerIsWorldPro
import vip.bingzi.playerisworld.util.PIWObject.delPreloadWorld
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
        val integral= when(val integral1: Any? = getIntegral(player)){
            null -> {
                logger.fine("未获取到玩家 ${player.name} 所属世界，准备进行分配")
                val worldName = getPreloadWorld()[0]
                setIntegral(player,worldName)
                logger.fine("世界分配完成，被分配分世界为：$worldName")
                delPreloadWorld(worldName)
                logger.fine("正在对预载世界进行补充")
                PlayerIsWorldPro.BuildWorld.buildWorldSync(1,true)
                worldName
            }
            is String -> integral1
            else -> integral1
        }.toString()
        logger.fine("Login -> 获取到的世界名称为：$integral")
        PlayerIsWorldPro.BuildWorld.loadWorldSync(integral)
    }

    // 玩家退出事件监听
    @EventHandler
    fun onPlayerQuit(playerQuitEvent: PlayerQuitEvent) {
        val player = playerQuitEvent.player
        val integral: String = getIntegral(player) as String
        logger.fine("Quit -> 获取到的世界名称为：$integral")
        PlayerIsWorldPro.BuildWorld.unloadWorld(integral)
    }

    // 玩家传送世界监听
    @EventHandler
    fun onPlayerTeleport(playerTeleportEvent: PlayerTeleportEvent) {

    }
}