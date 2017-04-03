package com.masahirosaito.spigot.homes.commands.subcommands.privatecommands

import com.masahirosaito.spigot.homes.Configs
import com.masahirosaito.spigot.homes.Homes
import com.masahirosaito.spigot.homes.Permission
import com.masahirosaito.spigot.homes.PlayerDataManager
import com.masahirosaito.spigot.homes.commands.BaseCommand
import com.masahirosaito.spigot.homes.commands.CommandUsage
import com.masahirosaito.spigot.homes.commands.PlayerCommand
import com.masahirosaito.spigot.homes.strings.commands.PrivateCommandStrings.DESCRIPTION
import com.masahirosaito.spigot.homes.strings.commands.PrivateCommandStrings.SET_DEFAULT_HOME_PRIVATE
import com.masahirosaito.spigot.homes.strings.commands.PrivateCommandStrings.SET_DEFAULT_HOME_PUBLIC
import com.masahirosaito.spigot.homes.strings.commands.PrivateCommandStrings.USAGE_PRIVATE
import com.masahirosaito.spigot.homes.strings.commands.PrivateCommandStrings.USAGE_PRIVATE_NAME
import org.bukkit.entity.Player

class PrivateCommand(override val plugin: Homes) : PlayerCommand {
    override val name: String = "private"
    override val description: String = DESCRIPTION()
    override val permissions: List<String> = listOf(
            Permission.home_command,
            Permission.home_command_private
    )
    override val usage: CommandUsage = CommandUsage(this, listOf(
            "/home private (on/off)" to USAGE_PRIVATE(),
            "/home private (on/off) <home_name>" to USAGE_PRIVATE_NAME()
    ))
    override val commands: List<BaseCommand> = listOf(
            PrivateNameCommand(this)
    )

    override fun fee(): Double = plugin.fee.PRIVATE

    override fun configs(): List<Boolean> = listOf(
            Configs.onPrivate
    )

    override fun isValidArgs(args: List<String>): Boolean = args.size == 1 && (args[0] == "on" || args[0] == "off")

    override fun execute(player: Player, args: List<String>) {
        if (args[0] == "on") {
            PlayerDataManager.setDefaultHomePrivate(player, true)
            send(player, SET_DEFAULT_HOME_PRIVATE())
        } else {
            PlayerDataManager.setDefaultHomePrivate(player, false)
            send(player, SET_DEFAULT_HOME_PUBLIC())
        }
    }
}
