package ru.sesh.composetraining.lore.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ru.sesh.composetraining.R

@Composable
fun ImageScreen() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                // закругленные углы
                .background(color = Color.Cyan, shape = RoundedCornerShape(16.dp))
                // срезанные углы
                .background(color = Color.Cyan, shape = CutCornerShape(16.dp))
                // круглый
                .background(color = Color.Cyan, shape = CircleShape)
                // градиент
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color.Red,
                            Color.Yellow,
                            Color.Green
                        )
                    ),
                    shape = RoundedCornerShape(16.dp)
                )
                // С помощью border мы можем задать как будет выглядеть граница нашего элемента
                .border(
                    width = 2.dp, color = Color.DarkGray
                )

        )

        // Для изображений у нас есть два элемента: Image и Icon
        // Icon для векторных изображений
        Icon(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = null
        )
        // Image для растровых изображений
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = null
        )

        // Библиотека Coil для загрузки изображений
        AsyncImage(
            model = "https://developer.android.com/images/android-go/next-billion-users_856.png",
            contentDescription = null
        )
    }
}