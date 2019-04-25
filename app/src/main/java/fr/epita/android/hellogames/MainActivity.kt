package fr.epita.android.hellogames

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragment = GameListFragment()

        supportFragmentManager.beginTransaction()
            .add(R.id.Main_conteneur, fragment)
            .commit()
    }

    fun goToGame(number : Int) {
        val databundle = Bundle()

        databundle.putInt("game_id", number);

        val newFragment = GameDetailFragment();
        newFragment.arguments = databundle;

        supportFragmentManager.beginTransaction()
            .replace(R.id.Main_conteneur, newFragment)
            .commit()
    }
}
