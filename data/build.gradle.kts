plugins {
    id("com.android.library")
    alias(libs.plugins.jetbrains.kotlin.android)
    kotlin("kapt")
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.tasks.data"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
        lint.targetSdk = 34


        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "OPEN_WEATHER_URL", "\"https://api.weatherapi.com/v1/\"")
        buildConfigField("String", "OPEN_WEATHER_KEY", "\"afcbed05dd0e4635b76103355230804\"")
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
        buildConfig = true
    }
}

dependencies {
    implementation(libs.hilt)
    kapt(libs.hilt.compiler)

    implementation(libs.retrofit)
    implementation(libs.moshiConverter)
    implementation(libs.gsonConverter)
    implementation(libs.okHttp)
    implementation(libs.okHttpLoggingInterceptor)
    implementation(libs.gson)

    implementation(libs.datastore)

    implementation(project(":domain"))
    implementation(project(":core"))
}