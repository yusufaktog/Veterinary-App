package com.aktog.yusuf.veteriner.dto

import com.aktog.yusuf.veteriner.entity.Role


data class UserDto @JvmOverloads constructor(
    val id:String?,
    val username:String,
    val password:String,
    val roles:Set<Role>? = HashSet<Role>()
)
