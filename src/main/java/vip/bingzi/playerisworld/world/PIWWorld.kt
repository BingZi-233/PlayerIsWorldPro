package vip.bingzi.playerisworld.world

import org.bukkit.World

/**
 * 世界对象
 */
abstract class PIWWorld {
    /**
     * 世界构建
     */
    abstract fun buildWorld(int: Int, saveToFile: Boolean): ArrayList<String>

    /**
     * 调度器世界构建
     */
    abstract fun buildWorldSync(int: Int, saveToFile: Boolean): ArrayList<String>

    /**
     * 载入世界
     */
    abstract fun loadWorld(worldName: String): Any

    /**
     * 调度器载入世界
     */
    abstract fun loadWorldSync(worldName: String): Any

    /**
     * 世界卸载（世界对象）
     */
    abstract fun unloadWorld(world: World)

    /**
     * 世界卸载（世界名称）
     */
    abstract fun unloadWorld(worldName: String)
}