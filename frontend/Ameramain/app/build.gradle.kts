plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.ameramain"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.ameramain"
        minSdk = 21
        targetSdk = 35
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
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)


    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0") // Для логов запросов
    implementation(libs.converter.gson)

    implementation ("com.github.bumptech.glide:glide:4.12.0")  // Убедитесь, что используете актуальную версию
    annotationProcessor ("com.github.bumptech.glide:compiler:4.12.0")

    implementation ("com.squareup.okhttp3:logging-interceptor:4.12.0")

    implementation ("io.jsonwebtoken:jjwt-api:0.11.5")
    runtimeOnly    ("io.jsonwebtoken:jjwt-impl:0.11.5")
    runtimeOnly    ("io.jsonwebtoken:jjwt-jackson:0.11.5") // или другой JSON-парсер

    // Logging interceptor (по желанию, для логов)
    implementation(libs.logging.interceptor)
    implementation(libs.play.services.maps)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

}