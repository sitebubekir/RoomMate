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
import ebubekirsit.roommate.models.UserData
import ebubekirsit.roommate.databinding.ActivityMainKayitOlBinding

class MainKayitOl : AppCompatActivity() {

    private lateinit var binding: ActivityMainKayitOlBinding
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainKayitOlBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.reference.child("users")

        binding.btnkaydet.setOnClickListener {
            val kayitKullaniciAdi = binding.kayitKullaniciAdi.text.toString()
            val kayitParola = binding.kayitParola.text.toString()

            if (kayitKullaniciAdi.isNotEmpty() && kayitParola.isNotEmpty()){
                kayitOl(kayitKullaniciAdi,kayitParola)
            }else{
                Toast.makeText(this@MainKayitOl,"Lütfen Gerekli Alanları Doldurunuz",Toast.LENGTH_LONG).show()
            }
        }

        binding.btngirisedon.setOnClickListener {
            intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }
    }
    private fun kayitOl(kullaniciAdi: String,kullaniciParola: String){
        databaseReference.orderByChild("kullaniciAdi").equalTo(kullaniciAdi).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (!dataSnapshot.exists()){
                    val id = databaseReference.push().key
                    val userData = UserData(id, kullaniciAdi, kullaniciParola)
                    databaseReference.child(id!!).setValue(userData)
                    Toast.makeText(this@MainKayitOl,"Kayıt Başarılı",Toast.LENGTH_LONG).show()
                    startActivity(Intent(this@MainKayitOl, MainActivity::class.java))
                    finish()
                }else{
                    Toast.makeText(this@MainKayitOl,"Bu Kullanıcı Adı Kullanılıyor",Toast.LENGTH_LONG).show()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@MainKayitOl,"Kayıt Başarısız: ${databaseError}",Toast.LENGTH_LONG).show()
            }
        })
    }
}