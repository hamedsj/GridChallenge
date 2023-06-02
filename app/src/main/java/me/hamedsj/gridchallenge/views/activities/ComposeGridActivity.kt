package me.hamedsj.gridchallenge.views.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import me.hamedsj.gridchallenge.utils.toDp
import me.hamedsj.gridchallenge.viewmodels.GridChallengeViewModel
import me.hamedsj.gridchallenge.views.states.GridListState

class ComposeGridActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val gridViewModel: GridChallengeViewModel = viewModel()
            LaunchedEffect(key1 = true) {
                gridViewModel.loadPhotos()
            }
            ComposeGridList(gridViewModel = gridViewModel)
        }
    }

    @Composable
    private fun ComposeGridList(gridViewModel: GridChallengeViewModel) {
        val gridState = gridViewModel.stateStream.collectAsState().value

        val widthPx = remember { mutableStateOf(0f) }
        val paddingBetweenItems = 4.dp

        LazyColumn(
            modifier = Modifier
                .padding(horizontal = paddingBetweenItems, vertical = paddingBetweenItems)
                .fillMaxSize()
                .onSizeChanged { size ->
                    widthPx.value = size.width.toFloat()
                },
            verticalArrangement = Arrangement.spacedBy(paddingBetweenItems)
        ) {
            items(
                count = gridState.list.size / 3,
                key = { index ->
                    gridState.list[index * 3].id +
                            if (gridState.list.size > index * 3 + 1) gridState.list[index * 3 + 1].id else "" +
                                    if (gridState.list.size > index * 3 + 2) gridState.list[index * 3 + 2].id else ""
                },
            ) { index ->
                if (index % 6 == 0) {
                    BigTwoLeftItem(
                        index = index * 3,
                        gridState = gridState,
                        lineWidth = widthPx.value,
                        paddingBetweenItems = paddingBetweenItems
                    )
                } else if (index % 3 == 0) {
                    BigTwoRightItem(
                        index = index * 3,
                        gridState = gridState,
                        lineWidth = widthPx.value,
                        paddingBetweenItems = paddingBetweenItems
                    )
                } else {
                    ThreeSmallItem(
                        index = index * 3,
                        gridState = gridState,
                        lineWidth = widthPx.value,
                        paddingBetweenItems = paddingBetweenItems
                    )
                }
            }
            if (gridState.list.isNotEmpty()) {
                item {
                    NextPageButton(
                        lineWidth = widthPx.value,
                        paddingBetweenItems = paddingBetweenItems
                    ) {
                        startActivity(Intent(this@ComposeGridActivity, ViewGridActivity::class.java))
                    }
                }
            }
        }

    }

    @Composable
    private fun ThreeSmallItem(
        index: Int,
        gridState: GridListState,
        lineWidth: Float,
        paddingBetweenItems: Dp
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(paddingBetweenItems)) {
            LazyImage(
                size = (lineWidth / 3f).toDp() - (paddingBetweenItems * 2 / 3),
                url = gridState.list[index].urls.small
            )
            if (index + 1 < gridState.list.size) {
                LazyImage(
                    size = (lineWidth / 3f).toDp() - (paddingBetweenItems * 2 / 3),
                    url = gridState.list[index + 1].urls.small
                )
            }
            if (index + 2 < gridState.list.size) {
                LazyImage(
                    size = (lineWidth / 3f).toDp() - (16.dp / 3),
                    url = gridState.list[index + 2].urls.small
                )
            }
        }
    }

    @Composable
    private fun BigTwoLeftItem(
        index: Int,
        gridState: GridListState,
        lineWidth: Float,
        paddingBetweenItems: Dp
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(paddingBetweenItems)) {
            Column(verticalArrangement = Arrangement.spacedBy(paddingBetweenItems)) {
                LazyImage(
                    size = (lineWidth / 3f).toDp() - paddingBetweenItems / 2f,
                    url = gridState.list[index + 2].urls.small
                )
                if (index + 1 < gridState.list.size) {
                    LazyImage(
                        size = (lineWidth / 3f).toDp() - paddingBetweenItems / 2f,
                        url = gridState.list[index + 1].urls.small
                    )
                }
            }
            if (index + 2 < gridState.list.size) {
                LazyImage(
                    size = (lineWidth * 2f / 3f).toDp(),
                    url = gridState.list[index].urls.small
                )
            }
        }
    }

    @Composable
    private fun BigTwoRightItem(
        index: Int,
        gridState: GridListState,
        lineWidth: Float,
        paddingBetweenItems: Dp
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(paddingBetweenItems)) {
            LazyImage(
                size = (lineWidth * 2f / 3f).toDp(),
                url = gridState.list[index].urls.small
            )
            Column(verticalArrangement = Arrangement.spacedBy(paddingBetweenItems)) {
                if (index + 1 < gridState.list.size) {
                    LazyImage(
                        size = (lineWidth / 3f).toDp() - paddingBetweenItems / 2f,
                        url = gridState.list[index + 1].urls.small
                    )
                }
                if (index + 2 < gridState.list.size) {
                    LazyImage(
                        size = (lineWidth / 3f).toDp() - paddingBetweenItems / 2f,
                        url = gridState.list[index + 2].urls.small
                    )
                }
            }
        }
    }

    @Composable
    private fun NextPageButton(
        lineWidth: Float,
        paddingBetweenItems: Dp,
        onClick: () -> Unit
    ) {
        Button(
            modifier = Modifier
                .padding(
                    start = paddingBetweenItems,
                    top = paddingBetweenItems / 2f,
                    end = paddingBetweenItems
                )
                .width(lineWidth.toDp())
                .height(48.dp),
            onClick = onClick,
        ) {
            Text(
                modifier = Modifier.align(Alignment.CenterVertically),
                text = "Next Page",
                color = Color.White
            )
        }
    }

    @Composable
    private fun LazyImage(size: Dp, url: String) {
        AsyncImage(
            modifier = Modifier
                .size(size)
                .clip(RoundedCornerShape(8.dp)),
            model = ImageRequest.Builder(LocalContext.current)
                .data(url)
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )
    }

}