plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    alias(libs.plugins.hilt) // Correct Hilt plugin alias
    id("kotlin-kapt") // Annotation processing

}

android {
    namespace = "com.example.tiqzy_mobile_frontend"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.tiqzy_mobile_frontend"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.espresso.core)
    implementation(libs.androidx.animation.core.lint)
    implementation(libs.androidx.datastore.core.android)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Retrofit dependencies
    implementation("com.squareup.retrofit2:retrofit:2.9.0") // Core Retrofit library
    implementation("com.squareup.retrofit2:converter-gson:2.9.0") // Gson for JSON parsing

    //Hilt
    implementation(libs.hilt.android) // Hilt runtime
    kapt(libs.hilt.compiler)         // Hilt annotation processor
    // Hilt navigation Compose integration
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")

    //nav
    implementation("androidx.compose.material:material:1.5.0")
    implementation("androidx.compose.material3:material3:1.2.0-alpha05")

    //datastore
    implementation ("androidx.datastore:datastore-preferences:1.0.0")

    //load image from URL using coil
    implementation ("io.coil-kt:coil-compose:2.1.0")




}