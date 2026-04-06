package com.acs.potato_source.domain.entity

import com.acs.potato_source.domain.valueobject.Category
import com.acs.potato_source.domain.valueobject.Code
import com.acs.potato_source.domain.valueobject.Description
import com.acs.potato_source.domain.valueobject.State

data class Rule(
    val code: Code,
    val description: Description,
    val state: State,
    val category: Category,
    val subCategory: Category,
    val rootNode: RuleNode
)
