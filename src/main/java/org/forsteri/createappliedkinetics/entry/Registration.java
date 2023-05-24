package org.forsteri.createappliedkinetics.entry;

import com.simibubi.create.content.processing.sequenced.SequencedAssemblyItem;
import com.tterrag.registrate.util.entry.ItemEntry;

import static org.forsteri.createappliedkinetics.CreateAppliedKinetics.REGISTERATE;

@SuppressWarnings("unused")
public class Registration {

    public static final ItemEntry<SequencedAssemblyItem>

            INCOMPLETE_PRINTED_ENGINEERING_CIRCUIT = sequencedIngredient("incomplete_printed_engineering_circuit"),
            INCOMPLETE_PRINTED_LOGIC_CIRCUIT = sequencedIngredient("incomplete_printed_logic_circuit"),
            INCOMPLETE_PRINTED_CALCULATION_CIRCUIT = sequencedIngredient("incomplete_printed_calculation_circuit"),
            INCOMPLETE_SILICON_PRINT = sequencedIngredient("incomplete_silicon_print"),
            INCOMPLETE_CALCULATION_PRESS = sequencedIngredient("incomplete_calculation_processor_press"),
            INCOMPLETE_ENGINEERING_PRESS = sequencedIngredient("incomplete_engineering_processor_press"),
            INCOMPLETE_LOGIC_PRESS = sequencedIngredient("incomplete_logic_processor_press"),
            INCOMPLETE_SILICON_PRESS = sequencedIngredient("incomplete_silicon_press");
    private static ItemEntry<SequencedAssemblyItem> sequencedIngredient(String name) {
        return REGISTERATE.item(name, SequencedAssemblyItem::new)
                .register();
    }

    public static void register(){}
}
