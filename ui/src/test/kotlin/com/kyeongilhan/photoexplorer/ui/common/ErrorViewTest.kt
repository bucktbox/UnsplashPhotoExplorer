package com.kyeongilhan.photoexplorer.ui.common

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.kyeongilhan.photoexplorer.dataresource.DomainException
import com.kyeongilhan.photoexplorer.ui.R
import com.kyeongilhan.photoexplorer.ui.string
import com.kyeongilhan.photoexplorer.ui.theme.PhotoExplorerTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

// 에러 화면: 에러 종류에 맞는 문구가 보이는지, 다시 시도 버튼이 콜백을 부르는지
// 기기 없이 JVM(Robolectric)에서 돈다
@RunWith(AndroidJUnit4::class)
@Config(sdk = [35])
class ErrorViewTest {

    @get:Rule
    val composeRule = createComposeRule()

    // 네트워크 에러면 "인터넷 연결을 확인해 주세요"
    @Test
    fun testNetworkErrorShowsNetworkMessage() {
        composeRule.setContent {
            PhotoExplorerTheme { ErrorView(throwable = DomainException.Network(), onRetry = {}) }
        }

        composeRule.onNodeWithText(string(R.string.error_network)).assertIsDisplayed()
    }

    // 한도 초과면 한도 문구 (에러 종류마다 문구가 갈리는지)
    @Test
    fun testRateLimitedErrorShowsRateLimitMessage() {
        composeRule.setContent {
            PhotoExplorerTheme { ErrorView(throwable = DomainException.RateLimited(), onRetry = {}) }
        }

        composeRule.onNodeWithText(string(R.string.error_rate_limited)).assertIsDisplayed()
        composeRule.onNodeWithText(string(R.string.error_network)).assertDoesNotExist()
    }

    // DomainException 이 아닌 예외는 기본 문구
    @Test
    fun testOtherThrowableShowsUnknownMessage() {
        composeRule.setContent {
            PhotoExplorerTheme { ErrorView(throwable = IllegalStateException("boom"), onRetry = {}) }
        }

        composeRule.onNodeWithText(string(R.string.error_unknown)).assertIsDisplayed()
    }

    // 다시 시도를 누르면 onRetry 가 한 번 불린다
    @Test
    fun testRetryButtonCallsOnRetry() {
        var retryCount = 0
        composeRule.setContent {
            PhotoExplorerTheme { ErrorView(throwable = DomainException.Network(), onRetry = { retryCount++ }) }
        }

        composeRule.onNodeWithText(string(R.string.retry)).performClick()

        assertThat(retryCount).isEqualTo(1)
    }
}
