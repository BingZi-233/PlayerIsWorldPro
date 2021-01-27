package vip.bingzi.playerisworld.world

import com.grinderwolf.swm.api.exceptions.*
import org.bukkit.Bukkit
import org.bukkit.World
import org.bukkit.scheduler.BukkitRunnable
import vip.bingzi.playerisworld.PlayerIsWorldPro
import vip.bingzi.playerisworld.PlayerIsWorldPro.BuildModel
import vip.bingzi.playerisworld.PlayerIsWorldPro.SlimeLoader
import vip.bingzi.playerisworld.util.PIWObject
import vip.bingzi.playerisworld.util.PIWObject.logger
import vip.bingzi.playerisworld.util.PIWUtil
import java.io.IOException

class WorldSlimeWorldManager : PIWWorld() {
    // SlimeWorldManager的世界生成对象
    override fun buildWorld(int: Int, saveToFile: Boolean): ArrayList<String> {
        val preloadWorld = ArrayList<String>()
        // 是否会自动载入
        val readOnly = PlayerIsWorldPro.setting.getBoolean("Settings.PreloadWorld.WorldSetting.readOnly")
        for (i in 1..int) {
            val randomString: String = PlayerIsWorldPro.setting.getString("Settings.PreloadWorld.Prefix") + "_" +
                    PIWUtil.getRandomString(PlayerIsWorldPro.setting.getInt("Settings.PreloadWorld.NameLength"))
            try {
                PlayerIsWorldPro.SlimeWorldManager?.createEmptyWorld(SlimeLoader, randomString, readOnly, BuildModel)
            } catch (e: Exception) {
                when (e) {
                    is WorldAlreadyExistsException -> {
                        logger.fine("发现异常！原因：世界已经存在。")
                    }
                    is IOException -> {
                        logger.fine("发现异常！原因：IO异常。")
                    }
                }
            }
            preloadWorld.add(randomString)
        }
        if (saveToFile){
            logger.fine("[SlimeWorldManager - buildWorld]Build -> 写入预载列表如下数据：$preloadWorld")
            PIWObject.addPreloadWorld(preloadWorld)
        }
        logger.fine("[SlimeWorldManager - buildWorld]Build -> 返回值：$preloadWorld")
        return preloadWorld
    }

    override fun buildWorldSync(int: Int, saveToFile: Boolean): ArrayList<String> {
        val preloadWorld = ArrayList<String>()
        object : BukkitRunnable() {
            override fun run() {
                object : BukkitRunnable() {
                    override fun run() {
                        preloadWorld.addAll(WorldSlimeWorldManager().buildWorld(int,saveToFile))
                        logger.fine("[SlimeWorldManager - buildWorldSync]Build(Sync) -> 获得到的列表为：$preloadWorld")
                    }
                }.runTask(PlayerIsWorldPro.plugin)
            }
        }.runTaskAsynchronously(PlayerIsWorldPro.plugin)
        logger.fine("[SlimeWorldManager - buildWorldSync]Build(Sync) -> 返回值：$preloadWorld")
        return preloadWorld
    }

    override fun loadWorld(worldName: String) {
        try {
            val builderWorld = PlayerIsWorldPro.SlimeWorldManager?.loadWorld(SlimeLoader,
                worldName,
                false,
                BuildModel)
            PlayerIsWorldPro.SlimeWorldManager?.generateWorld(builderWorld)
        } catch (e: Exception) {
            when (e) {
                is UnknownWorldException -> {
                    logger.fine("发现异常！原因：未知世界。")
                }
                is IOException -> {
                    logger.fine("发现异常！原因：IO异常。")
                }
                is CorruptedWorldException -> {
                    logger.fine("发现异常！原因：腐败的世界。")
                }
                is NewerFormatException -> {
                    logger.fine("发现异常！原因：过于新的格式。")
                }
                is WorldInUseException -> {
                    logger.fine("发现异常！原因：世界正在使用。")
                }
            }
        }
    }

    override fun loadWorldSync(worldName: String) {
        object : BukkitRunnable() {
            override fun run() {
                object : BukkitRunnable() {
                    override fun run() {
                        try {
                            val builderWorld = PlayerIsWorldPro.SlimeWorldManager?.loadWorld(SlimeLoader,
                                worldName,
                                false,
                                BuildModel)!!
                            PlayerIsWorldPro.SlimeWorldManager.generateWorld(builderWorld)
                        } catch (e: Exception) {
                            when (e) {
                                is UnknownWorldException -> {
                                    logger.fine("发现异常！原因：未知世界。")
                                }
                                is IOException -> {
                                    logger.fine("发现异常！原因：IO异常。")
                                }
                                is CorruptedWorldException -> {
                                    logger.fine("发现异常！原因：腐败的世界。")
                                }
                                is NewerFormatException -> {
                                    logger.fine("发现异常！原因：过于新的格式。")
                                }
                                is WorldInUseException -> {
                                    logger.fine("发现异常！原因：世界正在使用。")
                                }
                            }
                        }
                    }
                }.runTask(PlayerIsWorldPro.plugin)
            }
        }.runTaskAsynchronously(PlayerIsWorldPro.plugin)
    }

    override fun unloadWorld(world: World) {
        object : BukkitRunnable() {
            override fun run() {
                Bukkit.unloadWorld(world, true)
            }
        }.runTask(PlayerIsWorldPro.plugin)
    }

    override fun unloadWorld(worldName: String) {
        object : BukkitRunnable() {
            override fun run() {
                Bukkit.unloadWorld(worldName, true)
            }
        }.runTask(PlayerIsWorldPro.plugin)
    }
}