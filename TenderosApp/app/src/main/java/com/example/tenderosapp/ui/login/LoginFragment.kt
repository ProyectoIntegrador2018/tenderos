package com.example.tenderosapp.ui.login

import android.app.Activity
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View

import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController

import com.example.tenderosapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.fragment_login.*

/**
 * A simple [Fragment] subclass.
 */
class LoginFragment :  Fragment(R.layout.fragment_login) {
    private lateinit var auth: FirebaseAuth
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        login_button.setOnClickListener{
            //doSignUp()
            doLogin()
        }
    }
    public override fun onStart() {
        super.onStart()
        //Al iniciar la aplicación revisamos si tenemos ya un usuario usuario activo
        val currentUser = auth.currentUser
        //updateUI(currentUser)
    }
    //Actualizar la navegación si el usuario es iudentificado correctamente
    fun updateUI(currentUser: FirebaseUser?){
        if(currentUser != null) {
            Navigation.findNavController(login_button).navigate(R.id.action_loginFragment_to_mainFragment)
            Toast.makeText(this.context, "Bienvenido.",Toast.LENGTH_LONG).show()
            Navigation.createNavigateOnClickListener(R.id.action_loginFragment_to_mainFragment)
        } else {
            Toast.makeText( this.context, "Usuario no identificado.",Toast.LENGTH_LONG).show()
        }
    }


    private fun doLogin() {
        Toast.makeText(this.context, email_tf.text.toString(),Toast.LENGTH_LONG).show()

        if (email_tf.text.toString().isEmpty()) {
            email_layout.error = "Please enter email"
            email_tf.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email_tf.text.toString()).matches()) {
            email_layout.error = "Please enter valid email"
            email_tf.requestFocus()
            return
        }

        if (psswd_tf.text.toString().isEmpty()) {
            psswd_layout.error = "Please enter password"
            psswd_layout.requestFocus()
            return
        }

        auth.signInWithEmailAndPassword(email_tf.text.toString(), psswd_tf.text.toString())
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    updateUI(null)
                }
            }

    }


    //Crear Cuenta
    private fun doSignUp() {
        auth.createUserWithEmailAndPassword(email_tf.text.toString(), psswd_tf.text.toString())
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    // Exito, hacer login al nuevo usuario
                    Log.d(TAG, "createUserWithEmail:success")
                    Toast.makeText(this.context, "Usuario Creado",
                        Toast.LENGTH_LONG).show()
                    val user = auth.currentUser
                    if(user != null){
                        updateUI(user)
                    } else {
                        Toast.makeText(this.context, "Error al Crear usuario", Toast.LENGTH_LONG).show()
                    }

                } else {
                    // Error al Crear usuario
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(this.context, "Error al crear usuario.",
                        Toast.LENGTH_LONG).show()
                    updateUI(null)
                }
            }

    }

}
