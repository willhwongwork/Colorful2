package com.cp.purecolor2

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cp.purecolor2.data.Colors
import com.cp.purecolor2.data.DataRepository
import kotlinx.android.synthetic.main.fragment_collection.*

class CollectionFragment: Fragment() {
    companion object {
        val ARG_COLLECTION = "ARG_COLLECTION"

        fun newInstance(tab: Int):CollectionFragment {
            val args = Bundle()
            args.putInt(ARG_COLLECTION, tab)
            val fragment = CollectionFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var model: CollectionViewModel
    private lateinit var adapter: CollectionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater?.inflate(R.layout.fragment_collection, container, false)
        adapter = CollectionAdapter()
        return rootView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        //super.onViewCreated(view, savedInstanceState)
        val layoutManager = GridLayoutManager(context, Utility.calculateNoOfColumns(context, 96))
        layoutManager.isSmoothScrollbarEnabled = true
        color_rv.layoutManager = layoutManager
        val spacingInPixels = resources.getDimensionPixelSize(R.dimen.spacing)
        color_rv.addItemDecoration(SpacesItemDecoration(Utility.calculateNoOfColumns(context, 96), spacingInPixels, true));
        color_rv.adapter = adapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        model = getViewModel()
        model.initialize()
        model.getColors().observe(this, Observer<List<Colors>> {
            colors: List<Colors>? ->
            //Log.d("CollectionFragemnt", colors?.get(0)?.name)
            adapter.setColors(colors!!)})
    }

    private fun getViewModel() : CollectionViewModel {
        val repository = DataRepository(context, Injection.provideUserDataSource(context), Injection.provideExecutor())
        val factory = CollectionViewModel.Factory(repository)
        return ViewModelProviders.of(this, factory).get(CollectionViewModel::class.java)
    }

}