package id.ac.ubaya.onlensop

import android.content.ClipData
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_profile.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    val fragments: ArrayList<Fragment> = ArrayList()


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.itemLogout -> {
                val intent = Intent(this, LoginActivity::class.java)
                var customer = Customer(0, "", "", "", 0)
                Global.customer = customer
                startActivity(intent)
                finish()
            }
        }
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.main_toolbar)
        setSupportActionBar(toolbar)
        toolbar.showOverflowMenu()

//        val tombolKeluar = toolbar.findViewById<>(R.id.itemLogout)

        with(fragments) {
            add(HomeFragment())
            add(CartFragment())
            add(HistoryFragment())
            add(ProfileFragment())
        }

        viewPager.adapter = MyAdapter(this, fragments)
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                /*val menu = arrayOf(
                    R.id.itemHome,
                    R.id.itemCart,
                    R.id.itemHistory,
                    R.id.itemProfile
                )
                bottomNav.selectedItemId = menu[position]*/
                bottomNav.selectedItemId = bottomNav.menu.getItem(position).itemId
            }
        })
        bottomNav.setOnNavigationItemSelectedListener {
            viewPager.currentItem = when (it.itemId) {
                R.id.itemHome -> 0
                R.id.itemCart -> 1
                R.id.itemHistory -> 2
                R.id.itemProfile -> 3
                else -> 0
            }
            true
        }

    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
}

// lorem ipsum dolor sir amet