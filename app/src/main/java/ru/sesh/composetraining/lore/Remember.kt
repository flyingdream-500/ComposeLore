package ru.sesh.composetraining.lore

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import ru.sesh.composetraining.R

/**
 * Composable функция может перезапускаться.
 * Если вдруг мы в коде этой функции создаем какие-то объекты, то они будут создаваться заново при каждом перезапуске.
 * Функция remember позволяет решить эту проблему.
 * С ее помощью мы создаем объект только один раз при первом запуске Composable функции и получаем этот экземпляр во всех последующих перезапусках.
 *
 * Функция remember работает как кэш. Если объекта еще нет, то она его создаст. А если он уже был создан, она его вернет нам.
 */

@Composable
fun ClickCounterRemember(
    counterValue: Int,
    onCounterClick: () -> Unit,
    evenOddKey: String
) {
    // Каждый раз создается при вызове Composable функции
    val evenOddWithoutRemember = EvenOdd()
    // Сохраняем в кэш
    val evenOddWithRemember =  remember { EvenOdd() }
    // Сохраняем в кэш и создаем новый при смене ключа
    val evenOddWithRememberKey =  remember(evenOddKey) { EvenOdd() }
    Text(
        text = "Clicks: $counterValue ${evenOddWithRememberKey.check(counterValue)}",
        modifier = Modifier.clickable(onClick = onCounterClick)
    )
}

/**
 * Если state храним в Composable функции, то оборачиваем его в remember
 */
@Composable
fun ScreenWithState() {
    var checked by remember { mutableStateOf(false)}
    val checkedValue = checked
    Column {
        Row(verticalAlignment = CenterVertically) {
            Checkbox(checked = checkedValue, onCheckedChange = { value -> checked = value })
            Text("More details", fontSize = 18.sp)
        }
        if (checkedValue) {
            Text(text = stringResource(id = R.string.app_name))
        }
    }
}

class EvenOdd {
    fun check(value: Int): String {
        return if (value % 2 == 0) "even" else "odd"
    }
}