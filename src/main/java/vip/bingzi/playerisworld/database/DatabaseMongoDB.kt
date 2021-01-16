package vip.bingzi.playerisworld.database

import io.izzel.taboolib.cronus.bridge.CronusBridge
import io.izzel.taboolib.cronus.bridge.database.IndexType
import io.izzel.taboolib.kotlin.Tasks
import io.izzel.taboolib.module.nms.NMS
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.entity.Player
import vip.bingzi.playerisworld.PlayerIsWorldPro

class DatabaseMongoDB : Database(){
    private val setting = PlayerIsWorldPro.setting
    private val collection = CronusBridge.get(
        setting.getString("Database.Url.Client"),
        setting.getString("Database.Url.Database"),
        setting.getString("Database.Url.Collection"),
        IndexType.UUID
    )

    /**
     * 获取玩家的数据，并且以FileConfiguration的格式进行返回
     */
    override fun download(player: Player): FileConfiguration {
        return collection.get(player.uniqueId.toString()).run {
            if (this.contains("username")) {
                this.set("username", player.name)
            }
            this
        }
    }

    /**
     * MongoDB独有的保存方式
     */
    override fun upload(player: Player) {
        if (NMS.handle().isRunning) {
            Tasks.task(true) {
                collection.update(player.uniqueId.toString())
            }
        } else {
            collection.update(player.uniqueId.toString())
        }
    }

    /**
     * 在MongoDB下，这个方法没有任何作用
     */
    override fun upload(player: Player, fileConfiguration: FileConfiguration) {
    }
}