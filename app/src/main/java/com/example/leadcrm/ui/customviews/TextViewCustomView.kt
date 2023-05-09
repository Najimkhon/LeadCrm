package com.example.leadcrm.ui.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
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

    fun getText():String{
        return binding.tvText.text.toString()
    }

    fun setSelected(){
        binding.tvText.setTextColor(ContextCompat.getColor(context, R.color.dark_blue))
    }

    fun setDeselected(){
        binding.tvText.setTextColor(ContextCompat.getColor(context, R.color.edit_text_hint_color))
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
        binding.tvText.setTextColor(ContextCompat.getColor(context, R.color.edit_text_hint_color))
        binding.tvText.setBackgroundResource(R.drawable.edit_text_bg)
    }

    private fun initializeAttributes(attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {
        if (attrs == null) return
        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.TextViewCustomView,
            defStyleAttr,
            defStyleRes
        )

        with(binding) {

            val labelText = typedArray.getString(R.styleable.TextViewCustomView_CustomTvLabel)
            tvLabel.text = labelText

            val inputText = typedArray.getString(R.styleable.TextViewCustomView_CustomTvText)
            tvText.text = inputText

            val showIcon = typedArray.getBoolean(R.styleable.TextViewCustomView_ShowDialogIcon, false)
            if (showIcon){
                binding.ivDialogIcon.visibility = View.VISIBLE
            }else{
                binding.ivDialogIcon.visibility = View.INVISIBLE
            }

            val error = typedArray.getBoolean(R.styleable.TextViewCustomView_CustomTvError, false)
            if (error){
                showErrorState()
            }else{
                showNormalState()
            }
        }

        typedArray.recycle()
    }
}