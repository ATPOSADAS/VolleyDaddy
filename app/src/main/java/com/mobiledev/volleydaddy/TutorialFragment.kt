package com.mobiledev.volleydaddy

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mobiledev.volleydaddy.databinding.FragmentRotationBinding

class TutorialFragment : Fragment() {

    private var _binding: FragmentRotationBinding? = null
    private val binding get() = _binding!!
    private lateinit var current_view: View
    private lateinit var tutorialId: String

    companion object {
        val TUTORIAL = "tutorial"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            tutorialId = it.getString(TUTORIAL).toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (tutorialId.contains("Points")) {
            current_view = inflater.inflate(R.layout.tutorial_scoring_points, container, false)
        } else if (tutorialId.contains("Rotation")) {
            current_view = inflater.inflate(R.layout.tutorial_rotations, container, false)
        } else if (tutorialId.contains("Positions")) {
            current_view = inflater.inflate(R.layout.tutorial_positions, container, false)
        }

        return current_view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


    }
}