package com.example.venadostest

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.beust.klaxon.Klaxon
import kotlinx.android.synthetic.main.mold_player.view.*
import org.json.JSONObject
import java.io.FileNotFoundException
import java.util.concurrent.ExecutionException
import android.view.Gravity
import android.graphics.drawable.ColorDrawable
import android.graphics.Point
import android.widget.*
import kotlinx.android.synthetic.main.fragment_players.*
import kotlinx.android.synthetic.main.popup_players.*
import java.text.SimpleDateFormat


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [PLayersFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [PLayersFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class PlayersFragment(var adapter: PlayerAdapter? = null) : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    data class PlayerList (val forwards: List<Player>)
    data class PlayerList2 (val centers: List<Player>)
    data class PlayerList3 (val defenses: List<Player>)
    data class PlayerList4 (val goalkeepers: List<Player>)
    data class PlayerList5 (val coaches: List<Player>)

    var playerObject: PlayerList? = null
    var playerObject2: PlayerList2? = null
    var playerObject3: PlayerList3? = null
    var playerObject4: PlayerList4? = null
    var playerObject5: PlayerList5? = null

    var players = arrayListOf<Player>()
    var myList: GridView? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        var obj = JSONObject(param1)

        var teams = obj.getString("team")

        var jsonTeam = JSONObject(teams)

        var keys = ArrayList<String>()

        jsonTeam.keys().forEach { keys.add(it) }

        var forw = "{\"forwards\":" + jsonTeam.getString(keys[0]) + "}"
        var centers = "{\"centers\":" + jsonTeam.getString(keys[1]) + "}"
        var defenses = "{\"defenses\":" + jsonTeam.getString(keys[2]) + "}"
        var goalkeepers = "{\"goalkeepers\":" + jsonTeam.getString(keys[3]) + "}"
        var coaches = "{\"coaches\":" + jsonTeam.getString(keys[4]) + "}"

        playerObject = Klaxon()
            .parse<PlayerList>(forw)
        playerObject2 = Klaxon()
            .parse<PlayerList2>(centers)
        playerObject3 = Klaxon()
            .parse<PlayerList3>(defenses)
        playerObject4 = Klaxon()
            .parse<PlayerList4>(goalkeepers)
        playerObject5 = Klaxon()
            .parse<PlayerList5>(coaches)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_players, container, false)
        myList = view!!.findViewById<GridView>(R.id.gridView)

        players()

        return view
    }


    fun players(){

        players.clear()


        for (g in playerObject!!.forwards){
            players.add(Player(g.name, g.first_surname, g.second_surname, g.birthday, g.birth_place, g.weight!!, g.height!!, g.position!!, g.number!!, g.position_short!!, g.last_team!!, g.image))
        }
        for (g in playerObject2!!.centers){
            players.add(Player(g.name, g.first_surname, g.second_surname, g.birthday, g.birth_place, g.weight!!, g.height!!, g.position!!, g.number!!, g.position_short!!, g.last_team!!, g.image))
        }
        for (g in playerObject3!!.defenses){
            players.add(Player(g.name, g.first_surname, g.second_surname, g.birthday, g.birth_place, g.weight!!, g.height!!, g.position!!, g.number!!, g.position_short!!, g.last_team!!, g.image))
        }
        for (g in playerObject4!!.goalkeepers){
            players.add(Player(g.name, g.first_surname, g.second_surname, g.birthday, g.birth_place, g.weight!!, g.height!!, g.position!!, g.number!!, g.position_short!!, g.last_team!!, g.image))
        }
        for (g in playerObject5!!.coaches){
            players.add(Player(g.name, g.first_surname, g.second_surname, g.birthday, g.birth_place, g.weight!!, g.height!!, g.role!!, g.role_short!!, g.image))
        }

        adapter = PlayerAdapter(context!!, players)

        myList!!.adapter = adapter
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
         * @return A new instance of fragment PLayersFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PlayersFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    inner class PlayerAdapter(contexto : Context, var listadeNotas : ArrayList<Player>) : BaseAdapter()  {

        var contexto: Context? = contexto

        override fun getView(p0: Int, view: View?, p2: ViewGroup?): View {

            var convertView : View? = view
            if (convertView == null){
                convertView = View.inflate(contexto, R.layout.mold_player,null)
            }
            val game = listadeNotas[p0]

            val miVista =  convertView

            //val sd2 = SimpleDateFormat("EEE, dd")
            //val newDate = sd2.parse(game.datetime)

            //miVista!!.txtViewDate.text = game.datetime.split("-").toTypedArray().get(2) + " " + (SimpleDateFormat("yyyy-MM-dd").parse(game.datetime).toString().split(" ").toTypedArray().get(0))


            if (game.role!!.length>1)
                miVista!!.txtPos.text = game.role
            else
                miVista!!.txtPos.text = game.position

            miVista!!.txtName.text = game.name + " " + game.first_surname





            val task = ImageDownloader()
            val bitmap: Bitmap
            try {
                bitmap = task.execute(game.image).get()

                miVista!!.profile_image.setImageResource(0)
                miVista!!.profile_image.setImageBitmap(bitmap)
                //miVista!!.profile_image.requestLayout()

                //miVista!!.imgViewAway.layoutParams.height = 200;

                //miVista!!.imgViewAway.layoutParams.width = 250

                //miVista!!.imgViewAway.scaleType = ImageView.ScaleType.FIT_XY

            } catch (e: ExecutionException) {
                e.printStackTrace()
            } catch (e: InterruptedException) {
                e.printStackTrace()
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: Exception) {
                e.printStackTrace()
            }


            miVista!!.profile_image.setOnClickListener {

                val popupView = layoutInflater.inflate(R.layout.popup_players, null)
                val display = activity!!.windowManager.defaultDisplay
                val size = Point()
                display.getSize(size)
                val width = size.x
                val height = size.y

                val popupWindow = PopupWindow(
                    popupView
                )
                popupWindow.width = width-100
                popupWindow.height = height-120

                val name = popupView.findViewById(R.id.txtName) as TextView
                val pos = popupView.findViewById<TextView>(R.id.txtPos)
                name.text = game.name + " " + game.first_surname + " " + game.second_surname
                pos.text = game.position

                var b = game.birthday.substring(0,10).split("-").toTypedArray()

                popupView.findViewById<TextView>(R.id.txtBir).text = b[2] + "/" + b[1] + "/" + b[0]
                popupView.findViewById<TextView>(R.id.txtPlace).text = game.birth_place
                popupView.findViewById<TextView>(R.id.txtW).text = game.weight.toString()
                popupView.findViewById<TextView>(R.id.txtH).text = game.height.toString()
                popupView.findViewById<TextView>(R.id.txtLT).text = game.last_team

                val task = ImageDownloader()
                val bitmap: Bitmap
                try {
                    bitmap = task.execute(game.image).get()

                    popupView.findViewById<ImageView>(R.id.profile_image).setImageResource(0)
                    popupView.findViewById<ImageView>(R.id.profile_image).setImageBitmap(bitmap)

                } catch (e: ExecutionException) {
                    e.printStackTrace()
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                } catch (e: Exception) {
                    e.printStackTrace()
                }


                // If the PopupWindow should be focusable
                popupWindow.isFocusable = true

                // If you need the PopupWindow to dismiss when when touched outside
                popupWindow.setBackgroundDrawable(ColorDrawable())

                // Using location, the PopupWindow will be displayed right under anchorView
                popupWindow.showAtLocation(
                    it, Gravity.CENTER_HORIZONTAL,
                    0, 0 /*+ it.getHeight()*/
                )

                gridView.setBackgroundColor(resources.getColor(R.color.darkerGreen))

                popupWindow.setOnDismissListener {
                    gridView.setBackgroundColor(resources.getColor(R.color.white))
                }
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
