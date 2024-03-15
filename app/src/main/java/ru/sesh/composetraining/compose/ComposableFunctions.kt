package ru.sesh.composetraining.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

/**
 * @Composable аннотация похожа на корутинское suspend:
 * - она накладывает ограничения на возможные места вызова функции (setContent или другие Composable функции)
 * - при компиляции кода в эту функцию добавляются специальные системные параметры и вызовы
 * - мы можем создавать свои Composable функции
 */
object ComposableFunctions {

    /**
     * Modifier
     * Если мы хотим задать атрибут, специфичный для элемента,
     * то он будет в параметрах функции. Примеры: text и fontSize.
     * Такие атрибуты встречаются далеко не у всех элементов. Они специфичны для элемента Text.
     *
     * А если нам надо задать атрибут, который можно применить практически к любому элементу,
     * то они будут в Modifier. Примеры: width и background.
     */
    @Composable
    fun HomeScreen() {
        Text(
            text = "Home screen",
            fontSize = 32.sp,
            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth()
        )
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    ComposableFunctions.HomeScreen()
}