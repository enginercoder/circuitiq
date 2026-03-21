package com.circuitiq.app.util

import kotlin.math.*

object CalculatorEngine {

    data class Result(
        val values: Map<String, String>,
        val formula: String,
        val explanation: String
    )

    // ── 1. OHM'S LAW ─────────────────────────────────────────────────────────
    fun ohmsLaw(v: Double? = null, i: Double? = null, r: Double? = null): Result {
        return when {
            v == null && i != null && r != null -> Result(
                mapOf("Voltage (V)" to "${fmt(i * r)} V", "Power (W)" to "${fmt(i * i * r)} W"),
                "V = I × R", "Voltage = Current × Resistance. Power = I² × R"
            )
            i == null && v != null && r != null -> Result(
                mapOf("Current (A)" to "${fmt(v / r)} A", "Power (W)" to "${fmt(v * v / r)} W"),
                "I = V / R", "Current = Voltage ÷ Resistance. Power = V² ÷ R"
            )
            r == null && v != null && i != null -> Result(
                mapOf("Resistance (Ω)" to "${fmt(v / i)} Ω", "Power (W)" to "${fmt(v * i)} W"),
                "R = V / I", "Resistance = Voltage ÷ Current. Power = V × I"
            )
            else -> throw IllegalArgumentException("Provide exactly 2 values")
        }
    }

    // ── 2. POWER CALCULATOR ───────────────────────────────────────────────────
    fun power(v: Double? = null, i: Double? = null, r: Double? = null, p: Double? = null): Result {
        return when {
            v != null && i != null -> Result(mapOf("Power (W)" to "${fmt(v*i)} W","Energy/hr (Wh)" to "${fmt(v*i)} Wh"), "P = V × I", "Power equals Voltage multiplied by Current")
            v != null && r != null -> Result(mapOf("Power (W)" to "${fmt(v*v/r)} W"), "P = V² / R", "Power equals Voltage squared divided by Resistance")
            i != null && r != null -> Result(mapOf("Power (W)" to "${fmt(i*i*r)} W"), "P = I² × R", "Power equals Current squared multiplied by Resistance")
            p != null && v != null -> Result(mapOf("Current (A)" to "${fmt(p/v)} A"), "I = P / V", "Current equals Power divided by Voltage")
            p != null && i != null -> Result(mapOf("Voltage (V)" to "${fmt(p/i)} V"), "V = P / I", "Voltage equals Power divided by Current")
            else -> throw IllegalArgumentException("Provide valid inputs")
        }
    }

    // ── 3. RESISTOR SERIES / PARALLEL ─────────────────────────────────────────
    fun resistorSeries(values: List<Double>): Result {
        val total = values.sum()
        return Result(mapOf("Total Resistance" to "${fmt(total)} Ω"), "R_total = R1 + R2 + ... + Rn", "Series resistances simply add up")
    }

    fun resistorParallel(values: List<Double>): Result {
        val total = 1.0 / values.sumOf { 1.0 / it }
        return Result(mapOf("Total Resistance" to "${fmt(total)} Ω"), "1/R_total = 1/R1 + 1/R2 + ...", "Parallel resistance reciprocal sum formula")
    }

    // ── 4. RESISTOR COLOR CODE ─────────────────────────────────────────────────
    fun resistorColorCode(bands: List<String>): Result {
        val colorValues = mapOf("Black" to 0,"Brown" to 1,"Red" to 2,"Orange" to 3,"Yellow" to 4,"Green" to 5,"Blue" to 6,"Violet" to 7,"Gray" to 8,"White" to 9)
        val multipliers = mapOf("Black" to 1.0,"Brown" to 10.0,"Red" to 100.0,"Orange" to 1000.0,"Yellow" to 10000.0,"Green" to 100000.0,"Blue" to 1000000.0,"Gold" to 0.1,"Silver" to 0.01)
        val tolerances = mapOf("Brown" to "±1%","Red" to "±2%","Gold" to "±5%","Silver" to "±10%","None" to "±20%")
        val d1 = colorValues[bands[0]] ?: 0
        val d2 = colorValues[bands[1]] ?: 0
        val mult = multipliers[bands[2]] ?: 1.0
        val tol = tolerances[bands.getOrNull(3) ?: "None"] ?: "±20%"
        val resistance = (d1 * 10 + d2) * mult
        return Result(mapOf("Resistance" to "${fmtResistor(resistance)} Ω","Tolerance" to tol,"Min Value" to "${fmtResistor(resistance * (1 - tol.replace("±","").replace("%","").toDouble()/100))} Ω","Max Value" to "${fmtResistor(resistance * (1 + tol.replace("±","").replace("%","").toDouble()/100))} Ω"), "Value = (D1×10 + D2) × Multiplier", "Read color bands left to right: Digit1, Digit2, Multiplier, Tolerance")
    }

