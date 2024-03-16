package ru.sesh.composetraining.lore

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

/**
 * Recomposition - это и есть перезапуск Composable функции.
 * Но у него есть еще один важный момент.
 * Когда Composable функция перезапускается, она снова выполняет код внутри себя.
 * А это значит, что она снова запускает все Composable функции, которые вызываются внутри нее.
 * А эти функции вызывают свои вложенные функции и так далее.
 * В итоге при перезапуске корневой Composable функции, мы получим перезапуск всего дерева функций.
 * Кажется, это не очень правильно с точки зрения производительности. Но все не так плохо.
 * Механизм Recomposition автоматически определяет, есть ли необходимость перезапускать Composable функцию.
 * Ведь если ее входные данные не менялись, то ее перезапуск не имеет смысла. Она покажет на экране то же самое, что и раньше.
 *
 * Recomposition будет перезапускать только те функции, которые читают State(state.value).
 * А функции, которые просто передают State дальше - не перезапускаются.
 */

@Composable
fun HomeScreen(
    counter: State<Int>,
    onCounterClick: () -> Unit
) {
    // Перезапускается каждый раз при смене State
    val counterValue = counter.value
    Log.d("TAG", "HomeScreen")
    Column {
        // Так же перезапускается с новыми значениями
        ClickCounter(counterValue = counterValue, onCounterClick = onCounterClick)
        // Рекомпозиция только тогда, когда меняется значение
        InfoText(text = if (counterValue < 3) "More" else "Enough")
    }
}

@Composable
fun ClickCounter(
    counterValue: Int,
    onCounterClick: () -> Unit
) {
    Log.d("TAG", "ClickCounter $counterValue")
    Text(
        text = "Clicks: $counterValue",
        modifier = Modifier.clickable {
            Log.d("TAG", "--- click ---")
            onCounterClick()
        }
    )
}

@Composable
fun InfoText(text: String) {
    Log.d("TAG", "InfoText $text")
    Text(text = text, fontSize = 24.sp)
}