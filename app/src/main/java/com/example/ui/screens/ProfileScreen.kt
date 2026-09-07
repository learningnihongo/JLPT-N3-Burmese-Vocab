package com.example.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.example.data.model.Badge
import com.example.ui.components.AppThemeSettingsCard
import com.example.ui.components.BadgeDetailDialog
import com.example.ui.components.DailyReminderSettingsCard
import com.example.ui.components.TrophyShowcaseCard
import com.example.ui.components.VoiceSettingsCard
import com.example.ui.components.WeeklyMasteryProgressChartCard
import com.example.ui.theme.JapaneseCrimson
import com.example.ui.theme.JapaneseIndigo
import com.example.ui.theme.MasteredGreen
import com.example.ui.theme.PolishOutlineVariant
import com.example.ui.theme.StreakOrange
import com.example.ui.viewmodel.VocabViewModel

private val PRESET_AVATARS = listOf(
    Pair("🧑‍🏫", "Sensei"),
    Pair("🌸", "Sakura"),
    Pair("⚔️", "Samurai"),
    Pair("🥷", "Shinobi"),
    Pair("🦊", "Kitsune"),
    Pair("👺", "Tengu"),
    Pair("⛩️", "Torii"),
    Pair("🗻", "Fuji")
)

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
    val themeSettings by vocabViewModel.themeSettings.collectAsState()
    val voiceSettings by vocabViewModel.voiceSettings.collectAsState()
    val isSpeaking by vocabViewModel.isSpeaking.collectAsState()
    val weeklyProgressSummary by vocabViewModel.weeklyMasteryVsReviewedStats.collectAsState()

    var showEditProfileDialog by remember { mutableStateOf(false) }
    var selectedBadgeForDetail by remember { mutableStateOf<Badge?>(null) }
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    val tabs = listOf(
        Triple("Preferences", Icons.Default.Tune, "Settings & Audio"),
        Triple("Achievements", Icons.Default.EmojiEvents, "Badges & Rhythm"),
        Triple("System Info", Icons.Default.Info, "Offline & Details")
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("profile_screen"),
        contentPadding = PaddingValues(bottom = 96.dp, top = 16.dp, start = 20.dp, end = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 1. Clean & Elegant Profile Header Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("profile_header_card"),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.12f)),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Avatar + Profile Info + Edit Profile Button
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(14.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            // User Avatar (Clickable to change photo/profile)
                            Box(
                                modifier = Modifier
                                    .size(68.dp)
                                    .clip(CircleShape)
                                    .clickable { showEditProfileDialog = true }
                            ) {
                                if (!profile?.customAvatarUri.isNullOrBlank()) {
                                    AsyncImage(
                                        model = profile?.customAvatarUri,
                                        contentDescription = "Profile Picture",
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .clip(CircleShape)
                                            .border(2.dp, JapaneseCrimson, CircleShape)
                                    )
                                } else {
                                    val avatarPreset = PRESET_AVATARS.getOrNull(profile?.avatarIndex ?: 0)
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .clip(CircleShape)
                                            .background(
                                                Brush.radialGradient(
                                                    listOf(
                                                        JapaneseCrimson.copy(alpha = 0.2f),
                                                        JapaneseIndigo.copy(alpha = 0.15f)
                                                    )
                                                )
                                            )
                                            .border(2.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f), CircleShape),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        if (avatarPreset != null) {
                                            Text(
                                                text = avatarPreset.first,
                                                fontSize = 32.sp
                                            )
                                        } else {
                                            Text(
                                                text = profile?.name?.take(2)?.uppercase() ?: "JL",
                                                color = JapaneseCrimson,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 22.sp
                                            )
                                        }
                                    }
                                }

                                // Small Camera Badge
                                Box(
                                    modifier = Modifier
                                        .size(22.dp)
                                        .align(Alignment.BottomEnd)
                                        .clip(CircleShape)
                                        .background(JapaneseCrimson),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.PhotoCamera,
                                        contentDescription = "Change Photo",
                                        tint = Color.White,
                                        modifier = Modifier.size(12.dp)
                                    )
                                }
                            }

                            // Profile Name and JLPT Targets
                            Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
                                Text(
                                    text = profile?.name ?: "Sensei Student",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold
                                    ),
                                    color = MaterialTheme.colorScheme.onSurface
                                )

                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Surface(
                                        shape = RoundedCornerShape(50),
                                        color = JapaneseCrimson.copy(alpha = 0.1f),
                                        border = BorderStroke(0.8.dp, JapaneseCrimson.copy(alpha = 0.3f))
                                    ) {
                                        Text(
                                            text = "JLPT ${profile?.targetJlptLevel ?: "N3"}",
                                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, fontSize = 10.sp),
                                            color = JapaneseCrimson,
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                        )
                                    }

                                    Surface(
                                        shape = RoundedCornerShape(50),
                                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                                    ) {
                                        Text(
                                            text = "${profile?.dailyGoal ?: 15} words/day",
                                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                        )
                                    }
                                }

                                Text(
                                    text = profile?.levelTitle ?: "Novice Learner",
                                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                                    color = MaterialTheme.colorScheme.outline
                                )
                            }
                        }

                        // Edit Button
                        OutlinedButton(
                            onClick = { showEditProfileDialog = true },
                            shape = RoundedCornerShape(12.dp),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Edit Profile",
                                modifier = Modifier.size(14.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Edit",
                                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }

                    // Level & XP Bar
                    val xp = profile?.totalXp ?: 0
                    val currentLevel = profile?.level ?: 1
                    val currentLevelProgress = (xp % 150).toFloat() / 150f

                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.08f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
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
                                        tint = StreakOrange,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Text(
                                        text = "Level $currentLevel",
                                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                }

                                Text(
                                    text = "$xp XP Total",
                                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }

                            LinearProgressIndicator(
                                progress = { currentLevelProgress },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(5.dp)
                                    .clip(RoundedCornerShape(50)),
                                color = MaterialTheme.colorScheme.primary,
                                trackColor = PolishOutlineVariant
                            )

                            Text(
                                text = "${150 - (xp % 150)} XP to reach Level ${currentLevel + 1}",
                                style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
                                color = MaterialTheme.colorScheme.outline
                            )
                        }
                    }
                }
            }
        }

        // 2. Clean 4-Tile Learning Stats Summary
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.12f)),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Learning Overview",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        ProfileStatTile(
                            title = "Mastered",
                            value = "$masteredCards",
                            icon = Icons.Default.CheckCircle,
                            color = MasteredGreen,
                            modifier = Modifier.weight(1f)
                        )
                        ProfileStatTile(
                            title = "Due Review",
                            value = "$dueCards",
                            icon = Icons.Default.Psychology,
                            color = JapaneseCrimson,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        ProfileStatTile(
                            title = "Streak",
                            value = "${profile?.currentStreak ?: 1} Days",
                            icon = Icons.Default.LocalFireDepartment,
                            color = StreakOrange,
                            modifier = Modifier.weight(1f)
                        )
                        ProfileStatTile(
                            title = "Total Library",
                            value = "$totalCards Words",
                            icon = Icons.Default.School,
                            color = JapaneseIndigo,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }

        // 3. Clean Segmented Category Tabs (Preferences, Achievements, System Info)
        item {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.08f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    tabs.forEachIndexed { index, (label, icon, _) ->
                        val isSelected = selectedTabIndex == index
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = if (isSelected) MaterialTheme.colorScheme.surface else Color.Transparent,
                            border = if (isSelected) BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.12f)) else null,
                            shadowElevation = if (isSelected) 1.dp else 0.dp,
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(12.dp))
                                .clickable { selectedTabIndex = index }
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 10.dp),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = icon,
                                    contentDescription = null,
                                    tint = if (isSelected) JapaneseCrimson else MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.size(15.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = label,
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                        fontSize = 11.5.sp
                                    ),
                                    color = if (isSelected) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }
        }

        // 4. Tabbed Content (Clean & Organized, Avoiding Endless Monolithic Scroll)
        item {
            AnimatedContent(
                targetState = selectedTabIndex,
                transitionSpec = { fadeIn() togetherWith fadeOut() },
                label = "profile_tabs_content"
            ) { tabIndex ->
                when (tabIndex) {
                    0 -> {
                        // TAB 0: Preferences & Settings (Reminders, Voice Audio, Theme)
                        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
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

                            VoiceSettingsCard(
                                settings = voiceSettings,
                                isSpeaking = isSpeaking,
                                onUpdateSpeed = { speed ->
                                    vocabViewModel.setVoiceSpeechRate(speed)
                                },
                                onUpdatePitch = { pitch ->
                                    vocabViewModel.setVoicePitch(pitch)
                                },
                                onTogglePhonetic = { phonetic ->
                                    vocabViewModel.setVoicePreferPhonetic(phonetic)
                                },
                                onToggleAutoPlayFlip = { autoFlip ->
                                    vocabViewModel.setVoiceAutoPlayFlip(autoFlip)
                                },
                                onTestVoice = {
                                    vocabViewModel.testVoicePronunciation()
                                }
                            )

                            AppThemeSettingsCard(
                                themeSettings = themeSettings,
                                onSelectMode = { mode ->
                                    vocabViewModel.setThemeMode(mode)
                                },
                                onSelectPalette = { palette ->
                                    vocabViewModel.setThemePalette(palette)
                                },
                                onSelectIconStyle = { style ->
                                    vocabViewModel.setIconThemeStyle(style)
                                },
                                onToggleOledBlack = { oled ->
                                    vocabViewModel.setOledBlack(oled)
                                }
                            )
                        }
                    }

                    1 -> {
                        // TAB 1: Achievements & Progress Rhythm (Weekly Chart + Badges Showcase)
                        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                            WeeklyMasteryProgressChartCard(
                                weeklySummary = weeklyProgressSummary
                            )

                            TrophyShowcaseCard(
                                badges = badges,
                                onBadgeClick = { badge ->
                                    selectedBadgeForDetail = badge
                                }
                            )
                        }
                    }

                    2 -> {
                        // TAB 2: System Info & Offline Details
                        Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                            // Offline Status Tile
                            Card(
                                shape = RoundedCornerShape(18.dp),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.12f))
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(18.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(42.dp)
                                            .clip(CircleShape)
                                            .background(MasteredGreen.copy(alpha = 0.12f)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.CloudDone,
                                            contentDescription = "Offline Ready",
                                            tint = MasteredGreen,
                                            modifier = Modifier.size(22.dp)
                                        )
                                    }

                                    Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
                                        Text(
                                            text = "100% Offline Storage Active",
                                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                        Text(
                                            text = "All 880 JLPT N3 cards, Burmese meanings, audio pronunciations, and spaced repetition states are stored securely on this device.",
                                            style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }
                            }

                            // Storage Breakdown Card
                            Card(
                                shape = RoundedCornerShape(18.dp),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.12f))
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
                                            imageVector = Icons.Default.Storage,
                                            contentDescription = null,
                                            tint = JapaneseIndigo,
                                            modifier = Modifier.size(18.dp)
                                        )
                                        Text(
                                            text = "Curriculum & Database Overview",
                                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                    }

                                    StorageInfoRow("Target Level", "JLPT N3 (Intermediate)")
                                    StorageInfoRow("Total Vocabulary", "$totalCards Core Words")
                                    StorageInfoRow("Spaced Repetition Algorithm", "SuperMemo SM-2 Adapted")
                                    StorageInfoRow("Audio Synthesis", "Android Native Japanese TTS")
                                    StorageInfoRow("Local Storage Engine", "Android Jetpack Room SQLite")
                                }
                            }

                            // Study Tip Card
                            Surface(
                                shape = RoundedCornerShape(18.dp),
                                color = JapaneseCrimson.copy(alpha = 0.05f),
                                border = BorderStroke(1.dp, JapaneseCrimson.copy(alpha = 0.18f)),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp),
                                    verticalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Text(
                                        text = "💡 Daily Study Recommendation",
                                        style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                                        color = JapaneseCrimson
                                    )
                                    Text(
                                        text = "Reviewing 15 to 20 cards every day consistently produces higher retention than studying 100 cards once a week. Keep your daily streak going to build lasting memory!",
                                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // Badge details dialog
    selectedBadgeForDetail?.let { badge ->
        BadgeDetailDialog(
            badge = badge,
            onDismiss = { selectedBadgeForDetail = null }
        )
    }

    // Clean Edit Profile Dialog
    if (showEditProfileDialog) {
        EditProfileWithAvatarDialog(
            currentName = profile?.name ?: "Sensei Student",
            currentGoal = profile?.dailyGoal ?: 15,
            currentLevel = profile?.targetJlptLevel ?: "N3",
            currentAvatarIndex = profile?.avatarIndex ?: 0,
            currentCustomAvatarUri = profile?.customAvatarUri,
            onDismiss = { showEditProfileDialog = false },
            onSave = { name, goal, level, avatarIndex, customUri ->
                vocabViewModel.updateProfile(
                    name = name,
                    dailyGoal = goal,
                    targetJlpt = level,
                    avatarIndex = avatarIndex,
                    customAvatarUri = customUri
                )
                showEditProfileDialog = false
            }
        )
    }
}

