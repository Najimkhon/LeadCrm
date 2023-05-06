package com.example.leadcrm.ui.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.leadcrm.R
import com.example.leadcrm.databinding.SpinnerCustomViewBinding
import com.example.leadcrm.ui.adapters.TopicSpinnerAdapter

class SpinnerCustomView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr, defStyleRes) {
    private val binding: SpinnerCustomViewBinding

    private lateinit var topicSpinnerAdapter: TopicSpinnerAdapter

    init {
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.spinner_custom_view, this, true)
        binding = SpinnerCustomViewBinding.bind(this)
        initializeAttributes(attrs, defStyleAttr, defStyleRes)

    }

    fun setAdapter(adapter: TopicSpinnerAdapter) {
        topicSpinnerAdapter = adapter
        binding.spinner.adapter = topicSpinnerAdapter
    }

    fun showErrorState() {
        binding.spinner.setBackgroundResource(R.drawable.edit_text_error_bg)
        //topicSpinnerAdapter.setErrorState(true)
    }

    fun showNormalState() {
        //topicSpinnerAdapter.setErrorState(false)
        binding.spinner.setBackgroundResource(R.drawable.edit_text_bg)
    }

    private fun initializeAttributes(attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {
        if (attrs == null) return
        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.SpinnerCustomView,
            defStyleAttr,
            defStyleRes
        )

        val hint = typedArray.getString(R.styleable.SpinnerCustomView_spinnerHint)
        binding.tvLabel.text = hint

        val error = typedArray.getBoolean(R.styleable.SpinnerCustomView_spinnerError, false)

        if (error) {
            showErrorState()
        } else {
            showNormalState()
        }

        typedArray.recycle()
    }
}