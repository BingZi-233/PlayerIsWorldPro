package vip.bingzi.playerisworld.world

import org.bukkit.World
import org.bukkit.scheduler.BukkitRunnable
import vip.bingzi.playerisworld.PlayerIsWorldPro
import vip.bingzi.playerisworld.util.PIWUtil

class WorldSlimeWorldManager : PIWWorld() {
    // SlimeWorldManager插件对象
    private val slimeWorldManager = PlayerIsWorldPro.SlimeWorldManager

    // SlimeWorldManager的储存对象
    private val slimeLoader = PlayerIsWorldPro.SlimeLoader
    private val buildModel = PlayerIsWorldPro.BuildModel

    // SlimeWorldManager的世界生成对象
    override fun buildWorld(int: Int): ArrayList<String> {
        val preloadWorld = ArrayList<String>()
        val readOnly = PlayerIsWorldPro.setting.getBoolean("Settings.PreloadWorld.WorldSetting.readOnly")
        for (i in 1..int) {
            val randomString: String = PlayerIsWorldPro.setting.getString("Settings.PreloadWorld.Prefix") + "_" +
                    PIWUtil.getRandomString(PlayerIsWorldPro.setting.getInt("Settings.PreloadWorld.NameLength"))
            slimeWorldManager?.loadWorld(slimeLoader, randomString, readOnly, buildModel)
            preloadWorld.add(randomString)
        }
        return preloadWorld
    }

    override fun buildWorldSync(int: Int): ArrayList<String> {
        val preloadWorld = ArrayList<String>()
        object : BukkitRunnable() {
            override fun run() {
                object : BukkitRunnable() {
                    override fun run() {
                        preloadWorld.addAll(buildWorld(int))
                    }
                }.runTask(PlayerIsWorldPro.plugin)
            }
        }.runTaskAsynchronously(PlayerIsWorldPro.plugin)
        return preloadWorld
    }

    override fun unloadWorld(world: World) {

        TODO("Not yet implemented")
    }

    override fun unloadWorld(worldName: String) {
        TODO("Not yet implemented")
    }
}