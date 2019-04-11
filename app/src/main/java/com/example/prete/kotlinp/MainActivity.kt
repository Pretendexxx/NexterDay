package com.example.prete.kotlinp

import android.content.Intent
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.squareup.picasso.Picasso

class MainActivity: AppCompatActivity() {

    val mAuth = FirebaseAuth.getInstance()

    lateinit var mrecylerview : RecyclerView
    lateinit var ref: DatabaseReference
    lateinit var show_progress: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ref = FirebaseDatabase.getInstance().getReference().child("Superhero")
        mrecylerview = findViewById<RecyclerView>(R.id.reyclerview)
        mrecylerview.layoutManager = LinearLayoutManager(this)

        show_progress = findViewById(R.id.progress_bar)


        firebaseData()

    }


    fun firebaseData() {


        val option = FirebaseRecyclerOptions.Builder<Model>()
                .setQuery(ref, Model::class.java)
                .setLifecycleOwner(this)
                .build()


        val firebaseRecyclerAdapter = object: FirebaseRecyclerAdapter<Model, MyViewHolder>(option) {


            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
                val itemView = LayoutInflater.from(this@MainActivity).inflate(R.layout.activity_cardview,parent,false)
                return MyViewHolder(itemView)
            }

            override fun onBindViewHolder(holder: MyViewHolder, position: Int, model: Model) {
                val placeid = getRef(position).key.toString()

                ref.child(placeid).addValueEventListener(object: ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                        Toast.makeText(this@MainActivity, "Error Occurred "+ p0.toException(), Toast.LENGTH_SHORT).show()

                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        show_progress.visibility = if(itemCount == 0) View.VISIBLE else View.GONE

                        holder.txt_name.setText(model.Name)
                        Picasso.get().load(model.Image).into(holder.img_vet)

                    }
                })
            }
        }
        mrecylerview.adapter = firebaseRecyclerAdapter
        firebaseRecyclerAdapter.startListening()
    }

    class MyViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

        internal var txt_name: TextView = itemView!!.findViewById<TextView>(R.id.Display_title)
        internal var img_vet: ImageView = itemView!!.findViewById<ImageView>(R.id.Display_img)


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == R.id.signOut) {
            mAuth.signOut()
            startActivity(Intent(this, RegisterActivity::class.java))
            Toast.makeText(this, "Logged out.", Toast.LENGTH_LONG).show()
        }
        return super.onOptionsItemSelected(item)
    }


}