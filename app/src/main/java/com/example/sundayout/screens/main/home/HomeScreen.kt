package com.example.sundayout.screens.main.home

import android.media.Image
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sundayout.R
import com.example.sundayout.extensions.nativeGradient
import com.example.sundayout.model.Business
import com.example.sundayout.screens.auth.register.getPasswordTextColor
import com.example.sundayout.ui.theme.interFontFamily


@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel,
    businessesUiState: BusinessesUiState,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    Column {
        HomeBanner(homeViewModel = homeViewModel)

        when (businessesUiState) {
            is BusinessesUiState.Error -> BusinessesError()
            is BusinessesUiState.Loading -> BusinessesLoading()
            is BusinessesUiState.Success -> BusinessesList(businesses = businessesUiState.businesses)
        }
    }
}

@Composable
fun HomeBanner(
    homeViewModel: HomeViewModel
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = colorResource(R.color.white)
        ),
        border = BorderStroke(1.dp, brush = Brush.nativeGradient()),
        modifier = Modifier
            .padding(horizontal = 20.dp)
    ) {
        Column {
            SearchBarView(homeViewModel = homeViewModel)
            HorizontalDivider(
                thickness = 1.dp,
                color = colorResource(R.color.divider_color)
            )
            Row {
                HomeBannerCell(
                    handleAction = {},
                    title = stringResource(R.string.map),
                    imageId = R.drawable.ic_map
                )
                Spacer(modifier = Modifier.weight(1.0f))
                HomeBannerCell(
                    handleAction = {},
                    title = stringResource(R.string.sort),
                    imageId = R.drawable.ic_sort
                )
                Spacer(modifier = Modifier.weight(1.0f))
                HomeBannerCell(
                    handleAction = {},
                    title = stringResource(R.string.filter),
                    imageId = R.drawable.ic_filter
                )
            }
        }
    }
}

@Composable
fun SearchBarView(
    homeViewModel: HomeViewModel
) {
    Row(
        horizontalArrangement = Arrangement.Absolute.Left,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(horizontal = 20.dp)
    ) {
        Icon(Icons.Rounded.Search, contentDescription = null)

        TextField(
            value = homeViewModel.searchText,
            onValueChange = {
                homeViewModel.updateSearchText(it)
            },
            placeholder = {
                Text(text = "Search")
            },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White,
                unfocusedIndicatorColor = Color.White,
                focusedIndicatorColor = Color.White
            )
        )
    }
}

@Composable 
fun HomeBannerCell(
    handleAction: () -> Unit,
    title: String,
    imageId: Int
) {
    Button(
        onClick = {
            handleAction()
        },
        colors = ButtonDefaults.buttonColors(containerColor = Color.White)
    ) {
        Image(
            painter = painterResource(imageId),
            contentDescription = null,
            modifier = Modifier
                .padding(end = 6.dp)
        )
        Text(
            text = title,
            fontSize = 14.sp,
            fontFamily = interFontFamily,
            fontWeight = FontWeight.Medium,
            color = colorResource(R.color.banner_text_color),
        )
    }
}

@Composable
fun BusinessesList(
    businesses: List<Business>
) {
    LazyColumn {
        items(items = businesses, key = { business -> business.businessId }) { business ->
            Text(text = business.name)
        }
    }
}

@Composable
fun BusinessesLoading() {

}

@Composable
fun BusinessesError() {

}