package com.masahirosaito.spigot.homes.exceptions

import com.masahirosaito.spigot.homes.Strings

class LimitHomeException(limit: Int) :
        HomesException(Strings.HOME_LIMIT(limit))