    // ── 5. CAPACITOR CHARGE/ENERGY ────────────────────────────────────────────
    fun capacitor(c: Double, v: Double): Result {
        val charge = c * v
        val energy = 0.5 * c * v * v
        return Result(mapOf("Charge (C)" to "${fmt(charge)} C","Energy (J)" to "${fmt(energy)} J","Energy (mJ)" to "${fmt(energy*1000)} mJ"), "Q = C×V, E = ½CV²", "Charge = Capacitance × Voltage. Energy = half × C × V²")
    }

    fun rcTimeConstant(r: Double, c: Double): Result {
        val tau = r * c
        val chargeTime = 5 * tau
        return Result(mapOf("Time Constant τ" to "${fmt(tau)} s","63.2% charge at" to "${fmt(tau)} s","Full charge (~99%)" to "${fmt(chargeTime)} s"), "τ = R × C", "Time constant is the time to charge to 63.2% of supply voltage")
    }

    // ── 6. INDUCTOR ───────────────────────────────────────────────────────────
    fun inductorEnergy(l: Double, i: Double): Result {
        val energy = 0.5 * l * i * i
        return Result(mapOf("Energy (J)" to "${fmt(energy)} J"), "E = ½LI²", "Energy stored in inductor = half × Inductance × Current²")
    }

    fun rlTimeConstant(r: Double, l: Double): Result {
        val tau = l / r
        return Result(mapOf("Time Constant τ" to "${fmt(tau)} s","63.2% current at" to "${fmt(tau)} s"), "τ = L / R", "RL time constant = Inductance ÷ Resistance")
    }

    // ── 7. AC CIRCUITS ────────────────────────────────────────────────────────
    fun reactanceCapacitive(f: Double, c: Double): Result {
        val xc = 1.0 / (2 * PI * f * c)
        return Result(mapOf("Capacitive Reactance" to "${fmt(xc)} Ω"), "Xc = 1 / (2πfC)", "Capacitive reactance decreases with frequency")
    }

    fun reactanceInductive(f: Double, l: Double): Result {
        val xl = 2 * PI * f * l
        return Result(mapOf("Inductive Reactance" to "${fmt(xl)} Ω"), "Xl = 2πfL", "Inductive reactance increases with frequency")
    }

    fun resonantFrequency(l: Double, c: Double): Result {
        val f = 1.0 / (2 * PI * sqrt(l * c))
        return Result(mapOf("Resonant Frequency" to "${fmt(f)} Hz","Angular Frequency ω" to "${fmt(2*PI*f)} rad/s"), "f = 1 / (2π√LC)", "At resonance, inductive and capacitive reactance are equal")
    }

    fun powerFactor(realPower: Double, apparentPower: Double): Result {
        val pf = realPower / apparentPower
        val angle = Math.toDegrees(acos(pf))
        val reactivePower = sqrt(apparentPower*apparentPower - realPower*realPower)
        return Result(mapOf("Power Factor" to fmt(pf),"Phase Angle" to "${fmt(angle)}°","Reactive Power (VAR)" to "${fmt(reactivePower)} VAR","Apparent Power (VA)" to "${fmt(apparentPower)} VA"), "PF = P / S = cos(φ)", "Power Factor = Real Power ÷ Apparent Power")
    }

