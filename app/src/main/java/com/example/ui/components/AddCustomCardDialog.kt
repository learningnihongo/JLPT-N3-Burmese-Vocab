package com.example.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun AddCustomCardDialog(
    onDismiss: () -> Unit,
    onConfirm: (kanji: String, reading: String, burmese: String, pos: String, example: String, exBurmese: String, note: String) -> Unit
) {
    var kanji by remember { mutableStateOf("") }
    var reading by remember { mutableStateOf("") }
    var burmese by remember { mutableStateOf("") }
    var pos by remember { mutableStateOf("Noun") }
    var example by remember { mutableStateOf("") }
    var exBurmese by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }

    var isError by remember { mutableStateOf(false) }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .testTag("add_custom_card_dialog")
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Create Personalized Flashcard",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                Text(
                    text = "Add new Kanji, Kotoba, and Burmese notes to your custom deck.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.outline
                )

                OutlinedTextField(
                    value = kanji,
                    onValueChange = {
                        kanji = it
                        if (isError) isError = false
                    },
                    label = { Text("Kanji / Vocabulary *") },
                    placeholder = { Text("e.g. 挑戦する") },
                    isError = isError && kanji.isBlank(),
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("input_custom_kanji")
                )

                OutlinedTextField(
                    value = reading,
                    onValueChange = {
                        reading = it
                        if (isError) isError = false
                    },
                    label = { Text("Reading (Hiragana/Furigana) *") },
                    placeholder = { Text("e.g. ちょうせんする") },
                    isError = isError && reading.isBlank(),
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("input_custom_reading")
                )

                OutlinedTextField(
                    value = burmese,
                    onValueChange = {
                        burmese = it
                        if (isError) isError = false
                    },
                    label = { Text("Burmese Meaning *") },
                    placeholder = { Text("e.g. စိန်ခေါ်ယှဉ်ပြိုင်သည်") },
                    isError = isError && burmese.isBlank(),
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("input_custom_burmese")
                )

                OutlinedTextField(
                    value = pos,
                    onValueChange = { pos = it },
                    label = { Text("Part of Speech") },
                    placeholder = { Text("e.g. Verb, Noun, I-Adj, Na-Adj") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = example,
                    onValueChange = { example = it },
                    label = { Text("Example Japanese Sentence (Optional)") },
                    placeholder = { Text("e.g. 新しいことに挑戦する。") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = exBurmese,
                    onValueChange = { exBurmese = it },
                    label = { Text("Example Burmese Translation (Optional)") },
                    placeholder = { Text("e.g. အရာသစ်တစ်ခုကို စိန်ခေါ်လုပ်ဆောင်သည်။") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = note,
                    onValueChange = { note = it },
                    label = { Text("Personal Mnemonic / Note (Optional)") },
                    placeholder = { Text("e.g. Memory tip: 挑 (challenge) + 戦 (fight)") },
                    modifier = Modifier.fillMaxWidth()
                )

                if (isError) {
                    Text(
                        text = "Please fill in all required fields marked with *",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Text("Cancel")
                    }

                    Button(
                        onClick = {
                            if (kanji.isBlank() || reading.isBlank() || burmese.isBlank()) {
                                isError = true
                            } else {
                                onConfirm(kanji, reading, burmese, pos, example, exBurmese, note)
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        modifier = Modifier.testTag("save_custom_card_btn")
                    ) {
                        Text("Save Card (+15 XP)")
                    }
                }
            }
        }
    }
}
