package com.kyeongilhan.photoexplorer.ui.favorites

import androidx.compose.material3.SnackbarHostState
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.kyeongilhan.photoexplorer.domain.favorite.model.FavoritePhoto
import com.kyeongilhan.photoexplorer.domain.photo.model.Photo
import com.kyeongilhan.photoexplorer.presentation.favorites.FavoritesState
import com.kyeongilhan.photoexplorer.ui.MISSING_IMAGE_PATH
import com.kyeongilhan.photoexplorer.ui.R
import com.kyeongilhan.photoexplorer.ui.string
import com.kyeongilhan.photoexplorer.ui.testPhoto
import com.kyeongilhan.photoexplorer.ui.theme.PhotoExplorerTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

// 좋아요 목록 화면: 빈 목록 문구, 카드와 하트를 눌렀을 때 넘어가는 값
@RunWith(AndroidJUnit4::class)
@Config(sdk = [35])
class FavoritesScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    private fun setScreen(
        state: FavoritesState,
        onPhotoClick: (String) -> Unit = {},
        onRemoveClick: (Photo) -> Unit = {},
    ) {
        val snackbarHostState = SnackbarHostState()
        composeRule.setContent {
            PhotoExplorerTheme {
                FavoritesScreen(
                    state = state,
                    onBackClick = {},
                    onPhotoClick = onPhotoClick,
                    onRemoveClick = onRemoveClick,
                    snackbarHostState = snackbarHostState,
                )
            }
        }
    }

    private fun favorite(photo: Photo) =
        FavoritePhoto(photo = photo, localImagePath = MISSING_IMAGE_PATH, savedAt = 0L)

    // 좋아요가 없으면 안내 문구
    @Test
    fun testEmptyShowsEmptyMessage() {
        setScreen(FavoritesState(isLoading = false, favorites = emptyList()))

        composeRule.onNodeWithText(string(R.string.favorites_empty)).assertIsDisplayed()
    }

    // 목록이 있으면 안내 문구는 없고 작가 이름이 보인다
    @Test
    fun testListShowsAuthorName() {
        setScreen(FavoritesState(isLoading = false, favorites = listOf(favorite(testPhoto()))))

        composeRule.onNodeWithText(string(R.string.author_by, "홍길동")).assertIsDisplayed()
        composeRule.onNodeWithText(string(R.string.favorites_empty)).assertDoesNotExist()
    }

    // 카드의 하트를 누르면 그 사진이 onRemoveClick 으로 넘어온다
    @Test
    fun testHeartClickPassesPhotoToOnRemove() {
        val photo = testPhoto(id = "p1")
        var removed: Photo? = null
        setScreen(
            state = FavoritesState(isLoading = false, favorites = listOf(favorite(photo))),
            onRemoveClick = { removed = it },
        )

        composeRule.onNodeWithContentDescription(string(R.string.favorite_remove)).performClick()

        assertThat(removed).isEqualTo(photo)
    }

    // 카드를 누르면 사진 id 가 onPhotoClick 으로 넘어온다 (하트의 콜백은 불리지 않는다)
    @Test
    fun testCardClickPassesPhotoIdToOnPhotoClick() {
        var openedId: String? = null
        var removed: Photo? = null
        setScreen(
            state = FavoritesState(isLoading = false, favorites = listOf(favorite(testPhoto(id = "p1")))),
            onPhotoClick = { openedId = it },
            onRemoveClick = { removed = it },
        )

        composeRule.onNodeWithText(string(R.string.author_by, "홍길동")).performClick()

        assertThat(openedId).isEqualTo("p1")
        assertThat(removed).isNull()
    }
}
