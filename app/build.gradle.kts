plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
//    alias(libs.plugins.androidApplication)
//    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("com.google.gms.google-services")

}

android {


    namespace = "com.ininc.foodmarketadmin"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.ininc.foodmarketadmin"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildFeatures{
        viewBinding = true
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
}



dependencies {

//    implementation(libs.androidx.core.ktx)
//    implementation(libs.androidx.appcompat)
//    implementation(libs.material)
//    implementation(libs.androidx.activity)
//    implementation(libs.androidx.constraintlayout)
//    implementation(libs.firebase.auth)
//    implementation(libs.firebase.database)
//    implementation("com.google.firebase:firebase-bom:32.8.0")
//    implementation(libs.play.services.auth)
//    implementation(libs.firebase.firestore)
    implementation(libs.firebase.storage.ktx)
//    implementation(libs.androidx.tools.core)
//    testImplementation(libs.junit)
//    androidTestImplementation(libs.androidx.junit)
//    androidTestImplementation(libs.androidx.espresso.core)



    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.7")
    implementation("androidx.activity:activity:1.8.0")
    implementation("com.google.firebase:firebase-auth:22.3.1")
    implementation("com.google.firebase:firebase-database-ktx:20.3.1")
    implementation("com.google.android.gms:play-services-auth:21.0.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
//    implementation ("com.github.denzcoskun:ImageSlideshow:0.1.2")
//    implementation ("com.github.denzcoskun:ImageSlideshow:0.1.2")
    implementation ("com.github.jitpack:android-example:1.0.1")
    implementation("com.github.bumptech.glide:glide:4.16.0")

    implementation ("com.google.android.material:material:1.11.0")

    implementation ("io.github.chaosleung:pinview:1.4.4")







//    implementation("com.google.protobuf:protobuf-javalite:3.22.3")
//
////    implementation("com.plaid.link:sdk-core:3.3.0")
//
//    //Glide
//    implementation("com.github.bumptech.glide:glide:4.16.0")
//
//    implementation ("io.github.chaosleung:pinview:1.4.4")
    implementation("com.android.volley:volley:1.2.1")




}