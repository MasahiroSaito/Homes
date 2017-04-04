package com.masahirosaito.spigot.homes.commands

import com.masahirosaito.spigot.homes.Homes
import com.masahirosaito.spigot.homes.Messenger
import com.masahirosaito.spigot.homes.exceptions.ArgumentIncorrectException
import com.masahirosaito.spigot.homes.exceptions.InvalidCommandSenderException
import com.masahirosaito.spigot.homes.exceptions.NotAllowByConfigException
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Player

interface BaseCommand {
    val homes: Homes
    val name: String
    val description: String
    val commands: List<BaseCommand>
    val playerCommandUsage: CommandUsage
    val consoleCommandUsage: CommandUsage

    fun configs(): List<Boolean>

    fun isValidArgs(args: List<String>): Boolean

    fun send(sender: CommandSender, message: String) {
        if (!message.isNullOrBlank()) Messenger.send(sender, message)
    }

    fun send(sender: CommandSender, obj: Any?) {
        send(sender, obj.toString())
    }

    fun checkConfig() {
        if (configs().contains(false)) throw NotAllowByConfigException()
    }

    fun checkArgs(sender: CommandSender, args: List<String>) {
        if (!isValidArgs(args)) {
            when (sender) {
                is Player -> throw ArgumentIncorrectException(playerCommandUsage)
                is ConsoleCommandSender -> throw ArgumentIncorrectException(consoleCommandUsage)
            }
        }
    }

    fun executeCommand(sender: CommandSender, args: List<String>) {
        val cmd = findCommand(args)
        when {
            cmd is PlayerCommand && sender is Player -> cmd.onCommand(sender, args)
            cmd is ConsoleCommand && sender is ConsoleCommandSender -> cmd.onCommand(sender, args)
            else -> throw InvalidCommandSenderException()
        }
    }

    private fun findCommand(args: List<String>): BaseCommand {
        return commands.find { it.isValidArgs(args) } ?: this
    }
}
