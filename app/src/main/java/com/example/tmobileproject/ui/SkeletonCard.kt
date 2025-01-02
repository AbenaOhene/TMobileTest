
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.composed
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntSize


@Composable
fun SkeletonLoader() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        repeat(3) { // Repeat for 2 cards as an example
            SkeletonCard()
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun SkeletonCard() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        // Title Placeholder (Text Line)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
                .shimmerEffect()
        )

        Spacer(modifier = Modifier.height(8.dp)) // Spacer between title and description

        // Description Placeholder (Text Line)
        Box(
            modifier = Modifier
                .fillMaxWidth(0.5f)  // Adjust width for description
                .height(14.dp)
                .shimmerEffect()
        )

        Spacer(modifier = Modifier.height(16.dp)) // Spacer before the image

        // Image Placeholder (Represents the image area)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .shimmerEffect()
        )

        Spacer(modifier = Modifier.height(16.dp)) // Extra space before next card
    }
}

fun Modifier.shimmerEffect(): Modifier = composed {
    var size by remember {
        mutableStateOf(IntSize.Zero)
    }
    val transition = rememberInfiniteTransition(label = "FloatAnimation")
    val startOffsetX by transition.animateFloat(
        initialValue = -2 * size.width.toFloat(),
        targetValue =  size.width.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1200,
                easing = EaseInOut
            )
        ),
        label = "FloatAnimation",
    )
    background(
        brush = Brush.linearGradient(
            colors = listOf(
                Color(0xFFB8B5B5),
                Color(0xFF8F8B8B),
                Color(0xFFB8B5B5),
            ),
            start = Offset(startOffsetX, 0f),
            end = Offset(startOffsetX + size.width.toFloat(), size.height.toFloat())
        )
    )
        .onGloballyPositioned {
            size = it.size
        }

}

