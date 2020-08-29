package bungee.vaccum.module.joinme.utils;

import net.md_5.bungee.api.ChatColor;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Iterator;

public class ImageAPI {

    public ChatColor[][] toChatColorArray(BufferedImage image, int height) {
        double ratio = (image.getHeight() / image.getWidth());
        int width = (int) (height / ratio);
        BufferedImage resizedImage = resizeImage(image, width, height);
        ChatColor[][] chatImage = new ChatColor[resizedImage.getWidth()][resizedImage.getHeight()];

        for(int x = 0; x < resizedImage.getWidth(); x++) {
            for(int y = 0; y < resizedImage.getHeight(); y++) {
                int rgb = resizedImage.getRGB(x, y);
                ChatColor closestColor = getClosestChatColor(new Color(rgb));
                chatImage[x][y] = closestColor;
            }
        }

        return chatImage;
    }

    private BufferedImage resizeImage(BufferedImage originalImage, int width, int height) {
        BufferedImage resizedImage = new BufferedImage(width, height, 6);
        Graphics2D graphics2D = resizedImage.createGraphics();
        graphics2D.drawImage(originalImage, 0, 0, width, height, null);
        graphics2D.dispose();
        graphics2D.setComposite(AlphaComposite.Src);
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        return resizedImage;
    }

    private ChatColor getClosestChatColor(Color color) {
        if(color.getAlpha() < 128)
            return null;

        int index = 0;
        double best = -1.0D;
        int i;
        for(i = 0; i < colors.length; i++) {
            if(areIdentical(colors[i], color))
                return ChatColor.values()[i];
        }
        for(i = 0; i < colors.length; i++) {
            double distance = getDistance(color, colors[i]);
            if(distance < best || best == -1.0D) {
                best = distance;
                index = i;
            }
        }

        return ChatColor.values()[index];
    }

    private double getDistance(Color color1, Color color2) {
        double rmean = (color1.getRed() + color2.getRed()) / 2.0D;
        double r = (color1.getRed() - color2.getRed());
        double g = (color1.getGreen() - color2.getGreen());
        int b = color1.getBlue() - color2.getBlue();
        double weightR = 2.0D + rmean / 256.0D;
        double weightG = 4.0D;
        double weightB = 2.0D + (255.0D - rmean) / 256.0D;

        return weightR * r * r + weightG * g * g + weightB * b * b;
    }

    private final Color[] colors = new Color[] {
            new Color(0, 0, 0), new Color(0, 0, 170), new Color(0, 170, 0), new Color(0, 170, 170), new Color(170, 0, 0), new Color(170, 0, 170), new Color(255, 170, 0), new Color(170, 170, 170), new Color(85, 85, 85), new Color(85, 85, 255),
            new Color(85, 255, 85), new Color(85, 255, 255), new Color(255, 85, 85), new Color(255, 85, 255), new Color(255, 255, 85), new Color(255, 255, 255) };

    private static boolean areIdentical(Color color1, Color color2) {
        return (Math.abs(color1.getRed() - color2.getRed()) <= 5 && Math.abs(color1.getGreen() - color2.getGreen()) <= 5 && Math.abs(color1.getBlue() - color2.getBlue()) <= 5);
    }

    public String [] appendTextToImage(String[] chatImage, Iterable<String> text) {
        Iterator<String> i = text.iterator();
        int idx = 0;
        while(idx < chatImage.length && i.hasNext()) {
            String toAppend = i.next();
            chatImage[idx] = chatImage[idx] + " " + toAppend;
            idx++;
        }
        return chatImage;
    }
}
