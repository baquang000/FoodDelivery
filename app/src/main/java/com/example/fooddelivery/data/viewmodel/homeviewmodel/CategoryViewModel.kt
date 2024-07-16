package com.example.fooddelivery.data.viewmodel.homeviewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.fooddelivery.data.model.Food
import com.example.fooddelivery.data.model.FoodState
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CategoryViewModel : ViewModel() {
    val burgerFood: MutableState<FoodState> = mutableStateOf(FoodState.Empty)
    val chickenFood: MutableState<FoodState> = mutableStateOf(FoodState.Empty)
    val drinkFood: MutableState<FoodState> = mutableStateOf(FoodState.Empty)
    val hotdogFood: MutableState<FoodState> = mutableStateOf(FoodState.Empty)
    val meatFood: MutableState<FoodState> = mutableStateOf(FoodState.Empty)
    val moreFood: MutableState<FoodState> = mutableStateOf(FoodState.Empty)
    val pizzaFood: MutableState<FoodState> = mutableStateOf(FoodState.Empty)
    val shushiFood: MutableState<FoodState> = mutableStateOf(FoodState.Empty)

    init {
        fetchBurgerFood()
        fetchChickenFood()
        fetchDrinkFood()
        fetchHotDogFood()
        fetchMeatFood()
        fetchMoreFood()
        fetchPizzFood()
        fetchShushiFood()
    }

    private fun fetchBurgerFood() {
        val emptyList = mutableListOf<Food>()
        burgerFood.value = FoodState.Loading
        val query = FirebaseDatabase.getInstance().getReference("Foods").orderByChild("CategoryId")
            .equalTo(1.0)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnap in snapshot.children) {
                    val foodItem = dataSnap.getValue(Food::class.java)
                    if (foodItem != null) {
                        emptyList.add(foodItem)
                    }
                }
                burgerFood.value = FoodState.Success(emptyList)
            }

            override fun onCancelled(error: DatabaseError) {
                burgerFood.value = FoodState.Failure(error.message)
            }

        })
    }

    private fun fetchChickenFood() {
        val emptyList = mutableListOf<Food>()
        chickenFood.value = FoodState.Loading
        val query = FirebaseDatabase.getInstance().getReference("Foods").orderByChild("CategoryId")
            .equalTo(2.0)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnap in snapshot.children) {
                    val foodItem = dataSnap.getValue(Food::class.java)
                    if (foodItem != null) {
                        emptyList.add(foodItem)
                    }
                }
                chickenFood.value = FoodState.Success(emptyList)
            }

            override fun onCancelled(error: DatabaseError) {
                chickenFood.value = FoodState.Failure(error.message)
            }

        })
    }

    private fun fetchDrinkFood() {
        val emptyList = mutableListOf<Food>()
        drinkFood.value = FoodState.Loading
        val query = FirebaseDatabase.getInstance().getReference("Foods").orderByChild("CategoryId")
            .equalTo(6.0)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnap in snapshot.children) {
                    val foodItem = dataSnap.getValue(Food::class.java)
                    if (foodItem != null) {
                        emptyList.add(foodItem)
                    }
                }
                drinkFood.value = FoodState.Success(emptyList)
            }

            override fun onCancelled(error: DatabaseError) {
                drinkFood.value = FoodState.Failure(error.message)
            }

        })
    }

    private fun fetchHotDogFood() {
        val emptyList = mutableListOf<Food>()
        hotdogFood.value = FoodState.Loading
        val query = FirebaseDatabase.getInstance().getReference("Foods").orderByChild("CategoryId")
            .equalTo(5.0)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnap in snapshot.children) {
                    val foodItem = dataSnap.getValue(Food::class.java)
                    if (foodItem != null) {
                        emptyList.add(foodItem)
                    }
                }
                hotdogFood.value = FoodState.Success(emptyList)
            }

            override fun onCancelled(error: DatabaseError) {
                hotdogFood.value = FoodState.Failure(error.message)
            }

        })
    }

    private fun fetchMeatFood() {
        val emptyList = mutableListOf<Food>()
        meatFood.value = FoodState.Loading
        val query = FirebaseDatabase.getInstance().getReference("Foods").orderByChild("CategoryId")
            .equalTo(4.0)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnap in snapshot.children) {
                    val foodItem = dataSnap.getValue(Food::class.java)
                    if (foodItem != null) {
                        emptyList.add(foodItem)
                    }
                }
                meatFood.value = FoodState.Success(emptyList)
            }

            override fun onCancelled(error: DatabaseError) {
                meatFood.value = FoodState.Failure(error.message)
            }

        })
    }

    private fun fetchMoreFood() {
        val emptyList = mutableListOf<Food>()
        moreFood.value = FoodState.Loading
        val query = FirebaseDatabase.getInstance().getReference("Foods").orderByChild("CategoryId")
            .equalTo(7.0)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnap in snapshot.children) {
                    val foodItem = dataSnap.getValue(Food::class.java)
                    if (foodItem != null) {
                        emptyList.add(foodItem)
                    }
                }
                moreFood.value = FoodState.Success(emptyList)
            }

            override fun onCancelled(error: DatabaseError) {
                moreFood.value = FoodState.Failure(error.message)
            }

        })
    }

    private fun fetchPizzFood() {
        val emptyList = mutableListOf<Food>()
        pizzaFood.value = FoodState.Loading
        val query = FirebaseDatabase.getInstance().getReference("Foods").orderByChild("CategoryId")
            .equalTo(0.0)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnap in snapshot.children) {
                    val foodItem = dataSnap.getValue(Food::class.java)
                    if (foodItem != null) {
                        emptyList.add(foodItem)
                    }
                }
                pizzaFood.value = FoodState.Success(emptyList)
            }

            override fun onCancelled(error: DatabaseError) {
                pizzaFood.value = FoodState.Failure(error.message)
            }

        })
    }

    private fun fetchShushiFood() {
        val emptyList = mutableListOf<Food>()
        shushiFood.value = FoodState.Loading
        val query = FirebaseDatabase.getInstance().getReference("Foods").orderByChild("CategoryId")
            .equalTo(3.0)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnap in snapshot.children) {
                    val foodItem = dataSnap.getValue(Food::class.java)
                    if (foodItem != null) {
                        emptyList.add(foodItem)
                    }
                }
                shushiFood.value = FoodState.Success(emptyList)
            }

            override fun onCancelled(error: DatabaseError) {
                shushiFood.value = FoodState.Failure(error.message)
            }

        })
    }
}