package com.ss.localchat.db;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.ss.localchat.db.dao.UserDao;
import com.ss.localchat.db.entity.Chat;
import com.ss.localchat.db.entity.User;

import java.util.List;
import java.util.UUID;

public class UserRepository {

    private UserDao userDao;


    public UserRepository(Application application) {
        userDao = AppDatabase.getInstance(application).userDao();
    }

    public LiveData<List<User>> getUsersExceptOwner(UUID owner) {
        return userDao.getUsersExceptOwner(owner);
    }

    public LiveData<List<Chat>> getChatsExceptOwner(UUID owner) {
        return userDao.getChatsExceptOwner(owner);
    }

    public LiveData<User> getUserById(UUID id) {
        return userDao.getUserById(id);
    }

    public LiveData<List<User>> getUsersListById(UUID... uuids) {
        return userDao.getUsersListById(uuids);
    }

    public LiveData<List<String>> getEndpointId(UUID... uuids) {
        return userDao.getEndpointId(uuids);
    }

    public void insert(User user) {
        new InsertAsyncTask(userDao).execute(user);
    }

    public void update(User user) {
        new UpdateAsyncTask(userDao).execute(user);
    }

    public void updatePhoto(String endpointId, String photoUri) {
        new UpdatePhotoAsyncTask(userDao).execute(endpointId, photoUri);
    }

    public void delete(UUID id) {
        new DeleteAsyncTask(userDao).execute(id);
    }

    public void deleteAllUsers() {
        new DeleteAllUsersAsyncTask(userDao).execute();
    }

    private static class InsertAsyncTask extends AsyncTask<User, Void, Void> {

        private UserDao asyncTaskDao;

        InsertAsyncTask(UserDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(User... users) {
            asyncTaskDao.insert(users[0]);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<User, Void, Void> {

        private UserDao asyncTaskDao;

        UpdateAsyncTask(UserDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(User... users) {
            asyncTaskDao.update(users[0]);
            return null;
        }
    }

    private static class UpdatePhotoAsyncTask extends AsyncTask<String, Void, Void> {

        private UserDao asyncTaskDao;

        UpdatePhotoAsyncTask(UserDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(String... strings) {
            asyncTaskDao.updatePhoto(strings[0], strings[1]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<UUID, Void, Void> {

        private UserDao asyncTaskDao;

        DeleteAsyncTask(UserDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(UUID... uuids) {
            asyncTaskDao.delete(uuids[0]);
            return null;
        }
    }

    private static class DeleteAllUsersAsyncTask extends AsyncTask<Void, Void, Void> {

        private UserDao asyncTaskDao;

        DeleteAllUsersAsyncTask(UserDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            asyncTaskDao.deleteAllUsers();
            return null;
        }
    }
}
