package com.example.ott.screens

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.ott.NavigationKeys
import com.example.ott.R
import com.example.ott.model.Content
import com.example.ott.utils.getImageDrawable
import com.example.ott.utils.getScreenOrientation
import com.example.ott.viewmodel.ContentListViewModel

@Composable
fun ListingScreen(navController: NavHostController) {

    val context: Context = LocalContext.current
    val viewModel: ContentListViewModel = hiltViewModel()
    val contentList = viewModel.listPager.collectAsLazyPagingItems()

    ContentList(
        contentList = contentList,
        navController = navController,
        context = context
    )

}

@Composable
fun ContentList(
    contentList: LazyPagingItems<Content>,
    navController: NavHostController,
    context: Context
){

    Box(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(),

        ) {
        TopAppBar(contentList = contentList, navController = navController, context = context)
            //Show load when list is empty
            if(contentList.itemCount == 0) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.White
                )
            }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(contentList: LazyPagingItems<Content>, navController: NavHostController, context: Context) {

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),

        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Black,
                    titleContentColor = Color.White,
                ),
                title = {
                    Text(
                        text = "Recommended Movies",
                        fontSize = 20.sp,
                        fontStyle = FontStyle.Normal,
                        fontWeight = FontWeight.Normal,
                        color = Color.White,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { (context as? Activity)?.finish() }) {
                        Image(
                            painter = painterResource(id = R.drawable.back),
                            contentDescription = "Localized description"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { navController.navigate(NavigationKeys.Route.SEARCH_SCREEN) }) {
                        Image(
                            painter = painterResource(id = R.drawable.search),
                            contentDescription = "Localized description"
                        )
                    }
                },
                scrollBehavior = scrollBehavior,

                )
        },
    ) { innerPadding ->

        LazyVerticalGrid(
            contentPadding = innerPadding,
            modifier = Modifier.background(Color.Black),
            columns = GridCells.Fixed(getScreenOrientation(LocalConfiguration.current))
        ) {
            items(contentList.itemCount) { index ->
                contentList[index]?.let {
                    ContentListItemCard(item = it)
                }
            }
        }
    }
}

@Composable
fun ContentListItemCard(item: Content, searchKeyword: String = "") {
    Card(
        shape = RectangleShape,
        modifier = Modifier
            .background(Color.Black)
            .padding(
                horizontal = 8.dp,
                vertical = 8.dp
            )
    ) {

        Column(
            modifier = Modifier
                .background(Color.Black)
        ) {

            // Get images from drawable using posterImage string
            var drawableId =
                getImageDrawable(context = LocalContext.current, image = item.posterImage)
            if (drawableId == 0) {
                drawableId = R.drawable.placeholder_for_missing_posters
            }

            Image(
                painter = painterResource(id = drawableId),
                contentDescription = "",
                Modifier
                    .fillMaxWidth()
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop,
            )

            val contentName: AnnotatedString = if (searchKeyword.isNotEmpty()) {
                buildAnnotatedString {
                    // Apply style to a searched keyword
                    withStyle(style = SpanStyle(color = Color.Green)) {
                        append(item.name.substring(0, searchKeyword.length))
                    }
                    append(item.name.substring(searchKeyword.length))
                }

            } else {
                AnnotatedString(item.name)
            }

            Text(
                maxLines = 1,
                text = contentName,
                fontSize = 16.sp,
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.Light,
                color = Color.White,
                modifier = Modifier.padding(start = 2.dp, top = 2.dp, bottom = 4.dp),
                overflow = TextOverflow.Ellipsis
            )

        }
    }
    Spacer(modifier = Modifier.width(15.dp))
}
