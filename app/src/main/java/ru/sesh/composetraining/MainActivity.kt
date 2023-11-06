package ru.sesh.composetraining

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.sesh.composetraining.ui.theme.ComposeTrainingTheme
import ru.sesh.composetraining.ui.theme.Typography

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BaseTrainingScreen()
        }
    }
}

private var countState: MutableState<Int> = mutableStateOf(0)
private var checkBoxState: MutableState<Boolean> = mutableStateOf(false)

@Composable
fun BaseTrainingScreen() {
    ComposeTrainingTheme {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row {
                TextToolbar()
                Spacer(modifier = Modifier.weight(1f))
                CloseIcon()
            }
            Row {
                CounterText()
            }
            Row {
                CheckBoxExample()
            }
        }

    }
}

@Composable
fun TextToolbar() {
    Text(
        text = stringResource(id = R.string.app_name),
        style = Typography.headlineMedium,
        modifier = Modifier
            .padding(start = 20.dp, top = 20.dp)
    )
}

@Composable
fun CloseIcon() {
    Icon(
        painter = painterResource(id = R.drawable.ic_launcher_foreground),
        contentDescription = null,
        modifier = Modifier
            .height(52.dp)
            .width(52.dp)
            .padding(end = 20.dp, top = 20.dp)
    )
}

@Composable
fun CounterText() {
    Text(
        text = "Clicks: ${countState.value}",
        style = Typography.bodyMedium,
        modifier = Modifier
            .padding(start = 20.dp, top = 20.dp)
            .clickable {
                countState.value++
            }
    )
}

@Composable
fun CheckBoxExample() {
    Checkbox(
        checked = checkBoxState.value,
        onCheckedChange = {
            checkBoxState.value = !checkBoxState.value
        },
        modifier = Modifier
            .padding(start = 20.dp, top = 20.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BaseTrainingScreen()
}