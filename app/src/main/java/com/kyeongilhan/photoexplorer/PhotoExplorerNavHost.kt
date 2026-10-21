package com.kyeongilhan.photoexplorer

import androidx.compose.runtime.Composable
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.dropUnlessResumed
import androidx.lifecycle.withResumed
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kyeongilhan.photoexplorer.ui.favorites.FavoritesWrapper
import com.kyeongilhan.photoexplorer.ui.navigation.FavoritesDestination
import com.kyeongilhan.photoexplorer.ui.navigation.PhotoDetailDestination
import com.kyeongilhan.photoexplorer.ui.navigation.PhotoListDestination
import com.kyeongilhan.photoexplorer.ui.photodetail.PhotoDetailWrapper
import com.kyeongilhan.photoexplorer.ui.photolist.PhotoListWrapper

@Composable
fun PhotoExplorerNavHost(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = PhotoListDestination) {
        composable<PhotoListDestination> { entry ->
            PhotoListWrapper(
                onPhotoClick = { photoId ->
                    // 떠 있는 상태에서만 이동 가능하게 변경 (실기 테스트 버그)
                    if (entry.lifecycle.currentState == Lifecycle.State.RESUMED) {
                        navController.navigate(PhotoDetailDestination(photoId))
                    }
                },
                // 두번 따닥 누르면 버그 발생 수정 (실기테스트 버그)
                onFavoritesClick = dropUnlessResumed  {
                    navController.navigate(FavoritesDestination)
                },
            )
        }
        composable<PhotoDetailDestination> {
            // 두번 따닥 누르면 버그 발생 수정 (실기테스트 버그)
            PhotoDetailWrapper(onBackClick = dropUnlessResumed  {
                navController.popBackStack()
            })
        }
        composable<FavoritesDestination> { entry ->
            FavoritesWrapper(
                // 두번 따닥 누르면 버그 발생 수정 (실기테스트 버그)
                onBackClick = dropUnlessResumed  {
                    navController.popBackStack()
                },
                onPhotoClick = { photoId ->
                    // 떠 있는 상태에서만 이동 가능하게 변경 (실기 테스트 버그)
                    if (entry.lifecycle.currentState == Lifecycle.State.RESUMED) {
                        navController.navigate(PhotoDetailDestination(photoId))
                    }
                },
            )
        }
    }
}