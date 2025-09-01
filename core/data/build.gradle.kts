plugins {
    alias(libs.plugins.library)
    alias(libs.plugins.lib.flavor)
    alias(libs.plugins.lib.compose)
    alias(libs.plugins.all.hilt)
    alias(libs.plugins.kotlin.serialization)
}


android {
    namespace = "com.rebuilding.muscleatlas.data"
}

dependencies {
    implementation(project(":core:util"))
    implementation(project(":core:model"))
    implementation(project(":core:room"))
    implementation(project(":core:datastore"))
}