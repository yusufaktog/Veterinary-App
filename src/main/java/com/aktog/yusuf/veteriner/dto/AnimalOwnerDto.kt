package com.aktog.yusuf.veteriner.dto


import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_EMPTY)
data class AnimalOwnerDto @JvmOverloads constructor(

    val id: String? = "",
    val name: String,
    val surname: String,
    val email: String,
    val phoneNumber: String,


    val pets: List<AnimalDto>? = ArrayList(),
)

