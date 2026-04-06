package com.acs.potato_source.domain.entity

import com.acs.potato_source.domain.constant.LogicalOperator

data class LogicalNode(
    val operator: LogicalOperator,
    val children: List<RuleNode>
) : RuleNode()
