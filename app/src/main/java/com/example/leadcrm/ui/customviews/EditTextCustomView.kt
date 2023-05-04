package com.example.leadcrm.ui.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.example.leadcrm.R
import com.example.leadcrm.databinding.EditTextCustomViewBinding

class EditTextCustomView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
): ConstraintLayout(context, attrs, defStyleAttr, defStyleRes) {
    private val binding: EditTextCustomViewBinding

    init {
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.edit_text_custom_view, this, true)
        binding = EditTextCustomViewBinding.bind(this)
        initializeAttributes(attrs, defStyleAttr, defStyleRes)
    }

    fun setText(text: String){
        binding.editText.setText(text)
    }

    fun setHint(text: String){
        binding.editText.hint = text
    }

    fun setLabel(text: String){
        binding.tvLabel.text = text
    }

    fun showErrorState(){
        binding.editText.setTextColor(ContextCompat.getColor(context, R.color.error_color))
        binding.editText.setBackgroundResource(R.drawable.edit_text_error_bg)
    }

    fun showNormalState(){
        binding.editText.setTextColor(ContextCompat.getColor(context, R.color.dark_blue))
        binding.editText.setBackgroundResource(R.drawable.edit_text_bg)
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
            val hintText = typedArray.getString(R.styleable.EditTextCustomView_hint)
            editText.hint = hintText

            val labelText = typedArray.getString(R.styleable.EditTextCustomView_label)
            tvLabel.text = labelText

            val inputText = typedArray.getString(R.styleable.EditTextCustomView_text)
            editText.setText(inputText)

            val error = typedArray.getBoolean(R.styleable.EditTextCustomView_error, false)
            if (error){
                showErrorState()
            }else{
                showNormalState()
            }
        }

        typedArray.recycle()
    }
}