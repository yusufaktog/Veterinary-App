package com.aktog.yusuf.veteriner.dto


import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class AnimalDto @JvmOverloads constructor(
    val id: String? = "",
    val type: String,
    val genus: String,
    val name: String,
    val age: Int,
    val description: String,


    val owner: AnimalOwnerDto? = null,
)
