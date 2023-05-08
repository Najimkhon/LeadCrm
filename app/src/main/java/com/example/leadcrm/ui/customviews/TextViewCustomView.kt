package com.example.leadcrm.ui.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.example.leadcrm.R
import com.example.leadcrm.databinding.EditTextCustomViewBinding
import com.example.leadcrm.databinding.TextViewCustomViewBinding

class TextViewCustomView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
): ConstraintLayout(context, attrs, defStyleAttr, defStyleRes) {
    private val binding: TextViewCustomViewBinding

    init {
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.text_view_custom_view, this, true)
        binding = TextViewCustomViewBinding.bind(this)
        initializeAttributes(attrs, defStyleAttr, defStyleRes)
    }

    fun setText(text: String){
        binding.tvText.setText(text)
    }

    fun setLabel(text: String){
        binding.tvLabel.text = text
    }

    fun showErrorState(){
        binding.tvText.setTextColor(ContextCompat.getColor(context, R.color.error_color))
        binding.tvText.setBackgroundResource(R.drawable.edit_text_error_bg)
    }

    fun showNormalState(){
        binding.tvText.setTextColor(ContextCompat.getColor(context, R.color.dark_blue))
        binding.tvText.setBackgroundResource(R.drawable.edit_text_bg)
    }

    private fun initializeAttributes(attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {
        if (attrs == null) return
        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.EditTextCustomView,
            defStyleAttr,
            defStyleRes
        )

        with(binding) {

            val labelText = typedArray.getString(R.styleable.TextViewCustomView_tvLabel)
            tvLabel.text = labelText

            val inputText = typedArray.getString(R.styleable.TextViewCustomView_tvText)
            tvText.text = inputText

            val error = typedArray.getBoolean(R.styleable.TextViewCustomView_tvError, false)
            if (error){
                showErrorState()
            }else{
                showNormalState()
            }
        }

        typedArray.recycle()
    }
}