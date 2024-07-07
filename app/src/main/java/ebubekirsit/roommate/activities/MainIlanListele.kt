package ebubekirsit.roommate.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import ebubekirsit.roommate.R
import ebubekirsit.roommate.adapters.IlanAdapter
import ebubekirsit.roommate.databinding.ActivityMainIlanListeleBinding
import ebubekirsit.roommate.models.IlanData


class MainIlanListele : AppCompatActivity() {
    private lateinit var binding: ActivityMainIlanListeleBinding
    private lateinit var ilanRecyclerView: RecyclerView
    private lateinit var tvLoadingData: TextView
    private lateinit var ilanList: ArrayList<IlanData>
    private lateinit var dbRef: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainIlanListeleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ilanRecyclerView = findViewById(R.id.ilanRecycler)
        ilanRecyclerView.layoutManager = LinearLayoutManager(this)
        ilanRecyclerView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadingData)

        ilanList = arrayListOf<IlanData>()

        getIlanData()

        binding.btnMenu.setOnClickListener {
            intent = Intent(applicationContext, MainAnaSayfa::class.java)
            startActivity(intent)
        }

    }

    private fun getIlanData(){
        ilanRecyclerView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("ilanlar")

        dbRef.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                ilanList.clear()
                if (dataSnapshot.exists()){
                    for (ilanSnap in dataSnapshot.children){
                        val ilanlarData = ilanSnap.getValue(IlanData::class.java)
                        ilanList.add(ilanlarData!!)
                    }
                    val myAdapter = IlanAdapter(ilanList)
                    ilanRecyclerView.adapter = myAdapter

                    ilanRecyclerView.visibility = View.VISIBLE
                    tvLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@MainIlanListele,"İlanlar Yüklenemedi: ${databaseError}", Toast.LENGTH_LONG).show()
            }

        })
    }
}