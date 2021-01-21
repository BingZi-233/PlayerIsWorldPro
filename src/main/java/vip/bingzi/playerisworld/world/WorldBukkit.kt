package vip.bingzi.playerisworld.world

import org.bukkit.Bukkit
import org.bukkit.World
import org.bukkit.WorldCreator
import org.bukkit.scheduler.BukkitRunnable
import vip.bingzi.playerisworld.PlayerIsWorldPro
import vip.bingzi.playerisworld.util.PIWUtil

class WorldBukkit : PIWWorld() {
    /**
     * 构建世界
     */
    override fun buildWorld(int: Int): ArrayList<String> {
        var PreloadWorld = ArrayList<String>()
        for (i in 1..int) {
            var randomString: String = PlayerIsWorldPro.setting.getString("Settings.PreloadWorld.Prefix") + "_" +
                    PIWUtil.getRandomString(PlayerIsWorldPro.setting.getInt("Settings.PreloadWorld.NameLength"))
            Bukkit.createWorld(WorldCreator(randomString))
            PreloadWorld.add(randomString)
        }
        return PreloadWorld

    }

    /**
     * 构建世界（异步）
     */
    override fun buildWorldSync(int: Int): ArrayList<String> {
        var ProloadWorld = ArrayList<String>()
        object : BukkitRunnable() {
            override fun run() {
                object : BukkitRunnable() {
                    override fun run() {
                        ProloadWorld.addAll(buildWorld(int))
                    }
                }.runTask(PlayerIsWorldPro.plugin)
            }
        }.runTaskAsynchronously(PlayerIsWorldPro.plugin)
        return ProloadWorld
    }

    /**
     * 卸载世界
     */
    override fun unloadWorld(world: World) {
        Bukkit.unloadWorld(world, true)
    }

    /**
     * 卸载世界（指定世界名）
     */
    override fun unloadWorld(worldName: String) {
        // 在已加载世界中寻找是否加载了。如果没有在已载入世界中找到，那么将不需要卸载！
        for (world in Bukkit.getWorlds()) {
            // 如果寻找到
            if (world.name == worldName) {
                // 卸载世界
                Bukkit.unloadWorld(worldName, true)
            }
        }
    }
}