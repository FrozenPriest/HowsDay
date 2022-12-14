package ru.frozenpriest.howsday.data.db

import androidx.room.TypeConverter
import kotlinx.serialization.json.Json
import ru.frozenpriest.howsday.data.model.FaceDetails

class Converters {

    @TypeConverter
    fun fromFaceDetails(face: FaceDetails): String {
        return Json.encodeToString(serializer, face)
    }

    @TypeConverter
    fun toFaceDetails(jsonText: String): FaceDetails {
        return Json.decodeFromString(serializer, jsonText)
    }

    private val serializer = FaceDetails.serializer()
}
