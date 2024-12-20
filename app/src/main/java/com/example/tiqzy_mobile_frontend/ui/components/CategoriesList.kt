package com.example.tiqzy_mobile_frontend.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.*
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun CategoriesList(
    categories: List<String>,
    selectedCategories: MutableList<String>
) {
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        mainAxisSpacing = 8,
        crossAxisSpacing = 8
    ) {
        categories.forEach { category ->
            CategoryChip(
                text = category,
                isSelected = selectedCategories.contains(category),
                onClick = {
                    if (selectedCategories.contains(category)) {
                        selectedCategories.remove(category)
                    } else {
                        selectedCategories.add(category)
                    }
                }
            )
        }
    }
}

@Composable
fun CategoryChip(text: String, isSelected: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent,
            contentColor = if (isSelected) Color.White else MaterialTheme.colorScheme.primary
        ),
        border = if (!isSelected) BorderStroke(1.dp, MaterialTheme.colorScheme.primary) else null,
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
        modifier = Modifier
            .wrapContentWidth()
            .wrapContentHeight()
            .padding(4.dp)
    ) {
        Text(text = text, style = TextStyle(fontSize = 12.sp))
    }
}


@Composable
fun FlowRow(
    modifier: Modifier = Modifier,
    mainAxisSpacing: Int = 0,
    crossAxisSpacing: Int = 0,
    content: @Composable () -> Unit
) {
    Layout(
        content = content,
        modifier = modifier
    ) { measurables, constraints ->
        println("Available width: ${constraints.maxWidth}")
        val rows = mutableListOf<MutableList<Placeable>>()
        val currentRow = mutableListOf<Placeable>()
        var currentWidth = 0
        var totalHeight = 0

        measurables.forEach { measurable ->
            val placeable = measurable.measure(constraints)
            println("Placeable width: ${placeable.width}")
            if (currentWidth + placeable.width > constraints.maxWidth) {
                rows.add(currentRow.toMutableList())
                totalHeight += currentRow.maxOf { it.height } + crossAxisSpacing
                currentRow.clear()
                currentWidth = 0
            }
            currentRow.add(placeable)
            currentWidth += placeable.width + mainAxisSpacing
        }

        if (currentRow.isNotEmpty()) {
            rows.add(currentRow)
            totalHeight += rows.last().maxOf { it.height }
        }

        layout(constraints.maxWidth, totalHeight) {
            var yOffset = 0
            rows.forEach { row ->
                var xOffset = 0
                row.forEach { placeable ->
                    placeable.placeRelative(xOffset, yOffset)
                    xOffset += placeable.width + mainAxisSpacing
                }
                yOffset += row.maxOf { it.height } + crossAxisSpacing
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCategoriesList() {
    val mockCategories = listOf(
        "Museums",
        "Tours",
        "Food & Drinks",
        "Music & Concerts",
        "Parks & Gardens"
    )
    val selectedCategories = remember { mutableStateListOf<String>() }

    MaterialTheme {
        CategoriesList(
            categories = mockCategories,
            selectedCategories = selectedCategories
        )
    }
}
