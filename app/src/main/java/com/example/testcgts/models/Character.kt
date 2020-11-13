package com.example.testcgts.models

import java.io.Serializable

class Character (
    var id: Int? = null,
    var name: String? = null,
    var description: String? = null,
    var modified: String? = null,
    var thumbnail: CharacterThumbnail? = null,
    var resourceURI: String? = null,
    var comics: Comic? = null,
    var series: Serie? = null,
    var stories: Stories? = null,
    var events: Events? = null,
    var urls: List<MarvelURLS>? = arrayListOf(),
) : Serializable

class CharacterThumbnail (
    var path: String? = null,
    var extension: String? = null
) : Serializable