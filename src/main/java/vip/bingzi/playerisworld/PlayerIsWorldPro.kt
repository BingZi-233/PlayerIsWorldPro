package vip.bingzi.playerisworld

import com.grinderwolf.swm.api.SlimePlugin
import com.grinderwolf.swm.api.loaders.SlimeLoader
import com.grinderwolf.swm.api.world.SlimeWorld
import com.grinderwolf.swm.api.world.properties.SlimePropertyMap
import io.izzel.taboolib.loader.Plugin
import io.izzel.taboolib.module.config.TConfig
import io.izzel.taboolib.module.inject.TInject
import io.izzel.taboolib.module.locale.TLocale
import org.bukkit.Bukkit
import vip.bingzi.playerisworld.util.PIWObject
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

    // 创建SlimeLodaer对象,只有在检测到SlimeWorldManager插件运行的时候这个值才会被初始化
    lateinit var SlimeLoader: SlimeLoader
    lateinit var BuildModel: SlimeWorld.SlimeProperties
    lateinit var BuildMod: SlimePropertyMap
    lateinit var BuildWorld: PIWWorld

    override fun onLoad() {
        logger.info("Load process...")
        logger.info("Load process end!")
    }

    override fun onEnable() {
        logger.info("Enabled process...")
        // 输出系统语言环境
        logger.info(TLocale.asString("Enable.Language"))
        // 输出数据库使用类型
        logger.info(TLocale.asString("Enable.Database").format(setting.getString("Database.Type")))
        // 检测SlimeWorldManager插件是否存在（即被载入）
        if (SlimeWorldManager != null) {
            logger.info(TLocale.asString("Enable.SlimeWorldManager"))
            logger.info(
                TLocale.asString("Enable.SlimeWorldManagerDatabase")
                    .format(setting.getString("Settings.SlimeWorldManager.Database.Type"))
            )
            // 引导数据库类型
            try {
                SlimeLoader = SlimeWorldManager.getLoader(
                    when (setting.getString("Settings.SlimeWorldManager.Database.Type")) {
                        "LOCALE" -> "sqlite"
                        "ONLINE" -> "mongodb"
                        "MYSQL" -> "mysql"
                        else -> "sqlite"
                    }
                )
            } catch (e: Exception) {
                logger.info(TLocale.asString("Enable.SlimeWorldManagerDatabaseError"))
            }
            logger.info(TLocale.asString("Enable.SlimeWorldManagerBuildWorld"))
            BuildModel = SlimeWorld.SlimeProperties.builder()
                .readOnly(setting.getBoolean("Settings.PreloadWorld.WorldSetting.readOnly"))
                .allowMonsters(setting.getBoolean("Settings.PreloadWorld.WorldSetting.allowMonsters"))
                .allowAnimals(setting.getBoolean("Settings.PreloadWorld.WorldSetting.allowAnimals"))
                .difficulty(setting.getInt("Settings.PreloadWorld.WorldSetting.difficulty"))
                .pvp(setting.getBoolean("Settings.PreloadWorld.WorldSetting.pvp"))
                .environment(setting.getString("Settings.PreloadWorld.WorldSetting.enviroment"))
                .build()
            // 将世界生成交给SlimeWorldManage处理
            BuildWorld = WorldSlimeWorldManager()
        } else {
            logger.info(TLocale.asString("SlimeWorldManagerIsNull"))
            // 将世界生成交给Bukkit处理
            BuildWorld = WorldBukkit()
        }
        /*
        开始世界生成流程
         */
        // 获取需要增加的数量
        val PreloadSize = setting.getInt("Settings.PreloadWorld.Max") - data.getStringList("PreloadWorld").size
        // 进行世界生成
        var buildWorld = BuildWorld.buildWorld(PreloadSize)
        // 将世界名追加到预载世界列表中
        PIWObject.addPreloadWorld(buildWorld)
        logger.info("Enabled process end!")
    }

    override fun onDisable() {
        logger.info("Disable process...")
        logger.info("Disable process end!")
    }
}