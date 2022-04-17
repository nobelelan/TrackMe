package com.example.trackme.ui

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.trackme.R
import com.example.trackme.databinding.FragmentSignInBinding
import com.facebook.*
import com.facebook.login.LoginResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.json.JSONObject


class SignInFragment : Fragment() {


    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    private lateinit var callbackManager: CallbackManager
    private lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSignInBinding.bind(view)


        auth = FirebaseAuth.getInstance()

        callbackManager = CallbackManager.Factory.create()

        binding.loginButton.setPermissions(
            listOf("email", "public_profile","user_gender","user_birthday","user_friends"))
        binding.loginButton.registerCallback(callbackManager, object :
            FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult) {
//                Log.d(TAG, "facebook:onSuccess:$result")
//                handleFacebookAccessToken(result.accessToken)
                val graphRequest = GraphRequest.newMeRequest(result.accessToken){`object` , response ->
                    getFacebookData(`object`)
                }
                val parameters = Bundle()
                parameters.putString("fields","id,email,birthday,friends,gender,name")
                graphRequest.parameters = parameters
                graphRequest.executeAsync()
            }

            override fun onCancel() {
                Log.d(TAG, "facebook:onCancel")
            }

            override fun onError(error: FacebookException) {
                Log.d(TAG, "facebook:onError", error)
            }
        })

    }

    private fun getFacebookData(obj: JSONObject?) {
        val profilePic = "https://graph.facebook.com/${obj?.getString("id")}/picture?width=50&height=50"
        Glide.with(this)
            .load(profilePic)
            .into(binding.ivProfile)

        val name = obj?.getString("name")
        val birthday = obj?.getString("birthday")
        val gender = obj?.getString("gender")
        val total_count = obj?.getJSONObject("friends")?.getJSONObject("summary")?.getString("total_count")
        val email = obj?.getString("email")

        binding.tvEmail.text = "Email: ${email}"
        binding.tvName.text = "Name: ${name}"
        binding.tvGender.text = "Gender: ${gender}"
        binding.tvBirthday.text = "Birthday: ${birthday}"
        binding.tvFriends.text = "Friends: ${total_count}"
    }

//    private fun handleFacebookAccessToken(token: AccessToken) {
//        Log.d(TAG, "handleFacebookAccessToken:$token")
//
//        val credential = FacebookAuthProvider.getCredential(token.token)
//        auth.signInWithCredential(credential)
//            .addOnCompleteListener(requireActivity()) { task ->
//                if (task.isSuccessful) {
//                    // Sign in success, update UI with the signed-in user's information
//                    Log.d(TAG, "signInWithCredential:success")
//                    val user = auth.currentUser
//                    binding.tvName.text = user?.displayName
////                    updateUI(user)
//                } else {
//                    // If sign in fails, display a message to the user.
//                    Log.w(TAG, "signInWithCredential:failure", task.exception)
//                    Toast.makeText(requireContext(), "Authentication failed.",
//                        Toast.LENGTH_SHORT).show()
////                    updateUI(null)
//                }
//            }
//    }


    // ...
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Pass the activity result back to the Facebook SDK
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
//        updateUI(currentUser)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}