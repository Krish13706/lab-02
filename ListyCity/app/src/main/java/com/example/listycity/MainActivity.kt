package com.example.listycity

import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.remember
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.clickable
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.Row
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.listycity.ui.theme.ListyCityTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val cityRepository = CityRepository()
        setContent {
            ListyCityTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CityListScreen(
                        cities = cityRepository.cities,
                        onAddCity = { cityRepository.addCity(it) },
                        onRemoveCity = { cityRepository.removeCity(it) },
                        modifier = Modifier
                            .padding(innerPadding)
                            .background(Color(0xFFFFF8E7))
                    )
                }
            }
        }
    }
}

class CityRepository {
    private val _cities = mutableStateListOf(
        "Edmonton",
        "Dubai",
        "Tokyo",
        "Sharjah",
        "Toronto"
    )
    val cities: List<String>
        get() = _cities

    fun addCity(city: String) {
        _cities.add(city)
    }

    fun removeCity(city: String) {
        _cities.remove(city)
    }
}

@Composable
fun CityListScreen(
    cities: List<String>,
    onAddCity: (String) -> Unit,
    onRemoveCity: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var newCityName by remember { mutableStateOf("") }
    var selectedCity by remember { mutableStateOf<String?>(null) }
    Column(modifier = modifier.fillMaxSize()) {
        Row(modifier = Modifier.padding(16.dp)) {
            OutlinedTextField(
                value = newCityName,
                onValueChange = { newCityName = it },
                label = { Text("City Name") },
                modifier = Modifier.weight(1f)
            )


            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = {
                    if (newCityName.isNotBlank()) {
                        onAddCity(newCityName)
                        newCityName = ""
                    }
                }
            ) {
                Text("Add City")
            }
            Button(
                onClick = {
                    if(selectedCity!=null){
                        onRemoveCity(selectedCity!!)
                        selectedCity = null
                    }else if(newCityName.isNotBlank()){
                        onRemoveCity(newCityName)
                        newCityName=""
                    }
                }
            ) {
                Text("Delete City")
            }
        }
        LazyColumn(
            modifier = modifier.fillMaxSize()
        ) {
            items(cities) { city ->
                CityRow(
                    city = city,
                    isSelected = city == selectedCity,
                    onClick = { selectedCity = it })
            }
        }
    }
}

@Composable
fun CityRow(city: String, isSelected: Boolean,onClick: (String)-> Unit) {
    Text(
        text = city,
        fontSize = 30.sp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp, vertical = 14.dp)
            .clickable{
                onClick(city)
            }
            .background(
                if (isSelected) Color.LightGray else Color.Transparent
            )

    )
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ListyCityTheme {
        Greeting("Android")
    }
}