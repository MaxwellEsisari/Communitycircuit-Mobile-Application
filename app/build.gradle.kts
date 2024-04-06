import java.util.regex.Pattern.compile

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.communitycircuit1"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.communitycircuit1"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"


        // Define BuildConfig fields here
        buildConfigField("String", "consumerKey", "\"OB1vQIHu1fQBxe0cVLQd5oh9aXwuWu2dQkiTCzGHg7TdwFTz\"")
        buildConfigField("String", "consumerSecret", "\"8snqITdAmxQIgzirWG2nsPxUDqRuppUEGGqGHnDzyCXecMfUq6wqvuDpJ4VABMOF\"")

    }
    buildFeatures {
        buildConfig = true
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
    buildFeatures{
        viewBinding= true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.firebase:firebase-auth:22.3.1")
    implementation("androidx.preference:preference:1.2.1")
    implementation("androidx.activity:activity:1.8.2")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("com.google.android.gms:play-services-auth:21.0.0")
    implementation ("com.google.firebase:firebase-auth:22.3.1")
    implementation ("com.google.firebase:firebase-database:20.3.1")
    implementation ("com.google.firebase:firebase-firestore:24.11.0")
    implementation ("com.google.firebase:firebase-storage:20.3.0")
    implementation ("com.google.firebase:firebase-functions:20.4.0")
    implementation ("com.github.dhaval2404:imagepicker:2.1")
    val nav_version = "2.7.7"
    // Kotlin
    implementation("androidx.navigation:navigation-fragment-ktx:$nav_version")
    implementation("androidx.navigation:navigation-ui-ktx:$nav_version")
    implementation ("androidx.recyclerview:recyclerview:1.3.2")
    implementation("com.squareup.picasso:picasso:2.8")
    implementation ("com.google.code.gson:gson:2.10.1")
    implementation ("javax.annotation:javax.annotation-api:1.3.2")
    implementation ("androidx.activity:activity-ktx:1.8.2")
    //stk push libs

    implementation("com.jakewharton.timber:timber:5.0.1")



    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    implementation("com.squareup.okhttp3:okhttp:4.10.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.10.0")

    implementation("com.google.code.gson:gson:2.10.1")

    implementation("com.squareup.okio:okio:3.0.0")

    implementation("javax.annotation:javax.annotation-api:1.3.2")






}