package com.acs.potato_source.domain.entity

import java.util.UUID;

sealed class RuleNode {
    val id: String = UUID.randomUUID().toString()
}