package vip.bingzi.playerisworld.world

import org.bukkit.World

/**
 * 世界对象
 */
abstract class PIWWorld {
    /**
     * 世界构建
     */
    abstract fun buildWorld(int: Int): ArrayList<String>

    /**
     * 调度器世界构建
     */
    abstract fun buildWorldSync(int: Int): ArrayList<String>

    /**
     * 世界卸载（世界对象）
     */
    abstract fun unloadWorld(world: World)

    /**
     * 世界卸载（世界名称）
     */
    abstract fun unloadWorld(worldName: String)
}