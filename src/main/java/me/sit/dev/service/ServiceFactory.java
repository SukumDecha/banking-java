package me.sit.dev.service;

import me.sit.dev.exceptions.InvalidRepositoryException;
import me.sit.dev.repository.IProductRepo;
import me.sit.dev.repository.impl.order.OrderDatabaseRepo;
import me.sit.dev.repository.impl.order.OrderFileRepo;
import me.sit.dev.repository.impl.order.OrderMemoRepo;
import me.sit.dev.repository.impl.product.ProductDatabaseRepo;
import me.sit.dev.repository.impl.product.ProductFileRepo;
import me.sit.dev.repository.impl.product.ProductMemoRepo;
import me.sit.dev.repository.type.RepositoryType;
import me.sit.dev.repository.impl.restaurant.RestaurantDatabaseRepo;
import me.sit.dev.repository.impl.restaurant.RestaurantFileRepo;
import me.sit.dev.repository.impl.restaurant.RestaurantMemoRepo;
import me.sit.dev.repository.impl.user.UserDatabaseRepo;
import me.sit.dev.repository.impl.user.UserFileRepo;
import me.sit.dev.repository.impl.user.UserMemoRepo;
import me.sit.dev.service.IRestaurantService;
import me.sit.dev.service.IUserService;
import me.sit.dev.service.impl.OrderService;
import me.sit.dev.service.impl.ProductService;
import me.sit.dev.service.impl.RestaurantService;
import me.sit.dev.service.impl.UserService;

public class ServiceFactory {
    private final RepositoryType repositoryType;

    public ServiceFactory(RepositoryType repositoryType) {
        this.repositoryType = repositoryType;
    }

    public IUserService createUserService() {
        switch (repositoryType) {
            case MEMO:
                return new UserService(new UserMemoRepo());
            case FILE:
                return new UserService(new UserFileRepo());
            case DATABASE:
                return new UserService(new UserDatabaseRepo());
            default:
                throw new InvalidRepositoryException();
        }
    }

    public IRestaurantService createRestaurantService() {
        switch (repositoryType) {
            case MEMO:
                return new RestaurantService(new RestaurantMemoRepo());
            case FILE:
                return new RestaurantService(new RestaurantFileRepo());
            case DATABASE:
                return new RestaurantService(new RestaurantDatabaseRepo());
            default:
                throw new InvalidRepositoryException();
        }
    }

    public IOrderService createOrderService(IProductRepo productRepo) {
        switch (repositoryType) {
            case MEMO:
                return new OrderService(new OrderMemoRepo());
            case FILE:
                return new OrderService(new OrderFileRepo());
            case DATABASE:
                return new OrderService(new OrderDatabaseRepo(productRepo));
            default:
                throw new InvalidRepositoryException();
        }
    }

    public IProductService createProductService() {
        switch (repositoryType) {
            case MEMO:
                return new ProductService(new ProductMemoRepo());
            case FILE:
                return new ProductService(new ProductFileRepo());
            case DATABASE:
                return new ProductService(new ProductDatabaseRepo());
            default:
                throw new InvalidRepositoryException();
        }
    }
}