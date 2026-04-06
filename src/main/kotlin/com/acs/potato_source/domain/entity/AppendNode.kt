package com.acs.potato_source.domain.entity

data class AppendNode(
    val targetLogicalNodeId: String,
    val additionalNode: RuleNode
) : RuleNode()
