package com.example.login.ui

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.login.R
import com.example.login.utils.logout
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_home.*

class Home : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


        setSupportActionBar(toolbar)

        val navController= Navigation.findNavController(this, R.id.fragment)
        NavigationUI.setupWithNavController(nav_view,navController)
        NavigationUI.setupActionBarWithNavController(
            this,
            navController,drawer_layout
        )

    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(
            Navigation.findNavController(this, R.id.fragment),
            drawer_layout
        )
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item?.itemId== R.id.action_logout){

            AlertDialog.Builder(this).apply{
                setTitle("Are You sure ?")
                setPositiveButton("Yes"){ _ , _ ->

                    FirebaseAuth.getInstance().signOut()
                    logout()
                }
                setNegativeButton("Cancel"){ _ , _ ->
                }
            }.create().show()
        }
        return super.onOptionsItemSelected(item)
    }

}


