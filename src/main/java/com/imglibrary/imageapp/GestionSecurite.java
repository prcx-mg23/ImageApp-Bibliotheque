package com.imglibrary.imageapp;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class GestionSecurite {
    public static byte[] securiserImage(byte[] imageData, String password) {

        BufferedImage bufferedImage = toBufferedImage(imageData);
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                pixels[y * width + x] = bufferedImage.getRGB(x, y);
            }
        }

        // Générer un tableau de permutation en fonction du mot de passe
        int[] permutation = generatePermutation(password, width * height);

        // Appliquer la permutation aux pixels
        int[] pixelsPermutés = new int[width * height];
        for (int i = 0; i < width * height; i++) {
            pixelsPermutés[i] = pixels[permutation[i]];
        }

        // Convertir les pixels permutés en un tableau de bytes
        BufferedImage bufferedImagePermutée = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                bufferedImagePermutée.setRGB(x, y, pixelsPermutés[y * width + x]);
            }
        }

        // Écrire l'image permutée dans un flux de bytes
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ImageIO.write(bufferedImagePermutée, "jpg", bos);
        } catch (IOException e) {
            System.err.println("Erreur lors de la conversion de l'image : " + e.getMessage());
        }

        return bos.toByteArray();
    }

    public static byte[] dechiffrerImage(byte[] imageData, String password) {
        // Convertir les données d'image en un tableau de pixels
        BufferedImage bufferedImage = toBufferedImage(imageData);
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                pixels[y * width + x] = bufferedImage.getRGB(x, y);
            }
        }

        // Générer un tableau de permutation en fonction du mot de passe
        int[] permutation = generatePermutation(password, width * height);

        // Appliquer la permutation inverse aux pixels
        int[] pixelsPermutés = new int[width * height];
        for (int i = 0; i < width * height; i++) {
            pixelsPermutés[permutation[i]] = pixels[i];
        }

        // Convertir les pixels permutés en un tableau de bytes
        BufferedImage bufferedImagePermutée = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                bufferedImagePermutée.setRGB(x, y, pixelsPermutés[y * width + x]);
            }
        }

        // Écrire l'image permutée dans un flux de bytes
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ImageIO.write(bufferedImagePermutée, "jpg", bos);
        } catch (IOException e) {
            System.err.println("Erreur lors de la conversion de l'image : " + e.getMessage());
        }

        return bos.toByteArray();
    }

    private static int[] generatePermutation(String password, int size) {
        int[] permutation = new int[size];
        Random random = new Random(getHash(password));
        for (int i = 0; i < size; i++) {
            permutation[i] = i;
        }
        for (int i = 0; i < size; i++) {
            int j = random.nextInt(size);
            int temp = permutation[i];
            permutation[i] = permutation[j];
            permutation[j] = temp;
        }
        return permutation;
    }

    private static long getHash(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] bytes = md.digest(password.getBytes());
            long hash = 0;
            for (byte b : bytes) {
                hash = (hash << 8) | (b & 0xFF);
            }
            return hash;
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Erreur lors du calcul du hash : " + e.getMessage());
            return 0;
        }
    }

    private static BufferedImage toBufferedImage(byte[] imageData) {
        ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
        try {
            return ImageIO.read(bis);
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture de l'image : " + e.getMessage());
            return null;
        }
    }
}