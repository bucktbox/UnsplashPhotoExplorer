package com.kyeongilhan.photoexplorer.ui.common

import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.kyeongilhan.photoexplorer.ui.R
import com.kyeongilhan.photoexplorer.ui.string
import com.kyeongilhan.photoexplorer.ui.theme.PhotoExplorerTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

// 하트 버튼: 상태에 따라 설명(contentDescription)이 바뀌는지, 누르면 콜백이 불리는지
// 하트는 아이콘이라 글자가 없어서 contentDescription 으로 찾는다 (스크린 리더가 읽는 값이기도 하다)
@RunWith(AndroidJUnit4::class)
@Config(sdk = [35])
class FavoriteIconButtonTest {

    @get:Rule
    val composeRule = createComposeRule()

    // 좋아요 전: "좋아요" 로 읽히고 누를 수 있다
    @Test
    fun testNotFavoriteShowsAddDescription() {
        composeRule.setContent {
            PhotoExplorerTheme { FavoriteIconButton(isFavorite = false, onClick = {}) }
        }

        composeRule.onNodeWithContentDescription(string(R.string.favorite_add)).assertHasClickAction()
        composeRule.onNodeWithContentDescription(string(R.string.favorite_remove)).assertDoesNotExist()
    }

    // 좋아요 후: "좋아요 해제" 로 읽힌다
    @Test
    fun testFavoriteShowsRemoveDescription() {
        composeRule.setContent {
            PhotoExplorerTheme { FavoriteIconButton(isFavorite = true, onClick = {}) }
        }

        composeRule.onNodeWithContentDescription(string(R.string.favorite_remove)).assertHasClickAction()
        composeRule.onNodeWithContentDescription(string(R.string.favorite_add)).assertDoesNotExist()
    }

    // 누르면 onClick 이 한 번 불린다
    @Test
    fun testClickCallsOnClick() {
        var clickCount = 0
        composeRule.setContent {
            PhotoExplorerTheme { FavoriteIconButton(isFavorite = false, onClick = { clickCount++ }) }
        }

        composeRule.onNodeWithContentDescription(string(R.string.favorite_add)).performClick()

        assertThat(clickCount).isEqualTo(1)
    }
}
