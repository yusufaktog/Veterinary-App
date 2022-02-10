package com.aktog.yusuf.veteriner.dto.request

import com.aktog.yusuf.veteriner.entity.AnimalOwner
import javax.validation.constraints.*


data class CreateAnimalRequest(
    @field:NotBlank
    val type: String,

    @field:NotBlank
    val genus: String,

    @field:NotBlank
    val name: String,

    @field:Positive
    val age: Int,

    @field:NotBlank
    val description: String,

    @field:NotNull
    val owner: AnimalOwner,
)
