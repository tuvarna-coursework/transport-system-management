package com.tuvarna.transportsystem.services;

import java.util.List;
import java.util.Optional;

import com.tuvarna.transportsystem.dao.UserProfileDAO;
import com.tuvarna.transportsystem.entities.UserProfile;

public class UserProfileService implements CrudService<UserProfile> {
	private UserProfileDAO userProfileDAO;

	public UserProfileService() {
		this.userProfileDAO = new UserProfileDAO();
	}

	public List<UserProfile> getByRating(double rating) {
		return userProfileDAO.getByRating(rating);
	}

	public List<UserProfile> getByRatingRange(double lowerBoundary, double upperBoundary) {
		return userProfileDAO.getByRatingRange(lowerBoundary, upperBoundary);
	}

	public List<UserProfile> getByHorarium(double horarium) {
		return userProfileDAO.getByHonorarium(horarium);
	}

	public List<UserProfile> getByHorariumRange(double lowerBoundary, double upperBoundary) {
		return userProfileDAO.getByHonorariumRange(lowerBoundary, upperBoundary);
	}

	@Override
	public Optional<UserProfile> getById(int id) {
		return userProfileDAO.getById(id);
	}

	@Override
	public List<UserProfile> getAll() {
		return userProfileDAO.getAll();
	}

	@Override
	public void save(UserProfile profile) {
		userProfileDAO.save(profile);
	}

	@Override
	public void deleteById(int id) {
		userProfileDAO.deleteById(id);
	}

	@Deprecated
	@Override
	public Optional<UserProfile> getByName(String name) {
		/* Entity has no name column */
		return null;
	}

	@Deprecated
	@Override
	public void updateName(UserProfile profile, String newValue) {
		/* Entity has no name column */
	}

	@Override
	public void deleteByName(String name) {
		/* Entity has no name column */
	}

	@Deprecated
	@Override
	public void update(UserProfile profile, String[] newValues) {
	}
}
