package com.teruten.todolist

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import io.realm.Realm
import io.realm.RealmResults
import io.realm.Sort
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    private val realm = Realm.getDefaultInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val realmResult: RealmResults<Todo> = realm.where<Todo>()
            .findAll()
            .sort("date", Sort.DESCENDING)

        val adapter = TodoAdapter(realmResult) { id ->
            startActivity(Intent(this@MainActivity, EditActivity::class.java).putExtra("id", id))
        }

        realmResult.addChangeListener { _ ->
            adapter.notifyDataSetChanged()
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        fab.setOnClickListener {
            startActivity(Intent(this@MainActivity, EditActivity::class.java))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}