@Composable
private fun ProfileStatTile(
    title: String,
    value: String,
    icon: ImageVector,
    color: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(14.dp),
        color = color.copy(alpha = 0.08f),
        border = BorderStroke(1.dp, color.copy(alpha = 0.18f)),
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .clip(CircleShape)
                    .background(color.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = color,
                    modifier = Modifier.size(18.dp)
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(1.dp)) {
                Text(
                    text = value,
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = title,
                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.5.sp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun StorageInfoRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun EditProfileWithAvatarDialog(
    currentName: String,
    currentGoal: Int,
    currentLevel: String,
    currentAvatarIndex: Int,
    currentCustomAvatarUri: String?,
    onDismiss: () -> Unit,
    onSave: (String, Int, String, Int, String?) -> Unit
) {
    var name by remember { mutableStateOf(currentName) }
    var goalText by remember { mutableStateOf(currentGoal.toString()) }
    var level by remember { mutableStateOf(currentLevel) }
    var selectedAvatarIndex by remember { mutableIntStateOf(currentAvatarIndex) }
    var customAvatarUri by remember { mutableStateOf(currentCustomAvatarUri) }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            customAvatarUri = uri.toString()
        }
    }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .testTag("edit_profile_dialog")
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(22.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Text(
                    text = "Edit Student Profile",
                    style = MaterialTheme.typography.titleLarge.copy(fontSize = 20.sp),
                    fontWeight = FontWeight.Bold,
                    color = JapaneseCrimson
                )

                // Avatar Preview & Gallery Photo Picker Controls
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(76.dp)
                            .clip(CircleShape)
                            .clickable {
                                photoPickerLauncher.launch(
                                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                )
                            }
                    ) {
                        if (!customAvatarUri.isNullOrBlank()) {
                            AsyncImage(
                                model = customAvatarUri,
                                contentDescription = "Chosen Avatar",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(CircleShape)
                                    .border(2.5.dp, JapaneseCrimson, CircleShape)
                            )
                        } else {
                            val preset = PRESET_AVATARS.getOrNull(selectedAvatarIndex)
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(CircleShape)
                                    .background(JapaneseCrimson.copy(alpha = 0.12f))
                                    .border(2.dp, JapaneseCrimson.copy(alpha = 0.3f), CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = preset?.first ?: "🧑‍🏫",
                                    fontSize = 36.sp
                                )
                            }
                        }

                        // Overlay camera icon
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .align(Alignment.BottomEnd)
                                .clip(CircleShape)
                                .background(JapaneseCrimson),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.PhotoCamera,
                                contentDescription = "Pick Image",
                                tint = Color.White,
                                modifier = Modifier.size(13.dp)
                            )
                        }
                    }

                    // Actions for photo: Choose photo or Remove photo
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedButton(
                            onClick = {
                                photoPickerLauncher.launch(
                                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                )
                            },
                            shape = RoundedCornerShape(10.dp),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Image,
                                contentDescription = null,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Choose Photo", fontSize = 12.sp)
                        }

                        if (!customAvatarUri.isNullOrBlank()) {
                            IconButton(
                                onClick = { customAvatarUri = null },
                                modifier = Modifier.size(32.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Remove Photo",
                                    tint = MaterialTheme.colorScheme.error,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }
                }

                // Preset Avatars Selection
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = "Or Select Preset Avatar:",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(vertical = 4.dp)
                    ) {
                        itemsIndexed(PRESET_AVATARS) { index, preset ->
                            val isSelected = selectedAvatarIndex == index && customAvatarUri.isNullOrBlank()
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = if (isSelected) JapaneseCrimson.copy(alpha = 0.15f) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
                                border = BorderStroke(
                                    1.dp,
                                    if (isSelected) JapaneseCrimson else Color.Transparent
                                ),
                                modifier = Modifier
                                    .clip(RoundedCornerShape(12.dp))
                                    .clickable {
                                        selectedAvatarIndex = index
                                        customAvatarUri = null
                                    }
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Text(text = preset.first, fontSize = 18.sp)
                                    Text(
                                        text = preset.second,
                                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                        color = if (isSelected) JapaneseCrimson else MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            }
                        }
                    }
                }

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Student Name") },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = goalText,
                    onValueChange = { goalText = it },
                    label = { Text("Daily Review Goal (Cards)") },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                // Target JLPT Level Chips
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = "Target JLPT Level:",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        listOf("N5", "N4", "N3", "N2", "N1").forEach { jlpt ->
                            FilterChip(
                                selected = level == jlpt,
                                onClick = { level = jlpt },
                                label = { Text(jlpt, fontSize = 11.sp) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = JapaneseIndigo,
                                    selectedLabelColor = Color.White
                                ),
                                shape = RoundedCornerShape(8.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Text("Cancel")
                    }

                    Button(
                        onClick = {
                            val goal = goalText.toIntOrNull() ?: 15
                            onSave(name.trim(), goal, level.trim(), selectedAvatarIndex, customAvatarUri)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = JapaneseCrimson),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Save Profile")
                    }
                }
            }
        }
    }
}
