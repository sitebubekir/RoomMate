package ebubekirsit.roommate.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import ebubekirsit.roommate.models.IlanData
import ebubekirsit.roommate.databinding.ActivityMainIlanOlusturBinding

class MainIlanOlustur : AppCompatActivity() {

    private lateinit var binding: ActivityMainIlanOlusturBinding
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainIlanOlusturBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.reference.child("ilanlar")

        binding.btnIlanOlustur.setOnClickListener {
            val editAddress = binding.editAddress.text.toString()
            val editPhone = binding.editPhone.text.toString()
            val editEmail = binding.editEmail.text.toString()

            if (editAddress.isNotEmpty() && editPhone.isNotEmpty() && editEmail.isNotEmpty()){
                ilanOlustur(editAddress,editPhone,editEmail)
            }else{
                Toast.makeText(this@MainIlanOlustur,"Lütfen Boş Alanları Doldurunuz",Toast.LENGTH_LONG).show()
            }
        }

        binding.btnAnasayfayaDon.setOnClickListener {
            intent = Intent(applicationContext, MainAnaSayfa::class.java)
            startActivity(intent)
        }
    }

    private fun ilanOlustur(ilanAdress: String,kullaniciPhoneNo: String,kullaniciEmail: String){
        databaseReference.orderByChild("ilanAdress").equalTo(ilanAdress).addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (!dataSnapshot.exists()){
                    val ilanId = databaseReference.push().key
                    val ilanData = IlanData(ilanId,ilanAdress, kullaniciPhoneNo, kullaniciEmail)
                    databaseReference.child(ilanId!!).setValue(ilanData)
                    Toast.makeText(this@MainIlanOlustur,"Kayıt Başarılı", Toast.LENGTH_LONG).show()
                    startActivity(Intent(this@MainIlanOlustur, MainAnaSayfa::class.java))
                    finish()
                }else{
                    Toast.makeText(this@MainIlanOlustur,"Başarısız",Toast.LENGTH_LONG).show()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@MainIlanOlustur,"Kayıt Başarısız: ${databaseError}",Toast.LENGTH_LONG).show()
            }
        })

    }
}