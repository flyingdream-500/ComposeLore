package ru.sesh.composetraining.lore.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
     *
     * В Modifier имеет значение порядок операторов. Они применяются сверху вниз.
     *
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

    /**
     * Когда мы создаем свои Composable функции, рекомендуется добавлять в них параметр Modifier.
     *
     * Это позволяет менять размеры и параметры view снаружи,
     * например при необходимости получить кнопки во всю ширину экрана или в ширину контента внутри
     */
    @Composable
    fun MyButton(
        text: String,
        onClick: () -> Unit,
        modifier: Modifier = Modifier
    ) {
        Text(text = text,
            modifier = modifier.clickable(onClick = onClick)
                .border(1.dp, Color.Black, RoundedCornerShape(4.dp))
                .padding(8.dp)
        )
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    ComposableFunctions.HomeScreen()
}