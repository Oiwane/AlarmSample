package io.github.oiwane.alarmsample.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import io.github.oiwane.alarmsample.R
import io.github.oiwane.alarmsample.alarm.AlarmConfigurator
import io.github.oiwane.alarmsample.alarm.AlarmProperty
import io.github.oiwane.alarmsample.exception.InvalidAlarmOperationException
import io.github.oiwane.alarmsample.fileManager.JsonFileManager
import io.github.oiwane.alarmsample.log.LogType
import io.github.oiwane.alarmsample.log.Logger
import io.github.oiwane.alarmsample.util.Constants
import io.github.oiwane.alarmsample.message.ErrorMessageToast
import io.github.oiwane.alarmsample.viewModel.AlarmViewModel
import io.github.oiwane.alarmsample.widget.spinner.MySpinner
import io.github.oiwane.alarmsample.widget.toggleButton.DOWToggleButtonGroup

/**
 * アラームを追加する画面
 */
class EditFragment : Fragment() {
    private lateinit var alarmEditText: EditText
    private lateinit var hourSpinner: MySpinner
    private lateinit var minuteSpinner: MySpinner
    private lateinit var snoozeSwitchCompat: SwitchCompat
    private lateinit var snoozeSpinner: MySpinner
    private lateinit var dowToggleButtonGroup: DOWToggleButtonGroup
    private lateinit var registerButton: Button
    private lateinit var cancelButton: Button
    private var propertyId: String? = null

    private lateinit var alarmViewModel: AlarmViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.edit_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        alarmViewModel = ViewModelProvider(requireActivity(), AlarmViewModel.Factory(requireContext())).get(AlarmViewModel::class.java)

        if (!initComponents(view)) {
            if (!findNavController().popBackStack())
                requireActivity().finish()
            return
        }
        initListeners()
    }

    /**
     * コンポーネントを初期化する
     * @param view コンポーネントを保持するView
     */
    private fun initComponents(view: View): Boolean {
        alarmEditText = view.findViewById(R.id.alarmEditText)
        hourSpinner = view.findViewById(R.id.hourSpinner)
        minuteSpinner = view.findViewById(R.id.minuteSpinner)
        snoozeSwitchCompat = view.findViewById(R.id.snoozeSwitch)
        snoozeSpinner = view.findViewById(R.id.snoozeSpinner)
        dowToggleButtonGroup = DOWToggleButtonGroup(view)
        registerButton = view.findViewById(R.id.registerButton)
        cancelButton = view.findViewById(R.id.cancelButton)

        hourSpinner.initContent(0..23)
        minuteSpinner.initContent(0..59)
        snoozeSpinner.initContent(5..20, 5, "%d分")

        snoozeSpinner.isEnabled = false

        // 編集時の処理
        try {
            propertyId = requireArguments().getString(Constants.EDITED_PROPERTY_ID)
            if (!propertyId.isNullOrEmpty()) {
                val property = alarmViewModel.alarmList.value!!.find { it.id == propertyId!! } ?: return false

                initValueOfComponents(property)
            }
        } catch (e: IllegalStateException) {
            Logger.write(LogType.INFO, "bundle don't have key '${Constants.EDITED_PROPERTY_ID}'")
        }
        return true
    }

    /**
     * 編集時の各コンポーネントの初期値をセットする
     * @param property アラーム情報
     */
    private fun initValueOfComponents(property: AlarmProperty) {
        alarmEditText.setText(property.title)
        hourSpinner.setSelection(property.hour)
        minuteSpinner.setSelection(property.minute)
        snoozeSwitchCompat.isChecked = property.hasSnoozed
        snoozeSpinner.isEnabled = snoozeSwitchCompat.isChecked
        snoozeSpinner.setSelection(property.snoozeTime)
        dowToggleButtonGroup.setChecked(property.dow)
        registerButton.text = requireContext().getText(R.string.update)
    }

    /**
     * リスナーを初期化する
     */
    private fun initListeners() {
        snoozeSwitchCompat.setOnCheckedChangeListener { _, isChecked ->
            snoozeSpinner.isEnabled = isChecked
        }
        registerButton.setOnClickListener(createOnClickListenerOfRegisterButton())
        cancelButton.setOnClickListener {
            val alarmList = alarmViewModel.alarmList.value?: return@setOnClickListener
            val activity = requireActivity()
            val context = requireContext()
            try {
                AlarmConfigurator(context).resetAllAlarm(alarmList)
            } catch (e : InvalidAlarmOperationException) {
                ErrorMessageToast(context).showErrorMessage(R.string.error_failed_update_alarm)
            } finally {
                if (!findNavController().popBackStack())
                    activity.finish()
            }
        }
    }

    /**
     * registerButtonを押したときの処理
     * @return registerButton用のOnClickListener
     */
    private fun createOnClickListenerOfRegisterButton(): View.OnClickListener {
        return View.OnClickListener {
            val activity = requireActivity()
            val context = requireContext()

            // アラーム名を入力していなかった場合
            if (alarmEditText.text.toString() == "") {
                ErrorMessageToast(context).showErrorMessage(R.string.error_edit_text_empty)
                return@OnClickListener
            }

            val propertyList = alarmViewModel.alarmList.value!!
            val property = createPropertyFromInput()
            if (propertyId.isNullOrEmpty())
                propertyList.add(property)
            else
                propertyList.set(property)

            // ファイルの書き込みができなかった場合
            if (!JsonFileManager(context).write(propertyList)) {
                ErrorMessageToast(context).showErrorMessage(R.string.error_failed_write_file)
                propertyList.remove(property)
                return@OnClickListener
            }

            if (propertyId.isNullOrEmpty())
                AlarmConfigurator(context).setUpAlarm(property)
            else
                AlarmConfigurator(context).resetAlarm(property)

            if (!findNavController().popBackStack())
                activity.finish()
        }
    }

    /**
     * 入力値からプロパティを作成する
     * @return アラーム情報
     */
    private fun createPropertyFromInput(): AlarmProperty {
        return AlarmProperty(
                propertyId,
                alarmEditText.text.toString(),
                hourSpinner.selectedItemPosition,
                minuteSpinner.selectedItemPosition,
                snoozeSwitchCompat.isChecked,
                snoozeSpinner.selectedItemPosition,
                dowToggleButtonGroup.getDayOfWeek(),
                true
            )
    }
}