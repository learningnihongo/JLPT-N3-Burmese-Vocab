package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.model.Badge
import com.example.ui.components.BadgeDetailDialog
import com.example.ui.components.DailyReminderSettingsCard
import com.example.ui.components.TrophyShowcaseCard
import com.example.ui.theme.JapaneseCrimson
import com.example.ui.theme.JapaneseIndigo
import com.example.ui.theme.MasteredGreen
import com.example.ui.theme.ReviewBlue
import com.example.ui.theme.SakuraPinkDark
import com.example.ui.theme.WeakOrange
import com.example.ui.viewmodel.VocabViewModel

@Composable
fun ProfileScreen(
    vocabViewModel: VocabViewModel,
    modifier: Modifier = Modifier
) {
    val profile by vocabViewModel.userProfile.collectAsState()
    val totalCards by vocabViewModel.totalCardCount.collectAsState()
    val masteredCards by vocabViewModel.masteredCount.collectAsState()
    val dueCards by vocabViewModel.dueCount.collectAsState()
    val badges by vocabViewModel.allBadges.collectAsState()
    val reminderSettings by vocabViewModel.reminderSettings.collectAsState()

    var showEditProfileDialog by remember { mutableStateOf(false) }
    var selectedBadgeForDetail by remember { mutableStateOf<Badge?>(null) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .testTag("profile_screen"),
        contentPadding = PaddingValues(bottom = 90.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // User Profile Header Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.horizontalGradient(
                                listOf(
                                    JapaneseCrimson.copy(alpha = 0.08f),
                                    JapaneseIndigo.copy(alpha = 0.08f)
                                )
                            )
                        )
                        .padding(20.dp)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(14.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(56.dp)
                                        .clip(CircleShape)
                                        .background(MaterialTheme.colorScheme.primaryContainer),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = profile?.name?.take(2)?.uppercase() ?: "KK",
                                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 20.sp
                                    )
                                }

                                Column {
                                    Text(
                                        text = profile?.name ?: "JLPT Scholar",
                                        style = MaterialTheme.typography.titleLarge,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Text(
                                        text = "Target: JLPT ${profile?.targetJlptLevel ?: "N3"} • Daily Goal: ${profile?.dailyGoal ?: 15} words",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }

                            IconButton(onClick = { showEditProfileDialog = true }) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = "Edit Profile",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }

                        // Gamified Level & XP Section
                        val xp = profile?.totalXp ?: 0
                        val currentLevel = profile?.level ?: 1
                        val nextLevelXp = currentLevel * 200
                        val currentLevelProgress = (xp % 200).toFloat() / 200f

                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = MaterialTheme.colorScheme.surface,
                            border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.35f)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Star,
                                            contentDescription = null,
                                            tint = com.example.ui.theme.StreakOrange,
                                            modifier = Modifier.size(20.dp)
                                        )
                                        Text(
                                            text = "Level $currentLevel",
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                    }

                                    Text(
                                        text = "$xp Total XP",
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }

                                LinearProgressIndicator(
                                    progress = { currentLevelProgress },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(8.dp)
                                        .clip(RoundedCornerShape(50)),
                                    color = MaterialTheme.colorScheme.primary,
                                    trackColor = com.example.ui.theme.PolishOutlineVariant
                                )

                                Text(
                                    text = "${200 - (xp % 200)} XP to reach Level ${currentLevel + 1}",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }
        }

        // Spaced Repetition (SRS) Mastery Analytics
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Psychology,
                            contentDescription = null,
                            tint = JapaneseCrimson,
                            modifier = Modifier.size(22.dp)
                        )
                        Text(
                            text = "Long-Term Retention Tracker",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    Text(
                        text = "The SuperMemo-2 algorithm predicts optimal review intervals to guarantee long-term vocabulary memory before forgetting occurs.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.outline
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        SrsMetricBox(
                            title = "Mastered",
                            value = "$masteredCards",
                            color = MasteredGreen,
                            modifier = Modifier.weight(1f)
                        )
                        SrsMetricBox(
                            title = "Due for Review",
                            value = "$dueCards",
                            color = JapaneseCrimson,
                            modifier = Modifier.weight(1f)
                        )
                        SrsMetricBox(
                            title = "Total Database",
                            value = "$totalCards",
                            color = JapaneseIndigo,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }

        // Study Streak and Habit Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                            .background(WeakOrange.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocalFireDepartment,
                            contentDescription = null,
                            tint = WeakOrange,
                            modifier = Modifier.size(28.dp)
                        )
                    }

                    Column {
                        Text(
                            text = "${profile?.currentStreak ?: 1}-Day Learning Streak",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "Daily habit strengthens kanji neural pathways. Complete at least one review daily!",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.outline
                        )
                    }
                }
            }
        }

        // Daily Study Reminder Settings Card (WorkManager Local Notifications)
        item {
            DailyReminderSettingsCard(
                settings = reminderSettings,
                onToggleEnabled = { enabled ->
                    vocabViewModel.setReminderEnabled(enabled)
                },
                onUpdateTime = { hour, minute ->
                    vocabViewModel.setReminderTime(hour, minute)
                },
                onToggleSmartReminder = { smart ->
                    vocabViewModel.setSmartReminderEnabled(smart)
                },
                onTestReminder = {
                    vocabViewModel.triggerTestReminder()
                }
            )
        }

        // Digital Trophies & Milestones Showcase
        item {
            TrophyShowcaseCard(
                badges = badges,
                onBadgeClick = { badge ->
                    selectedBadgeForDetail = badge
                }
            )
        }

        // Offline Ready Indicator
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.CloudDone,
                        contentDescription = "Offline Mode",
                        tint = MasteredGreen,
                        modifier = Modifier.size(26.dp)
                    )

                    Column {
                        Text(
                            text = "Offline Mode: 100% Active",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "All JLPT N3 vocabulary, audio pronunciation, and SRS databases are stored locally on your device without needing internet.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.outline
                        )
                    }
                }
            }
        }
    }

    selectedBadgeForDetail?.let { badge ->
        BadgeDetailDialog(
            badge = badge,
            onDismiss = { selectedBadgeForDetail = null }
        )
    }

    if (showEditProfileDialog) {
        EditProfileDialog(
            currentName = profile?.name ?: "JLPT Scholar",
            currentGoal = profile?.dailyGoal ?: 15,
            currentLevel = profile?.targetJlptLevel ?: "N3",
            onDismiss = { showEditProfileDialog = false },
            onSave = { name, goal, level ->
                vocabViewModel.updateProfile(name, goal, level)
                showEditProfileDialog = false
            }
        )
    }
}

