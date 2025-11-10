package com.collisioncatcher.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun SpeedScreen() {
    var currentSpeed by remember { mutableStateOf(0) }
    var maxSpeed by remember { mutableStateOf(0) }
    var avgSpeed by remember { mutableStateOf(0) }
    var speedLimit by remember { mutableStateOf(60) }
    var isSpeeding by remember { mutableStateOf(false) }
    
    // Simulate real-time speed updates
    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)
            val newSpeed = Random.nextInt(0, 120)
            currentSpeed = newSpeed
            if (newSpeed > maxSpeed) maxSpeed = newSpeed
            avgSpeed = (avgSpeed + newSpeed) / 2
            isSpeeding = newSpeed > speedLimit
        }
    }
    
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Speed Monitor",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Real-time speed tracking and alerts",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        
        item {
            // Speed Gauge Card
            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.elevatedCardColors(
                    containerColor = if (isSpeeding) Color(0xFFFFEBEE) else Color(0xFFE8F5E8)
                )
            ) {
                Column(
                    modifier = Modifier.padding(24.dp).fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Current Speed",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(Modifier.height(16.dp))
                    
                    // Speed Display
                    Box(
                        modifier = Modifier
                            .size(200.dp)
                            .background(
                                color = if (isSpeeding) Color(0xFFD32F2F).copy(alpha = 0.1f) else Color(0xFF4CAF50).copy(alpha = 0.1f),
                                shape = RoundedCornerShape(100.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "$currentSpeed",
                                style = MaterialTheme.typography.displayLarge,
                                fontWeight = FontWeight.Bold,
                                color = if (isSpeeding) Color(0xFFD32F2F) else Color(0xFF4CAF50)
                            )
                            Text(
                                text = "km/h",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                    
                    Spacer(Modifier.height(16.dp))
                    
                    if (isSpeeding) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Warning,
                                contentDescription = null,
                                tint = Color(0xFFD32F2F),
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(Modifier.width(8.dp))
                            Text(
                                text = "Speed Limit Exceeded!",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFD32F2F)
                            )
                        }
                    } else {
                        Text(
                            text = "Within Speed Limit",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color(0xFF4CAF50)
                        )
                    }
                }
            }
        }
        
        item {
            // Speed Statistics
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                SpeedStatCard(
                    title = "Max Speed",
                    value = "$maxSpeed km/h",
                    color = Color(0xFFDC2626),
                    modifier = Modifier.weight(1f)
                )
                SpeedStatCard(
                    title = "Avg Speed",
                    value = "$avgSpeed km/h",
                    color = Color(0xFF1E3A8A),
                    modifier = Modifier.weight(1f)
                )
            }
        }
        
        item {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                SpeedStatCard(
                    title = "Speed Limit",
                    value = "$speedLimit km/h",
                    color = Color(0xFF059669),
                    modifier = Modifier.weight(1f)
                )
                SpeedStatCard(
                    title = "Over Limit",
                    value = if (isSpeeding) "${currentSpeed - speedLimit} km/h" else "0 km/h",
                    color = if (isSpeeding) Color(0xFFD32F2F) else Color(0xFF4CAF50),
                    modifier = Modifier.weight(1f)
                )
            }
        }
        
        item {
            // Speed History
            Text(
                text = "Speed History",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
        }
        
        item {
            OutlinedCard(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    SpeedHistoryItem(
                        time = "2:30 PM",
                        speed = "85 km/h",
                        location = "Highway 101",
                        isOverLimit = true
                    )
                    Divider(modifier = Modifier.padding(vertical = 8.dp))
                    SpeedHistoryItem(
                        time = "2:25 PM",
                        speed = "45 km/h",
                        location = "Main Street",
                        isOverLimit = false
                    )
                    Divider(modifier = Modifier.padding(vertical = 8.dp))
                    SpeedHistoryItem(
                        time = "2:20 PM",
                        speed = "95 km/h",
                        location = "Highway 101",
                        isOverLimit = true
                    )
                }
            }
        }
    }
}

@Composable
private fun SpeedStatCard(
    title: String,
    value: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        modifier = modifier,
        colors = CardDefaults.elevatedCardColors(
            containerColor = color.copy(alpha = 0.1f)
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 100.dp)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Medium,
                    color = color,
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = value,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = color,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun SpeedHistoryItem(
    time: String,
    speed: String,
    location: String,
    isOverLimit: Boolean
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = if (isOverLimit) Icons.Default.Warning else Icons.Default.CheckCircle,
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = if (isOverLimit) Color(0xFFD32F2F) else Color(0xFF4CAF50)
        )
        Spacer(Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "$speed at $location",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = time,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        if (isOverLimit) {
            Text(
                text = "OVER LIMIT",
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFD32F2F)
            )
        }
    }
}
