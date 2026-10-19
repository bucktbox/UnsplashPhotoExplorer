package com.kyeongilhan.photoexplorer.ui.common

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kyeongilhan.photoexplorer.dataresource.DomainException
import com.kyeongilhan.photoexplorer.ui.R
import com.kyeongilhan.photoexplorer.ui.theme.PhotoExplorerTheme

@Composable
fun ErrorView(
    throwable: Throwable,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = stringResource(throwable.toErrorMessageRes()), textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(12.dp))
        Button(onClick = onRetry) {
            Text(text = stringResource(R.string.retry))
        }
    }
}

// 에러 종류별 문구
@StringRes
fun Throwable.toErrorMessageRes(): Int = when (this) {
    is DomainException.Network -> R.string.error_network
    is DomainException.Auth -> R.string.error_auth
    is DomainException.RateLimited -> R.string.error_rate_limited
    is DomainException.NotFound -> R.string.error_not_found
    else -> R.string.error_unknown
}


@Preview(showBackground = true)
@Composable
fun ErrorViewPreview() {
    PhotoExplorerTheme {
        ErrorView(throwable = DomainException.Network(), onRetry = {})
    }
}