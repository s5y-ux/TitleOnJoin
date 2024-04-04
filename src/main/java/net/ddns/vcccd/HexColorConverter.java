package net.ddns.vcccd;

import net.md_5.bungee.api.ChatColor;


public class HexColorConverter {

    // Method to parse hex color code and return colored message
    public static String convertHexCodes(String input) {
        StringBuilder result = new StringBuilder();
        int index = 0;
        while (index < input.length()) {
            // Check if current character is '&' and next character is '#'
            if (input.charAt(index) == '&' && index + 7 <= input.length() && input.charAt(index + 1) == '#' && isHex(input.substring(index + 2, index + 8))) {
                // Extract hex code part
                String hexCode = input.substring(index + 1, index + 8);
                // Convert hex code to ChatColor
                ChatColor color = hexToChatColor(hexCode);
                // Append ChatColor to result
                result.append(color);
                // Move index to skip hex code
                index += 8;
            } else {
                // Append current character to result
                result.append(input.charAt(index));
                index++;
            }
        }
        return result.toString();
    }

    // Method to parse hex color code and return ChatColor
    public static ChatColor hexToChatColor(String hexCode) {
        // Check if the hex code starts with # and has 7 characters
        if (!hexCode.startsWith("#") || hexCode.length() != 7) {
            // Invalid hex code
            return ChatColor.RESET; // or whatever default color you want
        }

        try {
            // Extract RGB values
            int r = Integer.parseInt(hexCode.substring(1, 3), 16);
            int g = Integer.parseInt(hexCode.substring(3, 5), 16);
            int b = Integer.parseInt(hexCode.substring(5, 7), 16);

            // Convert RGB to ChatColor
            return ChatColor.of(new java.awt.Color(r, g, b));
        } catch (NumberFormatException e) {
            // Error in parsing hex code
            e.printStackTrace();
            return ChatColor.RESET; // or whatever default color you want
        }
    }

    // Method to check if a string is a valid hexadecimal color code
    private static boolean isHex(String s) {
        String hexPattern = "[0-9a-fA-F]+";
        return s.matches(hexPattern);
    }
}
