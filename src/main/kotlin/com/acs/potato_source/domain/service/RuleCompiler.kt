package com.acs.potato_source.domain.service

import com.acs.potato_source.domain.entity.AppendNode
import com.acs.potato_source.domain.entity.LogicalNode
import com.acs.potato_source.domain.entity.OverrideNode
import com.acs.potato_source.domain.entity.RuleNode

class RuleCompiler {

    fun compile(root: RuleNode): RuleNode {
        // In a real scenario, this would traverse the tree, find Modifiers,
        // and reconstruct the immutable tree. For demonstration, we handle basic replacement.
        return resolveModifiers(root, collectModifiers(root))
    }

    private fun collectModifiers(node: RuleNode): Map<String, RuleNode> {
        val modifiers = mutableMapOf<String, RuleNode>()
        // Recursive collection logic would go here
        return modifiers
    }

    private fun resolveModifiers(node: RuleNode, modifiers: Map<String, RuleNode>): RuleNode {
        // If this node ID has an override, replace it entirely
        if (modifiers.containsKey(node.id) && modifiers[node.id] is OverrideNode) {
            return (modifiers[node.id] as OverrideNode).replacementNode
        }

        return when (node) {
            is LogicalNode -> {
                // If there's an AppendNode for this logical group, add it
                val newChildren = node.children.map { resolveModifiers(it, modifiers) }.toMutableList()
                if (modifiers.containsKey(node.id) && modifiers[node.id] is AppendNode) {
                    newChildren.add((modifiers[node.id] as AppendNode).additionalNode)
                }
                node.copy(children = newChildren)
            }
            else -> node
        }
    }

}