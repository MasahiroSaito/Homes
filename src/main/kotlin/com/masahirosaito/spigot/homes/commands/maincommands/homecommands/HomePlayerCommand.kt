package com.masahirosaito.spigot.homes.commands.maincommands.homecommands

import com.masahirosaito.spigot.homes.Configs
import com.masahirosaito.spigot.homes.Permission
import com.masahirosaito.spigot.homes.commands.PlayerCommand
import com.masahirosaito.spigot.homes.commands.SubCommand
import com.masahirosaito.spigot.homes.findOfflinePlayer
import org.bukkit.entity.Player

class HomePlayerCommand(val homeCommand: HomeCommand) : SubCommand(homeCommand), PlayerCommand {
    override val permissions: List<String> = listOf(
            Permission.home_command,
            Permission.home_command_player
    )

    override fun fee(): Double = homes.fee.HOME_PLAYER

    override fun configs(): List<Boolean> = listOf(
            Configs.onFriendHome
    )

    override fun isValidArgs(args: List<String>): Boolean = args.size == 2 && args[0] == "-p"

    override fun execute(player: Player, args: List<String>) {
        player.teleport(homeCommand.getTeleportLocation(findOfflinePlayer(args[1])))
    }
}
