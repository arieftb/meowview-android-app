package id.my.arieftb.meowvie.data.model.request.detail

import id.my.arieftb.meowvie.data.model.request.BaseRequest

class DetailRequest(
    var id: Long = -1,
    var language: String = "en-US",
) : BaseRequest()