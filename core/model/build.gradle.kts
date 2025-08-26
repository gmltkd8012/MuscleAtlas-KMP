plugins {
    alias(libs.plugins.library)
    alias(libs.plugins.lib.flavor)
    alias(libs.plugins.lib.compose)
    alias(libs.plugins.kotlin.serialization)
}


android {
    namespace = "com.rebuilding.muscleatlas.model"
}

dependencies {
    implementation(project(":core:util"))
}