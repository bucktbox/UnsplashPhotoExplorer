package com.kyeongilhan.photoexplorer.ui.photodetail

import androidx.compose.material3.SnackbarHostState
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.kyeongilhan.photoexplorer.dataresource.DomainException
import com.kyeongilhan.photoexplorer.domain.photo.model.PhotoDetailResult
import com.kyeongilhan.photoexplorer.presentation.photodetail.PhotoDetailState
import com.kyeongilhan.photoexplorer.ui.MISSING_IMAGE_PATH
import com.kyeongilhan.photoexplorer.ui.R
import com.kyeongilhan.photoexplorer.ui.string
import com.kyeongilhan.photoexplorer.ui.testPhoto
import com.kyeongilhan.photoexplorer.ui.theme.PhotoExplorerTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

// 상세 화면: Screen 이 ViewModel 없이 상태와 람다만 받아서, 원하는 상태를 바로 넣어 확인한다
// 로딩 / 에러 / 상세 세 상태에서 무엇이 보이고 무엇이 안 보이는지
@RunWith(AndroidJUnit4::class)
@Config(sdk = [35])
class PhotoDetailScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    private fun setScreen(
        state: PhotoDetailState,
        onFavoriteClick: () -> Unit = {},
        onRetry: () -> Unit = {},
    ) {
        val snackbarHostState = SnackbarHostState()
        composeRule.setContent {
            PhotoExplorerTheme {
                PhotoDetailScreen(
                    state = state,
                    onBackClick = {},
                    onFavoriteClick = onFavoriteClick,
                    onRetry = onRetry,
                    snackBarHostState = snackbarHostState,
                )
            }
        }
    }

    private fun detail(isFavorite: Boolean) = PhotoDetailResult(
        photo = testPhoto(),
        stats = null,
        isFavorite = isFavorite,
        localImagePath = MISSING_IMAGE_PATH,
    )

    // 로딩 중에는 하트가 없다 (상세를 받은 뒤에만 표시)
    @Test
    fun testLoadingHidesFavoriteButton() {
        // 로딩 표시는 끝없이 도는 애니메이션이라 시계를 멈춰 두고 본다
        composeRule.mainClock.autoAdvance = false
        setScreen(PhotoDetailState())

        composeRule.onNodeWithText(string(R.string.photo_detail)).assertIsDisplayed()
        composeRule.onNodeWithContentDescription(string(R.string.favorite_add)).assertDoesNotExist()
        composeRule.onNodeWithContentDescription(string(R.string.favorite_remove)).assertDoesNotExist()
    }

    // 에러면 문구와 다시 시도 버튼이 보이고, 하트는 없다. 다시 시도는 onRetry 를 부른다
    @Test
    fun testErrorShowsMessageAndRetry() {
        var retryCount = 0
        setScreen(
            state = PhotoDetailState(isLoading = false, error = DomainException.Network()),
            onRetry = { retryCount++ },
        )

        composeRule.onNodeWithText(string(R.string.error_network)).assertIsDisplayed()
        composeRule.onNodeWithContentDescription(string(R.string.favorite_add)).assertDoesNotExist()

        composeRule.onNodeWithText(string(R.string.retry)).performClick()

        assertThat(retryCount).isEqualTo(1)
    }

    // 상세를 받으면 작가 이름과 @아이디가 보이고, 하트를 누르면 onFavoriteClick 이 불린다
    @Test
    fun testDetailShowsAuthorAndFavoriteClick() {
        var favoriteClickCount = 0
        setScreen(
            state = PhotoDetailState(isLoading = false, detail = detail(isFavorite = false)),
            onFavoriteClick = { favoriteClickCount++ },
        )

        composeRule.onNodeWithText("홍길동").assertIsDisplayed()
        composeRule.onNodeWithText("@gildong").assertIsDisplayed()

        composeRule.onNodeWithContentDescription(string(R.string.favorite_add)).performClick()

        assertThat(favoriteClickCount).isEqualTo(1)
    }

    // 이미 좋아요한 사진이면 하트가 "좋아요 해제" 로 바뀐다
    @Test
    fun testFavoriteDetailShowsRemoveDescription() {
        setScreen(PhotoDetailState(isLoading = false, detail = detail(isFavorite = true)))

        composeRule.onNodeWithContentDescription(string(R.string.favorite_remove)).assertIsDisplayed()
        composeRule.onNodeWithContentDescription(string(R.string.favorite_add)).assertDoesNotExist()
    }
}
