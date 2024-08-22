plugins {
    alias(libs.plugins.android.application)
    id ("com.google.gms.google-services")
    id("androidx.navigation.safeargs")
}

android {
    namespace = "com.example.mealplanner"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.mealplanner"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.firebase.auth)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    //Navigation
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)

    implementation (libs.navigation.fragment.ktx)
    implementation (libs.navigation.ui.ktx)

    //RXJava
    implementation (libs.rxjava)
    implementation (libs.rxandroid)

    //Card shape
    implementation (libs.cardview)

    //Glide For Photos
    implementation (libs.glide)
    annotationProcessor (libs.compiler)

    //Retrofit
    implementation (libs.retrofit)
    implementation (libs.converter.gson)

    //Retrofit with RX
    implementation (libs.adapter.rxjava3)

    //Room
    implementation(libs.room.runtime)
    annotationProcessor(libs.room.compiler)

    //Room with RX
    implementation (libs.room.rxjava3)
}