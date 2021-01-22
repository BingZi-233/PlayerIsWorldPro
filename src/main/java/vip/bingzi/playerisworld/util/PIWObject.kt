package vip.bingzi.playerisworld.util

import com.grinderwolf.swm.api.world.properties.SlimeProperties
import com.grinderwolf.swm.api.world.properties.SlimePropertyMap
import org.bukkit.entity.Player
import vip.bingzi.playerisworld.PlayerIsWorldPro
import vip.bingzi.playerisworld.database.Database
import java.util.logging.Logger

object PIWObject {
    // 日志
    val logger: Logger = PlayerIsWorldPro.plugin.logger
    // 仅对内部可见，方式从外部更改了加载方式
    private lateinit var database : Database

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
        val preloadWorld = getPreloadWorld()
        preloadWorld.add(string)
        PlayerIsWorldPro.data.set("PreloadWorld", preloadWorld)
        PlayerIsWorldPro.data.saveToFile()
    }

    /**
     * 在预载世界中追加多个指定世界名
     */
    fun addPreloadWorld(arrayList: ArrayList<String>) {
        val preloadWorld = getPreloadWorld()
        preloadWorld.addAll(arrayList)
        PlayerIsWorldPro.data.set("PreloadWorld", preloadWorld)
        PlayerIsWorldPro.data.saveToFile()
    }

    /**
     * 在预载世界列表中移除指定世界名
     */
    fun delPreloadWorld(string: String) {
        val preloadWorld = getPreloadWorld()
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

    fun getSlimePropertyMap(
        allowMonsters: Boolean,
        allowAnimals: Boolean,
        difficulty: String?,
        environment: String?,
        pvp: Boolean,
        worldType: String?
    ): SlimePropertyMap {
        PlayerIsWorldPro.setting.release()
        // 对世界模板进行设置
        val slimePropertyMap = SlimePropertyMap()
        // 设置世界模板会不会生成怪物
        slimePropertyMap.setBoolean(SlimeProperties.ALLOW_MONSTERS, allowMonsters)
        // 设置世界模板会不会生成动物
        slimePropertyMap.setBoolean(SlimeProperties.ALLOW_ANIMALS, allowAnimals)
        // 设置世界模板难度
        slimePropertyMap.setString(SlimeProperties.DIFFICULTY, difficulty)
        // 设置世界模板可否PVP
        slimePropertyMap.setBoolean(SlimeProperties.PVP, pvp)
        // 设置世界模板类型
        slimePropertyMap.setString(SlimeProperties.ENVIRONMENT, environment)
        // 设置世界模板世界生成方式
        slimePropertyMap.setString(SlimeProperties.WORLD_TYPE, worldType)
        return slimePropertyMap
    }
}