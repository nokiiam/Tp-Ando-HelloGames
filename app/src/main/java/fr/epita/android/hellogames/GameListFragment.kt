package fr.epita.android.hellogames

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.game_detail_fragment.*
import kotlinx.android.synthetic.main.game_list_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GameListFragment : Fragment() {

    private var gameList = arrayListOf<Game>();

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(
            R.layout.game_list_fragment,
            container,
            false)
        // Return layout view
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        val baseURL = "https://androidlessonsapi.herokuapp.com/api/"
        val jsonConverter = GsonConverterFactory.create(GsonBuilder().create())
        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(jsonConverter)
            .build()
        val service: IHelloGames = retrofit.create(IHelloGames::class.java)

        val callBack: Callback<List<Game>> = object : Callback<List<Game>> {
            override fun onFailure(call: Call<List<Game>>, t: Throwable) {
                Log.w("ERROR","Webservice call failed")
            }
            override fun onResponse(call: Call<List<Game>>, response: Response<List<Game>>) {
                if (response.code() == 200) {
                    var list : List<Game>? = response.body()
                    gameList = ArrayList(list!!.shuffled());

                    Glide.with(context!!)
                        .load(gameList[0].picture)
                        .into(GameView1)

                    Glide.with(context!!)
                        .load(gameList[1].picture)
                        .into(GameView2)

                    Glide.with(context!!)
                        .load(gameList[2].picture)
                        .into(GameView3)

                    Glide.with(context!!)
                        .load(gameList[3].picture)
                        .into(GameView4)
                    // Change the image
                }
            }
        }
        service.gameList().enqueue(callBack)

        GameView1.setOnClickListener {  (activity as MainActivity).goToGame(gameList[0].id) }
        GameView2.setOnClickListener {  (activity as MainActivity).goToGame(gameList[1].id) }
        GameView3.setOnClickListener {  (activity as MainActivity).goToGame(gameList[2].id) }
        GameView4.setOnClickListener {  (activity as MainActivity).goToGame(gameList[3].id) }

    }

}