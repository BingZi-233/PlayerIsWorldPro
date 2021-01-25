package vip.bingzi.playerisworld.logger

import io.izzel.taboolib.module.locale.TLocale
import io.izzel.taboolib.module.locale.logger.TLoggerManager
import io.izzel.taboolib.util.Ref
import vip.bingzi.playerisworld.PlayerIsWorldPro
import java.util.function.Consumer

class PIWLogger{
    private val path = PlayerIsWorldPro.plugin.toString()

    fun info(message: String) {
        TLocale.Logger.info(message,path)
    }

    fun warning(message: String) {
        TLocale.Logger.warn(path,message)
    }

    fun error(message: String) {
        TLocale.Logger.error(path,message)
    }

    fun severe(message: String) {
        TLocale.Logger.fatal(path,message)
    }

    fun fine(message: String) {
        TLocale.Logger.fine(path,message)
    }

    fun finest(message: String) {
        TLocale.Logger.finest(path,message)
    }

    fun verbose(message: String) {
        TLocale.Logger.verbose(path,message)
    }

    fun info(path: String?, vararg args: String?) {
        TLocale.asStringList(path, *args).forEach(Consumer { locale: String? ->
            TLoggerManager.getLogger(Ref.getCallerPlugin()).info(locale)
        })
    }

    fun warn(path: String?, vararg args: String?) {
        TLocale.asStringList(path, *args).forEach(Consumer { locale: String? ->
            TLoggerManager.getLogger(Ref.getCallerPlugin()).warn(locale)
        })
    }

    fun error(path: String?, vararg args: String?) {
        TLocale.asStringList(path, *args).forEach(Consumer { locale: String? ->
            TLoggerManager.getLogger(Ref.getCallerPlugin()).error(locale)
        })
    }

    fun fatal(path: String?, vararg args: String?) {
        TLocale.asStringList(path, *args).forEach(Consumer { locale: String? ->
            TLoggerManager.getLogger(Ref.getCallerPlugin()).fatal(locale)
        })
    }

    fun fine(path: String?, vararg args: String?) {
        TLocale.asStringList(path, *args).forEach(Consumer { locale: String? ->
            TLoggerManager.getLogger(Ref.getCallerPlugin()).fine(locale)
        })
    }

    fun finest(path: String?, vararg args: String?) {
        TLocale.asStringList(path, *args).forEach(Consumer { locale: String? ->
            TLoggerManager.getLogger(Ref.getCallerPlugin()).finest(locale)
        })
    }

    fun verbose(path: String?, vararg args: String?) {
        TLocale.asStringList(path, *args).forEach(Consumer { locale: String? ->
            TLoggerManager.getLogger(Ref.getCallerPlugin()).verbose(locale)
        })
    }
}