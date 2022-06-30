package com.aktog.yusuf.veteriner.dto.request

import com.aktog.yusuf.veteriner.entity.Role
import org.hibernate.validator.constraints.Length
import javax.validation.constraints.NotBlank

data class CreateUserRequest(
    @field:NotBlank
    val username: String,

    @field:NotBlank
    @field:Length(min = 8, max = 30)
    val password: String,

    val roles: Set<Role>? = HashSet()
)
