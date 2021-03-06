package com.masahirosaito.spigot.homes.datas

import com.masahirosaito.spigot.homes.exceptions.NoDefaultHomeException
import com.masahirosaito.spigot.homes.exceptions.NoNamedHomeException
import com.masahirosaito.spigot.homes.homedata.PlayerHome
import com.masahirosaito.spigot.homes.nms.HomesEntity
import org.bukkit.Chunk
import org.bukkit.OfflinePlayer

data class PlayerData(
        val offlinePlayer: OfflinePlayer,
        var defaultHome: HomesEntity? = null,
        val namedHomes: MutableList<HomesEntity> = mutableListOf()
) {
    fun tearDown() {
        defaultHome?.despawnEntities()
        namedHomes.forEach { it.despawnEntities() }
    }

    fun toPlayerHome(): PlayerHome = PlayerHome().apply {
        defaultHome?.let { defaultHomeData = it.toHomeData() }
        namedHomes.forEach { namedHomeData.add(it.toHomeData()) }
    }

    fun getHomesEntitiesIn(chunk: Chunk): List<HomesEntity> {
        return mutableListOf<HomesEntity>().apply {
            defaultHome?.let { if (it.inChunk(chunk)) add(it) }
            namedHomes.forEach { if (it.inChunk(chunk)) add(it) }
        }
    }

    fun load(): PlayerData = this.apply {
        defaultHome?.let { if (it.location.chunk.isLoaded) it.spawnEntities() }
        namedHomes.forEach { if (it.location.chunk.isLoaded) it.spawnEntities() }
    }

    fun hasNamedHome(homeName: String): Boolean {
        return namedHomes.any { it.homeName == homeName }
    }

    fun getNamedHome(homeName: String): HomesEntity {
        return namedHomes.find { it.homeName == homeName } ?:
                throw NoNamedHomeException(offlinePlayer, homeName)
    }

    fun removeDefaultHome() {
        if (defaultHome != null) {
            defaultHome!!.despawnEntities()
            defaultHome = null
        } else {
            throw NoDefaultHomeException(offlinePlayer)
        }
    }

    fun removeNamedHome(homeName: String) {
        if (hasNamedHome(homeName)) {
            getNamedHome(homeName).despawnEntities()
            namedHomes.removeIf { it.homeName == homeName }
        } else {
            throw NoNamedHomeException(offlinePlayer, homeName)
        }
    }
}
