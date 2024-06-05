package me.sit.dev.service.standalone;

import me.sit.dev.exceptions.ui.InvalidRepositoryException;
import me.sit.dev.repository.type.RepositoryType;
import me.sit.dev.repository.impl.restaurant.RestaurantDatabaseRepo;
import me.sit.dev.repository.impl.restaurant.RestaurantFileRepo;
import me.sit.dev.repository.impl.restaurant.RestaurantMemoRepo;
import me.sit.dev.repository.impl.user.UserDatabaseRepo;
import me.sit.dev.repository.impl.user.UserFileRepo;
import me.sit.dev.repository.impl.user.UserMemoRepo;
import me.sit.dev.service.IRestaurantService;
import me.sit.dev.service.IUserService;
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
}