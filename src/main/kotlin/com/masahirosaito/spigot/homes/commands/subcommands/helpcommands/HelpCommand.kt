package com.masahirosaito.spigot.homes.commands.subcommands.helpcommands

import com.masahirosaito.spigot.homes.Homes
import com.masahirosaito.spigot.homes.Permission
import com.masahirosaito.spigot.homes.commands.BaseCommand
import com.masahirosaito.spigot.homes.commands.CommandUsage
import com.masahirosaito.spigot.homes.commands.MainCommand
import com.masahirosaito.spigot.homes.commands.PlayerCommand
import org.bukkit.ChatColor
import org.bukkit.entity.Player

class HelpCommand(val mainCommand: MainCommand) : PlayerCommand {
    override val plugin: Homes = mainCommand.plugin
    override val name: String = "help"
    override val description: String = "Homes Help Command"
    override val permissions: List<String> = listOf(
            Permission.home_command,
            Permission.home_command_help
    )
    override val usage: CommandUsage = CommandUsage(this, listOf(
            "/home help" to "Display the list of Homes commands",
            "/home help <command_name>" to "Display the usage of Homes command"
    ))
    override val commands: List<BaseCommand> = listOf(
            HelpUsageCommand(this)
    )

    override fun fee(): Double = plugin.fee.HELP

    override fun configs(): List<Boolean> = listOf()

    override fun isValidArgs(args: List<String>): Boolean = args.isEmpty()

    override fun execute(player: Player, args: List<String>) {
        send(player, msg())
    }

    fun msg() = buildString {
        append("${ChatColor.GOLD}Homes command list${ChatColor.RESET}\n")
        append(ChatColor.LIGHT_PURPLE)
        append("/home help <command_name> : Display the usage of command\n")
        append(ChatColor.RESET)
        mainCommand.subCommands.forEach {
            append("  ${ChatColor.AQUA}${it.name}${ChatColor.RESET} : ${it.description}\n")
        }
        append("  ${ChatColor.AQUA}${mainCommand.name}${ChatColor.RESET} : ${mainCommand.description}\n")
    }
}
