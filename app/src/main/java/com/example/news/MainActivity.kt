package com.example.news

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.news.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout
import java.io.Serializable
import com.example.news.database.News
import com.example.news.ui_fragment.FavouriteFragment
import com.example.news.ui_fragment.HomeFragment
import com.example.news.ui_fragment.ProfileFragment

class MainActivity : AppCompatActivity(), Serializable, TabLayout.OnTabSelectedListener {

    lateinit var binding: ActivityMainBinding
    lateinit var newsViewModel: NewsViewModel

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val newsRepository = NewsRepository(News.getDatabase(this))
        val viewModelProvider = ViewModelFactory(newsRepository)
        newsViewModel = ViewModelProvider(this, viewModelProvider)[NewsViewModel::class.java]


        loadNews()
        binding.bottomNavigation.setOnItemSelectedListener {
            return@setOnItemSelectedListener startFragment(it)
        }
    }

    private fun startFragment(it: MenuItem): Boolean {
        when (it.itemId) {
            R.id.profile_page -> {
                binding.pageName.setImageDrawable(R.drawable.logonews)
                return fragmentTransaction(ProfileFragment())
            }
            R.id.discover_page -> {
                binding.pageName.setImageDrawable(R.drawable.logonews)
                return loadNews()
            }
            R.id.fav_page -> {
                binding.pageName.setImageDrawable(R.drawable.logonews)
                return fragmentTransaction(FavouriteFragment())
            }
        }
        return false
    }

    private fun loadNews(): Boolean {
        var homeFragment = HomeFragment()
        var bundle = Bundle()
        bundle.putString("fragmentName", resources.getString(R.string.discover))
        homeFragment.arguments = bundle
        return fragmentTransaction(homeFragment)
    }

    private fun fragmentTransaction(fragment: Fragment): Boolean {

        supportFragmentManager.beginTransaction().addToBackStack("ss")
            .replace(R.id.main_FrameL, fragment).commit()

        return true
    }

    override fun onBackPressed() {
        val f = supportFragmentManager.findFragmentById(R.id.main_FrameL)
        if (f is ProfileFragment || f is FavouriteFragment) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_FrameL, HomeFragment()).commit()
            binding.bottomNavigation.menu.findItem(R.id.discover_page).isChecked = true
        } else if (f is HomeFragment && f.arguments?.get("fragmentName") == resources.getString(R.string.discover)) {
            finishAffinity()
        } else {
            super.onBackPressed()
        }
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        binding.mainFrameL.visibility = View.GONE
        loadNews()
        binding.mainFrameL.visibility = View.VISIBLE
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {
    }

    override fun onResume() {
        super.onResume()
        loadNews()
    }

}

private fun Any.setImageDrawable(logonews: Int) {

}
