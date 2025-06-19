package com.cafe.cafeorder.repository.item.entity

import com.cafe.cafeorder.repository.entity.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "item")
class ItemEntity (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false, unique = true, length = 30)
    val name: String,

    @Column(nullable = false, length = 10)
    val price: Int,

    @Column(nullable = true, length = 200)
    val imageUrl: String? = null,

): BaseEntity()