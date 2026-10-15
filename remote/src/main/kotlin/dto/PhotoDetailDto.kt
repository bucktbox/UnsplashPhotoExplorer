package com.kyeongilhan.photoexplorer.remote.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PhotoDetailDto(
    @SerialName("id")
    val id: String?,
    @SerialName("alternative_slugs")
    val alternativeSlugs: AlternativeSlugs?,
    @SerialName("created_at")
    val createdAt: String?,
    @SerialName("updated_at")
    val updatedAt: String?,
    @SerialName("promoted_at")
    val promotedAt: Any?,
    @SerialName("width")
    val width: Int?,
    @SerialName("height")
    val height: Int?,
    @SerialName("color")
    val color: String?,
    @SerialName("blur_hash")
    val blurHash: String?,
    @SerialName("description")
    val description: String?,
    @SerialName("breadcrumbs")
    val breadcrumbs: List<Any?>?,
    @SerialName("urls")
    val urls: Urls?,
    @SerialName("likes")
    val likes: Int?,
    @SerialName("liked_by_user")
    val likedByUser: Boolean?,
    @SerialName("bookmarked")
    val bookmarked: Boolean?,
    @SerialName("current_user_collections")
    val currentUserCollections: List<Any?>?,
    @SerialName("sponsorship")
    val sponsorship: Sponsorship?,
    @SerialName("topic_submissions")
    val topicSubmissions: TopicSubmissions?,
    @SerialName("asset_type")
    val assetType: String?,
    @SerialName("slug")
    val slug: String?,
    @SerialName("alt_description")
    val altDescription: String?,
    @SerialName("short_description")
    val shortDescription: String?,
    @SerialName("seo_meta_description")
    val seoMetaDescription: Any?,
    @SerialName("links")
    val links: Links?,
    @SerialName("user")
    val user: User?,
    @SerialName("exif")
    val exif: Exif?,
    @SerialName("location")
    val location: Location?,
    @SerialName("meta")
    val meta: Meta?,
    @SerialName("public_domain")
    val publicDomain: Boolean?,
    @SerialName("tags")
    val tags: List<Tag?>?,
    @SerialName("views")
    val views: Int?,
    @SerialName("downloads")
    val downloads: Int?,
    @SerialName("topics")
    val topics: List<Any?>?
) {
    @Serializable
    data class AlternativeSlugs(
        @SerialName("en")
        val en: String?,
        @SerialName("es")
        val es: String?,
        @SerialName("ja")
        val ja: String?,
        @SerialName("fr")
        val fr: String?,
        @SerialName("it")
        val `it`: String?,
        @SerialName("ko")
        val ko: String?,
        @SerialName("de")
        val de: String?,
        @SerialName("pt")
        val pt: String?,
        @SerialName("id")
        val id: String?
    )

    @Serializable
    data class Urls(
        @SerialName("raw")
        val raw: String?,
        @SerialName("full")
        val full: String?,
        @SerialName("regular")
        val regular: String?,
        @SerialName("small")
        val small: String?,
        @SerialName("thumb")
        val thumb: String?,
        @SerialName("small_s3")
        val smallS3: String?
    )

    @Serializable
    data class Sponsorship(
        @SerialName("impression_urls")
        val impressionUrls: List<Any?>?,
        @SerialName("tagline")
        val tagline: String?,
        @SerialName("tagline_url")
        val taglineUrl: String?,
        @SerialName("sponsor")
        val sponsor: Sponsor?
    ) {
        @Serializable
        data class Sponsor(
            @SerialName("id")
            val id: String?,
            @SerialName("updated_at")
            val updatedAt: String?,
            @SerialName("username")
            val username: String?,
            @SerialName("name")
            val name: String?,
            @SerialName("first_name")
            val firstName: String?,
            @SerialName("last_name")
            val lastName: Any?,
            @SerialName("twitter_username")
            val twitterUsername: Any?,
            @SerialName("portfolio_url")
            val portfolioUrl: String?,
            @SerialName("bio")
            val bio: String?,
            @SerialName("location")
            val location: Any?,
            @SerialName("links")
            val links: Links?,
            @SerialName("profile_image")
            val profileImage: ProfileImage?,
            @SerialName("instagram_username")
            val instagramUsername: String?,
            @SerialName("total_collections")
            val totalCollections: Int?,
            @SerialName("total_likes")
            val totalLikes: Int?,
            @SerialName("total_photos")
            val totalPhotos: Int?,
            @SerialName("total_free_photos")
            val totalFreePhotos: Int?,
            @SerialName("total_promoted_photos")
            val totalPromotedPhotos: Int?,
            @SerialName("total_illustrations")
            val totalIllustrations: Int?,
            @SerialName("total_free_illustrations")
            val totalFreeIllustrations: Int?,
            @SerialName("total_promoted_illustrations")
            val totalPromotedIllustrations: Int?,
            @SerialName("accepted_tos")
            val acceptedTos: Boolean?,
            @SerialName("for_hire")
            val forHire: Boolean?,
            @SerialName("social")
            val social: Social?
        ) {
            @Serializable
            data class Links(
                @SerialName("self")
                val self: String?,
                @SerialName("photos")
                val photos: String?,
                @SerialName("likes")
                val likes: String?,
                @SerialName("html")
                val html: String?
            )

            @Serializable
            data class ProfileImage(
                @SerialName("small")
                val small: String?,
                @SerialName("medium")
                val medium: String?,
                @SerialName("large")
                val large: String?
            )

            @Serializable
            data class Social(
                @SerialName("instagram_username")
                val instagramUsername: String?,
                @SerialName("portfolio_url")
                val portfolioUrl: String?,
                @SerialName("twitter_username")
                val twitterUsername: Any?,
                @SerialName("paypal_email")
                val paypalEmail: Any?
            )
        }
    }

    @Serializable
    class TopicSubmissions

    @Serializable
    data class Links(
        @SerialName("self")
        val self: String?,
        @SerialName("html")
        val html: String?,
        @SerialName("download")
        val download: String?,
        @SerialName("download_location")
        val downloadLocation: String?
    )

    @Serializable
    data class User(
        @SerialName("id")
        val id: String?,
        @SerialName("updated_at")
        val updatedAt: String?,
        @SerialName("username")
        val username: String?,
        @SerialName("name")
        val name: String?,
        @SerialName("first_name")
        val firstName: String?,
        @SerialName("last_name")
        val lastName: Any?,
        @SerialName("twitter_username")
        val twitterUsername: Any?,
        @SerialName("portfolio_url")
        val portfolioUrl: String?,
        @SerialName("bio")
        val bio: String?,
        @SerialName("location")
        val location: Any?,
        @SerialName("links")
        val links: Links?,
        @SerialName("profile_image")
        val profileImage: ProfileImage?,
        @SerialName("instagram_username")
        val instagramUsername: String?,
        @SerialName("total_collections")
        val totalCollections: Int?,
        @SerialName("total_likes")
        val totalLikes: Int?,
        @SerialName("total_photos")
        val totalPhotos: Int?,
        @SerialName("total_free_photos")
        val totalFreePhotos: Int?,
        @SerialName("total_promoted_photos")
        val totalPromotedPhotos: Int?,
        @SerialName("total_illustrations")
        val totalIllustrations: Int?,
        @SerialName("total_free_illustrations")
        val totalFreeIllustrations: Int?,
        @SerialName("total_promoted_illustrations")
        val totalPromotedIllustrations: Int?,
        @SerialName("accepted_tos")
        val acceptedTos: Boolean?,
        @SerialName("for_hire")
        val forHire: Boolean?,
        @SerialName("social")
        val social: Social?
    ) {
        @Serializable
        data class Links(
            @SerialName("self")
            val self: String?,
            @SerialName("photos")
            val photos: String?,
            @SerialName("likes")
            val likes: String?,
            @SerialName("html")
            val html: String?
        )

        @Serializable
        data class ProfileImage(
            @SerialName("small")
            val small: String?,
            @SerialName("medium")
            val medium: String?,
            @SerialName("large")
            val large: String?
        )

        @Serializable
        data class Social(
            @SerialName("instagram_username")
            val instagramUsername: String?,
            @SerialName("portfolio_url")
            val portfolioUrl: String?,
            @SerialName("twitter_username")
            val twitterUsername: Any?,
            @SerialName("paypal_email")
            val paypalEmail: Any?
        )
    }

    @Serializable
    data class Exif(
        @SerialName("make")
        val make: String?,
        @SerialName("model")
        val model: String?,
        @SerialName("name")
        val name: String?,
        @SerialName("exposure_time")
        val exposureTime: String?,
        @SerialName("aperture")
        val aperture: String?,
        @SerialName("focal_length")
        val focalLength: String?,
        @SerialName("iso")
        val iso: Int?
    )

    @Serializable
    data class Location(
        @SerialName("name")
        val name: String?,
        @SerialName("city")
        val city: Any?,
        @SerialName("country")
        val country: Any?,
        @SerialName("position")
        val position: Position?
    ) {
        @Serializable
        data class Position(
            @SerialName("latitude")
            val latitude: Any?,
            @SerialName("longitude")
            val longitude: Any?
        )
    }

    @Serializable
    data class Meta(
        @SerialName("index")
        val index: Boolean?
    )

    @Serializable
    data class Tag(
        @SerialName("type")
        val type: String?,
        @SerialName("title")
        val title: String?
    )
}