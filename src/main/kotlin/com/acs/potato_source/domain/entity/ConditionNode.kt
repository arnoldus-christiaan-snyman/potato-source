package com.acs.potato_source.domain.entity

import com.acs.potato_source.domain.constant.Operator

data class ConditionNode(
    val field: String,
    val operator: Operator,
    val value: Any
) : RuleNode()
