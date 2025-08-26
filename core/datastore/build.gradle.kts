plugins {
    alias(libs.plugins.library)
    alias(libs.plugins.lib.flavor)
    alias(libs.plugins.all.hilt)
}

android {
    namespace = "com.rebuilding.muscleatlas.datastore"
}

dependencies {
    implementation(project(":core:util"))
    implementation(libs.androidx.datastore.preferences)
}