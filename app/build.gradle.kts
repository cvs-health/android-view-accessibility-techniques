/*
   Copyright 2023-2024 CVS Health and/or one of its affiliates

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

kotlin {
    jvmToolchain(17)
}

android {
    compileSdk = 34

    defaultConfig {
        applicationId = "com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        // The following line applies Espresso Accessibility Checks to all UI tests:
        testInstrumentationRunner = "com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques.AccessibilityChecksTestRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
    namespace = "com.cvshealth.accessibility.apps.androidviewaccessibilitytechniques"
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.7")
    implementation("com.google.android.material:material:1.12.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test:core-ktx:1.5.0")
    androidTestImplementation("androidx.test.ext:junit-ktx:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-accessibility:3.5.1")
}
