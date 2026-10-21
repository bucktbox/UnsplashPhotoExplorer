## Unsplash Photo Explorer

Unsplash API로 최신 사진을 보고, 좋아요한 사진은 기기에 저장해서 오프라인에서도 볼 수 있는 앱입니다.

[Releases](https://github.com/bucktbox/UnsplashPhotoExplorer/releases)에서 APK를 받아 설치하면 됩니다.

## 실행 방법

1. [Unsplash Developers](https://unsplash.com/developers)에서 앱을 만들고 Access Key 발급
2. 프로젝트 루트 `local.properties`에 추가

```
UNSPLASH_ACCESS_KEY=발급받은키
```

3. Android Studio에서 실행 (minSdk 26)

- API 키는 시간당 요청 수 제한(50회)이 있어서 스크롤을 많이 하면 요청 한도 초과 화면이 나올 수 있습니다.

## 기능

- 최신 사진 목록 (무한 스크롤)
- 사진 상세 (작가, 설명)
- 좋아요 / 해제 (목록, 상세, 좋아요 목록 어디서든) + 스낵바로 알림
- 목록 상단에 좋아요 개수 표시
- 좋아요 목록
- 좋아요한 사진은 기기에 저장 → 비행기 모드에서도 좋아요 목록, 상세에서 보임

## 모듈 구조

```
app ── ui ── presentation ── domain
 │                             ↑
 ├── data ─────────────────────┘
 ├── remote ── data
 └── local ── data
```

- Clean Architecture + Compose 로 구현 하였습니다.

| 모듈 | 내용 |
| --- | --- |
| app | Hilt Application, NavHost, WorkManager 설정 |
| ui | Compose 화면 |
| presentation | ViewModel, ViewContract (ViewState / ViewIntent / ViewEffect) |
| domain | Domain 모델, UseCase, Repository 인터페이스 |
| data | Repository 구현, PagingSource |
| remote | Retrofit, Unsplash API (네트워크 접근) |
| local | Room, 이미지 파일 저장, Worker (로컬 저장소) |
| data-resource | DataResource (Flow의 상태를 나누기 위함), DomainException |

## 사용 라이브러리

Kotlin, Coroutines/Flow, Jetpack Compose, Navigation, Hilt, Paging 3, Room, WorkManager, Retrofit, Coil

테스트: JUnit4, MockK, Turbine, Truth, Robolectric, MockWebServer 
(Presentation, Domain, Data, Local, Remote 레이어에 대한 테스트)
- domain: 좋아요 저장 순서, 상세 조회 실패 시 저장본 UseCase 테스트
- data: PagingSource, 이미지 저장 테스트
- remote: API 요청 / 에러 변환 테스트
- local: Room, 이미지 파일 저장/삭제 테스트
- presentation: 각 ViewModel 테스트 (목록, 상세, 좋아요 목록)

## 구현 특이 사항

- Clean Architecture + Compose 구조 구현
- PR 시 Test 코드가 통과해야만 가능하도록 CI 도입
- 앱 기능
  + 사진 리스트 : Paging 을 이용한 무한 스크롤 불러오기 가능
  + 좋아요 저장 : Room에 먼저 저장해서 하트는 바로 바뀌고, 이미지 저장은 WorkManager로 백그라운드에서 처리 (네트워크 있을 때만)
  + Flow를 통해 좋아요가 해제 될 시 리스트에 바로 반영 될 수 있도록 함
  + Unsplash 가이드에 맞춰 사진 저장할 때 다운로드 API를 한 번만 호출
  + 서버 에러를 DomainException으로 바꿔서 동일하게 처리
  + 상세 조회가 실패하면 저장된 사진으로 보여줌
- 실기 테스트 간 버그 수정
  + 최신순 목록은 페이지를 넘기는 사이 새 사진이 올라오면 같은 사진이 다음 페이지에 또 나와서 중복 제거 (fatal exception)
  + 화면 전환 중에 버튼을 연속으로 누르면 흰 화면이 나와서, NavHost에서 Resume 될때만 이동, BackStack이 두번 불리지 않게 수정
