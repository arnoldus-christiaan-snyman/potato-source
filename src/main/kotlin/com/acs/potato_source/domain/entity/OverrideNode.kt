package com.acs.potato_source.domain.entity

data class OverrideNode(
    val targetNodeId: String,
    val replacementNode: RuleNode
) : RuleNode()
