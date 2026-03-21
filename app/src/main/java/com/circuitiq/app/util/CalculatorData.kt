package com.circuitiq.app.util

import android.graphics.Color
import com.circuitiq.app.data.model.Calculator
import com.circuitiq.app.data.model.CalculatorCategory

object CalculatorData {

    fun getCategories(): List<CalculatorCategory> = listOf(

        CalculatorCategory("basic","⚡ Basic Electrical","⚡",Color.parseColor("#7B2FBE"), listOf(
            Calculator("ohms_law","Ohm's Law","Basic Electrical","⚡","Calculate V, I, R — enter any two, find the third"),
            Calculator("power","Power Calculator","Basic Electrical","💡","P = VI, V²/R, I²R — all power formulas"),
            Calculator("series_r","Series Resistance","Basic Electrical","〰","Total resistance of resistors in series"),
            Calculator("parallel_r","Parallel Resistance","Basic Electrical","⫛","Total resistance of resistors in parallel"),
            Calculator("voltage_divider","Voltage Divider","Basic Electrical","➗","Output voltage from resistor voltage divider"),
            Calculator("efficiency","System Efficiency","Basic Electrical","📊","Calculate efficiency and power losses"),
            Calculator("frequency","Frequency & Period","Basic Electrical","〜","Convert between frequency (Hz) and time period (s)"),
            Calculator("kirchhoff_v","Kirchhoff Voltage Law","Basic Electrical","🔁","Sum of voltages around a closed loop = 0"),
            Calculator("wheatstone","Wheatstone Bridge","Basic Electrical","🌉","Balance condition and unknown resistance"),
        )),

        CalculatorCategory("components","🔴 Components","🔴",Color.parseColor("#E63946"), listOf(
            Calculator("resistor_color","Resistor Color Code","Components","🎨","Decode 4-band resistor color code to value"),
            Calculator("capacitor","Capacitor Energy","Components","⚡","Charge Q=CV and energy E=½CV² stored in capacitor"),
            Calculator("rc_time","RC Time Constant","Components","⏱","τ=RC — charging time constant of RC circuit"),
            Calculator("inductor","Inductor Energy","Components","🔄","Energy E=½LI² stored in magnetic field"),
            Calculator("rl_time","RL Time Constant","Components","⏱","τ=L/R — time constant of RL circuit"),
            Calculator("led_resistor","LED Resistor","Components","💡","Current limiting resistor R=(Vs-Vf)/If"),
            Calculator("cap_series","Capacitors in Series","Components","⫛","Total capacitance of series capacitors"),
            Calculator("cap_parallel","Capacitors in Parallel","Components","〰","Total capacitance of parallel capacitors"),
            Calculator("zener","Zener Diode Regulator","Components","🔽","Zener current and power dissipation"),
        )),

        CalculatorCategory("ac","🔄 AC Circuits","🔄",Color.parseColor("#2196F3"), listOf(
            Calculator("reactance_c","Capacitive Reactance","AC Circuits","〜","Xc = 1/(2πfC) — opposition to AC by capacitor"),
            Calculator("reactance_l","Inductive Reactance","AC Circuits","〜","Xl = 2πfL — opposition to AC by inductor"),
            Calculator("resonance","Resonant Frequency","AC Circuits","📡","f = 1/(2π√LC) — LC circuit resonance"),
            Calculator("power_factor","Power Factor","AC Circuits","📐","PF = P/S = cos(φ) — real vs apparent power"),
            Calculator("rms_peak","RMS ↔ Peak","AC Circuits","〜","Vpeak = Vrms×√2 — AC voltage conversion"),
            Calculator("three_phase","3-Phase Power","AC Circuits","⚡","P = √3 × VL × IL × cos(φ)"),
            Calculator("db_power","dB Power Ratio","AC Circuits","📊","dB = 10×log10(P1/P2)"),
            Calculator("db_voltage","dB Voltage Ratio","AC Circuits","📊","dB = 20×log10(V1/V2)"),
            Calculator("impedance_rc","RC Impedance","AC Circuits","〜","Z = √(R² + Xc²) total impedance"),
            Calculator("impedance_rl","RL Impedance","AC Circuits","〜","Z = √(R² + Xl²) total impedance"),
        )),

        CalculatorCategory("machines","🔁 Machines","🔁",Color.parseColor("#FF6B00"), listOf(
            Calculator("transformer","Transformer","Machines","🔁","Turns ratio, voltage & current transformation"),
            Calculator("motor_fla","Motor Full Load Current","Machines","⚙","FLA for 1-phase and 3-phase motors"),
            Calculator("motor_slip","Motor Slip","Machines","🔄","Slip = (Ns-Nr)/Ns × 100%"),
            Calculator("sync_speed","Synchronous Speed","Machines","⚙","Ns = 120f/P — motor synchronous RPM"),
            Calculator("motor_torque","Motor Torque","Machines","🔧","T = P×9550/N — shaft torque in Nm"),
            Calculator("generator","Generator Output","Machines","⚡","Generator voltage and power output"),
        )),

        CalculatorCategory("power_energy","🔋 Power & Energy","🔋",Color.parseColor("#4CAF50"), listOf(
            Calculator("battery_life","Battery Life","Power & Energy","🔋","T = C/I — how long battery lasts"),
            Calculator("electricity_bill","Electricity Bill","Power & Energy","💰","Units = P×H×D/1000 — monthly cost in ₹"),
            Calculator("solar_sizing","Solar Panel Sizing","Power & Energy","☀","Size solar system for daily energy needs"),
            Calculator("energy_stored","Energy Stored","Power & Energy","⚡","E = Pt — energy in Wh and kWh"),
            Calculator("power_factor_corr","PF Correction","Power & Energy","📐","Capacitor size to correct power factor"),
        )),

        CalculatorCategory("electronics","📡 Electronics","📡",Color.parseColor("#9C27B0"), listOf(
            Calculator("timer_555","555 Timer (Astable)","Electronics","⏱","Frequency, period, duty cycle of 555 timer"),
            Calculator("voltage_drop","Voltage Drop","Electronics","📉","Vdrop = I×ρ×2L/A — wire voltage drop"),
            Calculator("power_convert","Power Unit Converter","Electronics","🔄","W ↔ kW ↔ HP ↔ BTU/hr conversion"),
            Calculator("db_power","dB Power","Electronics","📊","Power ratio in decibels"),
            Calculator("voltage_reg","Voltage Regulator","Electronics","🔽","78xx series output current and power"),
        )),

        CalculatorCategory("utilities","📐 Utilities","📐",Color.parseColor("#607D8B"), listOf(
            Calculator("wire_size","Wire / Cable Sizing","Utilities","📏","AWG and mm² current capacity guide"),
            Calculator("pf_correction","PF Capacitor","Utilities","🔋","kVAR needed to correct power factor"),
            Calculator("num_convert","Number System","Utilities","🔢","Binary ↔ Octal ↔ Decimal ↔ Hex"),
            Calculator("temp_coeff","Temperature Coefficient","Utilities","🌡","Resistance change with temperature"),
        )),
    )

