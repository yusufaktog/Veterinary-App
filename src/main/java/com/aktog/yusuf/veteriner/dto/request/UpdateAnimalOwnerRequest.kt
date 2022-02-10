package com.aktog.yusuf.veteriner.dto.request

import com.aktog.yusuf.veteriner.entity.Animal
import org.hibernate.validator.constraints.Length
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

data class UpdateAnimalOwnerRequest(

    @field:NotBlank
    val name: String,

    @field:NotBlank
    val surname:String,

    @field:Email
    val email:String,

    @field:Length(min = 10, max = 11)
    val phoneNumber:String,

    val pets: Set<Animal>? = HashSet(),
)
