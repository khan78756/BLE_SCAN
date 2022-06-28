package com.example.bluetooth_scan

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import com.example.bluetooth_scan.databinding.ActivityMainBinding
import com.example.bluetooth_scan.MainActivity as MainAcBluetooth_scanMainActivityvity1

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private val REQUEST_CODE_DISCOVERABLE_BT=1

    //Bluetooth Adaper
    private val badapter:BluetoothAdapter by lazy{
        (getSystemService(BLUETOOTH_SERVICE)as BluetoothManager).adapter
    }

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)





        //Checking for Bluetooth
        if (badapter == null) {
            binding.tvturnstatus.text = "BlueTooth is off"
        } else
        {
            binding.tvturnstatus.text = "BlueTooth is on"
            //For turn on Bluetooth
        }
        //For Image Acordingly
        if (badapter.isEnabled) {
            binding.ivturnstatus.setImageResource(R.drawable.ic_baseline_bluetooth_connected_24)
        } else {
            binding.ivturnstatus.setImageResource(R.drawable.ic_baseline_bluetooth_disabled_24)
        }




        //For turn on BlueTooth
        binding.btnon.setOnClickListener {
            if (badapter.isEnabled) {
                Toast.makeText(this, "Already On", Toast.LENGTH_LONG).show()
            } else {

                badapter.enable()
                binding.ivturnstatus.setImageResource(R.drawable.ic_baseline_bluetooth_connected_24)
               // val intent=Intent(this,com.example.bluetooth_scan.MainActivity::class.java)
                //getResult.launch(intent)

            }
        }





        //For turn off BlueTooth
        binding.btnoff.setOnClickListener {
            if (!badapter.isEnabled) {
                Toast.makeText(this, "Already off", Toast.LENGTH_LONG).show()
            } else {
                badapter.disable()
                binding.ivturnstatus.setImageResource(R.drawable.ic_baseline_bluetooth_disabled_24)
                return@setOnClickListener
            }
        }




        //For turn off BlueTooth
        binding.btnpair.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.BLUETOOTH_SCAN
                ) != PackageManager.PERMISSION_GRANTED
            )
            if (!badapter.isDiscovering) {
                Toast.makeText(this, "Scanning", Toast.LENGTH_LONG).show()
                val  intent=Intent(Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE))
                startActivityForResult(intent,REQUEST_CODE_DISCOVERABLE_BT)
            }
            return@setOnClickListener}





        //Information of paired devices
        binding.btnpaired.setOnClickListener {
           if( badapter.isEnabled)
           {
               binding.tvpaired.text="Paired Device"
               val devices=badapter.bondedDevices
               for(device in devices){
                   val name=device.name
                   val address=device
                   binding.tvpaired.append("\nDevices  $name  $address")
               }
           }
            else{
               Toast.makeText(this, "Pleae Turn On Bluetooth", Toast.LENGTH_LONG).show()
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when(requestCode){
            REQUEST_CODE_DISCOVERABLE_BT->
                if (requestCode==Activity.RESULT_OK){
                    binding.ivturnstatus.setImageResource(R.drawable.ic_baseline_bluetooth_connected_24)
                }
            else{
                    Toast.makeText(this, "Please turn on the Bluetooth", Toast.LENGTH_LONG).show()
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

}