package com.dpfht.android.demomovieflow

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.MenuCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.dpfht.android.demomovieflow.databinding.ActivityMainBinding
import com.dpfht.android.demomovieflow.view.about.AboutDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

  private lateinit var binding: ActivityMainBinding
  private lateinit var appBarConfiguration: AppBarConfiguration
  private lateinit var navController: NavController

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    setSupportActionBar(binding.appBarMain.toolbar)
    title = "${getString(R.string.app_name)}${getString(R.string.running_mode)}"

    val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
    navController = navHostFragment.navController

    appBarConfiguration = AppBarConfiguration(setOf(R.id.popularMoviesFragment, R.id.searchMovieFragment, R.id.favoriteMoviesFragment), binding.drawerLayout)

    setupActionBarWithNavController(navController, appBarConfiguration)
    binding.navView.setupWithNavController(navController)
  }

  override fun onSupportNavigateUp(): Boolean {
    if (navController.currentDestination?.id == R.id.movieDetailsFragment) {
      onBackPressed()
      return true
    }

    return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
  }

  override fun onBackPressed() {
    if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
      binding.drawerLayout.closeDrawer(GravityCompat.START)
    } else {
      super.onBackPressed()
    }
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menu?.let {
      MenuCompat.setGroupDividerEnabled(menu, true)
      menuInflater.inflate(R.menu.main_menu, menu)
    }

    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.itm_about -> {
        showAboutDialog()
        return true
      }
    }
    return super.onOptionsItemSelected(item)
  }

  private fun showAboutDialog() {
    val dialog = AboutDialogFragment.newInstance()
    dialog.show(supportFragmentManager, "fragment_about")
  }
}
