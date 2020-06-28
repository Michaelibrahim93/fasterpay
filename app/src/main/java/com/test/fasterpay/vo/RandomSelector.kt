package com.test.fasterpay.vo

import com.test.basemodule.utils.selectRandom

object RandomSelector {
    @JvmStatic
    fun getRandomImage(): String {
        return listOf(
            "https://image.winudf.com/v2/image/Y29tLmJhbGVmb290Lk1vbmtleURMdWZmeVdhbGxwYXBlcl9zY3JlZW5fMF8xNTI0NTE5MTEwXzAyOA/screen-0.jpg?fakeurl=1&type=.jpg",
            "https://onepiece-treasurecruise.com/en/wp-content/uploads/c1814.png",
            "https://vignette.wikia.nocookie.net/onepiece/images/6/64/Roronoa_Zoro_Anime_Pre_Timeskip_Infobox.png/revision/latest/scale-to-width-down/340?cb=20151110155131",
            "https://www.anime-planet.com/images/characters/nami-76.jpg",
            "https://4.bp.blogspot.com/-06dIm3FyLxI/WBwQjqKD0DI/AAAAAAAAAMQ/LJ8prWf0eaAjJ--Req132tyNIWrM-WitwCLcB/s1600/images.jpeg"
        ).selectRandom()
    }

    fun selectCurrency(): Currency {
        return listOf(
            Currency(1.0, "EGP"),
            Currency(16.16, "$"),
            Currency(18.13, "€")
        ).selectRandom()
    }
}