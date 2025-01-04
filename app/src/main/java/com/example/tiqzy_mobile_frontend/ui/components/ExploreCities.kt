package com.example.tiqzy_mobile_frontend.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import androidx.compose.foundation.shape.RoundedCornerShape

// Updated Category data class
data class Category(
    val categoryId: Int,
    val name: String,
    val image: String,
    val event: List<Event>
)

data class Event(
    val eventId: Int,
    val eventName: String
)

@Composable
fun ExploreCategories(categories: List<Category>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Explore Categories",
            style = MaterialTheme.typography.titleLarge, // Updated typography for Material3
            modifier = Modifier.padding(bottom = 8.dp)
        )

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 8.dp)
        ) {
            items(categories) { category ->
                CategoryCard(category)
            }
        }
    }
}

@Composable
fun CategoryCard(category: Category) {
    Card(
        modifier = Modifier
            .width(150.dp)
            .height(100.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // Display the category image using Coil
            Image(
                painter = rememberAsyncImagePainter(model = category.image),
                contentDescription = category.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            // Add gradient and category name overlay
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Brush.verticalGradient(listOf(Color.Transparent, Color.Black)))
                    .align(Alignment.BottomCenter)
            ) {
                Text(
                    text = category.name,
                    style = TextStyle(color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold),
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(8.dp)
                )
            }
        }
    }
}

// Sample usage with the updated Category model
@Preview(showBackground = true)
@Composable
fun ExploreCategoriesPreview() {
    val categories = listOf(
        Category(
            categoryId = 1,
            name = "Music",
            image = "https://example.com/music.jpg",
            event = listOf(
                Event(eventId = 1, eventName = "Concert"),
                Event(eventId = 2, eventName = "Festival")
            )
        ),
        Category(
            categoryId = 2,
            name = "Sports",
            image = "https://example.com/sports.jpg",
            event = listOf(
                Event(eventId = 3, eventName = "Soccer Match"),
                Event(eventId = 4, eventName = "Marathon")
            )
        ),
        Category(
            categoryId = 3,
            name = "Food",
            image = "https://example.com/food.jpg",
            event = listOf(
                Event(eventId = 5, eventName = "Food Expo"),
                Event(eventId = 6, eventName = "Cooking Class")
            )
        )
    )
    ExploreCategories(categories)
}
