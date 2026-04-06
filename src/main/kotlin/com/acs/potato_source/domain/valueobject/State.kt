package com.acs.potato_source.domain.valueobject

import com.acs.potato_source.domain.constant.Status
import java.time.Instant

data class State(
    val effectiveDate: Instant,
    val expiryDate: Instant,
    var modifiedDate: Instant,
    val status: Status
)
