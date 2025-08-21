plugins {
    alias(libs.plugins.library)
    alias(libs.plugins.lib.flavor)
    alias(libs.plugins.lib.compose)
    alias(libs.plugins.all.hilt)
}

android {
    namespace = "com.rebuilding.muscleatlas.main"
}

dependencies {
    implementation(project(":core:design-system"))
    implementation(project(":core:model"))
    implementation(project(":core:ui"))
    implementation(project(":feature:client"))
    implementation(project(":feature:setting"))
}
