package com.example.kakaopayad.repository.common

import org.springframework.data.repository.NoRepositoryBean
import org.springframework.data.repository.Repository

@NoRepositoryBean
interface BaseRepository<T, ID>: Repository<T, ID> {
    fun <T> save(entity: T): T
}