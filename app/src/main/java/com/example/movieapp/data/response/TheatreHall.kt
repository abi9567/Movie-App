package com.example.movieapp.data.response

data class TheatreHall(
    val layout : Map<String, List<Seat?>?>
) {
    companion object {
        val theatreSeatsAvailable = TheatreHall(
            layout = mapOf(
                "A" to Seat.seatsA,
                "B" to Seat.seatsB,
                "C" to Seat.seatsC,
                "D" to Seat.seatsD,
                "E" to Seat.seatsE,
                "F" to Seat.seatsF,
                "G" to Seat.seatsG,
                "H" to Seat.seatsH,
            ),
        )
    }
}

data class Seat(
    val seatNumber : String?,
    val available : Boolean?,
    val price : Int?,
    val type : String?
) {
    companion object {
        val seatsA = listOf(
            Seat(
                seatNumber = "A1",
                available = true,
                price = 250,
                type = "Regular"
            ),
            Seat(
                seatNumber = "A2",
                available = false,
                price = 200,
                type = "Regular"
            ),
            Seat(
                seatNumber = "A3",
                available = true,
                price = 300,
                type = "VIP"
            ),
            Seat(
                seatNumber = null,
                available = null,
                price = null,
                type = null
            ),
            Seat(
                seatNumber = "A4",
                available = false,
                price = 300,
                type = "VIP"
            ),
            Seat(
                seatNumber = "A5",
                available = true,
                price = 300,
                type = "VIP"
            ),
            Seat(
                seatNumber = "A6",
                available = false,
                price = 300,
                type = "VIP"
            ),
            Seat(
                seatNumber = "A7",
                available = true,
                price = 300,
                type = "VIP"
            ),
            Seat(
                seatNumber = "A8",
                available = false,
                price = 300,
                type = "VIP"
            ),
            Seat(
                seatNumber = "A9",
                available = true,
                price = 300,
                type = "VIP"
            ),
            Seat(
                seatNumber = "A10",
                available = false,
                price = 300,
                type = "VIP"
            ),
            Seat(
                seatNumber = "A11",
                available = true,
                price = 300,
                type = "VIP"
            ),
            Seat(
                seatNumber = null,
                available = null,
                price = null,
                type = null
            ),
            Seat(
                seatNumber = "A12",
                available = false,
                price = 300,
                type = "VIP"
            )
        )
        val seatsB = listOf(
            Seat(
                seatNumber = "B1",
                available = true,
                price = 250,
                type = "Regular"
            ),
            Seat(
                seatNumber = "B2",
                available = false,
                price = 200,
                type = "Regular"
            ),
            Seat(
                seatNumber = "B3",
                available = true,
                price = 300,
                type = "VIP"
            ),
            Seat(
                seatNumber = null,
                available = null,
                price = null,
                type = null
            ),
            Seat(
                seatNumber = "B4",
                available = false,
                price = 300,
                type = "VIP"
            ),
            Seat(
                seatNumber = "B5",
                available = true,
                price = 300,
                type = "VIP"
            ),
            Seat(
                seatNumber = "B6",
                available = false,
                price = 300,
                type = "VIP"
            ),
            Seat(
                seatNumber = "B7",
                available = true,
                price = 300,
                type = "VIP"
            ),
            Seat(
                seatNumber = "B8",
                available = false,
                price = 300,
                type = "VIP"
            ),
            Seat(
                seatNumber = "B9",
                available = true,
                price = 300,
                type = "VIP"
            ),
            Seat(
                seatNumber = "B10",
                available = false,
                price = 300,
                type = "VIP"
            ),
            Seat(
                seatNumber = "B11",
                available = true,
                price = 300,
                type = "VIP"
            ),
            Seat(
                seatNumber = null,
                available = null,
                price = null,
                type = null
            ),
            Seat(
                seatNumber = "B12",
                available = false,
                price = 300,
                type = "VIP"
            ),
            Seat(
                seatNumber = "B13",
                available = true,
                price = 300,
                type = "VIP"
            )
        )
        val seatsC = listOf(
            Seat(
                seatNumber = "C1",
                available = true,
                price = 250,
                type = "Regular"
            ),
            Seat(
                seatNumber = null,
                available = null,
                price = null,
                type = null
            ),
            Seat(
                seatNumber = null,
                available = null,
                price = null,
                type = null
            ),
            Seat(
                seatNumber = null,
                available = null,
                price = null,
                type = null
            ),
            Seat(
                seatNumber = "C2",
                available = true,
                price = 200,
                type = "Regular"
            ),
            Seat(
                seatNumber = "C3",
                available = true,
                price = 300,
                type = "VIP"
            ),
            Seat(
                seatNumber = "C4",
                available = true,
                price = 300,
                type = "VIP"
            ),
            Seat(
                seatNumber = "C5",
                available = true,
                price = 300,
                type = "VIP"
            ),
            Seat(
                seatNumber = "C6",
                available = true,
                price = 300,
                type = "VIP"
            ),
            Seat(
                seatNumber = "C7",
                available = true,
                price = 300,
                type = "VIP"
            ),
            Seat(
                seatNumber = "C8",
                available = true,
                price = 300,
                type = "VIP"
            ),
            Seat(
                seatNumber = "C9",
                available = true,
                price = 300,
                type = "VIP"
            ),
            Seat(
                seatNumber = null,
                available = null,
                price = null,
                type = null
            ),
            Seat(
                seatNumber = "C10",
                available = false,
                price = 300,
                type = "VIP"
            ),
            Seat(
                seatNumber = "C11",
                available = true,
                price = 300,
                type = "VIP"
            )
        )
        val seatsD = listOf(
            Seat(
                seatNumber = "D1",
                available = true,
                price = 250,
                type = "Regular"
            ),
            Seat(
                seatNumber = null,
                available = null,
                price = null,
                type = null
            ),
            Seat(
                seatNumber = null,
                available = null,
                price = null,
                type = null
            ),
            Seat(
                seatNumber = null,
                available = null,
                price = null,
                type = null
            ),
            Seat(
                seatNumber = "D2",
                available = true,
                price = 200,
                type = "Regular"
            ),
            Seat(
                seatNumber = "D3",
                available = true,
                price = 300,
                type = "VIP"
            ),
            Seat(
                seatNumber = "D4",
                available = true,
                price = 300,
                type = "VIP"
            ),
            Seat(
                seatNumber = "D5",
                available = true,
                price = 300,
                type = "VIP"
            ),
            Seat(
                seatNumber = "D6",
                available = true,
                price = 300,
                type = "VIP"
            ),
            Seat(
                seatNumber = "D7",
                available = true,
                price = 300,
                type = "VIP"
            ),
            Seat(
                seatNumber = "D8",
                available = false,
                price = 300,
                type = "VIP"
            ),
            Seat(
                seatNumber = "D9",
                available = true,
                price = 300,
                type = "VIP"
            ),
            Seat(
                seatNumber = null,
                available = null,
                price = null,
                type = null
            ),
            Seat(
                seatNumber = "D10",
                available = false,
                price = 300,
                type = "VIP"
            ),
            Seat(
                seatNumber = "D11",
                available = true,
                price = 200,
                type = "VIP"
            )
        )
        val seatsE = listOf(
            Seat(
                seatNumber = "E1",
                available = true,
                price = 250,
                type = "Regular"
            ),
            Seat(
                seatNumber = null,
                available = null,
                price = null,
                type = null
            ),
            Seat(
                seatNumber = null,
                available = null,
                price = null,
                type = null
            ),
            Seat(
                seatNumber = null,
                available = null,
                price = null,
                type = null
            ),
            Seat(
                seatNumber = "E2",
                available = true,
                price = 200,
                type = "Regular"
            ),
            Seat(
                seatNumber = "E3",
                available = true,
                price = 300,
                type = "VIP"
            ),
            Seat(
                seatNumber = "E4",
                available = true,
                price = 300,
                type = "VIP"
            ),
            Seat(
                seatNumber = "E5",
                available = true,
                price = 300,
                type = "VIP"
            ),
            Seat(
                seatNumber = "E6",
                available = true,
                price = 300,
                type = "VIP"
            ),
            Seat(
                seatNumber = "E7",
                available = true,
                price = 300,
                type = "VIP"
            ),
            Seat(
                seatNumber = "E8",
                available = true,
                price = 300,
                type = "VIP"
            ),
            Seat(
                seatNumber = "E9",
                available = false,
                price = 300,
                type = "VIP"
            ),
            Seat(
                seatNumber = null,
                available = null,
                price = null,
                type = null
            ),
            Seat(
                seatNumber = "E10",
                available = true,
                price = 300,
                type = "VIP"
            ),
            Seat(
                seatNumber = "E11",
                available = true,
                price = 300,
                type = null
            )
        )
        val seatsF = listOf(
            Seat(
                seatNumber = "F1",
                available = true,
                price = 250,
                type = "Regular"
            ),
            Seat(
                seatNumber = "F2",
                available = false,
                price = 200,
                type = "Regular"
            ),
            Seat(
                seatNumber = "F3",
                available = true,
                price = 300,
                type = "VIP"
            ),
            Seat(
                seatNumber = null,
                available = null,
                price = null,
                type = null
            ),
            Seat(
                seatNumber = "F4",
                available = false,
                price = 300,
                type = "VIP"
            ),
            Seat(
                seatNumber = "F5",
                available = true,
                price = 300,
                type = "VIP"
            ),
            Seat(
                seatNumber = "F6",
                available = false,
                price = 300,
                type = "VIP"
            ),
            Seat(
                seatNumber = "F7",
                available = true,
                price = 300,
                type = "VIP"
            ),
            Seat(
                seatNumber = "F8",
                available = false,
                price = 300,
                type = "VIP"
            ),
            Seat(
                seatNumber = "F9",
                available = true,
                price = 300,
                type = "VIP"
            ),
            Seat(
                seatNumber = "F10",
                available = false,
                price = 300,
                type = "VIP"
            ),
            Seat(
                seatNumber = "F11",
                available = true,
                price = 300,
                type = "VIP"
            ),
            Seat(
                seatNumber = null,
                available = null,
                price = null,
                type = null
            ),
            Seat(
                seatNumber = "F12",
                available = true,
                price = 300,
                type = "VIP"
            ),
            Seat(
                seatNumber = "F13",
                available = true,
                price = 250,
                type = "VIP"
            )
        )
        val seatsG = listOf(
            Seat(
                seatNumber = "G1",
                available = true,
                price = 250,
                type = "Regular"
            ),
            Seat(
                seatNumber = "G2",
                available = false,
                price = 200,
                type = "Regular"
            ),
            Seat(
                seatNumber = "G3",
                available = true,
                price = 300,
                type = "VIP"
            ),
            Seat(
                seatNumber = null,
                available = null,
                price = null,
                type = null
            ),
            Seat(
                seatNumber = "G4",
                available = false,
                price = 300,
                type = "VIP"
            ),
            Seat(
                seatNumber = "G5",
                available = true,
                price = 300,
                type = "VIP"
            ),
            Seat(
                seatNumber = "G6",
                available = false,
                price = 300,
                type = "VIP"
            ),
            Seat(
                seatNumber = "G7",
                available = true,
                price = 300,
                type = "VIP"
            ),
            Seat(
                seatNumber = "G8",
                available = false,
                price = 300,
                type = "VIP"
            ),
            Seat(
                seatNumber = "G9",
                available = true,
                price = 300,
                type = "VIP"
            ),
            Seat(
                seatNumber = "G10",
                available = false,
                price = 300,
                type = "VIP"
            ),
            Seat(
                seatNumber = "G11",
                available = true,
                price = 300,
                type = "VIP"
            ),
            Seat(
                seatNumber = null,
                available = null,
                price = null,
                type = null
            ),
            Seat(
                seatNumber = "G12",
                available = false,
                price = 300,
                type = "VIP"
            ),
            Seat(
                seatNumber = "G13",
                available = false,
                price = 250,
                type = "VIP"
            )
        )
        val seatsH = listOf(
            Seat(
                seatNumber = "H1",
                available = true,
                price = 250,
                type = "Regular"
            ),
            Seat(
                seatNumber = "H2",
                available = false,
                price = 200,
                type = "Regular"
            ),
            Seat(
                seatNumber = "H3",
                available = true,
                price = 300,
                type = "VIP"
            ),
            Seat(
                seatNumber = null,
                available = null,
                price = null,
                type = null
            ),
            Seat(
                seatNumber = "H4",
                available = false,
                price = 300,
                type = "VIP"
            ),
            Seat(
                seatNumber = "H5",
                available = true,
                price = 300,
                type = "VIP"
            ),
            Seat(
                seatNumber = "H6",
                available = false,
                price = 300,
                type = "VIP"
            ),
            Seat(
                seatNumber = "H7",
                available = true,
                price = 300,
                type = "VIP"
            ),
            Seat(
                seatNumber = "H8",
                available = false,
                price = 300,
                type = "VIP"
            ),
            Seat(
                seatNumber = "H9",
                available = true,
                price = 300,
                type = "VIP"
            ),
            Seat(
                seatNumber = "H10",
                available = true,
                price = 255,
                type = null
            ),
            Seat(
                seatNumber = "H11",
                available = true,
                price = 300,
                type = "VIP"
            ),
            Seat(
                seatNumber = null,
                available = null,
                price = null,
                type = null
            ),
            Seat(
                seatNumber = "H12",
                available = false,
                price = 300,
                type = "VIP"
            ),
            Seat(
                seatNumber = "H13",
                available = true,
                price = 300,
                type = "VIP"
            )
        )
    }
}
