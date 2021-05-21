package com.example.userdailydata

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.FirebaseDatabase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class GoogleSignin : AppCompatActivity() {
    companion object{
        public const val RC_SIGN_IN=6
    }
    lateinit var button:CardView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_google_signin)
        button=findViewById(R.id.google_sign_in)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
       val  mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        val account = GoogleSignIn.getLastSignedInAccount(this)
        if(account!=null){
            val intent=Intent(this, MainActivity::class.java)
            Log.i("CureentUser", FirebaseAuth.getInstance().currentUser!!.uid)
            startActivity(
                Intent(
                    this,
                    MainActivity::class.java
                ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            )
        }
        button.setOnClickListener{
            val signInIntent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }

    }

    @Override
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode== RC_SIGN_IN){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account: GoogleSignInAccount = task.getResult(ApiException::class.java)!!
                val idToken=account.idToken
                Log.i("IDToken", idToken.toString())
                var jsonString: String="{\n" +
                        "  \"Dates\" : {\n" +
                        "    \"0\" : {\n" +
                        "      \"date\" : \"01-05-2021\",\n" +
                        "      \"steps\" : \"8000\"\n" +
                        "    },\n" +
                        "    \"1\" : {\n" +
                        "      \"date\" : \"02-05-2021\",\n" +
                        "      \"steps\" : \"8200\"\n" +
                        "    },\n" +
                        "    \"2\" : {\n" +
                        "      \"date\" : \"03-05-2021\",\n" +
                        "      \"steps\" : \"8020\"\n" +
                        "    },\n" +
                        "    \"3\" : {\n" +
                        "      \"date\" : \"04-05-2021\",\n" +
                        "      \"steps\" : \"7600\"\n" +
                        "    },\n" +
                        "    \"4\" : {\n" +
                        "      \"date\" : \"05-05-2021\",\n" +
                        "      \"steps\" : \"7500\"\n" +
                        "    },\n" +
                        "    \"5\" : {\n" +
                        "      \"date\" : \"06-05-2021\",\n" +
                        "      \"steps\" : \"6500\"\n" +
                        "    },\n" +
                        "    \"6\" : {\n" +
                        "      \"date\" : \"07-05-2021\",\n" +
                        "      \"steps\" : \"9200\"\n" +
                        "    },\n" +
                        "    \"7\" : {\n" +
                        "      \"date\" : \"08-05-2021\",\n" +
                        "      \"steps\" : \"10000\"\n" +
                        "    },\n" +
                        "    \"8\" : {\n" +
                        "      \"date\" : \"09-05-2021\",\n" +
                        "      \"steps\" : \"7600\"\n" +
                        "    },\n" +
                        "    \"9\" : {\n" +
                        "      \"date\" : \"10-05-2021\",\n" +
                        "      \"steps\" : \"8000\"\n" +
                        "    },\n" +
                        "    \"10\" : {\n" +
                        "      \"date\" : \"11-05-2021\",\n" +
                        "      \"steps\" : \"6600\"\n" +
                        "    },\n" +
                        "    \"11\" : {\n" +
                        "      \"date\" : \"12-05-2021\",\n" +
                        "      \"steps\" : \"7600\"\n" +
                        "    },\n" +
                        "    \"12\" : {\n" +
                        "      \"date\" : \"13-05-2021\",\n" +
                        "      \"steps\" : \"6900\"\n" +
                        "    },\n" +
                        "    \"13\" : {\n" +
                        "      \"date\" : \"14-05-2021\",\n" +
                        "      \"steps\" : \"7900\"\n" +
                        "    },\n" +
                        "    \"14\" : {\n" +
                        "      \"date\" : \"15-05-2021\",\n" +
                        "      \"steps\" : \"6860\"\n" +
                        "    },\n" +
                        "    \"15\" : {\n" +
                        "      \"date\" : \"16-05-2021\",\n" +
                        "      \"steps\" : \"8750\"\n" +
                        "    },\n" +
                        "    \"16\" : {\n" +
                        "      \"date\" : \"17-05-2021\",\n" +
                        "      \"steps\" : \"3000\"\n" +
                        "    },\n" +
                        "    \"17\" : {\n" +
                        "      \"date\" : \"18-05-2021\",\n" +
                        "      \"steps\" : \"9760\"\n" +
                        "    },\n" +
                        "    \"18\" : {\n" +
                        "      \"date\" : \"19-05-2021\",\n" +
                        "      \"steps\" : \"9830\"\n" +
                        "    },\n" +
                        "    \"19\" : {\n" +
                        "      \"date\" : \"20-05-2021\",\n" +
                        "      \"steps\" : \"1060\"\n" +
                        "    },\n" +
                        "    \"20\" : {\n" +
                        "      \"date\" : \"21-05-2021\",\n" +
                        "      \"steps\" : \"4060\"\n" +
                        "    },\n" +
                        "    \"123\" : {\n" +
                        "      \"date\" : \"30-04-2021\",\n" +
                        "      \"steps\" : \"3000\"\n" +
                        "    }\n" +
                        "  }\n" +
                        "}\n"
                val jsonMap: Map<String, Any> = Gson().fromJson(
                    jsonString,
                    object : TypeToken<HashMap<String?, Any?>?>() {}.getType()
                )
                val credential = GoogleAuthProvider.getCredential(idToken, null)
                FirebaseAuth.getInstance().signInWithCredential(credential)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            FirebaseDatabase.getInstance().getReference(FirebaseAuth.getInstance().currentUser!!.uid)
                                .setValue(jsonMap)
                                .addOnCompleteListener { task->
                                    if (task.isSuccessful){
                                        startActivity(
                                            Intent(
                                                this,
                                                MainActivity::class.java
                                            ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                                        )
                                    }else{
                                        Snackbar.make(
                                            button,
                                            "Error Signing In",
                                            Snackbar.LENGTH_LONG
                                        ).show()
                                    }
                                }

                        } else {
                            // If sign in fails, display a message to the user.
                            Snackbar.make(button, "Error Signing In", Snackbar.LENGTH_LONG).show()
                        }
                    }
            } catch (e: ApiException) {
                Log.i("Error", e.toString());
                // The ApiException status code indicates the detailed failure reason.
                Snackbar.make(button, "Error Signing In", Snackbar.LENGTH_LONG).show()
            }
        }
    }
}