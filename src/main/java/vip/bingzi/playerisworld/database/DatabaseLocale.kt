package vip.bingzi.playerisworld.database

import io.izzel.taboolib.module.db.local.LocalPlayer
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.entity.Player

class DatabaseLocale : Database() {
    /**
     * 获取玩家的数据，并且以FileConfiguration的格式进行返回
     */
    override fun download(player: Player): FileConfiguration {
        return LocalPlayer.get(player)
    }

    /**
     * 在Locale模式下，这个方法并没有任何作用
     */
    override fun upload(player: Player) {
    }

    /**
     * Locale独有的一种保存方式
     */
    override fun upload(player: Player, fileConfiguration: FileConfiguration) {
        return LocalPlayer.set0(player, fileConfiguration)
    }

}