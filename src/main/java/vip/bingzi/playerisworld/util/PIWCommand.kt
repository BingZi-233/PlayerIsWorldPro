package vip.bingzi.playerisworld.util

import io.izzel.taboolib.module.command.base.*
import io.izzel.taboolib.module.locale.TLocale
import io.izzel.taboolib.module.locale.logger.TLogger
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import vip.bingzi.playerisworld.PlayerIsWorldPro

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
    var logger: BaseSubCommand = object : BaseSubCommand() {
        override fun getDescription(): String {
            return "主动调整日志等级"
        }

        override fun getArguments(): Array<Argument> {
            return arrayOf(Argument("等级"))
        }

        override fun onCommand(p0: CommandSender, p1: Command, p2: String, p3: Array<out String>) {
            if (p0 is Player){
                TLocale.sendTo(p0,TLocale.asString("Command.logger.NoConsole"))
                return
            }
            // 设置日志输出等级
            PIWObject.logger.level = when (p3[0]) {
                "VERBOSE" -> TLogger.VERBOSE
                "FINEST" -> TLogger.FINEST
                "FINE" -> TLogger.FINE
                "INFO" -> TLogger.INFO
                "WARN" -> TLogger.WARN
                "ERROR" -> TLogger.ERROR
                "FATAL" -> TLogger.FATAL
                else -> TLogger.INFO
            }
            PlayerIsWorldPro.plugin.logger.info("您当前可以看到的日志等级如下：")
            PIWObject.logger.verbose("INFO 级别输出")
            PIWObject.logger.finest("FINEST 级别输出")
            PIWObject.logger.fine("FINE 级别输出")
            PIWObject.logger.info("INFO 级别输出")
            PIWObject.logger.warn("WARN 级别输出")
            PIWObject.logger.error("ERROR 级别输出")
            PIWObject.logger.fatal("FATAL 级别输出")
        }
    }
}