package com.acs.potato_source.domain.port

import com.acs.potato_source.domain.entity.Rule

interface RuleRepositoryPort {
    fun save(rule: Rule): Rule
    fun findByCode(code: String): Rule?
}