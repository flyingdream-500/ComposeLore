package ru.sesh.composetraining.lore

import androidx.compose.foundation.clickable
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier

/**
 * State помогает Composable функции понять, что пора обновлять данные на экране.
 */
object StateLore {

    private var count = mutableStateOf(0)
    private var text = mutableStateOf("")

    @Composable
    fun MainScreen() {
        HomeScreen(
            counter = count,
            onCounterClick = { count.value++ },
            text = text,
            onValueChange = { newText ->
                text.value = newText
            }
        )
    }

    /**
     * Если мы хотим, чтобы Composable функция отобразила новое значение,
     * необходимо вызвать эту Composable функцию еще раз. Только в этом случае мы увидим на экране изменения.
     * Composable функция умеет перезапускать сама себя.
     * Но для этого необходимо использовать специальный контейнер State при передаче данных в Composable функцию.
     *
     * State это контейнер, который содержит значение. Когда мы меняем значение в State, то Composable функция узнает об этом и перезапустится,
     * чтобы считать это новое значение и отобразить его.
     *
     * Когда мы в Composable функции читаем значение из State,
     * функция понимает это и под капотом подписывается на факт изменения значения в этом State,
     * чтобы выполнить перезапуск себя при изменении значения.
     * В результате, когда мы меняем значение счетчика в counter: State<Int>, то Composable функция HomeScreen перезапустится.
     *
     * Без перезапуска Composable функции никаких изменений на экране мы не увидим.
     *
     * Вся эта магия с перезапуском обеспечивается Compose компилятором, который добавляет в наши Composable функции специальный код, отвечающий за подписку на State.
     *
     *  Т.е. когда значение State меняется, функция получает не новое значение State, а просто уведомление о том, что State изменился.
     *  И далее уже функция перезапускает себя, чтобы считать из State это новое значение.
     *
     *  Composable функция получает на вход State и отображает данные из него.
     *  Обратно она шлет действия пользователя, например, клики.
     *  Это приводит к изменениям в State, которые снова придут в функцию. И т.д. по кругу.
     *
     *  Основная мысль в том, что State находится снаружи, а не внутри функции. Это стандартный подход в Compose, и называется он - State hoisting.
     *
     *  Для Checkbox и EditText так же необходимо использовать State
     *
     */
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun HomeScreen(
        text: State<String>,
        counter: State<Int>,
        onCounterClick: () -> Unit,
        onValueChange: (String) -> Unit
    ) {
        Text(text = "Clicks: ${counter.value}", modifier = Modifier.clickable(onClick = onCounterClick))
        OutlinedTextField(value = text.value, onValueChange = onValueChange)
    }
}