    fun rmsTopeak(rms: Double): Result {
        val peak = rms * sqrt(2.0)
        val peakToPeak = 2 * peak
        return Result(mapOf("Peak Voltage" to "${fmt(peak)} V","Peak-to-Peak" to "${fmt(peakToPeak)} V","Average" to "${fmt(peak * 2/PI)} V"), "Vpeak = Vrms × √2", "RMS is the DC equivalent of AC voltage")
    }

    fun threePhasePower(v: Double, i: Double, pf: Double, isLineLine: Boolean = true): Result {
        val p = sqrt(3.0) * v * i * pf
        val q = sqrt(3.0) * v * i * sin(acos(pf))
        val s = sqrt(3.0) * v * i
        return Result(mapOf("Active Power P" to "${fmt(p)} W (${fmt(p/1000)} kW)","Reactive Power Q" to "${fmt(q)} VAR","Apparent Power S" to "${fmt(s)} VA"), "P = √3 × VL × IL × cos(φ)", "Three phase power formula using line voltage and line current")
    }

    // ── 8. TRANSFORMER ────────────────────────────────────────────────────────
    fun transformer(vp: Double, vs: Double? = null, np: Double, ns: Double? = null, ip: Double? = null): Result {
        val ratio = np / (ns ?: (np * (vs ?: 0.0) / vp))
        val vsCalc = vs ?: (vp * (ns ?: 0.0) / np)
        val isCalc = ip?.let { it * np / (ns ?: 1.0) }
        val map = mutableMapOf("Turns Ratio" to "${fmt(ratio)}:1","Secondary Voltage" to "${fmt(vsCalc)} V")
        isCalc?.let { map["Secondary Current"] = "${fmt(it)} A" }
        return Result(map, "Vs/Vp = Ns/Np, Ip/Is = Ns/Np", "Transformer voltage ratio equals turns ratio. Current ratio is inverse")
    }

    // ── 9. MOTOR ──────────────────────────────────────────────────────────────
    fun motorFullLoadCurrent(power: Double, voltage: Double, efficiency: Double, pf: Double, phases: Int): Result {
        val eff = efficiency / 100.0
        val fla = if (phases == 1) power / (voltage * eff * pf)
                  else power / (sqrt(3.0) * voltage * eff * pf)
        return Result(mapOf("Full Load Current" to "${fmt(fla)} A","Input Power" to "${fmt(power/eff)} W"), if(phases==1) "I = P / (V × η × PF)" else "I = P / (√3 × VL × η × PF)", "Motor full load amps considering efficiency and power factor")
    }

    fun motorSlip(syncSpeed: Double, actualSpeed: Double): Result {
        val slip = (syncSpeed - actualSpeed) / syncSpeed * 100
        return Result(mapOf("Slip" to "${fmt(slip)} %","Slip Speed" to "${fmt(syncSpeed - actualSpeed)} RPM"), "Slip = (Ns - Nr) / Ns × 100%", "Slip is difference between synchronous and rotor speed as percentage")
    }

    fun synchronousSpeed(frequency: Double, poles: Int): Result {
        val ns = 120.0 * frequency / poles
        return Result(mapOf("Synchronous Speed" to "${fmt(ns)} RPM"), "Ns = 120f / P", "Synchronous speed depends on supply frequency and number of poles")
    }

    // ── 10. BATTERY ───────────────────────────────────────────────────────────
    fun batteryLife(capacity: Double, current: Double): Result {
        val hours = capacity / current
        val minutes = (hours % 1) * 60
        return Result(mapOf("Battery Life" to "${fmt(hours)} hours (${hours.toInt()}h ${minutes.toInt()}m)","Energy" to "${fmt(capacity * 3.6)} kJ"), "T = C / I", "Battery life = Capacity (Ah) ÷ Current draw (A)")
    }

    fun electricityBill(power: Double, hours: Double, days: Double, rate: Double): Result {
        val units = power * hours * days / 1000.0
        val cost = units * rate
        return Result(mapOf("Units (kWh)" to fmt(units),"Daily Cost" to "₹${fmt(cost/days)}","Monthly Cost (30d)" to "₹${fmt(cost)}","Yearly Cost" to "₹${fmt(cost*12)}"), "Units = P(W) × H × D / 1000", "Electricity cost = Power consumption in kWh × Tariff rate")
    }

