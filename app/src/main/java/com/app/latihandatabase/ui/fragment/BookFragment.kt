package com.app.latihandatabase.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.latihandatabase.R
import com.app.latihandatabase.adapter.BookFragmentAdapter
import com.app.latihandatabase.database.AppDatabase
import com.app.latihandatabase.databinding.FragmentBookBinding
import com.app.latihandatabase.entity.Book

class BookFragment : Fragment(),BookFragmentAdapter.BookFRItemClickInterface {

    private lateinit var binding: FragmentBookBinding
    private lateinit var bookFRAdapter: BookFragmentAdapter
    private lateinit var database: AppDatabase
    private var listbook = mutableListOf<Book>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentBookBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        database = AppDatabase.getInstance(requireContext())
        bookFRAdapter = BookFragmentAdapter(listbook,this)
        binding.rvBookfr.apply {
            getData()
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = bookFRAdapter
            bookFRAdapter.notifyDataSetChanged()
        }
        binding.btnfloatfr.setOnClickListener {
            fragmentManager?.beginTransaction()?.replace(R.id.fragment_container, AddBookFragment())
                ?.commit()
        }
    }

    override fun onResume() {
        super.onResume()
        getData()
    }

    fun getData(){
        listbook.clear()
        listbook.addAll(database.bookDao().getAll())
        bookFRAdapter.notifyDataSetChanged()
    }

    override fun onDelete(position: Int) {
        database.bookDao().deleteBook(listbook[position])
        getData()
    }

    override fun onUpdate(position: Int) {
        val bundle = Bundle()
        listbook[position].bid?.let { bundle.putInt("bid", it) }
        val fragmentadd = AddBookFragment()
        fragmentadd.arguments = bundle
        fragmentManager?.beginTransaction()?.replace(R.id.fragment_container, fragmentadd)
            ?.commit()
    }

}