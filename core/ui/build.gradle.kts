plugins {
    alias(libs.plugins.library)
    alias(libs.plugins.lib.flavor)
    alias(libs.plugins.lib.compose)
}

android {
    namespace = "com.rebuilding.muscleatlas.ui"
}

dependencies {
    implementation(project(":core:model"))
    implementation(project(":core:util"))
}
