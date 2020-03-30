package com.conceptgang.coronanews.ui.dialog

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.fragment.app.DialogFragment
import com.conceptgang.coronanews.databinding.ZhDialogBinding
import com.conceptgang.coronanews.utils.exhaustive

sealed class ZHDialogType {
    object Normal: ZHDialogType()
    data class ButtonDialog(
        val noClick: ()->Unit,
        val yesClick: ()->Unit
    ): ZHDialogType()
    data class OnCancel(val onClick: ()->Unit): ZHDialogType()
    data class YesDialog(val onYes: ()->Unit, val onClick: ()->Unit): ZHDialogType()
}

class ZHDialog(
    @DrawableRes private val icon:Int,
    private val header:String,
    private val message:String,
    private val type: ZHDialogType = ZHDialogType.Normal,
    private val positiveTitle: String = "Yes",
    private val negativeTitle: String = "No"
): DialogFragment() {

    private lateinit var binding: ZhDialogBinding

    private var onCancel: (()->Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // to make this dialog full screen and
        // to remove the title from the dialog window
        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Translucent)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = ZhDialogBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        onCancel?.invoke()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.yesBtn.text = positiveTitle
        binding.noBtn.text = negativeTitle
        binding.headerTxt.text = header
        binding.messageTxt.text = message
        binding.iconImage.setImageResource(icon)

        binding.cancelImage.setOnClickListener {
            dismiss()
        }

        binding.dialogContainer.setOnClickListener {
            dismiss()
        }


        when (type) {

            is ZHDialogType.Normal -> {
                binding.yesBtn.visibility = View.GONE
                binding.noBtn.visibility = View.GONE
            }


            is ZHDialogType.ButtonDialog -> {

                binding.yesBtn.visibility = View.VISIBLE
                binding.noBtn.visibility = View.VISIBLE

                binding.yesBtn.setOnClickListener {
                    type.yesClick.invoke()
                    dismiss()
                }
                binding.noBtn.setOnClickListener {
                    type.noClick.invoke()
                    dismiss()
                }
            }

            is ZHDialogType.OnCancel ->{
                binding.yesBtn.visibility = View.GONE
                binding.noBtn.visibility = View.GONE
                onCancel = type.onClick
            }
            is ZHDialogType.YesDialog -> {

                binding.yesBtn.visibility = View.VISIBLE
                binding.noBtn.visibility = View.GONE

                binding.yesBtn.setOnClickListener {
                    type.onYes.invoke()
                    dismiss()
                }

                onCancel = type.onClick

            }
        }.exhaustive
    }

}
