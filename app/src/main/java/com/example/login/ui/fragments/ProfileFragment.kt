package com.example.login.ui.fragments


import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.example.login.R
import com.example.login.utils.toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.fragment_profile.*
import java.io.ByteArrayOutputStream
import java.util.*


class ProfileFragment : Fragment() {

    private val DEFAULT_IMAGE_URL = "http://picsum.photos/200"
    private lateinit var imageUri : Uri
    private val REQUEST_IMAGE_CAPTURE=100

    private val currentUser=FirebaseAuth.getInstance().currentUser
    private val userID = currentUser!!.uid
    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null






    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.reference!!.child("Users")



        currentUser?.let{ user->

            Glide.with(this)
                .load(user.photoUrl)
                .into(image_view)

            edit_text_name.setText(user.displayName)
            text_email.text= user.email


            text_phone.text = if(user.phoneNumber.isNullOrEmpty()) "ADD Number " else user.phoneNumber

            if(user.isEmailVerified){
                text_not_verified.visibility = View.INVISIBLE
            }else{
                text_not_verified.visibility= View.VISIBLE
            }
        }
        image_view.setOnClickListener{
            takePictureIntent()
        }

        button_save.setOnClickListener{
            val photo = when {
                ::imageUri.isInitialized->imageUri
                currentUser?.photoUrl == null ->Uri.parse(DEFAULT_IMAGE_URL)
                else -> currentUser.photoUrl
            }

            val name = edit_text_name.text.toString().trim()

            if(name.isEmpty()){
                edit_text_name.error="Name Required"
                edit_text_name.requestFocus()
                return@setOnClickListener
            }
            val updates = UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .setPhotoUri(photo)
                .build()

            progressbar.visibility= View.VISIBLE

            currentUser?.updateProfile(updates)
                ?.addOnCompleteListener{task->
                    progressbar.visibility= View.INVISIBLE
                    if(task.isSuccessful){
                        context?.toast("Profile Updated")
                    }else{
                        context?.toast(task.exception?.message!!)
                    }
                }
            val currentUserDb = mDatabaseReference!!.child(userID)
            currentUserDb.child("firstName").setValue(name)
            currentUserDb.child("Phone").setValue(text_phone.text)
        }
       text_not_verified.setOnClickListener {

           currentUser?.sendEmailVerification()
               ?.addOnCompleteListener {
                   if(it.isSuccessful){
                       context?.toast("Verification Email Sent")
                   }else{
                       context?.toast(it.exception?.message!!)
                   }
               }
       }

        text_phone.setOnClickListener {
            val action = ProfileFragmentDirections.actionVerifyPhone()
            Navigation.findNavController(it).navigate(action)
        }

        text_email.setOnClickListener {
            val action=ProfileFragmentDirections.actionUpdateEmail()
            Navigation.findNavController(it).navigate(action)
        }

        text_password.setOnClickListener {
            val action=ProfileFragmentDirections.actionUpdatePassword()
            Navigation.findNavController(it).navigate(action)
        }
    }

    private fun takePictureIntent(){
        //used to capture image by using default camera in device
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also{pictureIntent->
            pictureIntent.resolveActivity(activity?.packageManager!!)?.also{
                startActivityForResult(pictureIntent,REQUEST_IMAGE_CAPTURE)
            }
        }
    }
    //get result back from camera
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode ==REQUEST_IMAGE_CAPTURE && resultCode== RESULT_OK){

            val imageBitmap = data?.extras?.get("data") as Bitmap

            uploadImageAndSaveUri(imageBitmap)
        }
    }

    private fun uploadImageAndSaveUri(bitmap: Bitmap) {
        val baos = ByteArrayOutputStream()
        val StorageRef = FirebaseStorage.getInstance()
            .reference
            .child("pics/${FirebaseAuth.getInstance().currentUser?.uid}")

        bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos)
        val image = baos.toByteArray()

        val upload = StorageRef.putBytes(image)

        progressbar_pic.visibility= View.VISIBLE
        upload.addOnCompleteListener{uploadTask->

            progressbar_pic.visibility= View.INVISIBLE
            if(uploadTask.isSuccessful){
                StorageRef.downloadUrl.addOnCompleteListener{urlTask->
                    urlTask.result?.let{
                        imageUri=it
                        activity?.toast(imageUri.toString())

                        image_view.setImageBitmap(bitmap)
                    }
                }
            }else{
                uploadTask.exception?.let{
                    activity?.toast(it.message!!)
                }
            }
        }
    }

}
