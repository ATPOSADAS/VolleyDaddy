package com.mobiledev.volleydaddy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.mobiledev.volleydaddy.db.AppDatabase
import com.mobiledev.volleydaddy.model.RoomTeam

/**
 * A simple [Fragment] subclass.
 * Use the [NewTeamFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewTeamFragment : Fragment(), View.OnClickListener {
    private var positions: List<String> = listOf("S", "RSH", "L",
        "MB","OppH", "OutH")
    var views = ArrayList<Pair<EditText, EditText>>()
    lateinit var teamName: EditText;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_new_team, container, false)
        for (i in positions.indices){
            // get name
            val nameView = "enterName" + positions[i]
            val name = view.findViewById<EditText>(resources.getIdentifier(nameView, "id",
                activity?.packageName
            ))

            // get number
            val numberView = "enterNumber" + positions[i]
            val number = view.findViewById<EditText>(resources.getIdentifier(numberView, "id", activity?.packageName))
            views.add(Pair(name, number))
        }
        teamName = view.findViewById<EditText>(R.id.enterTeamName)
        var button = view.findViewById<Button>(R.id.create_team_button);
        button.setOnClickListener(this)
        return view
    }

    override fun onClick(view: View?) {
        if (view != null) {
            if(view.id == R.id.create_team_button && validateTeamInfoEntered()){
                // validate everything is entered

                // create the team objects
                var team = RoomTeam.Team(teamName.text.toString())
                var pair: Pair<EditText, EditText>?
                var members = ArrayList<RoomTeam.TeamMember>()
                val dbPair = AppDatabase.getInstance(requireContext())
                val db = dbPair.first
                val teamDao = db.teamDao()
                val teamId = teamDao.insertTeam(team)
                for (i in positions.indices){
                    // get info
                    pair = views[i]
                    //Toast.makeText(requireContext(), pair.first.text.toString(), Toast.LENGTH_SHORT).show()
                    members.add(RoomTeam.TeamMember(teamId.toInt(), RoomTeam.PositionType.fromInt(i), pair.first.text.toString(), pair.second.text.toString().toInt()))
                }
                teamDao.insertMembers(members)

                // save the team

                // teamDao.insertTeamsAndMembers(team, members)
                // return to settings
                parentFragmentManager.popBackStackImmediate()
            }
        }
    }

    private fun validateTeamInfoEntered() : Boolean{
        if (teamName.text.isEmpty()){
            Toast.makeText(requireContext(), "Enter every field", Toast.LENGTH_SHORT).show()
            return false
        }
        for (i in views){
            if (i.first.text.isEmpty() || i.second.text.isEmpty()) {
                Toast.makeText(requireContext(), "Enter every field", Toast.LENGTH_SHORT).show()
                return false
            }
        }
        return true
    }
}