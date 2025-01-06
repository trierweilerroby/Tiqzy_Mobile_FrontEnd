package com.example.tiqzy_mobile_frontend.data.model

data class Category(
    val name: String,                 // The name of the category
    val slug: String,                 // The unique slug for the category
    val termGroup: Int,               // Group ID for the term
    val termTaxonomyId: Int,          // ID for taxonomy term
    val taxonomy: String,             // Type of taxonomy
    val description: String,          // Description of the category
    val parent: Int,                  // Parent category ID (if any)
    val count: Int,                   // Number of items associated with this category
    val filter: String,               // Filter type (e.g., "raw")
    val id: Int,                      // Unique category ID
    val urls: CategoryUrls            // Nested URLs object
)

data class CategoryUrls(
    val self: String,                 // Link to the specific category
    val collection: String            // Link to the collection of categories
)