    // ── 11. VOLTAGE DIVIDER ───────────────────────────────────────────────────
    fun voltageDivider(vin: Double, r1: Double, r2: Double): Result {
        val vout = vin * r2 / (r1 + r2)
        val current = vin / (r1 + r2)
        return Result(mapOf("Output Voltage" to "${fmt(vout)} V","Current" to "${fmt(current)} A","Power in R1" to "${fmt(current*current*r1)} W","Power in R2" to "${fmt(current*current*r2)} W"), "Vout = Vin × R2 / (R1 + R2)", "Voltage divider divides input voltage proportional to resistance ratio")
    }

    // ── 12. LED RESISTOR ──────────────────────────────────────────────────────
    fun ledResistor(vSupply: Double, vForward: Double, iForward: Double): Result {
        val r = (vSupply - vForward) / iForward
        val power = (vSupply - vForward) * iForward
        return Result(mapOf("Resistor Value" to "${fmt(r)} Ω","Nearest Standard" to "${nearestE12(r)} Ω","Power Dissipation" to "${fmt(power*1000)} mW"), "R = (Vs - Vf) / If", "Current limiting resistor protects LED from excess current")
    }

    // ── 13. WIRE SIZING ───────────────────────────────────────────────────────
    fun voltageDrop(current: Double, length: Double, resistivity: Double = 1.68e-8, area: Double): Result {
        val resistance = resistivity * (2 * length) / area
        val drop = current * resistance
        val dropPct = drop / (current * resistance + drop) * 100
        return Result(mapOf("Voltage Drop" to "${fmt(drop)} V","Drop Percentage" to "${fmt(dropPct)} %","Wire Resistance" to "${fmt(resistance)} Ω"), "Vdrop = I × R = I × ρ × 2L / A", "Voltage drop = Current × Wire resistance. Factor of 2 for return path")
    }

    // ── 14. dB CALCULATOR ─────────────────────────────────────────────────────
    fun decibelPower(p1: Double, p2: Double): Result {
        val db = 10 * log10(p1 / p2)
        return Result(mapOf("Power Ratio (dB)" to "${fmt(db)} dB"), "dB = 10 × log10(P1/P2)", "Decibel power ratio uses factor of 10")
    }

    fun decibelVoltage(v1: Double, v2: Double): Result {
        val db = 20 * log10(v1 / v2)
        return Result(mapOf("Voltage Ratio (dB)" to "${fmt(db)} dB"), "dB = 20 × log10(V1/V2)", "Decibel voltage ratio uses factor of 20")
    }

    // ── 15. FREQUENCY & PERIOD ────────────────────────────────────────────────
    fun frequencyPeriod(f: Double? = null, t: Double? = null): Result {
        return if (f != null) {
            Result(mapOf("Period" to "${fmt(1/f)} s","Angular Frequency ω" to "${fmt(2*PI*f)} rad/s"), "T = 1/f", "Period is the inverse of frequency")
        } else {
            Result(mapOf("Frequency" to "${fmt(1/t!!)} Hz","Angular Frequency ω" to "${fmt(2*PI/t)} rad/s"), "f = 1/T", "Frequency is the inverse of period")
        }
    }

    // ── 16. UNIT CONVERTER ────────────────────────────────────────────────────
    fun convertPower(value: Double, from: String, to: String): Result {
        val toWatts = mapOf("W" to 1.0, "kW" to 1000.0, "MW" to 1e6, "HP" to 745.7, "BTU/hr" to 0.2931)
        val watts = value * (toWatts[from] ?: 1.0)
        val result = watts / (toWatts[to] ?: 1.0)
        return Result(mapOf("$value $from = " to "${fmt(result)} $to"), "$to = $from × ${toWatts[from]} / ${toWatts[to]}", "Power unit conversion via watts as base unit")
    }

