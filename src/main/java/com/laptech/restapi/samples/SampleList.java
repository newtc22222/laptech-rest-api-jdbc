package com.laptech.restapi.samples;

import com.laptech.restapi.samples.products.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class SampleList {
    public static List<Product> productList = new ArrayList<>();

    static String[] colors = {
            "Red",
            "Blue",
            "Green",
            "Aqua",
            "Brown",
            "Coral",
            "Grey",
            "Khaki",
            "Salmon",
            "Lime",
            "Navy",
            "Plum",
            "Snow",
            "Tan",
            "Teal",
            "Turquoise",
            "SteelBlue",
            "Olive",
            "Cyan",
            "Yellow",
            "Black",
            "White",
            "Orange",
            "Purple",
            "Violet",
            "Pink",
            "PowderBlue",
            "Crimson"
    };

    static String[] products = {
            "Hat",
            "Cap",
            "Tank top",
            "Sweater",
            "Trouser",
            "Underwear",
            "Shockpant",
            "Bracelet",
            "Ring",
            "Shirt",
            "Jacket",
            "Coat",
            "Necklace",
            "Highheel",
            "Shoes",
            "Jean",
            "Backpack",
            "Bag",
            "Socks"
    };

    static String[] imagesURL = {
            "https://loremflickr.com/cache/resized/65535_52352644350_64d12e3701_z_640_480_nofilter.jpg",
            "https://loremflickr.com/cache/resized/65535_52484905727_ff8da3b42e_c_640_480_nofilter.jpg",
            "https://loremflickr.com/cache/resized/65535_52375867317_766aee8983_c_640_480_nofilter.jpg",
            "https://loremflickr.com/cache/resized/65535_52800358781_167a680f18_z_640_480_nofilter.jpg",
            "https://loremflickr.com/cache/resized/5337_6917041132_e36ca1ef7d_c_640_480_nofilter.jpg"
    };

    private static Product getRandomProduct() {
        String name = colors[(int)(Math.random() * colors.length)] + " " +  products[(int)(Math.random() * products.length)];
        String id = name.trim().toLowerCase().replace(" ", "-");
        BigDecimal price = BigDecimal.valueOf(Math.random() * 1_000_000);
        BigDecimal discountPrice = BigDecimal.valueOf(price.doubleValue() - Math.random() * price.doubleValue());
        String[] images = { imagesURL[(int)(Math.random() * imagesURL.length)], imagesURL[(int)(Math.random() * imagesURL.length)] };
        return new Product(id, name, price, discountPrice, images);
    }

    private static void getRandomProductList() {
        int seedsLoop = 100;
        while(seedsLoop > 0) {
            productList.add(getRandomProduct());
            seedsLoop--;
        }
    }

    static {
        getRandomProductList();
    }
}
