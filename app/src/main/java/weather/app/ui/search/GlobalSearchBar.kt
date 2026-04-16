package weather.app.ui.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import weather.app.R
import weather.app.data.mappers.toDomain
import weather.app.data.mappers.toSearch
import weather.app.models.location.Location

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GlobalSearchBar(
    viewModel: SearchViewModel,
    onLocationSelected: (Location) -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    var expanded by rememberSaveable { mutableStateOf(false) }

    SearchBar(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
            .semantics { isTraversalGroup = true },
        expanded = expanded,
        onExpandedChange = { expanded = it },
        inputField = {
            SearchBarDefaults.InputField(
                query = state.query,
                onQueryChange = {
                    viewModel.onQueryChange(it)
                    expanded = true
                },
                onSearch = {
                    expanded = false
                    state.suggestions.firstOrNull()?.let { first ->
                        onLocationSelected(first.toDomain())
                        viewModel.onLocationSelected(first)
                    }
                },
                expanded = expanded,
                onExpandedChange = { isExpanded ->
                    expanded = isExpanded
                    if (isExpanded) {
                        viewModel.onQueryChange("")
                    }
                },
                placeholder = { Text(stringResource(R.string.search)) }
            )
        }
    ) {
        when {

            state.isLoading -> {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                }
            }

            state.query.isBlank() && state.history.isNotEmpty() -> {
                Column(Modifier.verticalScroll(rememberScrollState())) {
                    state.history.forEach { location ->
                        ListItem(
                            headlineContent = { Text(location.name) },
                            modifier = Modifier
                                .clickable {
                                    expanded = false
                                    onLocationSelected(location)
                                    viewModel.onLocationSelected(location.toSearch())
                                }
                                .fillMaxWidth()
                        )
                    }
                }
            }

            state.suggestions.isNotEmpty() -> {
                Column(Modifier.verticalScroll(rememberScrollState())) {
                    state.suggestions.forEach { location ->
                        ListItem(
                            headlineContent = { Text(location.name) },
                            modifier = Modifier
                                .clickable {
                                    expanded = false
                                    onLocationSelected(location.toDomain())
                                    viewModel.onLocationSelected(location)
                                }
                                .fillMaxWidth()
                        )
                    }
                }
            }

            state.query.isNotBlank() -> {
                Text(
                    stringResource(R.string.no_results),
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}
