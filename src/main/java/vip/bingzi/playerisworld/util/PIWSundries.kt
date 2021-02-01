package vip.bingzi.playerisworld.util

import io.izzel.taboolib.module.locale.TLocale
import vip.bingzi.playerisworld.PlayerIsWorldPro

/**
 * 一些凌乱的方法，俗称杂物堆
 */
object PIWSundries {
    fun previewLogger() {
        PlayerIsWorldPro.plugin.logger.info(TLocale.asString("Command.logger.Prompt"))
        PIWObject.logger.verbose(TLocale.asString("Command.logger.VERBOSE"))
        PIWObject.logger.finest(TLocale.asString("Command.logger.FINEST"))
        PIWObject.logger.fine(TLocale.asString("Command.logger.FINE"))
        PIWObject.logger.info(TLocale.asString("Command.logger.INFO"))
        PIWObject.logger.warn(TLocale.asString("Command.logger.WARN"))
        PIWObject.logger.error(TLocale.asString("Command.logger.ERROR"))
        PIWObject.logger.fatal(TLocale.asString("Command.logger.FATAL"))
    }
}