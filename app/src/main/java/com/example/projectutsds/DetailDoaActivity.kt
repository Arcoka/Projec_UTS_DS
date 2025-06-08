package com.example.projectutsds



import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class DetailDoaActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var textDoaName: TextView
    private lateinit var textArabic: TextView
    private lateinit var textLatin: TextView
    private lateinit var textTranslation: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_doa)
        initViews()
        setupToolbar()
        displayDoaDetails()
    }

    private fun initViews() {
        toolbar = findViewById(R.id.toolbar)
        textDoaName = findViewById(R.id.textDoaName)
        textArabic = findViewById(R.id.textArabic)
        textLatin = findViewById(R.id.textLatin)
        textTranslation = findViewById(R.id.textTranslation)
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun displayDoaDetails() {
        val doaName = intent.getStringExtra("doa_name") ?: ""
        val doaArabic = intent.getStringExtra("doa_arabic") ?: ""
        val doaLatin = intent.getStringExtra("doa_latin") ?: ""
        val doaTranslation = intent.getStringExtra("doa_translation") ?: ""

        textDoaName.text = doaName
        textArabic.text = doaArabic
        textLatin.text = doaLatin
        textTranslation.text = doaTranslation
    }
}