package vip.bingzi.playerisworld.world

import org.bukkit.Bukkit
import org.bukkit.World
import org.bukkit.WorldCreator
import org.bukkit.scheduler.BukkitRunnable
import vip.bingzi.playerisworld.PlayerIsWorldPro
import vip.bingzi.playerisworld.util.PIWUtil

/**
 * @author BingZi-233
 */
class WorldBukkit : PIWWorld() {
    /**
     * 构建世界
     */
    override fun buildWorld(int: Int): ArrayList<String> {
        val preloadWorld = ArrayList<String>()
        for (i in 1..int) {
            val randomString: String = PlayerIsWorldPro.setting.getString("Settings.PreloadWorld.Prefix") + "_" +
                    PIWUtil.getRandomString(PlayerIsWorldPro.setting.getInt("Settings.PreloadWorld.NameLength"))
            Bukkit.createWorld(WorldCreator(randomString))
            Bukkit.unloadWorld(randomString, true)
            preloadWorld.add(randomString)
        }
        return preloadWorld

    }

    /**
     * 构建世界（异步）
     */
    override fun buildWorldSync(int: Int): ArrayList<String> {
        val preloadWorld = ArrayList<String>()
        object : BukkitRunnable() {
            override fun run() {
                object : BukkitRunnable() {
                    override fun run() {
                        preloadWorld.addAll(WorldBukkit().buildWorld(int))
                    }
                }.runTask(PlayerIsWorldPro.plugin)
            }
        }.runTaskAsynchronously(PlayerIsWorldPro.plugin)
        return preloadWorld
    }

    /**
     * 载入世界
     */
    override fun loadWorld(worldName: String) {
        Bukkit.createWorld(WorldCreator(worldName))
    }

    /**
     * 载入世界（异步）
     */
    override fun loadWorldSync(worldName: String) {
        object : BukkitRunnable() {
            override fun run() {
                object : BukkitRunnable() {
                    override fun run() {
                        Bukkit.createWorld(WorldCreator(worldName))
                    }
                }.runTask(PlayerIsWorldPro.plugin)
            }
        }.runTaskAsynchronously(PlayerIsWorldPro.plugin)
    }

    /**
     * 卸载世界
     */
    override fun unloadWorld(world: World) {
        object : BukkitRunnable() {
            override fun run() {
                Bukkit.unloadWorld(world, true)
            }
        }.runTask(PlayerIsWorldPro.plugin)
    }

    /**
     * 卸载世界（指定世界名）
     */
    override fun unloadWorld(worldName: String) {
        object : BukkitRunnable() {
            override fun run() {
                // 在已加载世界中寻找是否加载了。如果没有在已载入世界中找到，那么将不需要卸载！
                for (world in Bukkit.getWorlds()) {
                    // 如果寻找到
                    if (world.name == worldName) {
                        // 卸载世界
                        Bukkit.unloadWorld(worldName, true)
                    }
                }
            }
        }.runTask(PlayerIsWorldPro.plugin)
    }
}