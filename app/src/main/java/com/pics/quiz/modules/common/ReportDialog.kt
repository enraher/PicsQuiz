package com.pics.quiz.modules.common

/**
 * Created by enrasoft on 8/3/18.
 */
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.pics.quiz.R
import com.pics.quiz.model.Report
import com.pics.quiz.other.ReportManager
import com.pics.quiz.other.getCurrentLanguage
import kotlinx.android.synthetic.main.dialog_report.view.*


class ReportDialog : DialogFragment() {

    interface ReportDialogListener {
        fun onReportDialogClose(reported: Boolean)
    }

    private var listener: ReportDialogListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ReportDialogListener) {
            listener = context
        }
    }

    override fun onActivityCreated(arg0: Bundle?) {
        super.onActivityCreated(arg0)
        dialog?.window?.attributes?.windowAnimations = R.style.DialogAnimation
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.dialog_report, container, false)
        dialog?.window?.setBackgroundDrawableResource(R.drawable.bg_dialogs)

        val report: Report? = arguments?.getSerializable(KEY_REPORT) as Report

        if(report == null) {
            view.radioGroupReport.visibility = View.GONE

            view.btn_report.setOnClickListener {

                context?.let { it1 ->
                    ReportManager.getInstance(it1).report("report from menu", map = hashMapOf(
                        "report_comment" to view.report_comment_edit.text.toString(),
                        "report_language_game" to  getCurrentLanguage(activity)))
                }
                listener?.onReportDialogClose(true)
                dismiss()
            }
        } else {
            view.radioGroupReport.setOnCheckedChangeListener { _, checkedIdView ->
                when (checkedIdView) {
                    view.radioMatch.id ->
                        report.reportType = "Word and image do not match"
                    view.radioMisspelling.id ->
                        report.reportType = "Misspelling"
                    else ->
                        report.reportType = "Other"
                }
            }
            view.radioMatch.isChecked = true

            view.btn_report.setOnClickListener {
                report.comment = view.report_comment_edit.text.toString()
                report.languageGame = getCurrentLanguage(activity)

                val params = hashMapOf(
                "report_comment" to report.comment,
                "report_language_game" to  report.languageGame,
                "report_level_id" to  report.levelId,
                "report_package_id" to  report.packageId,
                "report_type" to  report.reportType,
                "report_sublevel_id" to  report.sublevelId)
                context?.let { it1 -> ReportManager.getInstance(it1).report("user_report", map = params) }
                listener?.onReportDialogClose(true)
                dismiss()
            }
        }

        view.btn_report_quit.setOnClickListener {
            listener?.onReportDialogClose(false)
            dismiss()
        }



        return view
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    companion object {
        val TAG = ReportDialog::class.qualifiedName
        const val KEY_REPORT = "key_report"
        @JvmStatic
        fun newInstance(activity: FragmentActivity, fragment: Fragment?, report: Report? = null) {
            val dialogFragment =  GenericDialog().apply {
                arguments = Bundle().apply {
                    report?.let {
                        this.putSerializable(KEY_REPORT, report)
                    }
                }
            }
            dialogFragment.setTargetFragment(fragment, 0)
            dialogFragment.show(activity.supportFragmentManager, "report_dialog")
        }
    }
}