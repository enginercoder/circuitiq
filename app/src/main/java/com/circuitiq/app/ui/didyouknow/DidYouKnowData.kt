package com.circuitiq.app.ui.didyouknow

data class Fact(
    val emoji: String,
    val title: String,
    val fact: String,
    val category: String
)

object DidYouKnowData {
    fun getFacts() = listOf(
        Fact("⚡","Lightning is AC!","Lightning is not DC — it actually oscillates, making it closer to AC. A single lightning bolt carries about 1 billion volts and 20,000 amperes!","Electricity"),
        Fact("💡","LED vs Incandescent","An LED bulb uses 75-80% less energy than an incandescent bulb. If you replace one 60W bulb with an 8W LED, you save ₹450/year in electricity bills!","Energy"),
        Fact("🔋","Battery Invented in 1800","Alessandro Volta invented the first true battery in 1800 — the Voltaic Pile. He stacked zinc and copper discs with brine-soaked cloth between them.","History"),
        Fact("🌍","India's Power Grid","India has the world's largest electricity grid by connections — serving over 300 million households. The grid spans 4.5 lakh km of transmission lines!","India"),
        Fact("🔄","Transformers Lose Energy as Heat","Even the best power transformers are only 99.7% efficient. The remaining 0.3% is lost as heat in the core and windings — that's still millions of watts across a national grid!","Machines"),
        Fact("📡","Wi-Fi Uses Electricity","A typical Wi-Fi router uses 5-20 watts continuously. Left on 24/7 for a year, it consumes 44-175 kWh — costing ₹280 to ₹1100 annually!","Electronics"),
        Fact("☀","Solar Energy Potential","India receives solar energy equivalent to 5,000 trillion kWh per year — 388x our total annual electricity consumption. We're barely tapping 0.1% of it!","Renewable"),
        Fact("🔌","Why 50Hz in India?","India uses 50Hz AC power (unlike USA's 60Hz) because it was adopted from British standards during colonial times. At 50Hz, fluorescent lights flicker 100 times per second — invisible to the human eye!","AC Power"),
        Fact("⚙","Motor Efficiency Law","The efficiency of an electric motor improves as it gets larger. A small 1W motor might be 50% efficient, while a large 1MW industrial motor can reach 98% efficiency!","Motors"),
        Fact("🏠","Home Wiring Colors","In India, electrical wiring uses Red (Phase), Black (Neutral), Green (Earth) for older installations. New IS standard uses Brown (Phase), Light Blue (Neutral), Green-Yellow (Earth).","Wiring"),
        Fact("🔬","Ohm's Law Discovery","Georg Simon Ohm published his law in 1827 but was initially ridiculed by the scientific community. It took 16 years before his work was recognized, and he was awarded the Copley Medal in 1841!","History"),
        Fact("💰","Electricity Theft Costs","India loses approximately ₹25,000 crore annually due to electricity theft — called 'Aggregate Technical and Commercial (AT&C) losses'. This amounts to about 20% of total electricity generated!","India"),
        Fact("🌡","Superconductors","Some materials become superconductors at near absolute zero (-273°C) and conduct electricity with ZERO resistance. MRI machines use superconducting magnets cooled with liquid helium!","Physics"),
        Fact("📱","Phone Charging Physics","When you charge your phone at 5V, 2A — the power is 10W. But your battery is only 3.7V. The extra voltage is converted to heat — that's why your phone gets warm while charging!","Electronics"),
        Fact("🔴","Resistor Color Code Origin","The resistor color code was developed in the 1920s when resistors were too small to print numbers on. The colors were chosen based on their visibility and ease of distinction.","Components"),
        Fact("🌊","Power Factor in Real Life","Most household appliances have a power factor of 0.7-0.9. This means you pay for 100 units of apparent power but only get 70-90 units of real work done. Industrial consumers pay extra for low PF!","AC Power"),
        Fact("🚀","ISRO's Power Challenge","The Chandrayaan-3 lander used solar panels generating only 738 watts — less than a typical home iron! Engineers had to optimize every milliwatt to power the rover and communication systems.","Space"),
        Fact("🔋","Battery Capacity Trick","Battery capacity (mAh) is tested at room temperature (25°C). In cold weather (0°C), a battery might only deliver 70% of its rated capacity. That's why phones die faster in winter!","Battery"),
        Fact("⚡","Tesla vs Edison War","The famous 'War of Currents' (1880s-1890s) — Edison promoted DC while Tesla/Westinghouse pushed AC. AC won because it can be stepped up/down with transformers for long-distance transmission. DC loses too much energy over distance!","History"),
        Fact("🌐","Internet Uses 10% of World's Electricity","Data centers, servers, and networks powering the internet consume about 10% of global electricity production — approximately 2,000 TWh per year, comparable to the entire electricity consumption of India!","Digital"),
    )
}
