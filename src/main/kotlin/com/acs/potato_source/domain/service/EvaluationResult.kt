package com.acs.potato_source.domain.service

sealed class EvaluationResult {
    data class Match(val verdict: String) : EvaluationResult()
    object ConditionMet : EvaluationResult()
    object NoMatch : EvaluationResult()
    data class Skipped(val reason: String) : EvaluationResult()
}