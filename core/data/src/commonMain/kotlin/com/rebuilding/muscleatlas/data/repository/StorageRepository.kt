package com.rebuilding.muscleatlas.data.repository

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlin.random.Random

/**
 * Storage operations for file uploads to Supabase Storage
 */
interface StorageRepository {
    /**
     * Uploads an exercise image to Supabase Storage
     *
     * @param exerciseId Exercise ID for generating unique filename
     * @param imageBytes Image data as ByteArray
     * @param bucketId Storage bucket name (default: "exercise-images")
     * @return Public URL of uploaded image
     */
    suspend fun uploadExerciseImage(
        exerciseId: String,
        imageBytes: ByteArray,
        bucketId: String = EXERCISE_IMAGES_BUCKET
    ): String

    /**
     * Deletes an exercise image from Supabase Storage
     *
     * @param imageUrl Full URL of the image to delete
     * @param bucketId Storage bucket name
     */
    suspend fun deleteExerciseImage(
        imageUrl: String,
        bucketId: String = EXERCISE_IMAGES_BUCKET
    )

    companion object {
        const val EXERCISE_IMAGES_BUCKET = "exercise-images"
    }
}

class StorageRepositoryImpl(
    private val supabaseClient: SupabaseClient,
    private val ioDispatcher: CoroutineDispatcher,
) : StorageRepository {

    override suspend fun uploadExerciseImage(
        exerciseId: String,
        imageBytes: ByteArray,
        bucketId: String
    ): String = withContext(ioDispatcher) {
        // Generate unique filename: {exerciseId}_{random}.jpg
        val uniqueId = Random.nextLong()
        val filename = "${exerciseId}_${uniqueId}.jpg"

        // Upload to Supabase Storage
        val bucket = supabaseClient.storage.from(bucketId)
        bucket.upload(path = filename, data = imageBytes) {
            upsert = false  // Don't overwrite existing files
        }

        // Return public URL
        bucket.publicUrl(filename)
    }

    override suspend fun deleteExerciseImage(
        imageUrl: String,
        bucketId: String
    ) = withContext(ioDispatcher) {
        // Extract filename from URL
        val filename = imageUrl.substringAfterLast("/")

        // Delete from storage
        val bucket = supabaseClient.storage.from(bucketId)
        bucket.delete(filename)
    }
}
