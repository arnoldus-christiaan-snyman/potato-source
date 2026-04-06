package com.acs.potato_source.domain.service

import com.acs.potato_source.domain.constant.LogicalOperator
import com.acs.potato_source.domain.constant.Operator
import com.acs.potato_source.domain.constant.Status
import com.acs.potato_source.domain.entity.AppendNode
import com.acs.potato_source.domain.entity.ConditionNode
import com.acs.potato_source.domain.entity.LogicalNode
import com.acs.potato_source.domain.entity.OverrideNode
import com.acs.potato_source.domain.entity.Rule
import com.acs.potato_source.domain.entity.RuleNode
import com.acs.potato_source.domain.entity.VerdictNode
import com.acs.potato_source.domain.exception.RuleEvaluationException

class RuleEvaluator {

    fun evaluate(rule: Rule, fact: Map<String, Any>): EvaluationResult {
        if (!rule.state.status.equals(Status.EFFECTIVE) || rule.state.effectiveDate.isAfter(java.time.Instant.now())) {
            return EvaluationResult.Skipped("Rule is inactive or not yet effective")
        }

        // 1. Resolve Overrides and Appends (Compile the tree)
        val compiledNode = RuleCompiler().compile(rule.rootNode)

        // 2. Evaluate the compiled tree
        return executeNode(compiledNode, fact)
    }

    private fun executeNode(node: RuleNode, fact: Map<String, Any>): EvaluationResult {
        return when (node) {
            is VerdictNode -> EvaluationResult.Match(node.verdict)

            is ConditionNode -> {
                val factValue = fact[node.field] ?: return EvaluationResult.NoMatch
                val matches = compare(factValue, node.operator, node.value)
                if (matches) EvaluationResult.ConditionMet else EvaluationResult.NoMatch
            }

            is LogicalNode -> {
                when (node.operator) {
                    LogicalOperator.AND -> {
                        var lastVerdict: EvaluationResult.Match? = null
                        for (child in node.children) {
                            val result = executeNode(child, fact)
                            if (result is EvaluationResult.NoMatch) return EvaluationResult.NoMatch
                            if (result is EvaluationResult.Match) lastVerdict = result
                        }
                        lastVerdict ?: EvaluationResult.ConditionMet
                    }
                    LogicalOperator.OR -> {
                        for (child in node.children) {
                            val result = executeNode(child, fact)
                            if (result !is EvaluationResult.NoMatch) return result
                        }
                        EvaluationResult.NoMatch
                    }
                    else -> EvaluationResult.NoMatch
                }
            }
            is OverrideNode, is AppendNode -> throw RuleEvaluationException("Modifier nodes must be resolved before execution.")
        }
    }

    private fun compare(factValue: Any, operator: Operator, targetValue: Any): Boolean {
        // Handling basic comparable logic (Simplified for integers/strings)
        if (factValue is Number && targetValue is Number) {
            val f = factValue.toDouble()
            val t = targetValue.toDouble()
            return when (operator) {
                Operator.EQUALS -> f == t
                Operator.NOT_EQUALS -> f != t
                Operator.GREATER_THAN -> f > t
                Operator.GREATER_THAN_EQUALS -> f >= t
                Operator.LESS_THAN -> f < t
                Operator.LESS_THAN_EQUALS -> f <= t
            }
        }

        val fStr = factValue.toString()
        val tStr = targetValue.toString()
        return when (operator) {
            Operator.EQUALS -> fStr == tStr
            Operator.NOT_EQUALS -> fStr != tStr
            else -> false // String GT/LT skipped for brevity
        }
    }

}