package com.kyeongilhan.photoexplorer.ui.common

import androidx.compose.material3.SnackbarHostState
import kotlinx.coroutines.withTimeoutOrNull
import kotlin.time.Duration.Companion.milliseconds

// 짧게 보여 주고 닫는 스낵바
suspend fun SnackbarHostState.showShortSnackbar(message: String, durationMillis: Long = 1_500L) {
    // 떠 있는 게 있으면 바로 닫고 새 메시지로
    currentSnackbarData?.dismiss()
    withTimeoutOrNull(durationMillis.milliseconds) { showSnackbar(message) }
}