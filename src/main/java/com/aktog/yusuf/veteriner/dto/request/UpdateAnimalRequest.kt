package com.aktog.yusuf.veteriner.dto.request

import com.aktog.yusuf.veteriner.entity.AnimalOwner
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive

data class UpdateAnimalRequest(

    @field:NotBlank
    val name: String,

    @field:Positive
    val age: Int,

    @field:NotBlank
    val description: String,

    @field:NotBlank
    val ownerId: String,
)
