package fr.epita.android.hellogames

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface IHelloGames {
    @GET("game/list")
    fun gameList() : Call<List<Game>>

    @GET("game/details")
    fun gameDetail(@Query("game_id") game_id : Int): Call<Game>
}