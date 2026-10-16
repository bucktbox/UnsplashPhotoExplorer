package com.kyeongilhan.photoexplorer.domain.photo.model

// 화면에서 사용할 필드만 선정
data class Photo(
    // 사진 id
    val id: String,
    // 사진 가로
    val width: Int,
    // 사진 세로
    val height: Int,
    // 사진 설명
    val description: String?,
    // 사진 색깔 (로딩 배경)
    // 이 프로젝트에서는 blur hash 대신 이걸 사용
    val color: String?,
    // 사진 다운로드용 url -> urls.regular
    // https://images.unsplash.com/photo-1791395668835-9696dbfd2d95?ixlib=rb-4.1.0&q=85&fm=jpg&crop=entropy&cs=srgb
    val imageUrl: String,
    // 사진 저자
    val author: Author,
    // 사진 저장하고 다운로드 카운트 올려주는 api url - 다운로드 전 호출 권장 (api 정책 상 필수 사항!)
    // https://api.unsplash.com/photos/j9Vp6hAZDV8/download?ixid=M3wxMTAwNjU2fDB8MXxhbGx8fHx8fHx8fHwxNzkyMDczMjEyfA
    val downloadLocation: String,
)

// 작가 정보
data class Author(
    val name: String,
    val username: String,
    val profileImageUrl: String?
)