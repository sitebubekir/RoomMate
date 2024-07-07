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
import ebubekirsit.roommate.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.reference.child("users")


        binding.btnGirisYap.setOnClickListener {
            val girisKullaniciAdi = binding.girisKullaniciAdi.text.toString()
            val girisParola = binding.girisParola.text.toString()

            if (girisKullaniciAdi.isNotEmpty() && girisParola.isNotEmpty()){
                girisYap(girisKullaniciAdi,girisParola)
            }else{
                Toast.makeText(this@MainActivity,"Lütfen Gerekli Alanları Doldurunuz",Toast.LENGTH_LONG).show()
            }

        }
        binding.btnKayitOl.setOnClickListener {
            intent = Intent(applicationContext, MainKayitOl::class.java)
            startActivity(intent)
        }
    }
    private fun girisYap(kullaniciAdi: String, parola: String){
        databaseReference.orderByChild("kullaniciAdi").equalTo(kullaniciAdi).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()){
                    for (userSnapshot in dataSnapshot.children){
                        val userData = userSnapshot.getValue(UserData::class.java)
                        if (userData != null && userData.kullaniciParola == parola){
                            Toast.makeText(this@MainActivity,"Giriş Başarılı",Toast.LENGTH_LONG).show()
                            startActivity(Intent(this@MainActivity, MainAnaSayfa::class.java))
                            finish()
                            return
                        }
                    }
                }
                Toast.makeText(this@MainActivity,"Giriş Başarısız!!!",Toast.LENGTH_LONG).show()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@MainActivity,"Kayıt Başarısız: ${databaseError}",Toast.LENGTH_LONG).show()
            }
        })

    }
}
