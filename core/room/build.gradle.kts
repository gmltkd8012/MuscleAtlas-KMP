plugins {
    alias(libs.plugins.library)
    alias(libs.plugins.lib.flavor)
    alias(libs.plugins.lib.room)
    alias(libs.plugins.all.hilt)
    alias(libs.plugins.kotlin.serialization)
}


android {
    namespace = "com.rebuilding.muscleatlas.room"
}

dependencies {
    api(project(":core:model"))
}
