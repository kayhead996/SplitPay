package xyz.kayhead.splitpay.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.NavigationView
import android.support.v4.content.FileProvider
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.firebase.FirebaseApp
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer
import xyz.kayhead.splitpay.R
import xyz.kayhead.splitpay.fragments.NewTransactionFragment
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
        NewTransactionFragment.OnFragmentInteractionListener {
    private lateinit var toolbar: Toolbar
    private lateinit var fab: FloatingActionButton
    private lateinit var drawer_layout: DrawerLayout
    private lateinit var nav_view: NavigationView
    private var file: File? = null

    companion object {
       const val REQUEST_IMAGE_CAPTURE: Int = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        fab = findViewById(R.id.fab)
        drawer_layout = findViewById(R.id.drawer_layout)
        nav_view = findViewById(R.id.nav_view)


        setSupportActionBar(toolbar)

        fab.setOnClickListener {
            fragmentManager.beginTransaction().replace(R.id.fragment_parent, NewTransactionFragment.newInstance()).commit()
        }


        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
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
        when (item.itemId) {
            R.id.nav_camera -> {
                // Handle the camera action
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun openCamera() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        if (takePictureIntent.resolveActivity(packageManager) != null) {
            var photoFile: File? = null
            try {
                photoFile = createImageFile()
            } catch (ex: IOException) {
                Log.e("Error", ex.message)
                Toast.makeText(this, "Error creating image file", Toast.LENGTH_SHORT).show()
            }

            if (photoFile != null) {
                val photoUri: Uri = FileProvider.getUriForFile(this,
                        "xyz.kayhead.splitpay.fileprovider",
                        photoFile)
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }

        }
    }

    @SuppressLint("SimpleDateFormat")
    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyMMddHHmmssZ").format(java.util.Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)


        return File.createTempFile(
                "SplitPayReceipt_${timeStamp}_",
                ".jpg",
                storageDir
        ).also {
            file = it.absoluteFile
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            processImage()
        }
    }

    private fun processImage() {
        var image: FirebaseVisionImage? = null

        try {
            if (file != null) {
                image = FirebaseVisionImage.fromFilePath(this, FileProvider.getUriForFile(
                        this,
                        "xyz.kayhead.splitpay.fileprovider",
                        file!!
                ))
            }
        } catch (ex: IOException) {
            Log.e("Error", ex.message)
            Toast.makeText(this, "Error getting image file", Toast.LENGTH_SHORT).show()
        }
        val textRecognizer: FirebaseVisionTextRecognizer = FirebaseVision.getInstance().onDeviceTextRecognizer

        if (image != null) {
            textRecognizer.processImage(image)
                    .addOnSuccessListener {
                        val resultText = it.text
                        Log.i("Recognized text", resultText)
                    }
                    .addOnFailureListener{
                        Toast.makeText(this, "Image process failed", Toast.LENGTH_SHORT).show()
                    }
        }
    }

    override fun onButtonPressed(id: Int) {
        when (id) {
            R.id.open_camera -> openCamera()
        }
    }
}
