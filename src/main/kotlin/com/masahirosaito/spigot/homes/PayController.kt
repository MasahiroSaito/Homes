package com.masahirosaito.spigot.homes

import com.masahirosaito.spigot.homes.commands.subcommands.player.PlayerCommand
import com.masahirosaito.spigot.homes.exceptions.HomesException
import com.masahirosaito.spigot.homes.strings.EconomyStrings
import com.masahirosaito.spigot.homes.strings.EconomyStrings.NOT_ENOUGH_MONEY_ERROR
import com.masahirosaito.spigot.homes.strings.EconomyStrings.NO_ACCOUNT_ERROR
import org.bukkit.entity.Player

object PayController {

    fun checkBalance(playerCommand: PlayerCommand, player: Player) {
        if (playerCommand.fee() <= 0) return
        Homes.homes.econ?.let {
            if (!it.hasAccount(player)) {
                throw HomesException(NO_ACCOUNT_ERROR())
            }
            if (!it.has(player, playerCommand.fee())) {
                throw HomesException(NOT_ENOUGH_MONEY_ERROR(playerCommand.fee()))
            }
        }
    }

    fun payFee(playerCommand: PlayerCommand, player: Player) {
        if (playerCommand.fee() <= 0) return
        Homes.homes.econ?.let { economy ->
            val r = economy.withdrawPlayer(player, playerCommand.fee())
            if (r.transactionSuccess()) {
                playerCommand.send(player, EconomyStrings.PAY(economy.format(r.amount), economy.format(r.balance)))
            } else {
                throw HomesException(EconomyStrings.ECONOMY_ERROR(r.errorMessage))
            }
        }
    }
}
