plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.collegegraduate.notesapp"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.collegegraduate.notesapp"
        minSdk = 24
        targetSdk = 36
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
    testImplementation(libs.junit)


    // Room database dependencies
    implementation ("androidx.room:room-runtime:2.5.2")
    annotationProcessor ("androidx.room:room-compiler:2.5.2")

    // CardView for note items
    implementation ("androidx.cardview:cardview:1.0.0")

    // RecyclerView for displaying notes
    implementation ("androidx.recyclerview:recyclerview:1.3.1")

    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}