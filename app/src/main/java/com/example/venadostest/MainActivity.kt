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
import com.beust.klaxon.Klaxon
import java.net.HttpURLConnection
import java.net.URL



class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, GamesFragment.OnFragmentInteractionListener, StatisticsFragment.OnFragmentInteractionListener, PLayersFragment.OnFragmentInteractionListener {

    var jsonGames: String = ""

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

        //val url = "https://venados.dacodes.mx/api/games"
       // AsyncTaskHandle().execute(url)


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
        val args = Bundle()

        when (item.itemId) {
            R.id.nav_home -> {

                fragmentSelected = true
                //val url = "http://mysafeinfo.com/api/data?list=presidents&format=json"
                val url = "https://venados.dacodes.mx/api/games"

                AsyncTaskHandle().execute(url)

                Handler().postDelayed({
                    var x = jsonGames.split("{\"success\":true,\"data\":").toTypedArray()
                    var y = x[1].split(",\"code\":200}}").toTypedArray()
                    var z = y[0]+"}"

                    Myfragment = GamesFragment.newInstance(z,z)
                    //Log.d("key",gamesObject!!.games[0].opponent )
                }, 1000)


            }
            R.id.nav_sta -> {
                Myfragment = StatisticsFragment()
                fragmentSelected = true

                val url = "https://venados.dacodes.mx/api/statistics"

                AsyncTaskHandle().execute(url)
            }
            R.id.nav_player -> {
                Myfragment = StatisticsFragment()
                fragmentSelected = true

                val url = "https://venados.dacodes.mx/api/players"

                AsyncTaskHandle().execute(url)
            }
        }

        Handler().postDelayed({
            if (fragmentSelected){
                supportFragmentManager.beginTransaction().replace(R.id.content_main, Myfragment).commit()
            }
        }, 1050)
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onFragmentInteraction(uri: Uri) {

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
            Log.d("Get:", result)
            try {
                jsonGames = result!!
            }catch (e: java.lang.Exception){
                Log.d("Json null: ", e.printStackTrace().toString())
            }

        }
    }

}
