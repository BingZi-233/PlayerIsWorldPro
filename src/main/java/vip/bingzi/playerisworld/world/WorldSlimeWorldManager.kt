package vip.bingzi.playerisworld.world

import com.grinderwolf.swm.api.world.SlimeWorld
import org.bukkit.World
import vip.bingzi.playerisworld.PlayerIsWorldPro

class WorldSlimeWorldManager:PIWWorld() {
    // SlimeWorldManager插件对象
    val SlimeworldManager = PlayerIsWorldPro.SlimeWorldManager
    // SlimeWorldManager的储存对象
    val SliemLoader = PlayerIsWorldPro.SlimeLoader
    // SlimeWorldManager的世界生成对象
    override fun buildWorld(int: Int): ArrayList<String> {

    }

    override fun buildWorldSync(int: Int): ArrayList<String> {
        TODO("Not yet implemented")
    }

    override fun unloadWorld(world: World) {
        TODO("Not yet implemented")
    }

    override fun unloadWorld(worldName: String) {
        TODO("Not yet implemented")
    }
}