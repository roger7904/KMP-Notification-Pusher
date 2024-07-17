import kotlinx.serialization.*
import kotlinx.serialization.json.*
import java.io.File

@Serializable
data class UserSettings(
    val serviceAccountPath: String = "",
    val projectId: String = "",
    val jsonMessage: String = "",
    val targetDeviceToken: String = ""
)

object SettingsUtil {
    private val userHome = System.getProperty("user.home")
    private val appSupportDir = File("$userHome/Library/Application Support/FCM Pusher")
    private val settingsFile = File(appSupportDir, "user-settings.json")

    init {
        if (!appSupportDir.exists()) {
            appSupportDir.mkdirs()
        }
    }

    fun saveSettings(settings: UserSettings) {
        val jsonString = Json.encodeToString(settings)
        settingsFile.writeText(jsonString)
    }

    fun loadSettings(): UserSettings {
        return try {
            val jsonString = settingsFile.readText()
            Json.decodeFromString(jsonString)
        } catch (e: Exception) {
            UserSettings()
        }
    }
}