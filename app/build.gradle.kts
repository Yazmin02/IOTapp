plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.example.iotapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.iotapp"
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
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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
    dependencies {
        implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")
        implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
        implementation (libs.androidx.lifecycle.runtime.ktx.v231)
        implementation (libs.androidx.lifecycle.viewmodel.ktx)
        implementation ("androidx.compose.ui:ui:1.6.8")
        implementation ("androidx.compose.material:material:1.6.8")
        implementation ("androidx.compose.ui:ui-tooling-preview:1.6.8")
        implementation (libs.androidx.activity.compose.v131)
        implementation ("com.google.code.gson:gson:2.10.1")
        implementation (libs.retrofit)
        implementation (libs.converter.gson)
    }
}
