package utils;

public final class TextUtils {


    private TextUtils() {}

    /**
     * Centers text in terminal width.
     */
    public static String center(String text, int width) {
//        if (text == null || text.length() >= TERMINAL_WIDTH) {
//            return text;
//        }
        if (text.length() >= width) return text.substring(0, width);

        int leftPadding = (width - text.length()) / 2;
        int rightPadding = width - text.length() - leftPadding;
        return " ".repeat(leftPadding) + text + " ".repeat(rightPadding);

    }

    /**
     * Clears terminal screen (ANSI escape).
     */
    public static void clearTerminal() {
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
        }

    public static void infoPlacer(String[] info, String text){
        if(info[2].isEmpty()) info[2] = text;
        else info[3] = text;
    }
}
