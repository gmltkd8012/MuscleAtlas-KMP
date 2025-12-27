package com.rebuilding.muscleatlas.login.component

import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import io.github.jan.supabase.auth.providers.Apple
import io.github.jan.supabase.auth.providers.Google
import io.github.jan.supabase.auth.providers.Kakao
import io.github.jan.supabase.auth.providers.OAuthProvider
import io.github.jan.supabase.compose.auth.ui.ProviderButtonContent
import io.github.jan.supabase.compose.auth.ui.annotations.AuthUiExperimental
import muscleatlas.feature.login.generated.resources.Res
import muscleatlas.feature.login.generated.resources.login_with
import org.jetbrains.compose.resources.stringResource

@OptIn(AuthUiExperimental::class)
@Composable
fun SignInButton(
    onClick: () -> Unit,
    oAuthProvider: OAuthProvider,
    modifier: Modifier = Modifier,
) {
    val buttonProperties = oAuthProvider.toButtonProperties()

    Button(
        onClick = onClick,
        colors =
            ButtonDefaults.buttonColors(
                containerColor = buttonProperties.containerColor,
                contentColor = buttonProperties.contentColor,
            ),
        border = if (oAuthProvider is Google) ButtonDefaults.outlinedButtonBorder() else null,
        modifier = modifier.then(Modifier.height(buttonHeight)),
    ) {
        ProviderButtonContent(
            oAuthProvider,
            text = stringResource(Res.string.login_with, oAuthProvider.name.capitalize(Locale.current)),
        )
    }
}

data class SignInButtonProperties(
    val containerColor: Color,
    val contentColor: Color,
)

private fun OAuthProvider.toButtonProperties(): SignInButtonProperties {
    return when (this) {
        is Google ->
            SignInButtonProperties(
                containerColor = Color.White,
                contentColor = Color.Black,
            )

        is Apple ->
            SignInButtonProperties(
                containerColor = Color.Black,
                contentColor = Color.White,
            )

        is Kakao ->
            SignInButtonProperties(
                containerColor = Color(0xFFFEE500),
                contentColor = Color(0xD9000000),
            )

        else ->
            SignInButtonProperties(
                containerColor = Color.LightGray,
                contentColor = Color.Black,
            )
    }
}

private val buttonHeight = 48.dp