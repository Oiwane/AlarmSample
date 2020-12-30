package io.github.oiwane.alarmsample.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import io.github.oiwane.alarmsample.R
import io.github.oiwane.alarmsample.alarm.AlarmConfigurator
import io.github.oiwane.alarmsample.data.AlarmProperty
import io.github.oiwane.alarmsample.fileManager.JsonFileManager
import io.github.oiwane.alarmsample.log.LogType
import io.github.oiwane.alarmsample.log.Logger
import io.github.oiwane.alarmsample.util.Constants
import io.github.oiwane.alarmsample.message.ErrorMessageToast
import io.github.oiwane.alarmsample.viewModel.AlarmViewModel
import io.github.oiwane.alarmsample.widget.spinner.SpinnerContentInitializer
import io.github.oiwane.alarmsample.widget.toggleButton.DOWToggleButtonGroup

/**
 * アラームを追加する画面
 */
class EditFragment : Fragment() {
    private lateinit var alarmEditText: EditText
    private lateinit var hourSpinner: Spinner
    private lateinit var minuteSpinner: Spinner
    private lateinit var snoozeSwitchCompat: SwitchCompat
    private lateinit var snoozeSpinner: Spinner
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

        val initializer = SpinnerContentInitializer(requireContext())
        initializer.initSpinnerContent(hourSpinner, 0..23)
        initializer.initSpinnerContent(minuteSpinner, 0..59)
        initializer.initSpinnerContent(snoozeSpinner, 5..20, 5, "%d分")

        snoozeSpinner.isEnabled = false

        // 編集時の処理
        try {
            propertyId = requireArguments().getString(Constants.EDITED_PROPERTY_ID)
            if (!propertyId.isNullOrEmpty()) {
                val property = alarmViewModel.alarmList.value!!.findById(propertyId!!) ?: return false

                initValueOfComponents(property)
            }
        } catch (e: IllegalStateException) {
            Logger.write(LogType.INFO, "bundle don't have key '${Constants.EDITED_PROPERTY_ID}'")
            return false
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
            findNavController().popBackStack()
        }
    }

    /**
     * registerButtonを押したときの処理
     * @return registerButton用のOnClickListener
     */
    private fun createOnClickListenerOfRegisterButton(): View.OnClickListener {
        return View.OnClickListener {
            val context = requireContext()
            val toast = ErrorMessageToast(context)

            // アラーム名を入力していなかった場合
            if (alarmEditText.text.toString() == "") {
                toast.showErrorMessage(R.string.error_edit_text_empty)
                return@OnClickListener
            }

            val propertyList = alarmViewModel.alarmList.value
            val property = createPropertyFromInput()
            if (propertyId.isNullOrEmpty()) {
                propertyList!!.add(property)
            } else {
                propertyList!!.set(property)
            }

            // ファイルの書き込みができなかった場合
            if (!JsonFileManager(context).write(propertyList)) {
                toast.showErrorMessage(R.string.error_failed_write_file)
                propertyList.remove(property)
                return@OnClickListener
            }

            AlarmConfigurator(requireActivity(), requireContext()).setUpAlarm(property)

            findNavController().popBackStack()
        }
    }

    /**
     * 入力値からプロパティを作成する
     * @return アラーム情報
     */
    private fun createPropertyFromInput(): AlarmProperty {
        return AlarmProperty(
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