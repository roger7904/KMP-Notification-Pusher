import com.google.auth.oauth2.GoogleCredentials
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.*
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.*
import kotlinx.serialization.Serializable
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.json
import java.io.File

interface GoogleAuthProvider {
    suspend fun getAccessToken(serviceAccountJson: String): String
}

class FcmClient(private val authProvider: GoogleAuthProvider) {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }

        install(Logging) {
            level = LogLevel.BODY
        }
    }

    suspend fun sendNotification(
        projectId: String,
        messageBody: FcmMessage,
        serviceAccountJson: String
    ): HttpResponse {
        val accessToken = authProvider.getAccessToken(serviceAccountJson)
        val url = "https://fcm.googleapis.com/v1/projects/$projectId/messages:send"

        val response: HttpResponse = client.post(url) {
            header(HttpHeaders.Authorization, "Bearer $accessToken")
            contentType(ContentType.Application.Json)
            setBody(messageBody)
        }

        println("Response: ${response.status}")
        println("Response: ${response.bodyAsText()}")

        return response
    }
}

class GoogleAuthProviderImpl : GoogleAuthProvider {
    override suspend fun getAccessToken(serviceAccountJson: String): String {
        val credentials = GoogleCredentials
            .fromStream(File(serviceAccountJson).inputStream())
            .createScoped(listOf("https://www.googleapis.com/auth/firebase.messaging"))
        credentials.refreshIfExpired()

        return credentials.accessToken.tokenValue
    }
}

@Serializable
data class FcmNotification(
    val token: String,
    val data: FcmData
)

@Serializable
data class FcmData(
    val title: String,
    val content: String,
    val category: String,
    val web_url: String,
    val badge: String,
    val message_badge: String,
    val noti_identifiers: String,
    val extra_data: String?,
    val image_url: String
)

@Serializable
data class FcmMessage(
    val message: FcmNotification
)