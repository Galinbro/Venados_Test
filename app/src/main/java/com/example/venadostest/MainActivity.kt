package com.example.venadostest

import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.core.view.GravityCompat
import androidx.appcompat.app.ActionBarDrawerToggle
import android.view.MenuItem
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import androidx.fragment.app.Fragment
import org.jetbrains.anko.toast
import java.net.HttpURLConnection
import java.net.URL


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, GamesFragment.OnFragmentInteractionListener, StatisticsFragment.OnFragmentInteractionListener, PlayersFragment.OnFragmentInteractionListener {

    var jsonHome: String = ""
    var jsonPlayer: String = ""
    var menu = 0
    var previous = 0
    var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)


        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener(this)

        //loading data


        var Myfragment: Fragment
        val url = "https://venados.dacodes.mx/api/games"
        val url2 = "https://venados.dacodes.mx/api/players"
        AsyncTaskHandle().execute(url)
        Handler().postDelayed({
            AsyncTaskHandle().execute(url2)
        }, 500)
        Handler().postDelayed({
            Myfragment = GamesFragment.newInstance(clearJson(jsonHome), clearJson(jsonHome))
            supportFragmentManager.beginTransaction().add(R.id.content_main, Myfragment, "TAG_FRAGMENT_HOME").commit()

        }, 2500)


    }

    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.

        var Myfragment : Fragment = GamesFragment()
        var fragmentSelected = false
        var tag = ""

        when (item.itemId) {
            R.id.nav_home -> {
                if (menu != 0){
                    tag = "TAG_FRAGMENT_HOME"
                    previous = menu
                    menu = 0
                    fragmentSelected = true

                    Myfragment = GamesFragment.newInstance(clearJson(jsonHome),clearJson(jsonHome))

                }
            }
            R.id.nav_sta -> {
                if (menu != 1) {
                    tag = "TAG_FRAGMENT_STA"
                    previous = menu
                    menu = 1
                    Myfragment = StatisticsFragment()
                    fragmentSelected = true

                    val url = "https://venados.dacodes.mx/api/statistics"

                    AsyncTaskHandle().execute(url)

                    Handler().postDelayed({
                        Myfragment = StatisticsFragment.newInstance(clearJson(jsonPlayer), clearJson(jsonPlayer))
                    }, 1000)
                }
            }
            R.id.nav_player -> {
                if (menu != 2) {
                    toast("Player")
                    tag = "TAG_FRAGMENT_PLAYER"
                    previous = menu
                    menu = 2
                    Myfragment = PlayersFragment()
                    fragmentSelected = true

                    Myfragment = PlayersFragment.newInstance(clearJson(jsonPlayer), clearJson(jsonPlayer))

                }
            }
        }

        //previous()

        if (fragmentSelected){
            supportFragmentManager.beginTransaction().replace(R.id.content_main, Myfragment, tag).commit()
        }

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onFragmentInteraction(uri: Uri) {

    }
    

    fun clearJson(json: String): String{
        var x = json.split("{\"success\":true,\"data\":").toTypedArray()
        var y = x[1].split(",\"code\":200}}").toTypedArray()
        var z = y[0]+"}"
        return z
    }

    inner class AsyncTaskHandle : AsyncTask<String, String, String>(){
        
        override fun doInBackground(vararg url: String?): String {

            var text : String
            val connection = URL(url[0]).openConnection() as HttpURLConnection
            connection.setRequestProperty("Content-Type", "application/json; utf-8")
            connection.setRequestProperty("Accept", "application/json")

            try {
                connection.connect()
                text = connection.inputStream.use { it.reader().use { reader -> reader.readText() } }
            }finally {
                connection.disconnect()
            }
            return text
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            handleResponse(result)
        }

        private fun handleResponse(result: String?) {
            try {
                when(count){
                    0 -> {jsonHome = result!!}
                    1 -> {jsonPlayer = result!!}
                }
                count++
            }catch (e: java.lang.Exception){
                Log.d("Json null: ", e.printStackTrace().toString())
            }

        }
    }

}
