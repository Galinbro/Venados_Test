package com.example.venadostest

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import com.beust.klaxon.Klaxon
import kotlinx.android.synthetic.main.mold_games.*
import kotlinx.android.synthetic.main.mold_games.view.*
import java.io.FileNotFoundException
import java.text.SimpleDateFormat
import java.util.concurrent.ExecutionException
import java.util.*
import kotlin.collections.ArrayList




// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [GamesFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [GamesFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class GamesFragment(var adapter: GameAdapter? = null) : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null


    data class GamesList (val games: List<Games>)
    var gamesObject: GamesList? = null

    var listaCopa = arrayListOf<Games>()
    var listaAsc = arrayListOf<Games>()
    var myList: ListView? = null

    var liga: Boolean = true
    var repeated = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        Log.d("key", param1)

        gamesObject = Klaxon()
            .parse<GamesList>(param1!!)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_games, container, false)

        myList = view.findViewById<ListView>(R.id.listView)

        games()

        view.findViewById<Button>(R.id.btnCopa).setOnClickListener {
            liga = true
            repeated = ""
            txtViewMonth.setBackgroundColor(getResources().getColor(R.color.grey))
            view.findViewById<Button>(R.id.btnCopa).setBackgroundResource(R.drawable.btn_light_green)
            view.findViewById<Button>(R.id.btnAsc).setBackgroundResource(R.drawable.btn_unselected)
            games()
        }

        view.findViewById<Button>(R.id.btnAsc).setOnClickListener {
            liga = false
            repeated = ""
            txtViewMonth.setBackgroundColor(getResources().getColor(R.color.grey))
            view.findViewById<Button>(R.id.btnAsc).setBackgroundResource(R.drawable.btn_light_green)
            view.findViewById<Button>(R.id.btnCopa).setBackgroundResource(R.drawable.btn_unselected)
            games()
        }
        return view
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    fun games(){

        listaCopa.clear()
        listaAsc.clear()

        for (g in gamesObject!!.games){
            if (g.league.equals("Copa MX"))
                listaCopa.add(Games(g.local, g.opponent, g.opponent_image, g.datetime, g.league, g.image, g.home_score, g.away_score))
            else
                listaAsc.add(Games(g.local, g.opponent, g.opponent_image, g.datetime, g.league, g.image, g.home_score, g.away_score))
        }

        if (liga)
            adapter = GameAdapter(context!!, listaCopa)
        else
            adapter = GameAdapter(context!!, listaAsc)

        myList!!.adapter = adapter
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment GamesFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            GamesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    /*

                    aqui llenamos el template de nota con la informacion

     */
    inner class GameAdapter(contexto : Context, var listadeNotas : ArrayList<Games>) : BaseAdapter()  {

        var contexto: Context? = contexto

        override fun getView(p0: Int, view: View?, p2: ViewGroup?): View {

            var convertView : View? = view
            if (convertView == null){
                convertView = View.inflate(contexto, R.layout.mold_games,null)
            }
            val game = listadeNotas[p0]

            val miVista =  convertView

            //val sd2 = SimpleDateFormat("EEE, dd")
            //val newDate = sd2.parse(game.datetime)

            miVista!!.txtViewDate.text = game.datetime.split("-").toTypedArray().get(2) + " " + (SimpleDateFormat("yyyy-MM-dd").parse(game.datetime).toString().split(" ").toTypedArray().get(0))
            miVista!!.txtViewHome.text = "Venados F.C"
            miVista!!.txtScore.text = """${game.home_score} - ${game.away_score}"""
            miVista!!.txtViewAway.text = game.opponent


            if (repeated.equals(game.datetime.split("-").toTypedArray().get(1))) {
                miVista!!.txtViewMonth.text = ""
                miVista!!.txtViewMonth.setBackgroundColor(getResources().getColor(R.color.green))
            }
            else {
                miVista!!.txtViewMonth.setBackgroundColor(getResources().getColor(R.color.grey))
                repeated = game.datetime.split("-").toTypedArray().get(1)
                miVista!!.txtViewMonth.text =
                    SimpleDateFormat("MMMM").format(SimpleDateFormat("yyyy-MM-dd").parse(game.datetime))
            }

            Log.d("date:",game.datetime)
            val task = ImageDownloader()
            val bitmap: Bitmap
            try {
                bitmap = task.execute(game.opponent_image).get()

                miVista!!.imgViewAway.setImageResource(0)
                miVista!!.imgViewAway.setImageBitmap(bitmap)
                miVista!!.imgViewAway.requestLayout()

                miVista!!.imgViewAway.layoutParams.height = 200;

                miVista!!.imgViewAway.layoutParams.width = 250

                miVista!!.imgViewAway.scaleType = ImageView.ScaleType.FIT_XY

            } catch (e: ExecutionException) {
                e.printStackTrace()
            } catch (e: InterruptedException) {
                e.printStackTrace()
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: Exception) {
                e.printStackTrace()
            }


            miVista!!.imgViewCalendar.setOnClickListener {
                val date = game.datetime.split("-").toTypedArray()
                val calDate = GregorianCalendar(date[0].toInt(), date[1].toInt()-1, date[2].toInt())

                val calendarEvent = Calendar.getInstance()
                val i = Intent(Intent.ACTION_EDIT)
                i.type = "vnd.android.cursor.item/event"
                i.putExtra("beginTime", calDate.getTimeInMillis())
                i.putExtra("allDay", true)
                i.putExtra("rule", "FREQ=YEARLY")
                i.putExtra("endTime", calDate.getTimeInMillis() + 60 * 60 * 1000)
                i.putExtra("title", "Venados F.C vs ${game.opponent}")
                startActivity(i)
            }


            return miVista
        }

        override fun getItem(p0: Int): Any {
            return listadeNotas[p0]
        }

        override fun getItemId(p0: Int): Long {
            return p0.toLong()
        }

        override fun getCount(): Int {
            return listadeNotas.size
        }

    }
}
