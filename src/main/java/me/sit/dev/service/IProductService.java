package me.sit.dev.service;

import me.sit.dev.entity.impl.Product;
import me.sit.dev.entity.impl.Restaurant;
import me.sit.dev.repository.IProductRepo;

import java.util.List;

public interface IProductService extends IProductRepo  {
    void showAllProducts(Restaurant restaurant);
}
