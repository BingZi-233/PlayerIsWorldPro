package vip.bingzi.playerisworld

import com.grinderwolf.swm.api.SlimePlugin
import com.grinderwolf.swm.api.loaders.SlimeLoader
import com.grinderwolf.swm.api.world.properties.SlimePropertyMap
import io.izzel.taboolib.loader.Plugin
import io.izzel.taboolib.module.config.TConfig
import io.izzel.taboolib.module.inject.TInject
import io.izzel.taboolib.module.locale.TLocale
import io.izzel.taboolib.module.locale.logger.TLogger
import org.bukkit.Bukkit
import vip.bingzi.playerisworld.database.DatabaseLocale
import vip.bingzi.playerisworld.database.DatabaseMongoDB
import vip.bingzi.playerisworld.util.PIWObject
import vip.bingzi.playerisworld.util.PIWObject.database
import vip.bingzi.playerisworld.util.PIWObject.logger
import vip.bingzi.playerisworld.world.PIWWorld
import vip.bingzi.playerisworld.world.WorldBukkit
import vip.bingzi.playerisworld.world.WorldSlimeWorldManager

object PlayerIsWorldPro : Plugin() {
    // 配置文件释放
    @TInject(value = ["setting.yml"], locale = "LOCALE-PRIORITY")
    lateinit var setting: TConfig
        private set

    @TInject(value = ["Data.yml"], locale = "LOCALE-PRIORITY")
    lateinit var data: TConfig
        private set

    // 创建SlimeWorldManager对象，方便后续进行调用
    val SlimeWorldManager = Bukkit.getPluginManager().getPlugin("SlimeWorldManager") as SlimePlugin?

    // 创建SlimeLoader对象,只有在检测到SlimeWorldManager插件运行的时候这个值才会被初始化
    lateinit var SlimeLoader: SlimeLoader

    // 这个值在没有SlimeWorldManager的时候是不会被初始化的。
    lateinit var BuildModel: SlimePropertyMap

    // 世界载入方式
    lateinit var BuildWorld: PIWWorld
    override fun onLoad() {
    }

    override fun onEnable() {
        // 设置日志输出等级
        logger.level = when (setting.getString("Settings.Logger")) {
            "VERBOSE" -> TLogger.VERBOSE
            "FINEST" -> TLogger.FINEST
            "FINE" -> TLogger.FINE
            "INFO" -> TLogger.INFO
            "WARN" -> TLogger.WARN
            "ERROR" -> TLogger.ERROR
            "FATAL" -> TLogger.FATAL
            else -> TLogger.INFO
        }
        logger.info(TLocale.asString("Enable.Start"))
        logger.info(TLocale.asString("Enable.LoggerInfo").format(logger.level))
        // 输出系统语言环境
        logger.info(TLocale.asString("Enable.Language"))
        // 输出数据库使用类型
        logger.info(TLocale.asString("Enable.Database").format(setting.getString("Database.Type")))
        // 引导本地数据库使用类型，避免出现大量IF语句
        database = when (setting.getString("Database.Type")) {
            "LOCAL" -> DatabaseLocale()
            "ONLINE" -> DatabaseMongoDB()
            else -> DatabaseLocale()
        }
        // 检测SlimeWorldManager插件是否存在（即被载入）
        if (SlimeWorldManager != null) {
            var info = true
            logger.info(TLocale.asString("Enable.SlimeWorldManager"))
            logger.info(
                TLocale.asString("Enable.SlimeWorldManagerDatabase")
                    .format(setting.getString("Settings.SlimeWorldManager.Database.Type"))
            )
            val set = when (setting.getString("Settings.SlimeWorldManager.Database.Type")) {
                "LOCALE" -> "file"
                "ONLINE" -> "mongodb"
                "MYSQL" -> "mysql"
                else -> "sqlite"
            }
            val setBack = arrayListOf("file", "mysql", "mongodb")
            try {
                SlimeLoader = SlimeWorldManager.getLoader(set)
            } catch (e: Exception) {
                logger.warn(TLocale.asString("Enable.SlimeWorldManagerDatabaseError").format(set))
                // 移除当前已经尝试的方法
                setBack.remove(set)
                // 悲观情况，大概率其他两种也无法正常运行
                info = false
                // 尝试使用另外两种方法进行操作
                for (back in setBack) {
                    try {
                        logger.warn(TLocale.asString("Enable.SlimeWorldManagerTryDatabase").format(back))
                        // 尝试进行注册
                        SlimeLoader = SlimeWorldManager.getLoader(back)
                    } catch (e: Exception) {
                        // 跳过下面代码
                        logger.warn(TLocale.asString("Enable.SlimeWorldManagerNo"))
                        continue
                    }
                    // 如果发现有执行成功的模式，则将状态调整为True
                    logger.info(TLocale.asString("Enable.SlimeWorldManagerYes"))
                    info = true
                    // 避免过多的尝试
                    break
                }
            }
            if (info) {
                logger.info(TLocale.asString("Enable.SlimeWorldManagerBuildWorld"))
                logger.fine("正在对BuildModel进行初始化操作")
                // 对世界模型进行初始化
                BuildModel = PIWObject.getSlimeBuildModer(
                    setting.getBoolean("Settings.PreloadWorld.WorldSetting.allowMonsters"),
                    setting.getBoolean("Settings.PreloadWorld.WorldSetting.allowAnimals"),
                    setting.getString("Settings.PreloadWorld.WorldSetting.difficulty"),
                    setting.getString("Settings.PreloadWorld.WorldSetting.environment"),
                    setting.getBoolean("Settings.PreloadWorld.WorldSetting.pvp"),
                    setting.getString("Settings.PreloadWorld.WorldSetting.worldType"),
                    setting.getInt("Settings.PreloadWorld.WorldSetting.Spawn.x"),
                    setting.getInt("Settings.PreloadWorld.WorldSetting.Spawn.y"),
                    setting.getInt("Settings.PreloadWorld.WorldSetting.Spawn.z")
                )
                logger.info(TLocale.asString("Enable.SlimeWorldManagerBuildModerInfo"))
                BuildWorld = WorldSlimeWorldManager()
            } else {
                logger.warn(TLocale.asString("Enable.SlimeWorldManagerSuccess"))
                BuildWorld = WorldBukkit()
            }
        } else {
            logger.info(TLocale.asString("SlimeWorldManagerIsNull"))
            // 将世界生成交给Bukkit处理
            BuildWorld = WorldBukkit()
        }
        /*
        开始世界生成流程
         */
        // 获取需要增加的数量
        val preloadSize = setting.getInt("Settings.PreloadWorld.Max") - data.getStringList("PreloadWorld").size
        logger.info(TLocale.asString("Enable.PreloadWorldSize").format(preloadSize))
        logger.info(TLocale.asString("Enable.PreloadBuildStart"))
        // 进行世界生成
        val buildWorld = BuildWorld.buildWorld(preloadSize,false)
        // 将世界名追加到预载世界列表中
        PIWObject.addPreloadWorld(buildWorld)
        logger.info(TLocale.asString("Enable.PreloadBuildEnd").format(buildWorld))
        logger.info(TLocale.asString("Enable.End"))
    }

    override fun onDisable() {
        logger.info("Disable process...")
        logger.info("Disable process end!")
    }
}