// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    // Add the Google Services plugin
    id("com.google.gms.google-services") version "4.3.15" apply false // Or the latest version
}

// Build script dependencies
buildscript {
    dependencies {
        // Ensure that you include the Google Services classpath
        classpath("com.google.gms:google-services:4.3.15") // Or the latest version
    }
}
