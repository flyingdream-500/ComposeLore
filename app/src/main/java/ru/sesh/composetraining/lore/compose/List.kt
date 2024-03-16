package ru.sesh.composetraining.lore.compose

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.sesh.composetraining.utils.TAG
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.graphics.Color

@Composable
fun SomeItem(text: String) {
    Log.d(TAG, "SomeItem $text")
    Text(
        text = text, fontSize = 20.sp,
        modifier = Modifier.padding(16.dp)
    )
}

/**
 * Чтобы отличать между собой разные экземпляры одной и той же Composable функции,
 * система использует call site. Так называется место в нашем коде, где мы вызываем функцию.
 * Для простоты мы можем считать, что это, например, имя файла + номер строки.
 *
 * key может повлиять на формирование значения call site. Вместо порядкового номера вызова функции мы можем использовать свое значение.
 * Это поможет системе правильно сопоставлять вызовы и экземпляры даже при изменении порядка данных в списке.
 */
@Composable
fun ListScreen() {
    val list = remember {
        mutableStateListOf("list1", "list2", "list3")
    }

    val scrollState = rememberScrollState()

    // Используя Column все данные в списке отображаются, что не очень правильно
    Column(modifier = Modifier.verticalScroll(scrollState)) {
        // При добавлении элемента в конец списка произойдет рекомпозиция только добавленного элемента
        TextButton(onClick = {
            list.add("Item ${list.size + 1}")
        }) {
            Text(text = "Append")
        }
        list.forEach { item ->
            /**
             * Для call site используется порядковый номер вызова функции
             * Если использовать key, то он добавит в call site наше значение вместо порядкового номера вызова.
             *
             * Используем key для избежания лишних рекомпозиций
             */
            key(item) {
                SomeItem(text = item)
            }
        }
    }
}

@Composable
fun LazyListScreen() {
    val list = remember {
        mutableStateListOf("list1", "list2", "list3")
    }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        // Отступы для по вертикали для первого и последнего элемента
        contentPadding = PaddingValues(16.dp),
        modifier = Modifier
            .border(width = 2.dp, color = Color.Green)
    ) {
        items(list) { item ->
            key(item) {
                SomeItem(text = item)
            }

        }
    }
}

@Preview
@Composable
fun ListScreenPreview() {
    ListScreen()
}