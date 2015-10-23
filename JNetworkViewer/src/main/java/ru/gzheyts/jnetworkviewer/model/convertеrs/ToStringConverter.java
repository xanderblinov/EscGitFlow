package ru.gzheyts.jnetworkviewer.model.convertеrs;

import net.inference.database.dto.IAuthor;
import net.inference.database.dto.ICluster;
import net.inference.database.dto.IEvolution;
import net.inference.database.dto.IEvolutionSlice;

/**
 * @author gzheyts
 */
public class ToStringConverter {
    private ToStringConverter() {
    }

    public static String convert(Object obj) {

        if (obj instanceof IAuthor) {
            return ((IAuthor) obj).getName() + " " + ((IAuthor) obj).getSurname();
        } else if (obj instanceof ICluster) {
            return "cluster-" + ((ICluster) obj).getId();
        } else if (obj instanceof IEvolution) {
            return "evolution-" + ((IEvolution) obj).getId();
        } else if (obj instanceof IEvolutionSlice) {
            return "slice-" + ((IEvolutionSlice) obj).getId();
        }

        return null;
    }

}
