package vip.bingzi.playerisworld.util

import io.izzel.taboolib.module.command.base.*
import io.izzel.taboolib.module.locale.TLocale
import io.izzel.taboolib.module.locale.logger.TLogger
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import vip.bingzi.playerisworld.PlayerIsWorldPro
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
                return
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
                Argument("debug(调试)/logger(日志-需要参数等级)/info(信息)") { listOf("debug", "logger", "info") },
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
                    logger.info("Logger Level:${logger.level}")
                    logger.info("Permanent World List:${PlayerIsWorldPro.setting.getStringList("Settings.WorldList")}")
                    logger.info("Preload World List:${PlayerIsWorldPro.data.getStringList("PreloadWorld")}")
                    logger.info("WorldList:${PlayerIsWorldPro.setting.getStringList("Settings.WorldList")}")
                }
                else -> {
                    logger.warn(TLocale.asString("Warn.CommandParameterError"))
                }
            }
        }
    }

}


