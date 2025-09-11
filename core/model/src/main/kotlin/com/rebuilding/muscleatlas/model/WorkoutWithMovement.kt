package com.rebuilding.muscleatlas.model

// 운동 종목 추가 화면에서 사용
data class WorkoutWithMovement(
    val workoutData: WorkoutData,
    val concentricMovementList: List<MovementData>,
    val eccentricMovementList: List<MovementData>,
)


// 고객 별 운동 목록 중 가능한 세부동작여부 데이터 포함
data class WorkoutWithClientMovement(
    val workoutData: WorkoutData,
    val concentricMovementList: List<MovementByClientData>,
    val eccentricMovementList: List<MovementByClientData>,
)
