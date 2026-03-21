package com.circuitiq.app.ui.calculator

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.circuitiq.app.R
import com.circuitiq.app.data.model.Favorite
import com.circuitiq.app.data.model.HistoryEntry
import com.circuitiq.app.data.repository.Repository
import com.circuitiq.app.databinding.ActivityCalculatorBinding
import com.circuitiq.app.util.CalculatorData
import com.circuitiq.app.util.CalculatorEngine
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class CalculatorActivity : AppCompatActivity() {
    private lateinit var b: ActivityCalculatorBinding
    private var calcId = ""
    private var calcName = ""
    private var calcCategory = ""
    private var calcDesc = ""
    private var isFav = false
    private var lastResult: CalculatorEngine.Result? = null

    override fun onCreate(s: Bundle?) {
        super.onCreate(s)
        b = ActivityCalculatorBinding.inflate(layoutInflater)
        setContentView(b.root)
        calcId = intent.getStringExtra("calc_id") ?: ""
        calcName = intent.getStringExtra("calc_name") ?: ""
        calcCategory = intent.getStringExtra("calc_category") ?: ""
        calcDesc = intent.getStringExtra("calc_desc") ?: ""
        b.tvTitle.text = calcName
        b.tvCategory.text = calcCategory
        b.tvDesc.text = calcDesc
        b.btnBack.setOnClickListener { finish() }
        checkFavorite()
        b.btnFav.setOnClickListener { toggleFavorite() }
        b.btnCalculate.setOnClickListener { calculate() }
        b.btnClear.setOnClickListener { clearInputs() }
        setupInputs()
        setupLearningSection()
    }

    private fun setupLearningSection() {
        val learning = CalculatorData.getLearningContent(calcId)
        if (learning != null) {
            b.learningCard.visibility = View.VISIBLE
            b.tvConceptText.text = learning.concept
            b.tvStepsText.text = learning.steps.mapIndexed { i, s -> "${i+1}. $s" }.joinToString("\n")
            b.tvExampleText.text = learning.example
            b.tvTipText.text = learning.tip
        } else {
            b.learningCard.visibility = View.GONE
        }
    }

    private fun setupInputs() {
        val inputs = getInputFields()
        b.inputContainer.removeAllViews()
        inputs.forEach { (label, hint) ->
            val tv = TextView(this).apply {
                text = label; textSize = 13f
                setTextColor(0xFF9E9E9E.toInt())
                setPadding(4, 16, 0, 6)
            }
            val et = EditText(this).apply {
                this.hint = hint
                inputType = android.text.InputType.TYPE_CLASS_NUMBER or
                        android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL or
                        android.text.InputType.TYPE_NUMBER_FLAG_SIGNED
                background = getDrawable(R.drawable.bg_input)
                setPadding(32, 20, 32, 20)
                textSize = 16f
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply { setMargins(0, 0, 0, 8) }
                tag = label
            }
            b.inputContainer.addView(tv)
            b.inputContainer.addView(et)
        }
    }

    private fun getInputFields(): List<Pair<String, String>> = when (calcId) {
        "ohms_law" -> listOf("Voltage V (leave blank to calculate)" to "Volts", "Current I (leave blank to calculate)" to "Amperes", "Resistance R (leave blank to calculate)" to "Ohms")
        "power" -> listOf("Voltage V" to "Volts", "Current I" to "Amperes")
        "series_r", "parallel_r" -> listOf("R1" to "Ohms", "R2" to "Ohms", "R3 (optional)" to "Ohms", "R4 (optional)" to "Ohms")
        "voltage_divider" -> listOf("Input Voltage Vin" to "Volts", "R1 (top)" to "Ohms", "R2 (bottom)" to "Ohms")
        "efficiency" -> listOf("Output Power Pout" to "Watts", "Input Power Pin" to "Watts")
        "frequency" -> listOf("Frequency f (leave blank to calculate)" to "Hz", "Period T (leave blank to calculate)" to "Seconds")
        "kirchhoff_v" -> listOf("V1" to "Volts", "V2" to "Volts", "V3" to "Volts", "Unknown (leave blank)" to "Volts")
        "wheatstone" -> listOf("R1" to "Ohms", "R2" to "Ohms", "R3" to "Ohms")
        "resistor_color" -> listOf("Band 1 Color" to "e.g. Brown", "Band 2 Color" to "e.g. Black", "Multiplier Band" to "e.g. Red", "Tolerance Band" to "e.g. Gold")
        "capacitor" -> listOf("Capacitance C" to "Farads (1μF = 0.000001)", "Voltage V" to "Volts")
        "rc_time" -> listOf("Resistance R" to "Ohms", "Capacitance C" to "Farads")
        "inductor" -> listOf("Inductance L" to "Henrys", "Current I" to "Amperes")
        "rl_time" -> listOf("Resistance R" to "Ohms", "Inductance L" to "Henrys")
        "led_resistor" -> listOf("Supply Voltage Vs" to "Volts", "LED Forward Voltage Vf" to "Volts (e.g. 2.0)", "LED Forward Current If" to "Amperes (e.g. 0.02)")
        "cap_series", "cap_parallel" -> listOf("C1" to "Farads", "C2" to "Farads", "C3 (optional)" to "Farads")
        "zener" -> listOf("Supply Voltage Vs" to "Volts", "Zener Voltage Vz" to "Volts", "Series Resistor Rs" to "Ohms", "Load Resistor RL" to "Ohms")
        "reactance_c" -> listOf("Frequency f" to "Hz", "Capacitance C" to "Farads")
        "reactance_l" -> listOf("Frequency f" to "Hz", "Inductance L" to "Henrys")
        "resonance" -> listOf("Inductance L" to "Henrys", "Capacitance C" to "Farads")
        "power_factor" -> listOf("Real Power P" to "Watts", "Apparent Power S" to "VA")
        "rms_peak" -> listOf("RMS Voltage" to "Volts")
        "three_phase" -> listOf("Line Voltage VL" to "Volts", "Line Current IL" to "Amperes", "Power Factor" to "0 to 1 (e.g. 0.85)")
        "db_power" -> listOf("Power P1" to "Watts", "Reference P2" to "Watts")
        "db_voltage" -> listOf("Voltage V1" to "Volts", "Reference V2" to "Volts")
        "impedance_rc" -> listOf("Resistance R" to "Ohms", "Frequency f" to "Hz", "Capacitance C" to "Farads")
        "impedance_rl" -> listOf("Resistance R" to "Ohms", "Frequency f" to "Hz", "Inductance L" to "Henrys")
        "transformer" -> listOf("Primary Voltage Vp" to "Volts", "Primary Turns Np" to "Turns", "Secondary Turns Ns" to "Turns", "Primary Current Ip (optional)" to "Amperes")
        "motor_fla" -> listOf("Motor Power" to "Watts", "Voltage" to "Volts", "Efficiency %" to "e.g. 90", "Power Factor" to "e.g. 0.85", "Phases (1 or 3)" to "1 or 3")
        "motor_slip" -> listOf("Synchronous Speed Ns" to "RPM", "Rotor Speed Nr" to "RPM")
        "sync_speed" -> listOf("Frequency f" to "Hz", "Number of Poles P" to "e.g. 4")
        "motor_torque" -> listOf("Motor Power" to "Watts", "Speed" to "RPM")
        "generator" -> listOf("Voltage" to "Volts", "Current" to "Amperes", "Power Factor" to "0 to 1", "Phases (1 or 3)" to "1 or 3")
        "battery_life" -> listOf("Battery Capacity" to "Ah", "Current Draw" to "Amperes")
        "electricity_bill" -> listOf("Appliance Power" to "Watts", "Daily Usage" to "Hours per day", "Days" to "e.g. 30", "Tariff Rate" to "₹ per kWh (e.g. 6.5)")
        "solar_sizing" -> listOf("Daily Energy Need" to "Wh", "Peak Sun Hours" to "Hours (India avg: 5)", "System Voltage" to "Volts (e.g. 12)")
        "energy_stored" -> listOf("Power" to "Watts", "Time" to "Hours")
        "power_factor_corr", "pf_correction" -> listOf("Real Power P" to "Watts", "Existing PF" to "e.g. 0.7", "Target PF" to "e.g. 0.95", "Voltage" to "Volts")
        "timer_555" -> listOf("R1" to "Ohms", "R2" to "Ohms", "Capacitance C" to "Farads")
        "voltage_drop" -> listOf("Current" to "Amperes", "Cable Length (one way)" to "Metres", "Cable Area" to "m² (2.5mm²=0.0000025)")
        "power_convert" -> listOf("Value" to "Number", "From Unit" to "W / kW / MW / HP / BTU", "To Unit" to "W / kW / MW / HP / BTU")
        "voltage_reg" -> listOf("Input Voltage Vin" to "Volts", "Output Voltage Vout" to "Volts", "Load Current IL" to "Amperes")
        "num_convert" -> listOf("Value" to "Number", "From Base" to "10=decimal, 2=binary, 8=octal, 16=hex")
        "temp_coeff" -> listOf("Resistance at ref temp R0" to "Ohms", "Temp coefficient α" to "e.g. 0.00393 for copper", "Reference temp T0" to "°C (usually 20)", "New temperature T1" to "°C")
        else -> listOf("Input" to "Value")
    }

    private fun getInputValues(): List<String?> {
        val inputs = mutableListOf<String?>()
        for (i in 0 until b.inputContainer.childCount) {
            val child = b.inputContainer.getChildAt(i)
            if (child is EditText) inputs.add(child.text.toString().trim().ifBlank { null })
        }
        return inputs
    }

    private fun calculate() {
        try {
            val inputs = getInputValues()
            val result = when (calcId) {
                "ohms_law" -> CalculatorEngine.ohmsLaw(inputs[0]?.toDoubleOrNull(), inputs[1]?.toDoubleOrNull(), inputs[2]?.toDoubleOrNull())
                "power" -> CalculatorEngine.power(inputs[0]?.toDouble(), inputs[1]?.toDouble())
                "series_r" -> CalculatorEngine.resistorSeries(inputs.filterNotNull().mapNotNull { it.toDoubleOrNull() })
                "parallel_r" -> CalculatorEngine.resistorParallel(inputs.filterNotNull().mapNotNull { it.toDoubleOrNull() })
                "voltage_divider" -> CalculatorEngine.voltageDivider(inputs[0]!!.toDouble(), inputs[1]!!.toDouble(), inputs[2]!!.toDouble())
                "efficiency" -> CalculatorEngine.efficiency(inputs[0]!!.toDouble(), inputs[1]!!.toDouble())
                "frequency" -> CalculatorEngine.frequencyPeriod(inputs[0]?.toDoubleOrNull(), inputs[1]?.toDoubleOrNull())
                "kirchhoff_v" -> CalculatorEngine.kirchhoffVoltage(inputs.filterNotNull().mapNotNull { it.toDoubleOrNull() })
                "wheatstone" -> CalculatorEngine.wheatstoneBridge(inputs[0]!!.toDouble(), inputs[1]!!.toDouble(), inputs[2]!!.toDouble())
                "resistor_color" -> CalculatorEngine.resistorColorCode(inputs.filterNotNull())
                "capacitor" -> CalculatorEngine.capacitor(inputs[0]!!.toDouble(), inputs[1]!!.toDouble())
                "rc_time" -> CalculatorEngine.rcTimeConstant(inputs[0]!!.toDouble(), inputs[1]!!.toDouble())
                "inductor" -> CalculatorEngine.inductorEnergy(inputs[0]!!.toDouble(), inputs[1]!!.toDouble())
                "rl_time" -> CalculatorEngine.rlTimeConstant(inputs[0]!!.toDouble(), inputs[1]!!.toDouble())
                "led_resistor" -> CalculatorEngine.ledResistor(inputs[0]!!.toDouble(), inputs[1]!!.toDouble(), inputs[2]!!.toDouble())
                "cap_series" -> CalculatorEngine.capacitorSeries(inputs.filterNotNull().mapNotNull { it.toDoubleOrNull() })
                "cap_parallel" -> CalculatorEngine.capacitorParallel(inputs.filterNotNull().mapNotNull { it.toDoubleOrNull() })
                "zener" -> CalculatorEngine.zenerRegulator(inputs[0]!!.toDouble(), inputs[1]!!.toDouble(), inputs[2]!!.toDouble(), inputs[3]!!.toDouble())
                "reactance_c" -> CalculatorEngine.reactanceCapacitive(inputs[0]!!.toDouble(), inputs[1]!!.toDouble())
                "reactance_l" -> CalculatorEngine.reactanceInductive(inputs[0]!!.toDouble(), inputs[1]!!.toDouble())
                "resonance" -> CalculatorEngine.resonantFrequency(inputs[0]!!.toDouble(), inputs[1]!!.toDouble())
                "power_factor" -> CalculatorEngine.powerFactor(inputs[0]!!.toDouble(), inputs[1]!!.toDouble())
                "rms_peak" -> CalculatorEngine.rmsTopeak(inputs[0]!!.toDouble())
                "three_phase" -> CalculatorEngine.threePhasePower(inputs[0]!!.toDouble(), inputs[1]!!.toDouble(), inputs[2]!!.toDouble())
                "db_power" -> CalculatorEngine.decibelPower(inputs[0]!!.toDouble(), inputs[1]!!.toDouble())
                "db_voltage" -> CalculatorEngine.decibelVoltage(inputs[0]!!.toDouble(), inputs[1]!!.toDouble())
                "impedance_rc" -> CalculatorEngine.impedanceRC(inputs[0]!!.toDouble(), inputs[1]!!.toDouble(), inputs[2]!!.toDouble())
                "impedance_rl" -> CalculatorEngine.impedanceRL(inputs[0]!!.toDouble(), inputs[1]!!.toDouble(), inputs[2]!!.toDouble())
                "transformer" -> CalculatorEngine.transformer(inputs[0]!!.toDouble(), null, inputs[1]!!.toDouble(), inputs[2]!!.toDouble(), inputs[3]?.toDoubleOrNull())
                "motor_fla" -> CalculatorEngine.motorFullLoadCurrent(inputs[0]!!.toDouble(), inputs[1]!!.toDouble(), inputs[2]!!.toDouble(), inputs[3]!!.toDouble(), inputs[4]!!.toInt())
                "motor_slip" -> CalculatorEngine.motorSlip(inputs[0]!!.toDouble(), inputs[1]!!.toDouble())
                "sync_speed" -> CalculatorEngine.synchronousSpeed(inputs[0]!!.toDouble(), inputs[1]!!.toInt())
                "motor_torque" -> CalculatorEngine.motorTorque(inputs[0]!!.toDouble(), inputs[1]!!.toDouble())
                "generator" -> CalculatorEngine.generatorOutput(inputs[0]!!.toDouble(), inputs[1]!!.toDouble(), inputs[2]!!.toDouble(), inputs[3]!!.toInt())
                "battery_life" -> CalculatorEngine.batteryLife(inputs[0]!!.toDouble(), inputs[1]!!.toDouble())
                "electricity_bill" -> CalculatorEngine.electricityBill(inputs[0]!!.toDouble(), inputs[1]!!.toDouble(), inputs[2]!!.toDouble(), inputs[3]!!.toDouble())
                "solar_sizing" -> CalculatorEngine.solarPanelSizing(inputs[0]!!.toDouble(), inputs[1]!!.toDouble(), inputs[2]!!.toDouble())
                "energy_stored" -> CalculatorEngine.energyStored(inputs[0]!!.toDouble(), inputs[1]!!.toDouble())
                "power_factor_corr", "pf_correction" -> CalculatorEngine.pfCorrection(inputs[0]!!.toDouble(), inputs[1]!!.toDouble(), inputs[2]!!.toDouble(), inputs[3]!!.toDouble())
                "timer_555" -> CalculatorEngine.timer555Astable(inputs[0]!!.toDouble(), inputs[1]!!.toDouble(), inputs[2]!!.toDouble())
                "voltage_drop" -> CalculatorEngine.voltageDrop(inputs[0]!!.toDouble(), inputs[1]!!.toDouble(), area = inputs[2]!!.toDouble())
                "power_convert" -> CalculatorEngine.convertPower(inputs[0]!!.toDouble(), inputs[1]!!, inputs[2]!!)
                "voltage_reg" -> CalculatorEngine.voltageRegulator(inputs[0]!!.toDouble(), inputs[1]!!.toDouble(), inputs[2]!!.toDouble())
                "num_convert" -> CalculatorEngine.numberConvert(inputs[0]!!, inputs[1]!!.toInt())
                "temp_coeff" -> CalculatorEngine.tempCoefficient(inputs[0]!!.toDouble(), inputs[1]!!.toDouble(), inputs[2]!!.toDouble(), inputs[3]!!.toDouble())
                else -> throw Exception("Unknown calculator: $calcId")
            }
            lastResult = result
            showResult(result)
            saveHistory(result, inputs.filterNotNull().joinToString(", "))
        } catch (e: Exception) {
            Toast.makeText(this, "⚠️ Check inputs: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun showResult(result: CalculatorEngine.Result) {
        b.resultCard.visibility = View.VISIBLE
        b.tvFormula.text = "📐 Formula: " + result.formula
        b.tvExplanation.text = "📖 " + result.explanation
        b.tvResults.text = result.values.entries.joinToString("\n") { "✅ " + it.key + " = " + it.value }
        b.btnShare.setOnClickListener { shareResult(result) }
    }

    private fun shareResult(result: CalculatorEngine.Result) {
        val time = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault()).format(Date())
        val lines = result.values.entries.joinToString("\n") { "  • " + it.key + " = " + it.value }
        val text = buildString {
            appendLine("⚡ *CircuitIQ - Electrical Calculator*")
            appendLine("━━━━━━━━━━━━━━━━━━━━━━")
            appendLine("📌 *Calculator:* $calcName")
            appendLine("📂 *Category:* $calcCategory")
            appendLine()
            appendLine("📊 *Results:*")
            appendLine(lines)
            appendLine()
            appendLine("📐 *Formula Used:*")
            appendLine("  " + result.formula)
            appendLine()
            appendLine("📖 *Explanation:*")
            appendLine("  " + result.explanation)
            appendLine()
            appendLine("━━━━━━━━━━━━━━━━━━━━━━")
            appendLine("🕐 Calculated on: $time")
            appendLine("👨‍💻 *Calculated using CircuitIQ*")
            appendLine("   by *Aditya Pal* | EEE Engineer")
            appendLine("   📲 Download: CircuitIQ on Play Store")
        }
        startActivity(Intent.createChooser(
            Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, text)
            }, "Share via"
        ))
    }

    private fun clearInputs() {
        for (i in 0 until b.inputContainer.childCount) {
            val child = b.inputContainer.getChildAt(i)
            if (child is EditText) child.text.clear()
        }
        b.resultCard.visibility = View.GONE
        lastResult = null
    }

    private fun saveHistory(result: CalculatorEngine.Result, inputs: String) {
        lifecycleScope.launch {
            Repository.saveHistory(HistoryEntry(
                calculatorName = calcName, category = calcCategory,
                inputs = inputs,
                result = result.values.entries.first().value,
                formula = result.formula
            ))
        }
    }

    private fun checkFavorite() {
        lifecycleScope.launch {
            isFav = Repository.isFavorite(calcId)
            b.btnFav.text = if (isFav) "★" else "☆"
        }
    }

    private fun toggleFavorite() {
        lifecycleScope.launch {
            if (isFav) {
                Repository.removeFavorite(calcId); isFav = false; b.btnFav.text = "☆"
                Toast.makeText(this@CalculatorActivity, "Removed from favourites", Toast.LENGTH_SHORT).show()
            } else {
                Repository.addFavorite(Favorite(calcId, calcName, calcCategory, "⚡"))
                isFav = true; b.btnFav.text = "★"
                Toast.makeText(this@CalculatorActivity, "Added to favourites ★", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
