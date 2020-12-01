package id.ac.ubaya.onlensop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val fragments: ArrayList<Fragment> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        with(fragments) {
            add(HomeFragment())
            add(CartFragment())
            add(HistoryFragment())
            add(ProfileFragment())
        }

        viewPager.adapter = MyAdapter(this, fragments)
        viewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
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
            viewPager.currentItem = when(it.itemId){
                R.id.itemHome -> 0
                R.id.itemCart -> 1
                R.id.itemHistory -> 2
                R.id.itemProfile -> 3
                else -> 0
            }
            true
        }

    }
}

// tes doank