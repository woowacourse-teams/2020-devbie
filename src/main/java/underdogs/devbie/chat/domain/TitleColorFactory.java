package underdogs.devbie.chat.domain;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class TitleColorFactory {

    private static final Set<TitleColor> colors = createAllColors();

    private static Set<TitleColor> createAllColors() {
        Set<TitleColor> colors = new HashSet<>(Arrays.asList(TitleColor.values()));

        return colors;
    }

    public static TitleColor createNonOverlappingColor(Set<TitleColor> existTitleColor) {
        Set<TitleColor> titleColors = new HashSet<>(colors);
        titleColors.removeAll(existTitleColor);

        return titleColors.iterator().next();
    }
}
