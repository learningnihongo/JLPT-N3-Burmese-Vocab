package com.example.data.model

enum class BadgeCategory(val title: String) {
    ALL("All Badges"),
    STREAK("Streaks"),
    MASTERY("Mastery"),
    QUIZ("Quizzes"),
    LEVEL("Milestones")
}

enum class BadgeTier(val displayName: String, val colorHex: Long) {
    BRONZE("Bronze", 0xFFCD7F32),
    SILVER("Silver", 0xFFA8A8A8),
    GOLD("Gold", 0xFFFFD700),
    PLATINUM("Platinum", 0xFF00E5FF),
    DIAMOND("Diamond", 0xFFB388FF)
}

data class Badge(
    val id: String,
    val title: String,
    val japaneseTitle: String,
    val description: String,
    val category: BadgeCategory,
    val tier: BadgeTier,
    val targetValue: Int,
    val currentValue: Int,
    val xpReward: Int,
    val iconEmoji: String,
    val quote: String = "七転び八起き (Fall seven times, stand up eight)"
) {
    val isUnlocked: Boolean
        get() = currentValue >= targetValue

    val progressFraction: Float
        get() = if (targetValue > 0) (currentValue.toFloat() / targetValue.toFloat()).coerceIn(0f, 1f) else 1f

    val progressPercent: Int
        get() = (progressFraction * 100).toInt()
}
