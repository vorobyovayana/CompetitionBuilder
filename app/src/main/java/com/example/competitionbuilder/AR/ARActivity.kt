package com.example.competitionbuilder.AR

import android.app.Activity
import android.app.ActivityManager
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.MotionEvent
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.example.competitionbuilder.MainActivity
import com.example.competitionbuilder.R
import com.google.ar.core.Anchor
import com.google.ar.core.HitResult
import com.google.ar.core.Plane
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.TransformableNode
import java.util.*
import java.util.function.Consumer
import java.util.function.Function

class ARActivity : AppCompatActivity() {
    private lateinit var arFragment: ArFragment


    //object of ArFragment Class
    private var arCam  : ArFragment? = null

    //lateinit for Augmented Reality Fragment
    private var clickNo = 0 //helps to render the 3d model only once when we tap the screen

    fun checkSystemSupport(activity: Activity): Boolean {
        //checking whether the API version of the running Android >= 24 that means Android Nougat 7.0
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val openGlVersion =
                (Objects.requireNonNull(activity.getSystemService(Context.ACTIVITY_SERVICE)) as ActivityManager).deviceConfigurationInfo.glEsVersion
            //checking whether the OpenGL version >= 3.0
            if (openGlVersion.toDouble() >= 3.0) {
                true
            } else {
                Toast.makeText(
                    activity,
                    "App needs OpenGl Version 3.0 or later",
                    Toast.LENGTH_SHORT
                ).show()
                activity.finish()
                false
            }
        } else {
            Toast.makeText(
                activity,
                "App does not support required Build Version",
                Toast.LENGTH_SHORT
            ).show()
            activity.finish()
            false
        }
    }

    private fun addModel(anchor: Anchor, modelRenderable: ModelRenderable) {
        // Creating a AnchorNode with a specific anchor
        val anchorNode = AnchorNode(anchor)
        //attaching the anchorNode with the ArFragment
        anchorNode.setParent(arCam!!.arSceneView.scene)
        //attaching the anchorNode with the TransformableNode
        // TransformableNode - allows you to the spin the model
        val model = TransformableNode(arCam!!.transformationSystem)
        model.setParent(anchorNode)
        //attaching the 3d model with the TransformableNode that is already attached with the node
        model.renderable = modelRenderable
        model.select()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aractivity)
        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
        }

        if (this.checkSystemSupport(this)){
            //ArFragment is linked up with its respective id used in the activity_aractivity.xml
            arCam = supportFragmentManager.findFragmentById(R.id.arCameraArea) as ArFragment ?
            arCam!!.setOnTapArPlaneListener { hitResult: HitResult, plane: Plane?, motionEvent: MotionEvent? ->
                clickNo++
                //the 3d model comes to the scene only when clickNo is one that means once
                if (clickNo == 1) {
                    val anchor = hitResult.createAnchor()
                    // Render the model, pass the anchor, and the modelRenderable if it's not null
                    ModelRenderable.builder()
                        .setSource(this, R.raw.piste_basic_katushki_sm3_lower)
                        .setIsFilamentGltf(true)
                        .build()
                        .thenAccept(Consumer { modelRenderable: ModelRenderable? ->
                            addModel(
                                anchor,
                                modelRenderable!!
                            )
                        })
                        .exceptionally(Function<Throwable, Void?> { throwable: Throwable ->
                            val builder =
                                AlertDialog.Builder(this)
                            builder.setMessage("Something is not right" + throwable.message).show()
                            Toast.makeText(this, "Model not shown", Toast.LENGTH_SHORT).show()
                            null
                        })
                }
            }
        } else {
            Toast.makeText(this, "Not returning anything", Toast.LENGTH_SHORT).show()
            return
        }
    }
    // Enable back arrow on the action bar.
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}