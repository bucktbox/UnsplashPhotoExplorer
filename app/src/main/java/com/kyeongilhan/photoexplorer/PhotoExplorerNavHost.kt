package com.kyeongilhan.photoexplorer

import androidx.compose.runtime.Composable
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
        composable<PhotoListDestination> {
            PhotoListWrapper(
                onPhotoClick = { photoId -> navController.navigate(PhotoDetailDestination(photoId)) },
                onFavoritesClick = { navController.navigate(FavoritesDestination) },
            )
        }
        composable<PhotoDetailDestination> {
            PhotoDetailWrapper(onBackClick = { navController.popBackStack() })
        }
        composable<FavoritesDestination> {
            FavoritesWrapper(
                onBackClick = { navController.popBackStack() },
                onPhotoClick = { photoId -> navController.navigate(PhotoDetailDestination(photoId)) },
            )
        }
    }
}