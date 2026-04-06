package com.acs.potato_source.domain.port

import com.acs.potato_source.domain.entity.Rule

interface RuleSearchPort {
    fun indexRule(rule: Rule)
    fun search(category: String?, subcategory: String?, activeOnly: Boolean): List<Rule>
}