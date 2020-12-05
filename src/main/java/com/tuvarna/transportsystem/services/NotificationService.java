package com.tuvarna.transportsystem.services;

import java.util.List;
import java.util.Optional;

import com.tuvarna.transportsystem.dao.NotificationDAO;
import com.tuvarna.transportsystem.entities.Notification;

public class NotificationService implements CrudService<Notification> {
	private NotificationDAO notificationDAO;

	public NotificationService() {
		this.notificationDAO = new NotificationDAO();
	}

	public List<Notification> getNotificationsBySenderId(int userId) {
		return notificationDAO.getNotificationsBySenderId(userId);
	}

	public List<Notification> getNotificationsForReceiverId(int userId) {
		return notificationDAO.getNotificationsForReceiverId(userId);
	}

	@Override
	public Optional<Notification> getById(int id) {
		return notificationDAO.getById(id);
	}

	@Override
	public List<Notification> getAll() {
		return notificationDAO.getAll();
	}

	@Override
	public void save(Notification notification) {
		notificationDAO.save(notification);
	}

	@Override
	public void deleteById(int id) {
		notificationDAO.deleteById(id);
	}

	@Deprecated
	@Override
	public Optional<Notification> getByName(String name) {
		return null;
	}

	@Deprecated
	@Override
	public void updateName(Notification notification, String newValue) {
	}

	@Deprecated
	@Override
	public void deleteByName(String name) {
	}

	@Deprecated
	@Override
	public void update(Notification notification, String[] newValues) {
	}
}