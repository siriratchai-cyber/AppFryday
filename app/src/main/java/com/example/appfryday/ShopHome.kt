package com.example.appfryday

import AdAdapter
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.firebase.firestore.FirebaseFirestore

class ShopHome : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private lateinit var layoutMenuContainer: LinearLayout
    private lateinit var rvRecommend: RecyclerView
    private lateinit var rvCategory: RecyclerView
    private lateinit var viewPagerAd: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_home)

        db = FirebaseFirestore.getInstance()

        layoutMenuContainer = findViewById(R.id.layoutMenuContainer)
        rvRecommend = findViewById(R.id.rvRecommend)
        rvCategory = findViewById(R.id.rvCategory)
        viewPagerAd = findViewById(R.id.viewPagerAd)

        // Popular menu
        rvRecommend.layoutManager = LinearLayoutManager(this)

        // Category แนวนอน
        rvCategory.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        setupAds()

        loadRecommendMenu()
        loadAllCategoryWithMenu()
    }

    // =========================
    // 🎯 โฆษณาสไลด์
    // =========================
    private fun setupAds() {

        val ads = listOf(
            R.drawable.ad1,
            R.drawable.ad2
        )

        viewPagerAd.adapter = AdAdapter(ads)
    }

    // =========================
    // ⭐ โหลด Popular (Recommended)
    // =========================
    private fun loadRecommendMenu() {

        db.collection("Menu")
            .whereEqualTo("Recommended", true)
            .get()
            .addOnSuccessListener { result ->

                val list = result.toObjects(MenuModel::class.java)
                rvRecommend.adapter = MenuVerticalAdapter(list)
            }
    }

    // =========================
    // 📌 โหลด Category + เมนู
    // =========================
    private fun loadAllCategoryWithMenu() {

        layoutMenuContainer.removeAllViews()

        db.collection("Category")
            .orderBy("Category name")
            .get()
            .addOnSuccessListener { categoryResult ->

                for (categoryDoc in categoryResult) {

                    val categoryId = categoryDoc.id
                    val categoryName =
                        categoryDoc.getString("Category name") ?: ""

                    loadMenuByCategory(categoryId, categoryName)
                }
            }
    }

    private fun loadMenuByCategory(categoryId: String, categoryName: String) {

        db.collection("Menu")
            .whereEqualTo("CategoryID", categoryId)
            .get()
            .addOnSuccessListener { menuResult ->

                val menuList =
                    menuResult.toObjects(MenuModel::class.java)

                if (menuList.isNotEmpty()) {

                    addCategoryTitle(categoryName)
                    addMenuRecycler(menuList)
                }
            }
    }

    // =========================
    // 🔹 หัวข้อ Category + ปุ่มแก้ไข
    // =========================
    private fun addCategoryTitle(title: String) {

        val row = LinearLayout(this)
        row.orientation = LinearLayout.HORIZONTAL
        row.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        val textView = TextView(this)
        textView.text = title
        textView.textSize = 18f
        textView.layoutParams = LinearLayout.LayoutParams(
            0,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            1f
        )

        val editBtn = ImageView(this)
        editBtn.setImageResource(android.R.drawable.ic_menu_edit)

        row.setPadding(8, 32, 8, 16)

        row.addView(textView)
        row.addView(editBtn)

        layoutMenuContainer.addView(row)
    }

    // =========================
    // 🔹 Recycler เมนู
    // =========================
    private fun addMenuRecycler(menuList: List<MenuModel>) {

        val recyclerView = RecyclerView(this)

        recyclerView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = MenuVerticalAdapter(menuList)
        recyclerView.isNestedScrollingEnabled = false

        layoutMenuContainer.addView(recyclerView)
    }
}
