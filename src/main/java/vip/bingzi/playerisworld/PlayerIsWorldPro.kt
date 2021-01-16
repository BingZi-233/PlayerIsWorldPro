package vip.bingzi.playerisworld

import com.grinderwolf.swm.api.SlimePlugin
import com.grinderwolf.swm.api.loaders.SlimeLoader
import io.izzel.taboolib.loader.Plugin
import io.izzel.taboolib.module.config.TConfig
import io.izzel.taboolib.module.inject.TInject
import io.izzel.taboolib.module.locale.TLocale
import org.bukkit.Bukkit
import vip.bingzi.playerisworld.util.PIWObject.logger

object PlayerIsWorldPro : Plugin() {
    // 配置文件释放
    @TInject(value = ["setting.yml"], locale = "LOCALE-PRIORITY")
    lateinit var setting: TConfig
        private set

    // 创建SlimeWorldManager对象，方便后续进行调用
    val SlimeWorldManager = Bukkit.getPluginManager().getPlugin("SlimeWorldManager") as SlimePlugin?

    // 创建SlimeLodaer对象,只有在检测到SlimeWorldManager插件运行的时候这个值才会被初始化
    lateinit var SlimeLoader: SlimeLoader
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
            SlimeLoader = SlimeWorldManager.getLoader(
                when (setting.getString("Settings.SlimeWorldManager.Database.Type")) {
                    "LOCALE" -> "sqlite"
                    "ONLINE" -> "mongodb"
                    "MYSQL" -> "mysql"
                    else -> "sqlite"
                }
            )
        } else {
            logger.info(TLocale.asString("SlimeWorldManagerIsNull"))
        }
        logger.info("Enabled process end!")
    }

    override fun onDisable() {
        logger.info("Disable process...")
        logger.info("Disable process end!")
    }
}