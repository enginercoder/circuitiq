package com.circuitiq.app.ui.game

data class QuizQuestion(
    val question: String,
    val options: List<String>,
    val correct: String
)

object ElectricityQuizData {
    fun getQuestions() = listOf(
        QuizQuestion("What does Ohm's Law state?", listOf("V = I × R","V = I + R","V = I / R","V = I²R"), "V = I × R"),
        QuizQuestion("What is the SI unit of electrical resistance?", listOf("Volt","Ampere","Ohm","Watt"), "Ohm"),
        QuizQuestion("Which material is the best conductor of electricity?", listOf("Iron","Copper","Silver","Aluminium"), "Silver"),
        QuizQuestion("What does AC stand for?", listOf("Active Current","Alternating Current","Automatic Control","Arc Current"), "Alternating Current"),
        QuizQuestion("What is the frequency of AC supply in India?", listOf("60 Hz","50 Hz","40 Hz","100 Hz"), "50 Hz"),
        QuizQuestion("What is the formula for electrical power?", listOf("P = V/I","P = V + I","P = V × I","P = V - I"), "P = V × I"),
        QuizQuestion("A capacitor stores energy in what form?", listOf("Magnetic field","Chemical energy","Electric field","Heat"), "Electric field"),
        QuizQuestion("An inductor stores energy in what form?", listOf("Electric field","Magnetic field","Chemical energy","Nuclear"), "Magnetic field"),
        QuizQuestion("What is the colour code for a 1kΩ resistor?", listOf("Brown Black Red Gold","Red Red Red Gold","Brown Brown Red Gold","Orange Black Brown"), "Brown Black Red Gold"),
        QuizQuestion("What does LED stand for?", listOf("Light Emitting Diode","Low Energy Device","Light Energy Diode","Linear Electronic Device"), "Light Emitting Diode"),
        QuizQuestion("Which law states sum of voltages in a loop = 0?", listOf("Ohm's Law","Kirchhoff's Voltage Law","Faraday's Law","Lenz's Law"), "Kirchhoff's Voltage Law"),
        QuizQuestion("Power Factor is a ratio of:", listOf("Voltage to Current","Real Power to Apparent Power","Resistance to Impedance","Energy to Time"), "Real Power to Apparent Power"),
        QuizQuestion("Transformer works on principle of:", listOf("Self induction","Mutual induction","Static electricity","Photoelectric effect"), "Mutual induction"),
        QuizQuestion("What is synchronous speed formula?", listOf("Ns = 120f/P","Ns = 60f/P","Ns = 2f/P","Ns = fP/120"), "Ns = 120f/P"),
        QuizQuestion("Which device converts AC to DC?", listOf("Inverter","Transformer","Rectifier","Amplifier"), "Rectifier"),
        QuizQuestion("Unit of capacitance is:", listOf("Henry","Ohm","Farad","Weber"), "Farad"),
        QuizQuestion("Unit of inductance is:", listOf("Farad","Henry","Tesla","Volt"), "Henry"),
        QuizQuestion("RMS value of AC is approximately what % of peak?", listOf("50%","63.2%","70.7%","86.6%"), "70.7%"),
        QuizQuestion("Zener diode is used mainly for:", listOf("Amplification","Voltage regulation","Rectification","Oscillation"), "Voltage regulation"),
        QuizQuestion("What is 1 kWh equal to?", listOf("1000 watts","3.6 MJ","3600 J","1 unit of electricity"), "3.6 MJ"),
        QuizQuestion("Which gas is used in fluorescent tubes?", listOf("Nitrogen","Argon and Mercury vapour","Carbon dioxide","Helium"), "Argon and Mercury vapour"),
        QuizQuestion("What colour is the Earth wire in new IS standard?", listOf("Red","Black","Green-Yellow","Blue"), "Green-Yellow"),
        QuizQuestion("555 timer IC has how many pins?", listOf("8","14","16","6"), "8"),
        QuizQuestion("What does MOSFET stand for?", listOf("Metal Oxide Semiconductor Field Effect Transistor","Main Output Signal FET","Multiple Output Signal FET","Metal Output Silicon FET"), "Metal Oxide Semiconductor Field Effect Transistor"),
        QuizQuestion("Voltage at which household appliances work in India?", listOf("110V","115V","220-240V","380V"), "220-240V"),
    )
}
