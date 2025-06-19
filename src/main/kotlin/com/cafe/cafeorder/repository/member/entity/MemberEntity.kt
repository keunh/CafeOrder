package com.cafe.cafeorder.repository.member.entity

import com.cafe.cafeorder.repository.entity.BaseEntity
import com.cafe.cafeorder.repository.member.constants.Gender
import com.cafe.cafeorder.repository.member.constants.MemberStatus
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "member")
class MemberEntity (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false, unique = true, length = 30)
    val loginId: String,

    @Column(nullable = false, length = 20)
    var name: String,

    @Column(nullable = false, length = 11)
    var phoneNumber: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 5)
    var gender: Gender,

    @Column(nullable = false, length = 6)
    var birth: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    var status: MemberStatus = MemberStatus.AVAILABLE,

    @Column
    var withdrawnAt: LocalDateTime? = null,

    @Column
    var restoreAt: LocalDateTime? = null,

): BaseEntity()  {

    fun isWithdrawn(): Boolean {
        return this.status == MemberStatus.WITHDRAWN && this.withdrawnAt != null
    }

    fun withdraw(now: LocalDateTime) {
        this.status = MemberStatus.WITHDRAWN
        this.withdrawnAt = now
    }

    fun isWithdrawalIn30Days(now: LocalDateTime): Boolean {
        this.withdrawnAt ?: return false
        return this.withdrawnAt!!.plusDays(30).isAfter(now)
    }

    fun restore(now: LocalDateTime) {
        this.status = MemberStatus.AVAILABLE
        this.withdrawnAt = null
        this.restoreAt = now
    }
}