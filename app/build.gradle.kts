plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "com.amonteiro.a2024_04_fidme"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.amonteiro.a2024_04_fidme"
        minSdk = 28
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
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
        viewBinding = true
        dataBinding = true
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


    //Requete web
    implementation("com.squareup.okhttp3:okhttp:+")
    //Parsing JSON
    implementation("com.google.code.gson:gson:+")

    //Image URL compose
    implementation("com.github.bumptech.glide:compose:1.0.0-beta01")

    //Navigation compose
    implementation("androidx.navigation:navigation-compose:2.+")


    //Coroutine
    //Utilisation générale
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:+")
//LifeCycleScope
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.+")
//ViewModelScope
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.+")
    implementation ("com.google.android.gms:play-services-location:+")
    implementation ("com.google.accompanist:accompanist-permissions:+")

    //Image url
    implementation("com.squareup.picasso:picasso:2.8")

    //Fragment
    implementation ("androidx.navigation:navigation-ui:2.+")

    implementation ("androidx.navigation:navigation-ui-ktx:2.+")
    implementation ("androidx.navigation:navigation-fragment-ktx:2.+")


    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}