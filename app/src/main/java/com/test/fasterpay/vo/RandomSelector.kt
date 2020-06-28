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

    fun nextSource(): Source {
        return listOf(
            Source("Google", "https://media-exp1.licdn.com/dms/image/C4D0BAQHiNSL4Or29cg/company-logo_200_200/0?e=2159024400&v=beta&t=0e00tehBFFtuqgUCfAijpOkoBl89jxOTIe_k9HHpi_4"),
            Source("Facebook", "https://lh5.googleusercontent.com/proxy/3tZhvZdKWIMN2MOhUFUeLj1VYkSpnRBG0KiDKZ6drEEU-6Nf73V6bX2k_dKt0SLskbf1vMozXODP5yQNb-zBtk58qTg_ha17r-4cJMeDCVVC"),
            Source("Amazon", "https://store-images.s-microsoft.com/image/apps.55760.13510798887500513.d2cc5d6f-e9f4-4850-a5d6-bbd7976d6c2d.ed4ef02c-b3d1-497d-8297-1a54e79abfad?mode=scale&q=90&h=200&w=200&background=%230078D7"),
            Source("ebay", "https://www.talkwalker.com/Pictures/Brands/small/csm-talkwalker-ebay-fce6911624..jpg")
            ).selectRandom()
    }
}