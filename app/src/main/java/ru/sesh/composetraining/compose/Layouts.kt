package ru.sesh.composetraining.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.sesh.composetraining.R

@Composable
fun Layouts() {
    /**
     * Column - это аналог вертикального LinearLayout. Он выстроит элементы в вертикальный ряд.
     * Есть атрибуты для выравнивания: вертикального и горизонтального.
     *
     * Если нам надо поменять выравнивание для одного из элементов в Column, то мы можем это сделать через Modifier этого элемента.
     * Это будет иметь приоритет перед общим выравниванием в Column.
     *
     */
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Text(text = "Some text", modifier = Modifier.align(Alignment.Start))
    }

    /**
     * Row - это горизонтальный LinearLayout.
     * Соответственно он полностью аналогичен Column, только выстраивает элементы не по вертикали, а по горизонтали
     */
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Text(
            text = stringResource(id = R.string.app_name),
            modifier = Modifier.align(Alignment.Top)
        )
    }

    /**
     * Для отступов
     *
     * Если нам надо не просто добавить фиксированный отступ, а максимально раскидать элементы, то используем weight внутри Layout
     */
    Spacer(modifier = Modifier.width(8.dp))

    /**
     * Box - это аналог FrameLayout. Т.е. Layout в котором мы можем накладывать элементы друг на друга.
     */
    Box(
        modifier = Modifier.padding(horizontal = 20.dp)
    ) {
        Text("N", fontSize = 48.sp)
        Text("ame", modifier = Modifier.align(Alignment.BottomCenter))
    }
}