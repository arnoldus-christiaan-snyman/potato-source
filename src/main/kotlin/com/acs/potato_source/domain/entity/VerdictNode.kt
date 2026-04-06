package com.acs.potato_source.domain.entity

data class VerdictNode(
    val verdict: String,
    val score: Int? = null
) : RuleNode()