@Composable
private fun SrsMetricBox(
    title: String,
    value: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(14.dp),
        color = color.copy(alpha = 0.1f),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = color
            )
            Text(
                text = title,
                style = MaterialTheme.typography.labelSmall,
                color = color,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
private fun EditProfileDialog(
    currentName: String,
    currentGoal: Int,
    currentLevel: String,
    onDismiss: () -> Unit,
    onSave: (String, Int, String) -> Unit
) {
    var name by remember { mutableStateOf(currentName) }
    var goalText by remember { mutableStateOf(currentGoal.toString()) }
    var level by remember { mutableStateOf(currentLevel) }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .testTag("edit_profile_dialog")
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Edit Student Profile",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Student Name") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = goalText,
                    onValueChange = { goalText = it },
                    label = { Text("Daily Review Goal (Cards)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = level,
                    onValueChange = { level = it },
                    label = { Text("Target JLPT Level") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    OutlinedButton(onClick = onDismiss, modifier = Modifier.padding(end = 8.dp)) {
                        Text("Cancel")
                    }

                    Button(
                        onClick = {
                            val goal = goalText.toIntOrNull() ?: 15
                            onSave(name.trim(), goal, level.trim())
                        }
                    ) {
                        Text("Save Profile")
                    }
                }
            }
        }
    }
}
