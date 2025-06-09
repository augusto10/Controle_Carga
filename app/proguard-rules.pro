# Room Database
-keep class * extends androidx.room.RoomDatabase
-keep class * extends androidx.room.Database
-keep class * extends androidx.room.Entity
-keep class * extends androidx.room.Dao

# Hilt
-keep class dagger.hilt.android.internal.managers.*
-keep class dagger.hilt.android.internal.managers.ViewComponentManager$FragmentContextWrapper

# Coroutines
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}

# Compose
-dontwarn androidx.compose.**
-keep class androidx.compose.** { *; }
-keep class kotlin.jvm.internal.** { *; }
-keepclassmembers class ** {
    @com.google.android.material.composethemeadapter.annotations.* <methods>;
}

# ZXing
-keep class com.google.zxing.** { *; }
-keep class com.journeyapps.barcodescanner.** { *; }

# PDF
-keep class com.itextpdf.** { *; }

# Other
-keepattributes *Annotation*
-keepattributes Signature
