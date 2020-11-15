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
import androidx.navigation.fragment.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.github.oiwane.alarmsample.R
import io.github.oiwane.alarmsample.alarm.AlarmConfigurator
import io.github.oiwane.alarmsample.data.AlarmProperty
import io.github.oiwane.alarmsample.fileManager.JsonFileManager
import io.github.oiwane.alarmsample.log.LogType
import io.github.oiwane.alarmsample.log.Logger
import io.github.oiwane.alarmsample.message.ErrorMessageToast
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
    private lateinit var repeatSwitchCompat: SwitchCompat
    private lateinit var dowToggleButtonGroup: DOWToggleButtonGroup
    private lateinit var registerButton: Button
    private lateinit var cancelButton: Button
    private var propertyId = -1

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.edit_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initComponents(view)
        initListeners()
    }

    /**
     * コンポーネントを初期化する
     * @param view コンポーネントを保持するView
     */
    private fun initComponents(view: View) {
        alarmEditText = view.findViewById(R.id.alarmEditText)
        hourSpinner = view.findViewById(R.id.hourSpinner)
        minuteSpinner = view.findViewById(R.id.minuteSpinner)
        snoozeSwitchCompat = view.findViewById(R.id.snoozeSwitch)
        snoozeSpinner = view.findViewById(R.id.snoozeSpinner)
        repeatSwitchCompat = view.findViewById(R.id.repeatSwitch)
        dowToggleButtonGroup = DOWToggleButtonGroup(view)
        registerButton = view.findViewById(R.id.registerButton)
        cancelButton = view.findViewById(R.id.cancelButton)

        val initializer = SpinnerContentInitializer(requireContext())
        initializer.initSpinnerContent(hourSpinner, 0..23)
        initializer.initSpinnerContent(minuteSpinner, 0..59)
        initializer.initSpinnerContent(snoozeSpinner, 5..20, 5, "%d分")

        snoozeSpinner.isEnabled = false
        dowToggleButtonGroup.setEnabledToAll(false)

        // 編集時の処理
        try {
            val propertyIdStr = requireArguments().getString("EDITED_PROPERTY_ID")
            if (propertyIdStr != null) {
                propertyId = Integer.parseInt(propertyIdStr)
                val property = AlarmConfigurator.createPropertyList(requireContext())!![propertyId]

                initValueOfComponents(property)
            }
        } catch (e: IllegalStateException) {
            Logger.write(LogType.INFO, "bundle don't have key 'EDITED_PROPERTY_ID'")
        }
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
        repeatSwitchCompat.isChecked = property.hasRepeated
        dowToggleButtonGroup.setEnabledToAll(repeatSwitchCompat.isChecked)
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
        repeatSwitchCompat.setOnCheckedChangeListener { _, isChecked ->
            dowToggleButtonGroup.setEnabledToAll(isChecked)
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

            val propertyList = AlarmConfigurator.createPropertyList(context)
            val property = createPropertyFromInput()
            if (propertyId == -1) {
                propertyList!!.add(property)
            } else {
                propertyList!![propertyId] = property
            }

            // ファイルの書き込みができなかった場合
            if (!JsonFileManager(context).write(propertyList)) {
                toast.showErrorMessage(R.string.error_failed_write_file)
                propertyList.remove(property)
                return@OnClickListener
            }

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
            repeatSwitchCompat.isChecked,
            dowToggleButtonGroup.getDayOfWeek()
        )
    }
}