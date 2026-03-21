package com.circuitiq.app.util

import android.graphics.Color
import com.circuitiq.app.data.model.Calculator
import com.circuitiq.app.data.model.CalculatorCategory

object CalculatorData {

    fun getCategories(): List<CalculatorCategory> = listOf(
        CalculatorCategory("basic","⚡ Basic Electrical","⚡",Color.parseColor("#7B2FBE"), listOf(
            Calculator("ohms_law","Ohm's Law","Basic Electrical","⚡","Calculate V, I, R using Ohm's Law"),
            Calculator("power","Power Calculator","Basic Electrical","💡","Calculate electrical power P = VI"),
            Calculator("series_r","Series Resistance","Basic Electrical","〰","Total resistance in series circuit"),
            Calculator("parallel_r","Parallel Resistance","Basic Electrical","⫛","Total resistance in parallel circuit"),
            Calculator("voltage_divider","Voltage Divider","Basic Electrical","➗","Output voltage of resistor divider"),
            Calculator("efficiency","Efficiency","Basic Electrical","📊","Calculate system efficiency"),
            Calculator("frequency","Frequency & Period","Basic Electrical","〜","Convert frequency and time period"),
        )),
        CalculatorCategory("components","🔴 Components","🔴",Color.parseColor("#E63946"), listOf(
            Calculator("resistor_color","Resistor Color Code","Components","🎨","Decode 4-band resistor color code"),
            Calculator("capacitor","Capacitor Energy","Components","⚡","Capacitor charge and energy storage"),
            Calculator("rc_time","RC Time Constant","Components","⏱","RC circuit charging time constant"),
            Calculator("inductor","Inductor Energy","Components","🔄","Energy stored in an inductor"),
            Calculator("rl_time","RL Time Constant","Components","⏱","RL circuit time constant"),
            Calculator("led_resistor","LED Resistor","Components","💡","Current limiting resistor for LED"),
            Calculator("cap_series","Capacitors in Series","Components","⫛","Total series capacitance"),
            Calculator("cap_parallel","Capacitors in Parallel","Components","〰","Total parallel capacitance"),
        )),
        CalculatorCategory("ac","🔄 AC Circuits","🔄",Color.parseColor("#2196F3"), listOf(
            Calculator("reactance_c","Capacitive Reactance","AC Circuits","〜","Xc = 1/(2πfC)"),
            Calculator("reactance_l","Inductive Reactance","AC Circuits","〜","Xl = 2πfL"),
            Calculator("resonance","Resonant Frequency","AC Circuits","📡","LC circuit resonant frequency"),
            Calculator("power_factor","Power Factor","AC Circuits","📐","Real vs apparent power"),
            Calculator("rms_peak","RMS ↔ Peak","AC Circuits","〜","Convert between RMS and peak voltage"),
            Calculator("three_phase","3-Phase Power","AC Circuits","⚡","Three phase power calculation"),
            Calculator("db_power","dB Power Ratio","AC Circuits","📊","Decibel power calculation"),
            Calculator("db_voltage","dB Voltage Ratio","AC Circuits","📊","Decibel voltage calculation"),
        )),
        CalculatorCategory("machines","🔁 Machines","🔁",Color.parseColor("#FF6B00"), listOf(
            Calculator("transformer","Transformer","Machines","🔁","Turns ratio, voltage, current"),
            Calculator("motor_fla","Motor Full Load Current","Machines","⚙","Motor FLA calculation"),
            Calculator("motor_slip","Motor Slip","Machines","🔄","Induction motor slip calculation"),
            Calculator("sync_speed","Synchronous Speed","Machines","⚙","Motor synchronous speed (RPM)"),
        )),
        CalculatorCategory("power_energy","🔋 Power & Energy","🔋",Color.parseColor("#4CAF50"), listOf(
            Calculator("battery_life","Battery Life","Power & Energy","🔋","How long battery will last"),
            Calculator("electricity_bill","Electricity Bill","Power & Energy","💰","Calculate your electricity cost"),
            Calculator("solar_sizing","Solar Panel Sizing","Power & Energy","☀","Size your solar power system"),
        )),
        CalculatorCategory("utilities","📐 Utilities","📐",Color.parseColor("#9C27B0"), listOf(
            Calculator("voltage_drop","Voltage Drop","Utilities","📉","Wire voltage drop calculation"),
            Calculator("power_convert","Power Unit Converter","Utilities","🔄","W, kW, HP, BTU/hr conversion"),
            Calculator("timer_555","555 Timer (Astable)","Utilities","⏱","555 timer frequency calculator"),
        )),
    )
}
