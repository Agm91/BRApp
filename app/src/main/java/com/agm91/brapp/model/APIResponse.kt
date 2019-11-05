package com.agm91.brapp.model

data class APIResponse<T>(val data: T?, val error: Throwable?)