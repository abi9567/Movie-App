import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

val appPropertiesFile = rootProject.file("app.properties")
val appProperties = Properties()
appProperties.load(FileInputStream(appPropertiesFile))

android {

    namespace = "com.example.movieapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.movieapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            buildConfigField("String", "BASE_URL", "\"${appProperties["BASE_URL"]}\"")
            buildConfigField("String", "IMAGE_URL", "\"${appProperties["IMAGE_URL"]}\"")
            buildConfigField("String", "API_KEY", "\"${appProperties["API_KEY"]}\"")
            buildConfigField("String", "ACCESS_TOKEN", "\"${appProperties["ACCESS_TOKEN"]}\"")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            buildConfigField("String", "BASE_URL", "\"${appProperties["BASE_URL"]}\"")
            buildConfigField("String", "IMAGE_URL", "\"${appProperties["IMAGE_URL"]}\"")
            buildConfigField("String", "API_KEY", "\"${appProperties["API_KEY"]}\"")
            buildConfigField("String", "ACCESS_TOKEN", "\"${appProperties["ACCESS_TOKEN"]}\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        buildConfig = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
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
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //Retrofit
    implementation (libs.retrofit)
    implementation (libs.converter.gson)
    implementation(libs.logging.interceptor)

    //Viewmodel & Live Data
    implementation (libs.androidx.lifecycle.viewmodel.compose)
    implementation (libs.androidx.lifecycle.livedata)
    implementation (libs.androidx.runtime.livedata)

    //Hilt Dependency
    implementation (libs.hilt.android)
    kapt (libs.hilt.compiler)
    kapt (libs.androidx.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    //Navigation
    implementation(libs.androidx.navigation.compose)

    //Coil Image
    implementation(libs.coil.compose)

    //Paging 3
    implementation (libs.androidx.paging.compose)
    implementation (libs.androidx.paging.runtime)

    //Lottiefiles
    implementation(libs.lottie.compose)
}