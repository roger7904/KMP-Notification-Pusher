import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch
import androidx.compose.material.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.awt.FileDialog
import java.awt.Frame

@Composable
fun NotificationSenderScreen() {
    var serviceAccountPath by remember { mutableStateOf(SettingsUtil.loadSettings().serviceAccountPath) }
    var projectId by remember { mutableStateOf(SettingsUtil.loadSettings().projectId) }
    var targetDeviceToken by remember { mutableStateOf(SettingsUtil.loadSettings().targetDeviceToken) }
    var jsonMessage by remember { mutableStateOf(SettingsUtil.loadSettings().jsonMessage) }
    val authProvider = GoogleAuthProviderImpl()
    val fcmClient = FcmClient(authProvider)
    val scope = rememberCoroutineScope()

    AppTheme {
        Column(
            modifier = Modifier.background(color = MaterialTheme.colors.background)
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(0.25f),
                    text = "Service Account JSON Path",
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colors.onBackground,
                    style = MaterialTheme.typography.body2.copy(fontWeight = FontWeight.Bold, fontSize = 12.sp),
                )

                Spacer(modifier = Modifier.width(8.dp))

                TextField(
                    modifier = Modifier.weight(0.65f),
                    value = serviceAccountPath,
                    textStyle = MaterialTheme.typography.body2.copy(fontSize = 12.sp),
                    onValueChange = { serviceAccountPath = it },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = MaterialTheme.colors.surface,
                        cursorColor = MaterialTheme.colors.onSurface,
                        textColor = MaterialTheme.colors.onSurface,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                    ),
                )

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary),
                    modifier = Modifier.weight(0.1f),
                    onClick = {
                        val fileDialog = FileDialog(Frame(), "Select Service Account JSON", FileDialog.LOAD)
                        fileDialog.isVisible = true
                        val selectedFile = fileDialog.files.firstOrNull()
                        if (selectedFile != null) {
                            serviceAccountPath = selectedFile.absolutePath
                        }
                    }
                ) {
                    Text(
                        "Browse",
                        style = LocalTextStyle.current.copy(fontSize = 12.sp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(0.25f),
                    text = "Project ID",
                    color = MaterialTheme.colors.onBackground,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.body2.copy(fontWeight = FontWeight.Bold, fontSize = 12.sp),
                )

                Spacer(modifier = Modifier.width(8.dp))

                TextField(
                    modifier = Modifier.weight(0.75f),
                    value = projectId,
                    textStyle = MaterialTheme.typography.body2.copy(fontSize = 12.sp),
                    onValueChange = { projectId = it },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = MaterialTheme.colors.surface,
                        cursorColor = MaterialTheme.colors.onSurface,
                        textColor = MaterialTheme.colors.onSurface,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                    ),
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(0.25f),
                    text = "Target Device Token",
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colors.onBackground,
                    style = MaterialTheme.typography.body2.copy(fontWeight = FontWeight.Bold, fontSize = 12.sp),
                )

                Spacer(modifier = Modifier.width(8.dp))

                TextField(
                    modifier = Modifier.weight(0.75f),
                    value = targetDeviceToken,
                    textStyle = MaterialTheme.typography.body2.copy(fontSize = 12.sp),
                    onValueChange = { targetDeviceToken = it },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = MaterialTheme.colors.surface,
                        cursorColor = MaterialTheme.colors.onSurface,
                        textColor = MaterialTheme.colors.onSurface,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth().weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(0.25f),
                    text = "External json data(Optional)",
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colors.onBackground,
                    style = MaterialTheme.typography.body2.copy(fontWeight = FontWeight.Bold, fontSize = 12.sp),
                )

                Spacer(modifier = Modifier.width(8.dp))

                TextField(
                    modifier = Modifier.weight(0.75f).fillMaxHeight(),
                    value = jsonMessage,
                    onValueChange = { jsonMessage = it },
                    maxLines = 20,
                    textStyle = MaterialTheme.typography.body2.copy(fontSize = 12.sp),
                    singleLine = false,
                    label = { Text("JSON Message") },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = MaterialTheme.colors.surface,
                        cursorColor = MaterialTheme.colors.onSurface,
                        textColor = MaterialTheme.colors.onSurface,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                    ),
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                modifier = Modifier.fillMaxWidth(0.3f),
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary),
                onClick = {
                    SettingsUtil.saveSettings(
                        UserSettings(
                            serviceAccountPath,
                            projectId,
                            jsonMessage,
                            targetDeviceToken
                        )
                    )

                    scope.launch {
                        try {
                            val fcmData =
                                kotlinx.serialization.json.Json.decodeFromString<FcmData>(jsonMessage)

                            val response = fcmClient.sendNotification(
                                projectId,
                                FcmMessage(
                                    FcmNotification(
                                        targetDeviceToken,
                                        fcmData
                                    )
                                ),
                                serviceAccountPath
                            )
                            println("Notification sent successfully: ${response.status}")
                        } catch (e: Exception) {
                            println("Failed to send notification: ${e.message}")
                        }
                    }
                },
            ) {
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = "Send Notification",
                    style = LocalTextStyle.current.copy(fontSize = 14.sp)
                )
            }
        }
    }
}