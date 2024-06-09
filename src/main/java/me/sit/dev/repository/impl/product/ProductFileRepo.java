package me.sit.dev.repository.impl.product;

import me.sit.dev.entity.impl.Product;
import me.sit.dev.entity.impl.user.User;

import java.io.*;
import java.util.List;

public class ProductFileRepo extends ProductMemoRepo {

    private final String path = "products/";

    public ProductFileRepo() {
        File file = new File(path);

        if (!file.exists()) {
            file.mkdirs();
        }

        File[] files = file.listFiles();
        if (files != null) {
            for (File f : files) {
                try (ObjectInputStream reader = new ObjectInputStream(new BufferedInputStream(new FileInputStream(f))))  {
                    Product product = (Product) reader.readObject();
                    productMap.put(product.getId(), product);
                } catch (Exception e) {
                    System.err.println("Error reading from file (Product): " + e.getMessage());
                }
            }
        }
    }
    @Override
    public Product addProduct(String restaurantId, String productName, double price, int quantity) {
        Product product =  super.addProduct(restaurantId, productName, price, quantity);

        File file = getFileFromProduct(product);
        try(ObjectOutputStream write = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)))) {
            write.writeObject(product);
            write.flush();
        } catch (Exception e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }

        return product;
    }

    @Override
    public Product updateProduct(String productId, Product updatedProduct) {
        updatedProduct =  super.updateProduct(productId, updatedProduct);

        File file = getFileFromProduct(updatedProduct);
        try(ObjectOutputStream write = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)))) {
            write.writeObject(updatedProduct);
        } catch (Exception e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }

        return updatedProduct;
    }

    @Override
    public Product deleteProduct(String restaurantId, String productId) {
        Product removedProduct = super.deleteProduct(restaurantId, productId);

        File file = getFileFromProduct(removedProduct);
        if (!file.delete()) {
            System.err.println("Error deleting file: " + file.getName());
        }
        return removedProduct;
    }

    private File getFileFromProduct(Product product) {
        return new File(path + product.getId() + ".ser");
    }

}
