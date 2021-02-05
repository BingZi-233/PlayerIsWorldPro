package vip.bingzi.playerisworld.event

import io.izzel.taboolib.module.inject.TListener
import io.izzel.taboolib.module.locale.TLocale
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
        val integral = when (val integral: Any? = getIntegral(player, "WorldName")) {
            null -> {
                logger.fine("未获取到玩家 ${player.name} 所属世界，准备进行分配")
                val worldName = getPreloadWorld()[0]
                setIntegral(player, "WorldName", worldName)
                logger.fine("世界分配完成，被分配分世界为：$worldName")
                delPreloadWorld(worldName)
                logger.fine("正在对预载世界进行补充")
                PlayerIsWorldPro.BuildWorld.buildWorldSync(1, true)
                worldName
            }
            is String -> integral
            else -> integral
        }.toString()
//      如果玩家在常驻世界列表，则不需要立刻进行世界加载
        for (world in PlayerIsWorldPro.setting.getStringList("Settings.WorldList")) {
            if (player.world.name == world) {
                logger.fine("玩家${player.name}所在世界为：${player.world.name}，此世界属于常驻世界。无需进行立刻加载！")
                return
            }
        }
        logger.fine("Login -> 获取到的世界名称为：$integral")
        val loadWorldSync = PlayerIsWorldPro.BuildWorld.loadWorldSync(integral)
        logger.fine("[PIWEvent - onPlayerLogin]loadWorldSync -> 世界载入结果：$loadWorldSync")
        if (loadWorldSync as Boolean) {
            if (loadWorldSync) {
                playerLoginEvent.kickMessage = TLocale.asString("Event.Login.KICK_OTHER")
                playerLoginEvent.result = PlayerLoginEvent.Result.KICK_OTHER
            }
        }
    }

    // 玩家退出事件监听
    @EventHandler
    fun onPlayerQuit(playerQuitEvent: PlayerQuitEvent) {
        val player = playerQuitEvent.player
        val integral = getIntegral(player, "WorldName") as String
        logger.fine("Quit -> 获取到的世界名称为：$integral")
        PlayerIsWorldPro.BuildWorld.unloadWorld(integral)
    }

    // 玩家传送世界监听
    @EventHandler
    fun onPlayerTeleport(playerTeleportEvent: PlayerTeleportEvent) {
        val fromWorldName = playerTeleportEvent.from.world?.name
        val toWorldName = playerTeleportEvent.to?.world?.name
        if (fromWorldName == toWorldName) {
            return
        }
    }
}