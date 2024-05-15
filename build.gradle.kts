// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false

    //Dans plugin du build.gradle (Celui ou il n'y a pas grand chose)
    id("androidx.navigation.safeargs.kotlin") version "2.5.1" apply false
}