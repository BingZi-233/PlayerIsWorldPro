package vip.bingzi.playerisworld.util

import io.izzel.taboolib.module.command.base.*
import io.izzel.taboolib.module.locale.TLocale
import io.izzel.taboolib.module.locale.logger.TLogger
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import vip.bingzi.playerisworld.PlayerIsWorldPro
import vip.bingzi.playerisworld.util.PIWObject.getPreloadWorld
import vip.bingzi.playerisworld.util.PIWObject.logger
import vip.bingzi.playerisworld.util.PIWSundries.previewLogger

@BaseCommand(name = "PlayerIsWorldPro", aliases = ["playerisworldpro", "PIW", "piw"], permission = "playerisworld.use")
class PIWCommand : BaseMainCommand() {
    override fun getCommandTitle(): String {
        return "PlayerIsWorldPro Command"
    }

    @SubCommand
    var teleport: BaseSubCommand = object : BaseSubCommand() {
        override fun getDescription(): String {
            return "传送到指定玩家世界，不指定玩家时传送到自己的世界"
        }

        override fun getArguments(): Array<Argument> {
            return arrayOf(Argument("玩家名", false))
        }

        override fun onCommand(p0: CommandSender, p1: Command, p2: String, p3: Array<out String>) {
            if (p0 is Player) {
                val integral = PIWObject.getIntegral(p0, "WorldName") as String
                logger.fine("已载入世界列表为：")
                for (world in Bukkit.getWorlds()) {
                    logger.fine("-> ${world.name}")
                    if (world.name == integral) {
                        logger.fine("[PIWCommand - teleport]onCommand -> 探测到世界已载入，传送至$integral")
                        p0.teleport(Bukkit.getWorld(integral)?.spawnLocation!!)
                        return
                    }
                }
                object : BukkitRunnable() {
                    override fun run() {
                        object : BukkitRunnable() {
                            override fun run() {
                                logger.fine("正在对 $integral 进行加载，并将${p0.name}传送至此世界！")
                                if (PlayerIsWorldPro.BuildWorld.loadWorld(integral) as Boolean) {
                                    logger.fine("玩家${p0.name}在载入世界时出现了错误，指定世界名为：$integral")
                                    TLocale.sendTo(p0,
                                        TLocale.asString("Command.Teleport.ErrorTitle"),
                                        TLocale.asString("Command.Teleport.ErrorSubTitle"))
                                    return
                                }
                                p0.teleport(Bukkit.getWorld(integral)?.spawnLocation!!)
                            }
                        }.runTask(PlayerIsWorldPro.plugin)
                    }
                }.runTaskAsynchronously(PlayerIsWorldPro.plugin)
            }
        }
    }

    @SubCommand
    var adminUtil: BaseSubCommand = object : BaseSubCommand() {
        override fun getPermission(): String {
            return "playerisworld.admin"
        }

        override fun getDescription(): String {
            return "管理员工具"
        }

        override fun getArguments(): Array<Argument> {
            return arrayOf(
                Argument("debug(调试)/logger(日志-需要参数等级)/info(信息)/world(世界)") {
                    listOf("debug",
                        "logger",
                        "info",
                        "world")
                },
                Argument("等级(默认为INFO)", false) { listOf("VERBOSE", "FINEST", "FINE", "INFO", "WARN", "ERROR", "FATAL") }
            )
        }

        override fun onCommand(p0: CommandSender, p1: Command, p2: String, p3: Array<out String>) {
            if (p0 is Player) {
                TLocale.sendTo(p0, TLocale.asString("Command.logger.NoConsole"))
                return
            }
            when (p3[0]) {
                "debug" -> {
                    // 设置日志输出等级
                    logger.level = TLogger.FINEST
                    previewLogger()
                }
                "logger" -> {
                    // 设置日志输出等级
                    logger.level = when (p3[1]) {
                        "VERBOSE" -> TLogger.VERBOSE
                        "FINEST" -> TLogger.FINEST
                        "FINE" -> TLogger.FINE
                        "INFO" -> TLogger.INFO
                        "WARN" -> TLogger.WARN
                        "ERROR" -> TLogger.ERROR
                        "FATAL" -> TLogger.FATAL
                        else -> TLogger.INFO
                    }
                    previewLogger()
                }
                "info" -> {
                    logger.info("Logger Level: ${logger.level}")
                    logger.info("Permanent World List: ${PlayerIsWorldPro.setting.getStringList("Settings.WorldList")}")
                    logger.info("Preload World List: ${PlayerIsWorldPro.data.getStringList("PreloadWorld")}")
                    logger.info("WorldList: ${PlayerIsWorldPro.setting.getStringList("Settings.WorldList")}")
                }
                "world" -> {
                    logger.info("Preload World List[Size:${getPreloadWorld().size}]:")
                    for (PreloadWorldName in getPreloadWorld()) {
                        logger.info("-> $PreloadWorldName")
                    }
                    for (player in Bukkit.getOnlinePlayers()) {
                        logger.info("${player.name} -> ${PIWObject.getIntegral(player, "WorldName") as String}")
                    }
                }
                else -> {
                    logger.warn(TLocale.asString("Warn.CommandParameterError"))
                }
            }
        }
    }

}


