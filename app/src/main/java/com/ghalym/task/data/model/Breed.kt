package com.ghalym.task.data.model


import com.google.gson.annotations.SerializedName

data class Breed(
    @SerializedName("adaptability")
    val adaptability: Int,
    @SerializedName("affection_level")
    val affectionLevel: Int,
    @SerializedName("alt_names")
    val altNames: String,
    @SerializedName("cfa_url")
    val cfaUrl: String,
    @SerializedName("child_friendly")
    val childFriendly: Int,
    @SerializedName("country_code")
    val countryCode: String,
    @SerializedName("country_codes")
    val countryCodes: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("dog_friendly")
    val dogFriendly: Int,
    @SerializedName("energy_level")
    val energyLevel: Int,
    @SerializedName("experimental")
    val experimental: Int,
    @SerializedName("grooming")
    val grooming: Int,
    @SerializedName("hairless")
    val hairless: Int,
    @SerializedName("health_issues")
    val healthIssues: Int,
    @SerializedName("hypoallergenic")
    val hypoallergenic: Int,
    @SerializedName("id")
    val id: String,
    @SerializedName("indoor")
    val indoor: Int,
    @SerializedName("intelligence")
    val intelligence: Int,
    @SerializedName("lap")
    val lap: Int,
    @SerializedName("life_span")
    val lifeSpan: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("natural")
    val natural: Int,
    @SerializedName("origin")
    val origin: String,
    @SerializedName("rare")
    val rare: Int,
    @SerializedName("rex")
    val rex: Int,
    @SerializedName("shedding_level")
    val sheddingLevel: Int,
    @SerializedName("short_legs")
    val shortLegs: Int,
    @SerializedName("social_needs")
    val socialNeeds: Int,
    @SerializedName("stranger_friendly")
    val strangerFriendly: Int,
    @SerializedName("suppressed_tail")
    val suppressedTail: Int,
    @SerializedName("temperament")
    val temperament: String,
    @SerializedName("vcahospitals_url")
    val vcahospitalsUrl: String,
    @SerializedName("vetstreet_url")
    val vetstreetUrl: String,
    @SerializedName("vocalisation")
    val vocalisation: Int,
    @SerializedName("weight")
    val weight: Weight,
    @SerializedName("wikipedia_url")
    val wikipediaUrl: String
)