import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Color(0xFFBB86FC),
    primaryVariant = Color(0xFF3700B3),
    secondary = Color(0xFF495057),
    background = Color(0xFF303030),
    surface = Color(0xFF383838),
    onPrimary = Color(0xFFCED4DA),
    onSecondary = Color(0xFFCED4DA),
    onBackground = Color(0xFFCED4DA),
    onSurface = Color(0xFFCED4DA)
)

@Composable
fun AppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = DarkColorPalette,
        content = content
    )
}