    // ── Learning content for each calculator ─────────────────────────────────
    fun getLearningContent(calcId: String): LearningContent? = learningMap[calcId]

    data class LearningContent(
        val concept: String,
        val steps: List<String>,
        val example: String,
        val tip: String
    )

    private val learningMap = mapOf(
        "ohms_law" to LearningContent(
            concept = "Ohm's Law states that the current through a conductor is directly proportional to the voltage and inversely proportional to the resistance.",
            steps = listOf("Identify the two known quantities (V, I, or R)","Apply the formula: V=IR, I=V/R, or R=V/I","Calculate power using P=VI"),
            example = "A 12V battery connected to a 4Ω resistor: I = 12/4 = 3A, P = 12×3 = 36W",
            tip = "💡 Remember: More resistance = less current for same voltage. More voltage = more current for same resistance."
        ),
        "power" to LearningContent(
            concept = "Electrical power is the rate of energy transfer. It can be calculated using voltage, current, and resistance.",
            steps = listOf("Use P=VI when voltage and current are known","Use P=V²/R when voltage and resistance are known","Use P=I²R when current and resistance are known"),
            example = "A 230V appliance drawing 5A: P = 230×5 = 1150W = 1.15 kW",
            tip = "💡 Watt (W) = Joules per second. A 1kW appliance uses 1 unit of electricity per hour."
        ),
        "resistor_color" to LearningContent(
            concept = "Resistor values are encoded using colored bands. Each color represents a digit (0-9) or multiplier.",
            steps = listOf("Hold resistor with gold/silver band on RIGHT","Read Band 1 (first digit), Band 2 (second digit)","Band 3 is the multiplier (power of 10)","Band 4 is tolerance (Gold=±5%, Silver=±10%)"),
            example = "Brown-Black-Red-Gold = 1,0 × 100 = 1000Ω = 1kΩ ±5%",
            tip = "💡 Mnemonic: BB ROY Great Britain Very Good Wife = Black Brown Red Orange Yellow Green Blue Violet Gray White"
        ),
        "transformer" to LearningContent(
            concept = "A transformer transfers electrical energy between circuits through electromagnetic induction, changing voltage and current levels.",
            steps = listOf("Turns ratio: Np/Ns = Vp/Vs = Is/Ip","Step-up: Ns > Np increases voltage","Step-down: Ns < Np decreases voltage","Power is conserved: Pp ≈ Ps (ideal)"),
            example = "230V primary, 50 turns. For 12V output: Ns = 50×12/230 ≈ 3 turns",
            tip = "💡 Transformers only work with AC, not DC. Core losses cause real transformers to be 95-99% efficient."
        ),
        "three_phase" to LearningContent(
            concept = "Three-phase power uses three AC voltages 120° apart. It is more efficient for power transmission and industrial motors.",
            steps = listOf("Line voltage (VL) is between any two phases","Phase voltage (Vph) = VL/√3 in star connection","P = √3 × VL × IL × cos(φ)","Total apparent power S = √3 × VL × IL"),
            example = "415V, 10A, PF=0.8: P = 1.732×415×10×0.8 = 5750W ≈ 5.75kW",
            tip = "💡 Three-phase is preferred for motors above 1HP because it provides constant torque and is more efficient."
        ),
        "motor_fla" to LearningContent(
            concept = "Full Load Amperes (FLA) is the maximum current a motor draws at rated load, voltage, and frequency.",
            steps = listOf("1-phase: I = P/(V×η×PF)","3-phase: I = P/(√3×VL×η×PF)","η is efficiency (typically 85-95%)","PF is typically 0.8-0.9 for motors"),
            example = "5kW, 415V, 3-phase, η=90%, PF=0.85: I = 5000/(1.732×415×0.9×0.85) = 9.1A",
            tip = "💡 Always size circuit breakers at 125% of FLA for motor protection per NEC/IS standards."
        ),
        "electricity_bill" to LearningContent(
            concept = "Electricity consumption is measured in kilowatt-hours (kWh), also called units. 1 unit = 1kW used for 1 hour.",
            steps = listOf("Units = Power(W) × Hours × Days ÷ 1000","Cost = Units × Tariff rate per unit","Monthly bill = Cost + fixed charges + taxes","Check your meter for actual consumption"),
            example = "1000W AC, 8hrs/day, 30 days, ₹6/unit: Units=240, Cost=₹1440",
            tip = "💡 An AC unit typically consumes 1-1.5 units/hour. LED bulb vs incandescent saves 80% energy."
        ),
        "rc_time" to LearningContent(
            concept = "The RC time constant (τ) determines how fast a capacitor charges/discharges through a resistor.",
            steps = listOf("τ = R × C (in seconds)","Capacitor reaches 63.2% charge in 1τ","Fully charged (99%) in 5τ","Discharge follows same curve"),
            example = "R=10kΩ, C=100μF: τ = 10000×0.0001 = 1 second. Full charge in 5 seconds.",
            tip = "💡 RC circuits are used in filters, timers, and signal smoothing. Larger RC = slower response."
        ),
        "power_factor" to LearningContent(
            concept = "Power Factor (PF) is the ratio of real power (doing work) to apparent power (total power drawn). PF=1 is ideal.",
            steps = listOf("PF = Real Power (W) / Apparent Power (VA)","PF = cos(φ) where φ is phase angle","Reactive power Q = S×sin(φ) in VAR","Low PF means wasted energy in the system"),
            example = "Motor draws 5kVA but only does 4kW of work: PF = 4000/5000 = 0.8",
            tip = "💡 Power companies penalize industrial users with PF < 0.85. Capacitors are used to improve PF."
        ),
        "solar_sizing" to LearningContent(
            concept = "Solar system sizing determines the panel wattage and battery capacity needed for your daily energy consumption.",
            steps = listOf("Calculate total daily energy (Wh) needed","Divide by peak sun hours in your location","Add 15-25% for system losses","Size battery for 1-2 days autonomy"),
            example = "2000Wh/day, 5 sun hours, 85% efficiency: Panel = 2000/(5×0.85) = 470W",
            tip = "💡 India gets 4-7 peak sun hours/day. Rajasthan gets most, northeastern states get least."
        ),

        "series_r" to LearningContent(
            concept = "In a series circuit, resistors are connected end-to-end. The same current flows through all resistors, and total resistance is the sum of all individual resistances.",
            steps = listOf("List all resistance values","Add them directly: R_total = R1 + R2 + R3...","Current is same through all resistors","Voltage divides proportionally across each resistor"),
            example = "R1=100Ω, R2=220Ω, R3=330Ω in series: R_total = 650Ω",
            tip = "💡 Series resistors always give a total resistance GREATER than the largest individual resistor."
        ),
        "parallel_r" to LearningContent(
            concept = "In a parallel circuit, resistors share the same voltage. The total resistance is always less than the smallest individual resistor.",
            steps = listOf("Take reciprocal of each: 1/R1, 1/R2, 1/R3...","Add the reciprocals: 1/R_total = 1/R1 + 1/R2","Take reciprocal of sum for R_total","For two resistors: R = R1×R2/(R1+R2)"),
            example = "R1=100Ω, R2=100Ω in parallel: R = 100×100/(100+100) = 50Ω",
            tip = "💡 Two equal resistors in parallel always give half the resistance. n equal resistors give R/n."
        ),
        "voltage_divider" to LearningContent(
            concept = "A voltage divider uses two series resistors to produce an output voltage that is a fraction of the input voltage.",
            steps = listOf("Identify R1 (top) and R2 (bottom)","Vout = Vin × R2/(R1+R2)","Output is across R2","Load affects the output — use high-value load resistors"),
            example = "Vin=12V, R1=8kΩ, R2=4kΩ: Vout = 12×4/(8+4) = 4V",
            tip = "💡 Voltage dividers are not suitable for driving low-resistance loads as it changes the ratio."
        ),
        "motor_torque" to LearningContent(
            concept = "Torque is the rotational force produced by a motor. It is inversely related to speed — higher speed means lower torque for the same power.",
            steps = listOf("T = P × 9550 / N (in Nm)","Or T = P × 60 / (2π × N)","Power = Torque × Angular velocity","Check motor nameplate for rated torque"),
            example = "1.5kW motor at 1440 RPM: T = 1500×9.55/1440 = 9.95 Nm",
            tip = "💡 A motor's starting torque is 1.5-2.5x its full-load torque. This is why motors sometimes trip on heavy starts."
        ),
        "timer_555" to LearningContent(
            concept = "The 555 timer IC in astable mode continuously oscillates between high and low states, generating a square wave output without any external trigger.",
            steps = listOf("Charge time (high): t_H = 0.693×(R1+R2)×C","Discharge time (low): t_L = 0.693×R2×C","Frequency: f = 1.44/((R1+2×R2)×C)","Duty cycle = (R1+R2)/(R1+2×R2)×100%"),
            example = "R1=1kΩ, R2=10kΩ, C=10μF: f = 1.44/(21000×0.00001) = 6.86 Hz",
            tip = "💡 To get 50% duty cycle, make R1 very small compared to R2, or use a diode across R2."
        ),
        "resonance" to LearningContent(
            concept = "At resonant frequency, inductive reactance equals capacitive reactance (XL=XC), and they cancel each other out. The circuit draws minimum current (series) or maximum current (parallel).",
            steps = listOf("f = 1/(2π√LC)","At resonance: XL = XC","Series resonance: minimum impedance","Parallel resonance: maximum impedance"),
            example = "L=10mH, C=100μF: f = 1/(2π√(0.01×0.0001)) = 159 Hz",
            tip = "💡 Radio tuning circuits use resonance — you change C to tune to different frequencies (stations)!"
        ),
        "efficiency" to LearningContent(
            concept = "Efficiency is the ratio of useful output power to total input power. No machine is 100% efficient — losses occur as heat, friction, or other forms of waste energy.",
            steps = listOf("η = (Pout/Pin) × 100%","Losses = Pin - Pout","% Loss = 100% - η","Typical: Transformer 95-99%, Motor 85-95%"),
            example = "Motor input 1000W, output 850W: η = 850/1000 × 100 = 85%",
            tip = "💡 Even a 1% improvement in large machine efficiency can save millions of rupees annually in industrial plants."
        ),
        "db_power" to LearningContent(
            concept = "Decibel (dB) is a logarithmic unit used to express power ratios. Logarithmic scale is used because human perception (hearing, vision) is also logarithmic.",
            steps = listOf("dB = 10 × log10(P1/P2)","Positive dB = amplification","Negative dB = attenuation","+3dB ≈ double the power, -3dB ≈ half the power"),
            example = "Amplifier input 1mW, output 100mW: dB = 10×log10(100) = 20 dB gain",
            tip = "💡 Every 10dB increase means 10x more power. Every 3dB means 2x more power. Easy to remember!"
        ),
    )
}
