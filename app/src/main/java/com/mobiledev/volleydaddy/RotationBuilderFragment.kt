package com.mobiledev.volleydaddy

import android.R.attr.button
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.util.Log.d
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import com.mobiledev.volleydaddy.data.DataSource
import com.mobiledev.volleydaddy.databinding.FragmentRotationBuilderBinding
import com.mobiledev.volleydaddy.db.AppDatabase
import com.mobiledev.volleydaddy.model.RotationCard
import com.mobiledev.volleydaddy.model.RotationData
import java.util.logging.Logger


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RotationBuilderFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RotationBuilderFragment : Fragment(){
    // for touch listener for each icon
    private var dX: Float = 0f
    private var dY: Float = 0f

    // for onfling method
    private val SWIPE_MIN_DISTANCE = 120
    private val SWIPE_THRESHOLD_VELOCITY = 200

    // constraint set data
    private lateinit var defaultSet: ConstraintSet
    private lateinit var currentSet: ConstraintSet

    // current rotation data
    private var rotationData = RotationData("new rotation")
    private var rotationNum: Int = 0 // rotations from 1-6
    private var rotationState: Int = 0 // different state of current rotation

    // binding
    private lateinit var _binding: FragmentRotationBuilderBinding
    private val binding get() = _binding!!

    private var romans = arrayOf("I", "II", "III", "IV", "V", "VI")
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable("prevRotationData", rotationData)
        super.onSaveInstanceState(outState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        _binding = FragmentRotationBuilderBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        defaultSet = ConstraintSet()
        defaultSet.clone(binding.courtLayout)

        // set up gesture recognition for each of the icons
        var listener = View.OnTouchListener(function = {view, event ->
            var oldX = view.x
            var oldY = view.y
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    dX = view.x - event.rawX
                    dY = view.y - event.rawY
                }
                MotionEvent.ACTION_MOVE -> view.animate()
                    .x(event.rawX + dX)
                    .y(event.rawY + dY)
                    .setDuration(0)
                    .start()
                else -> false
            }

            if (view.x <= binding.court.x || view.x >= binding.court.x + binding.court.width - view.width ||
                    view.y <= binding.court.y || view.y >= binding.court.y + binding.court.height - view.width) {
                view.animate()
                    .x(oldX)
                    .y(oldY)
                    .setDuration(0)
                    .start()
            }
            saveConstraintSet()
            true
        })

        binding.I.setOnTouchListener(listener)
        binding.II.setOnTouchListener(listener)
        binding.III.setOnTouchListener(listener)
        binding.IV.setOnTouchListener(listener)
        binding.V.setOnTouchListener(listener)
        binding.VI.setOnTouchListener(listener)

        if (savedInstanceState != null) {
            Log.d("tagger", "state is NOT NULL!!")
            rotationData = savedInstanceState.getParcelable("prevRotationData")!!
        } else {
            Log.d("tagger", "state is NULL!!")
            makeNewRotationData()
        }

        changeConstraintSet()

        binding.upButton.setOnClickListener {
            rotationNum++
            if (rotationNum >= rotationData.getSize()) {
                rotationNum = 0
            }
            saveConstraintSet()
            changeConstraintSet()
        }
        binding.downButton.setOnClickListener {
            rotationNum--
            if (rotationNum < 0) {
                rotationNum = rotationData.getSize()-1
            }
            saveConstraintSet()
            changeConstraintSet()
        }
        binding.leftButton.setOnClickListener {
            rotationState--
            if (rotationState < 0) {
                rotationState = rotationData.getRotation(rotationNum).size-1
            }
            saveConstraintSet()
            changeConstraintSet()
        }
        binding.rightButton.setOnClickListener {
            rotationState++
            if (rotationState >= rotationData.getRotation(rotationNum).size) {
                rotationState = 0
            }
            saveConstraintSet()
            changeConstraintSet()
        }
        binding.saveButton.setOnClickListener {

            val name = binding.rotationName.text.toString()
            var bool = false
            for (card: RotationCard in DataSource.rotationCards) {
                if (card.name.equals(name)) {
                    bool = true
                }
            }

            if (!bool) {
                DataSource.rotationCards.add(
                    RotationCard (
                        R.drawable.custom_made,
                        name,
                        0,
                    ))
            }
            DataSource.rotations.put(name, rotationData)
            makeNewRotationData()

        }
        binding.resetButton.setOnClickListener {
            makeNewRotationData()
        }
    }

    private fun saveConstraintSet() {
        currentSet.clone(binding.courtLayout)
    }

    private fun changeConstraintSet() {
        currentSet = rotationData.getRotation(rotationNum)[rotationState]
        currentSet.applyTo(binding.courtLayout)
        var temp = currentSet.mIdString

        // temp fix
        temp = temp.replace("_", " ")
        temp = temp.replace("{", "")
        temp = temp.replace("}", "")

        binding.rotationText.text = temp
    }

    private fun makeNewRotationData() {
        rotationData = RotationData("temp")
        // clone each constraint set in rotation data to current view
        for (i in 1..6) {
            val list: MutableList<ConstraintSet> = rotationData.getRotation(i-1)
            for (constraint: ConstraintSet in list) {
                constraint.clone(defaultSet)
            }
        }
        rotationNum = 0
        rotationState = 0
        changeConstraintSet()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.top_menu_builder, menu)
        super.onCreateOptionsMenu(menu,inflater);
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {

        R.id.builder_display_option -> {

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