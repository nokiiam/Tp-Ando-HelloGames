package fr.epita.android.hellogames

import android.content.Intent
import android.net.Uri
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

class GameDetailFragment : Fragment() {

    private var gameId : Int = 0;
    private var game : Game? = null;

    private var url : String = "";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(
            R.layout.game_detail_fragment,
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

        val callBack: Callback<Game> = object : Callback<Game> {
            override fun onFailure(call: Call<Game>, t: Throwable) {
                Log.w("ERROR","Webservice call failed")
            }
            override fun onResponse(call: Call<Game>, response: Response<Game>) {
                if (response.code() == 200) {
                    game = response.body()
                    NameValue.setText(game!!.name)
                    TypeValue.setText(game!!.type)
                    NbPlayerValue.setText(game!!.players!!.toString())
                    YearValue.setText(game!!.year!!.toString())
                    DescriptionValue.setText(game!!.description_en!!)
                    Glide.with(context!!)
                        .load(game!!.picture)
                        .into(GameIMG)
                }
            }
        }
        gameId = arguments!!.getInt("game_id")
        service.gameDetail(gameId).enqueue(callBack)

        knowMoreButton.setOnClickListener {
            val openURL = Intent(android.content.Intent.ACTION_VIEW)
            openURL.data = Uri.parse(game!!.url!!)
            startActivity(openURL)
        }
    }
}