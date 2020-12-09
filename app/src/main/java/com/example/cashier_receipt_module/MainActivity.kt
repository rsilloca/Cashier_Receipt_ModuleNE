package com.example.cashier_receipt_module

import android.os.Bundle
import android.view.Menu
import android.widget.Button
import android.widget.NumberPicker
import android.widget.ToggleButton
import androidx.appcompat.app.AlertDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.NavHostFragment
import com.example.cashier_receipt_module.repository.models.Cashier
import com.example.cashier_receipt_module.ui.cajeros.CashiersFragment
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textfield.TextInputEditText
import java.time.LocalDateTime

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // val fab: FloatingActionButton = findViewById(R.id.fab)
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
                R.id.nav_home, R.id.nav_cashiers, R.id.nav_clients), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun showDialogCashier() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_cashier, null)
        val alertDialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .show()
        val cancelButton = dialogView.findViewById(R.id.cancel_button) as Button
        cancelButton.setOnClickListener {
            alertDialog.dismiss()
        }
        val toggleGroup = dialogView.findViewById(R.id.toggle_button_cashier) as MaterialButtonToggleGroup
        toggleGroup.check(R.id.active_cashier_btn)
        val saveButton = dialogView.findViewById(R.id.save_button) as Button
        saveButton.setOnClickListener {
            val code = (dialogView.findViewById(R.id.input_code_cashier) as TextInputEditText).text.toString()
            val name = (dialogView.findViewById(R.id.input_name_cashier) as TextInputEditText).text.toString()
            val idButtonChecked = toggleGroup.checkedButtonId
            var status = "1"
            if (idButtonChecked == R.id.inactive_cashier_btn) {
                status = "0"
            } else if (idButtonChecked == R.id.deleted_cashier_btn) {
                status = "*"
            }
            val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            val cashiersFragment = navHostFragment.childFragmentManager.fragments[0] as CashiersFragment?
            cashiersFragment?.addCashier(
                Cashier(
                    0,
                    code,
                    name,
                    status
                )
            )
            alertDialog.dismiss()
        }
    }
}