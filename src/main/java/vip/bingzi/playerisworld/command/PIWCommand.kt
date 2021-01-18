package vip.bingzi.playerisworld.command

import io.izzel.taboolib.module.command.base.*
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

@BaseCommand(name = "playerisworld",aliases = ["PlayerIsWorld","PIW","piw"],permission = "playerisworld.use")
class PIWCommand: BaseMainCommand() {
    @SubCommand
    var teleport : BaseSubCommand = object : BaseSubCommand(){
        override fun getDescription(): String {
            return "传送到指定玩家世界，不指定玩家时传送到自己的世界"
        }

        override fun getArguments(): Array<Argument> {
            return arrayOf(Argument("玩家名",false))
        }

        override fun onCommand(p0: CommandSender, p1: Command, p2: String, p3: Array<out String>) {
            if (p0 is Player){
                TODO("玩家传送逻辑")
            }
        }
    }
}