package ru.sesh.composetraining.lore

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.sesh.composetraining.utils.TAG

class HomeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(HomeScreenUiState())
    val uiState: StateFlow<HomeScreenUiState> = _uiState

    fun changeCount() {
        _uiState.value = uiState.value.copy(count = uiState.value.count + 1)
    }

    fun changeEnabled(enabled: Boolean) {
        _uiState.value = uiState.value.copy(enabled = enabled)
    }

}

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = viewModel()
) {
    val uiState by homeViewModel.uiState.collectAsState()
    Log.d(TAG, "HomeScreen")

    Column {
        ClickButton(
            count = uiState.count,
            onCounterClick = homeViewModel::changeCount
        )
        EnableFeature(
            enabled = uiState.enabled,
            onEnabledChange = homeViewModel::changeEnabled
        )
    }
}

@Composable
fun EnableFeature(
    enabled: Boolean,
    onEnabledChange: (Boolean) -> Unit
) {
    Log.d(TAG, "EnableFeature")
    Row(verticalAlignment = CenterVertically) {
        Checkbox(checked = enabled, onCheckedChange = onEnabledChange)
        Text("enable feature")
    }
}

@Composable
fun ClickButton(
    count: Int,
    onCounterClick: () -> Unit
) {
    Log.d(TAG, "ClickButton")
    Text(
        text = "Clicks: $count",
        modifier = Modifier.clickable(onClick = onCounterClick)
    )
}

data class HomeScreenUiState(
    val count: Int = 0,
    val enabled: Boolean = false
)