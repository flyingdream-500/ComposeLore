package ru.sesh.composetraining.lore.compose

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner

/**
 * Обычно мы используем параметры Composable функции, чтобы передать в эту функцию все необходимое.
 * Но есть и другой путь. У Compose под капотом есть хранилище CompositionLocal. Оно похоже на Map.
 * Мы можем поместить туда значение в корневой Composable функции, и это значение будет доступно в дочерних функциях на любом уровне вложенности.
 * Это избавит нас от необходимости тащить это значение, как параметр, через все промежуточные функции на пути от корневой до нужной нам.
 *
 * Это должно быть скорее исключением. Самый распространенный пример использования CompositionLocal - это дизайн.
 * Мы в корневой функции задаем цвета, размеры и т.п. И любая функция сможет их считать и использовать.
 *
 *  Для CompositionLocal ключей рекомендуется использовать префикс Local.
 *
 *  Когда мы в хранилище помещаем значение, то под капотом оно хранится там в полноценной State обертке.
 *  Когда мы читаем значение с помощью current, то на самом деле мы читаем значение State.
 *
 *  compositionLocalOf - при смене значения в хранилище, перевызов только функции, использующих значение из CompositionLocalProvider
 *  staticCompositionLocalOf - при смене значения в хранилище, мы получаем принудительный перевызов всех функций в блоке CompositionLocalProvider
 *  Именно поэтому функцию staticCompositionLocalOf рекомендуется использовать, если мы редко меняем значение в хранилище,
 *  а лучше - вообще не меняем. В таких случаях использование этой функции считается более производительным.
 *  Скорее всего потому, что нет кучи подписок на State.
 */
@Composable
fun CompositionLocalScreen() {
    // LocalFontStyle - это CompositionLocal ключ. Значение по умолчанию (FontStyle.Normal)
    val LocalFontStyle = compositionLocalOf { FontStyle.Normal }
    val LocalFontStyleStatic = staticCompositionLocalOf { FontStyle.Normal }

    // localFontStyle - пара ключ-значение
    val localFontStyle = LocalFontStyle.provides(FontStyle.Italic)
    // CompositionLocalProvider - scope для пары localFontStyle. Значение localFontStyle будет доступно только Composable функциям, вызванным в этом блоке (и их дочерним функциям).
    CompositionLocalProvider(localFontStyle) {
        Text(text = "MyText", fontStyle = LocalFontStyle.current)
    }

    // Infix запись
    CompositionLocalProvider(LocalFontStyle provides FontStyle.Normal) {
        Text(
            text = "MyText",
            fontStyle = LocalFontStyle.current
        )
    }
}

/**
 * Compose и сам активно использует CompositionLocal технику, чтобы предоставлять нам в UI некоторые важные объекты.
 *
 * Если начать вводить Local, то студия покажет, какие варианты нам доступны из коробки.
 */
@Composable
fun LocalScreen() {
    LocalContext
    LocalViewModelStoreOwner
}

/**
 * Можем добавлять data классы в CompositionLocalProvider
 *
 * CompositionLocalProvider дает возможность поместить в хранилище два и более значения
 */
@Composable
fun AddDataClassToCompositionLocalProvider() {
    data class MyTextStyle(
        val color: Color = Color.Unspecified,
        val fontSize: TextUnit = 12.sp,
        val align: TextAlign = TextAlign.Left
    )

    val myTextStyle = MyTextStyle(
        color = Color.Green,
        fontSize = 16.sp
    )

    val LocalMyTextStyle = compositionLocalOf { MyTextStyle() }
    CompositionLocalProvider(LocalMyTextStyle provides myTextStyle) {
        val dataClass = LocalMyTextStyle.current
    }
}