    // ── 17. EFFICIENCY ────────────────────────────────────────────────────────
    fun efficiency(pOut: Double, pIn: Double): Result {
        val eff = pOut / pIn * 100
        val losses = pIn - pOut
        return Result(mapOf("Efficiency" to "${fmt(eff)} %","Power Losses" to "${fmt(losses)} W","Loss Percentage" to "${fmt(100-eff)} %"), "η = Pout / Pin × 100%", "Efficiency = Output power ÷ Input power × 100")
    }

    // ── 18. CAPACITOR IN SERIES/PARALLEL ─────────────────────────────────────
    fun capacitorSeries(values: List<Double>): Result {
        val total = 1.0 / values.sumOf { 1.0 / it }
        return Result(mapOf("Total Capacitance" to "${fmt(total)} F (${fmt(total*1e6)} μF)"), "1/C_total = 1/C1 + 1/C2 + ...", "Series capacitance decreases - opposite to resistance")
    }

    fun capacitorParallel(values: List<Double>): Result {
        val total = values.sum()
        return Result(mapOf("Total Capacitance" to "${fmt(total)} F (${fmt(total*1e6)} μF)"), "C_total = C1 + C2 + ...", "Parallel capacitance adds up - opposite to resistance")
    }

    // ── 19. SOLAR PANEL ───────────────────────────────────────────────────────
    fun solarPanelSizing(dailyEnergy: Double, sunHours: Double, systemVoltage: Double, efficiency: Double = 0.85): Result {
        val totalEnergy = dailyEnergy / efficiency
        val panelPower = totalEnergy / sunHours
        val batteryCap = dailyEnergy / systemVoltage
        return Result(mapOf("Panel Power Needed" to "${fmt(panelPower)} W","Battery Capacity" to "${fmt(batteryCap)} Ah","Daily Energy Required" to "${fmt(totalEnergy)} Wh"), "Panel Power = Daily Energy / (Peak Sun Hours × Efficiency)", "Solar system sizing accounts for losses in wiring, battery, inverter")
    }

    // ── 20. 555 TIMER ─────────────────────────────────────────────────────────
    fun timer555Astable(r1: Double, r2: Double, c: Double): Result {
        val tHigh = 0.693 * (r1 + r2) * c
        val tLow = 0.693 * r2 * c
        val period = tHigh + tLow
        val frequency = 1.0 / period
        val dutyCycle = tHigh / period * 100
        return Result(mapOf("Frequency" to "${fmt(frequency)} Hz","Period" to "${fmt(period*1000)} ms","High Time" to "${fmt(tHigh*1000)} ms","Low Time" to "${fmt(tLow*1000)} ms","Duty Cycle" to "${fmt(dutyCycle)} %"), "f = 1.44 / ((R1+2R2)×C)", "555 timer astable mode generates continuous square wave")
    }

    // ── HELPERS ───────────────────────────────────────────────────────────────
    private fun fmt(v: Double): String {
        return when {
            v == 0.0 -> "0"
            abs(v) >= 1e9 -> "%.4f G".format(v/1e9)
            abs(v) >= 1e6 -> "%.4f M".format(v/1e6)
            abs(v) >= 1000 -> "%.4f k".format(v/1000)
            abs(v) >= 1 -> "%.6f".format(v).trimEnd('0').trimEnd('.')
            abs(v) >= 1e-3 -> "%.4f m".format(v*1000)
            abs(v) >= 1e-6 -> "%.4f μ".format(v*1e6)
            abs(v) >= 1e-9 -> "%.4f n".format(v*1e9)
            else -> "%.4e".format(v)
        }
    }

    private fun fmtResistor(v: Double): String {
        return when {
            v >= 1e6 -> "${fmt(v/1e6)}M"
            v >= 1000 -> "${fmt(v/1000)}k"
            else -> fmt(v)
        }
    }

    private fun nearestE12(r: Double): Double {
        val e12 = listOf(1.0,1.2,1.5,1.8,2.2,2.7,3.3,3.9,4.7,5.6,6.8,8.2)
        val exp = floor(log10(r)).toInt()
        val mantissa = r / 10.0.pow(exp)
        val nearest = e12.minByOrNull { abs(it - mantissa) } ?: 1.0
        return nearest * 10.0.pow(exp)
    }
}
