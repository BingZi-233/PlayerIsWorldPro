package vip.bingzi.playerisworld.util

import com.grinderwolf.swm.api.world.properties.SlimeProperties
import com.grinderwolf.swm.api.world.properties.SlimePropertyMap
import io.izzel.taboolib.module.locale.logger.TLoggerManager
import org.bukkit.entity.Player
import vip.bingzi.playerisworld.PlayerIsWorldPro
import vip.bingzi.playerisworld.database.Database

object PIWObject {
    // 日志
    val logger = TLoggerManager.getLogger(PlayerIsWorldPro.plugin)

    // 仅对内部可见，方式从外部更改了加载方式
    lateinit var database: Database

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
        return download["PlayerIsWorld.List.WorldName", null]
    }

    /**
     * 设置玩家数据
     */
    fun setIntegral(player: Player?, WorldName: String) {
        val download = database.download(player!!)
        download["PlayerIsWorld.List.WorldName"] = WorldName
    }

    fun getSlimeBuildModer(
        allowMonsters: Boolean,
        allowAnimals: Boolean,
        difficulty: String?,
        environment: String?,
        pvp: Boolean,
        worldType: String?,
        spawnX: Int,
        SpawnY: Int,
        SpawnZ: Int,
    ): SlimePropertyMap {
        logger.fine("构建模型参数信息为：$allowMonsters,$allowAnimals,$difficulty,$environment,$pvp,$worldType,X:$spawnX,Y:$SpawnY,Z:$SpawnZ")
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
        // 设置世界模板世界坐标
        slimePropertyMap.setInt(SlimeProperties.SPAWN_X, spawnX)
        slimePropertyMap.setInt(SlimeProperties.SPAWN_Y, SpawnY)
        slimePropertyMap.setInt(SlimeProperties.SPAWN_Z, SpawnZ)
        return slimePropertyMap
    }
}