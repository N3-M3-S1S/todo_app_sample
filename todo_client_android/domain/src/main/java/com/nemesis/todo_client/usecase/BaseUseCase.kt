package com.nemesis.todo_client.usecase

interface BaseUseCase<in Params, out Result> {
    suspend operator fun invoke(params: Params): Result
}
