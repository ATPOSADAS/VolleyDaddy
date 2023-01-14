package com.mobiledev.volleydaddy

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import com.mobiledev.volleydaddy.data.DataSource
import com.mobiledev.volleydaddy.databinding.FragmentRotationBinding
import com.mobiledev.volleydaddy.db.AppDatabase
import com.mobiledev.volleydaddy.model.RotationCard
import com.mobiledev.volleydaddy.model.RotationData
import org.w3c.dom.Text
import java.util.stream.IntStream.range


class RotationFragment : Fragment() {

    private lateinit var current_view: View
    private lateinit var rotationId: String
    private var isMotionLayout = false
    private var romans = arrayOf("I", "II", "III", "IV", "V", "VI")

    // constraint set data
    private lateinit var currentSet: ConstraintSet

    // current rotation data
    private var rotationData = RotationData("new rotation")
    private var rotationNum: Int = 0 // rotations from 1-6
    private var rotationState: Int = 0 // different state of current rotation

    companion object {
        val ROTATION = "rotation"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            rotationId = it.getString(ROTATION).toString()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        if (rotationId.contains("5-1")) {
            current_view = inflater.inflate(R.layout.fragment_51, container, false)
            isMotionLayout = true
        } else if (rotationId.contains("6-2")) {
            // change to 62 later
            current_view = inflater.inflate(R.layout.fragment_51, container, false)
            isMotionLayout = true
        } else {
            current_view = inflater.inflate(R.layout.fragment_rotation, container, false)
            isMotionLayout = false

            for (card: RotationCard in DataSource.rotationCards) {
                if (card.name.equals(rotationId)) {
                    rotationData = DataSource.rotations[card.name]!!
                }
            }
        }
        return current_view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        if (isMotionLayout) {
            val motionLayout: MotionLayout = current_view.findViewById(R.id.motionLayout)
            val transitionListener = object : MotionLayout.TransitionListener {

                override fun onTransitionStarted(p0: MotionLayout?, startId: Int, endId: Int) {
                    //nothing to do
                }

                override fun onTransitionChange(
                    p0: MotionLayout?,
                    startId: Int,
                    endId: Int,
                    progress: Float
                ) {
                    //nothing to do
                }

                override fun onTransitionCompleted(p0: MotionLayout?, currentId: Int) {
                    val current_text: TextView = current_view.findViewById(R.id.currentRotation)
                    val cd1: ConstraintSet = motionLayout.getConstraintSet(currentId)
                    current_text.text =
                        motionLayout.getConstraintSet(currentId).mIdString.replace("_", " ")
                }

                override fun onTransitionTrigger(
                    p0: MotionLayout?,
                    triggerId: Int,
                    positive: Boolean,
                    progress: Float
                ) {
                    //not used here
                }
            }

            motionLayout.setTransitionListener(transitionListener)
        } else {

            changeConstraintSet()

            current_view.findViewById<Button>(R.id.up_button).setOnClickListener {
                rotationNum++
                if (rotationNum >= rotationData.getSize()) {
                    rotationNum = 0
                }
                changeConstraintSet()
            }
            current_view.findViewById<Button>(R.id.down_button).setOnClickListener {
                rotationNum--
                if (rotationNum < 0) {
                    rotationNum = rotationData.getSize()-1
                }
                changeConstraintSet()
            }
            current_view.findViewById<Button>(R.id.left_button).setOnClickListener {
                rotationState--
                if (rotationState < 0) {
                    rotationState = rotationData.getRotation(rotationNum).size-1
                }
                changeConstraintSet()
            }
            current_view.findViewById<Button>(R.id.right_button).setOnClickListener {
                rotationState++
                if (rotationState >= rotationData.getRotation(rotationNum).size) {
                    rotationState = 0
                }
                changeConstraintSet()
            }

        }

    }

    private fun changeConstraintSet() {
        currentSet = rotationData.getRotation(rotationNum)[rotationState]
        currentSet.applyTo(current_view.findViewById(R.id.court_layout))
        var temp = currentSet.mIdString

        // temp fix
        temp = temp.replace("_", " ")
        temp = temp.replace("{", "")
        temp = temp.replace("}", "")

        current_view.findViewById<TextView>(R.id.rotation_text).text = temp
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.top_menu, menu)
        super.onCreateOptionsMenu(menu,inflater);
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_help -> {
            // User chose the Help. Help
            Toast.makeText(context, "Swipe up or down to go to the next rotation, " +
                    "left or right to change positions", Toast.LENGTH_LONG).show()
            true
        }

        R.id.action_display_option -> {

            // switch icon text to the next display option
            var teamId = prefs.teamId
            var db = activity?.let { AppDatabase.getInstance(it.applicationContext) }!!.first
            var teamDao = db.teamDao()

            var members = teamDao.getMembers(teamId)
            if  (members.isEmpty()){
                Toast.makeText(context, "Please select a team before proceeding", Toast.LENGTH_LONG).show()
            } else {
                var textView: TextView;
                for (i in 0..5) {
                    var roman_numeral = romans[i]
                    textView = requireView().findViewById<TextView>(
                        resources.getIdentifier(
                            roman_numeral, "id",
                            activity?.packageName
                        )
                    )
                    if (item.title == getString(R.string.position)) {
                        textView.text = members[i].positionType.name
                    } else if (item.title == getString(R.string.name)) {
                        textView.text = members[i].name
                    } else if (item.title == getString(R.string.number)) {
                        textView.text = members[i].number.toString()
                    }

                }
                //Toast.makeText(context, team.team_name, Toast.LENGTH_SHORT).show()
                // find next text and icon
                if (item.title == getString(R.string.position)) {
                    item.title = getString(R.string.name)
                    item.icon =
                        AppCompatResources.getDrawable(requireContext(), R.drawable.ic_name_switch);
                } else if (item.title == getString(R.string.name)) {
                    item.title = getString(R.string.number)
                    item.icon = AppCompatResources.getDrawable(
                        requireContext(),
                        R.drawable.ic_numbers_switch
                    );
                } else if (item.title == getString(R.string.number)) {
                    item.title = getString(R.string.position)
                    item.icon = AppCompatResources.getDrawable(
                        requireContext(),
                        R.drawable.ic_location_switch
                    );
                } else {
                    Toast.makeText(context, "The hell", Toast.LENGTH_SHORT).show()
                }
            }
            true
        }

        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }

}