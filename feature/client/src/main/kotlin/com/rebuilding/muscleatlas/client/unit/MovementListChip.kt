package com.rebuilding.muscleatlas.client.unit

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rebuilding.muscleatlas.model.ClientMovementData
import com.rebuilding.muscleatlas.model.Movement
import com.rebuilding.muscleatlas.model.MovementData
import com.rebuilding.muscleatlas.model.state.ContractionTypeList
import com.rebuilding.muscleatlas.model.state.ContractionTypeWithClientMovementList

@Composable
fun MovementListChip(
    scrollState: ScrollState = ScrollState(0),
    contractions: ContractionTypeWithClientMovementList = ContractionTypeWithClientMovementList(),
    onClickCheckBox: (ClientMovementData) -> Unit = {},
    onClick: (MovementData) -> Unit = {},
) {
    Column(
        modifier = Modifier.verticalScroll(scrollState)
    ) {
        MovementChip(
            title = Movement.JoinMovement.title,
            icon = Icons.Default.Warning,
            movemenetList = contractions.joinMovementList,
            onClickCheckBox = onClickCheckBox,
            onClick = onClick
        )

        Spacer(Modifier.height(16.dp))

        MovementChip(
            title = Movement.StabilizationMechanism.title,
            icon = Icons.Default.Warning,
            movemenetList = contractions.stabilizationMechanismList,
            onClickCheckBox = onClickCheckBox,
            onClick = onClick
        )

        Spacer(Modifier.height(16.dp))

        MovementChip(
            title = Movement.NeuromuscularRelation.title,
            icon = Icons.Default.Warning,
            movemenetList = contractions.neuromuscularRelationList,
            onClickCheckBox = onClickCheckBox,
            onClick = onClick
        )

        Spacer(Modifier.height(100.dp))
    }
}