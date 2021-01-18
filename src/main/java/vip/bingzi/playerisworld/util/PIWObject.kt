`package vip.bingzi.playerisworld.util

import com.grinderwolf.swm.api.world.properties.SlimePropertyMap
import org.bukkit.entity.Player
import vip.bingzi.playerisworld.PlayerIsWorldPro
import vip.bingzi.playerisworld.database.Database
import vip.bingzi.playerisworld.database.DatabaseLocale
import vip.bingzi.playerisworld.database.DatabaseMongoDB
import java.util.logging.Logger

object PIWObject {
    // 日志
    val logger: Logger = PlayerIsWorldPro.plugin.logger
    private val database = init()
    private fun init(): Database {
        return when (PlayerIsWorldPro.setting.getString("Database.Type")) {
            "LOCAL" -> DatabaseLocale()
            "ONLINE" -> DatabaseMongoDB()
            else -> DatabaseLocale()
        }
    }

    /**
     * 获取预载世界列表
     */
    fun getPreloadWorld(): ArrayList<String> {
        return PlayerIsWorldPro.data.getStringList("PreloadWorld") as ArrayList<String>
    }

    /**
     * 在预载世界中追加单个指定世界名
     */
    fun addPreloadWorld(string: String) {
        var preloadWorld = getPreloadWorld()
        preloadWorld.add(string)
        PlayerIsWorldPro.data.set("PreloadWorld", preloadWorld)
        PlayerIsWorldPro.data.saveToFile()
    }

    /**
     * 在预载世界中追加多个指定世界名
     */
    fun addPreloadWorld(arrayList: ArrayList<String>) {
        var preloadWorld = getPreloadWorld()
        preloadWorld.addAll(arrayList)
        PlayerIsWorldPro.data.set("PreloadWorld", preloadWorld)
        PlayerIsWorldPro.data.saveToFile()
    }

    /**
     * 在预载世界列表中移除指定世界名
     */
    fun delPreloadWorld(string: String) {
        var preloadWorld = getPreloadWorld()
        preloadWorld.remove(string)
        PlayerIsWorldPro.data.set("PreloadWorld", preloadWorld)
        PlayerIsWorldPro.data.saveToFile()
    }

    /**
     * 获取玩家数据
     */
    fun getIntegral(player: Player?): Any? {
        // 获取对应玩家的数据库
        val download = database.download(player!!)
        // 如果未查询到玩家世界，则返回false
        return download["PlayerIsWorld.List.WorldName", true]
    }

    /**
     * 设置玩家数据
     */
    fun setIntegral(player: Player?, WorldName: String) {
        val download = database.download(player!!)
        download["PlayerIsWorld.List.WorldName"] = WorldName
    }

    fun setSlimePropertyMap(
        allowMonsters: Boolean,
        allowAnimals: Boolean,
        difficulty: Int,
        environment: String,
        pvp: Boolean,
        worldType: String
    ): SlimePropertyMap {

    }
}