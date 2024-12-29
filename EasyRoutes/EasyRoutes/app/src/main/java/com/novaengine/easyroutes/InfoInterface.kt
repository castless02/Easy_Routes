package com.novaengine.easyroutes

import org.jetbrains.annotations.NotNull
import java.net.CacheResponse

interface InfoInterface {

    fun onError(@NotNull response: Any)

    fun onReturnValue(@NotNull response: Any)
}
