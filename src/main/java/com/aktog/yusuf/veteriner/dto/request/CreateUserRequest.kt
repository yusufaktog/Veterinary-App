package com.aktog.yusuf.veteriner.dto.request

import com.aktog.yusuf.veteriner.entity.Role
import org.hibernate.validator.constraints.Length
import org.springframework.security.config.web.servlet.SecurityMarker
import java.util.*
import javax.persistence.UniqueConstraint
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class CreateUserRequest(
    @field:NotBlank
    @field:Length(min = 8, max = 20, message = "Username length mus be between 8-20 characters")
    val username: String,

    @field:NotBlank
    @field:Length(min = 8, max = 36, message = "Password length is invalid ( min:8, max:36)")
    val password: String,

    val roles: Set<Role>
)
