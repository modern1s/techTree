package com.sparta.techTree.common.exception

data class LikeSameIdException(val id: Any) : RuntimeException(
    "Author cannot like their own id: $id"
)
