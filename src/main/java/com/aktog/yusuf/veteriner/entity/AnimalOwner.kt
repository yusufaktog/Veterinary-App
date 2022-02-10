package com.aktog.yusuf.veteriner.entity

import org.hibernate.Hibernate
import org.hibernate.annotations.GenericGenerator
import javax.persistence.*

@Entity
data class AnimalOwner @JvmOverloads constructor(
    @Id
    @Column(name = "owner_id")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    val id: String? = "",
    val name: String,
    val surname:String,
    val email:String,
    val phoneNumber:String,

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    val pets: Set<Animal>? = HashSet(),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as AnimalOwner

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id )"
    }
}
