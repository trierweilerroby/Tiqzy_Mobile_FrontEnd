package com.example.tiqzy_mobile_frontend.data.repository

import com.example.tiqzy_mobile_frontend.data.model.City
import com.example.tiqzy_mobile_frontend.data.model.Event
import javax.inject.Inject

class CitiesRepository @Inject constructor() {

    fun fetchCities(): List<City> {
        return listOf(
            City(
                cityId = 1,
                name = "Amsterdam",
                image = "https://media.iamsterdam.com/w_768,h_436/49fxg4ua8q6r-amsterdam-canals-in-the-city-centre-with-a-beaufitul-sky.webp"
            ),
            City(
                cityId = 2,
                name = "Rotterdam",
                image = "https://www.travelcircus.de/urlaubsziele/wp-content/uploads/2016/10/shutterstock_227678515-1024x645.jpg"
            ),
            City(
                cityId = 3,
                name = "Utrecht",
                image = "https://media.cntraveller.com/photos/66e94ba5a235add05a2dbf98/16:9/w_5104,h_2871,c_limit/Utrecht%20canal-GettyImages-553820561.jpg"
            ),
            City(
                cityId = 4,
                name = "The Hague",
                image = "https://cdn.audleytravel.com/5234/3740/79/15985279-the-hague-skyline-netherlands.jpg"
            ),
            City(
                cityId = 5,
                name = "Eindhoven",
                image = "https://www.holland.com/upload_mm/7/a/0/68599_fullimage_de%20blob%281%29.jpg"
            )
        )
    }

    fun fetchByCityId(cityId: Int): City? {
        return fetchCities().find { it.cityId == cityId }
    }
}
