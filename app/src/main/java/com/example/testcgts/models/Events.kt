package com.example.testcgts.models

import java.io.Serializable

class Events (
    var available: Int? = null,
    var collectionURI: String? = null,
    var items: List<ItemCommon>? = arrayListOf(),
    var returned: Int? = null,
) : Serializable