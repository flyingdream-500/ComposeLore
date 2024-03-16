package ru.sesh.composetraining.lore.compose

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.RememberObserver
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.sesh.composetraining.utils.TAG

/**
 * Каждая Composable функция проходит три lifecycle этапа:
 *
 * Enter the Composition - функция была вызвана первый раз, ее содержимое отобразилось на экране. Она теперь является частью Composition.
 *
 * Recompose - функция была перевызвана, т.к. изменились входные значения. Это могло привести к изменениям на экране. Функция продолжает оставаться частью Composition.
 *
 * Leave the Composition - содержимое функции больше не отображается на экране, т.к. эта функция теперь не вызывается. Функция более не является частью Composition.
 */

/**
 * Мы можем в onRemembered вызвать какой-то свой код, который должен выполниться один раз при первом вызове Composable кода,
 * например - при старте экрана.
 *
 * Или мы можем стартовать корутину в onRemembered и отменить ее в onForgotten.
 * Или подписаться на что-либо в onRemembered и отписаться в onForgotten.
 * Таким образом жизненный цикл корутины или подписки совпадет с жизненным циклом Composable кода.
 */
class MyRememberObserver : RememberObserver {
    init {}

    // remember создал и запомнил объект
    override fun onRemembered() {}

    // remember забыл объект
    override fun onForgotten() {}

    // что-то в Compose пошло не так, значение будет забыто (например краш)
    override fun onAbandoned() {}
}

@Composable
fun RememberedScreen() {
    val myRememberObserver = remember { MyRememberObserver() }
}

/**
 * Когда мы включим чекбокс, вызовется функция LaunchedEffect,
 * объект LaunchedEffectImpl в remember получит вызов onRemembered и стартует корутину.
 *
 * Когда мы выключим чекбокс, Compose определит, что LaunchedEffect больше не вызывается,
 * объект LaunchedEffectImpl в remember получит вызов onForgotten и отменит корутину. Наш код прекратит выполняться.
 *
 * При смене значения key, remember вызовет onForgotten для текущего объекта, создаст новый объект и вызовет у него onRemembered.
 * Т.е. как будто мы убрали remember из Composition и поместили обратно.
 * Таким образом старая корутина в LaunchedEffectImpl отменится, а новая стартует.
 * Это может быть полезно, если мы извне получили новое значение и хотим заново стартовать корутину.
 *
 * Если вам такая опция не нужна, то просто передавайте в качестве key значение true, Unit, null
 */
@Composable
fun LaunchEffectScreen() {
    Column {
        var checked by remember { mutableStateOf(false) }
        Checkbox(checked = checked, onCheckedChange = { checked = it })
        if (checked) {
            LaunchedEffect(key1 = Unit) {

            }
        }
    }
}

/**
 * Функция DisposableEffect так же, как и LaunchedEffect, позволяет выполнить код один раз при первом вызове Composable кода. Но есть пара отличий.
 * Код будет выполнен не в корутине. И у нас есть возможность повесить свой колбэк на onForgotten.
 * Т.е. эта функция подходит, когда нам нужно подписаться на что-либо при старте экрана, а при закрытии - отписаться.
 */

@Composable
fun BroadcastReceiver(intentFilter: IntentFilter, onReceive: (Intent) -> Unit) {
    val context = LocalContext.current
    DisposableEffect(context) {
        val broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                onReceive(intent)
            }
        }
        context.registerReceiver(broadcastReceiver, intentFilter)
        onDispose {
            context.unregisterReceiver(broadcastReceiver)
        }
    }
}

/**
 * Чекбокс включен по умолчанию. Внутри IF мы получаем scope от rememberCoroutineScope и используем его,
 * чтобы запустить корутину по нажатию на текст. Корутина считает секунды и выводит их в лог.
 *
 * Что произойдет, когда мы выключим чекбокс?
 * rememberCoroutineScope покинет Composition, а значит отменит scope, и, тем самым, и нашу корутину.
 */
@Composable
fun HomeScreen() {
    Column {
        var checked by remember { mutableStateOf(true) }
        Checkbox(checked = checked, onCheckedChange = { checked = it })

        if (checked) {
            val scope = rememberCoroutineScope()
            Text(text = "Click", modifier = Modifier.clickable {
                scope.launch {
                    var count = 0
                    while (true) {
                        Log.d(TAG, "count = ${count++}")
                        delay(1000)
                    }
                }
            })
        }
    }
}

/**
 * Функция produceState создает для нас State и запускает корутину, в которой мы можем менять значение этого State
 * Внутри используется LaunchedEffect
 *
 *
 * В качестве полезного применения produceState обычно приводят пример с загрузкой данных.
 * Мы стартуем корутину, постим в value статус Loading и начинаем в этой корутине что-то загружать.
 * После завершения загрузки постим в value результат Result<...> или ошибку Error.
 */
@Composable
fun ProduceStateScreen() {
    val count by produceState(initialValue = 0) {
        while (true) {
            delay(1000)
            value++
        }
        // Функция awaitDispose может быть использована для выполнения каких-либо операций при отмене корутины
        awaitDispose {

        }
    }

    Text(text = "count = $count")
}

/**
 * Функция rememberUpdatedState позволяет создать State из переменной.
 * Это может пригодиться, когда у нас в Composable функцию передается параметр, который периодически получает новое значение.
 *
 *
 * Если Composable функция читает State, то она подписывается на изменение этого State.
 * Когда значение в State меняется, Composable функция перезапускается и читает новое значение State.
 *
 * Корутина в LaunchedEffect НЕ подписывается на positionState, потому что она не Composable функция.
 * Соответственно, когда значение positionState меняется, корутина не перезапускается, чтобы считать новое значение.
 * Она просто в цикле раз в секунду читает значение из positionState.
 */
@Composable
fun RememberUpdatedStateScreen(
) {
    Column {
        var sliderPosition by remember { mutableFloatStateOf(1f) }
        Slider(
            value = sliderPosition,
            valueRange = 1f..10f,
            onValueChange = { sliderPosition = it })

        TrackPosition(position = sliderPosition)
    }

}

/**
 * Корутина в LaunchedEffect НЕ подписывается на positionState, потому что она не Composable функция.
 * Соответственно, когда значение positionState меняется, корутина не перезапускается, чтобы считать новое значение.
 * Она просто в цикле раз в секунду читает значение из positionState.
 */
@Composable
fun TrackPosition(position: Float) {

    val positionState = rememberUpdatedState(newValue = position)

    LaunchedEffect(key1 = Unit) {
        while(true) {
            delay(1000)
            Log.d(TAG, "track position ${positionState.value}")
        }
    }
}

/**
 * SideEffect
 * Функция SideEffect гарантирует, что код будет выполнен только в случае успешного выполнения Composable кода.
 * Если же что-то пошло не так, что SideEffect не выполнится.
 *
 * В данном методе код внутри SideEffect не выполнится
 */
@Composable
fun SideEffectScreen() {
    Column {
        var checked by remember { mutableStateOf(false) }
        Checkbox(checked = checked, onCheckedChange = { checked = it })
        if (checked) {
            Log.d(TAG, "HomeScreen log")
            SideEffect {
                Log.d(TAG, "HomeScreen log in SideEffect")
            }
            val a = 1/ 0
        }
    }
}


