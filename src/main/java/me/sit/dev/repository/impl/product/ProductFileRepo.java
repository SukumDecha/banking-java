package me.sit.dev.repository.impl.product;

import me.sit.dev.entity.impl.Product;
import me.sit.dev.entity.impl.user.User;

import java.io.File;
import java.util.List;

public class ProductFileRepo extends ProductMemoRepo {

    public ProductFileRepo() {

    }
    @Override
    public Product addProduct(String restaurantId, String productName, double price, int quantity) {
        Product product =  super.addProduct(restaurantId, productName, price, quantity);

        return product;
    }

    @Override
    public Product updateProduct(String restaurantId, String productId, Product updatedProduct) {
        updatedProduct =  super.updateProduct(restaurantId, productId, updatedProduct);

        return updatedProduct;
    }

    @Override
    public Product deleteProduct(String restaurantId, String productId) {
        Product removedProduct = super.deleteProduct(restaurantId, productId);

        return removedProduct;
    }

    private File getFileFromProduct(Product product) {
        return new File(product.getId() + ".ser");
    }

}
