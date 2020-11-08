package com.tuvarna.transportsystem.services;

import java.util.Date;
import java.util.List;

import com.tuvarna.transportsystem.dao.TicketDAO;
import com.tuvarna.transportsystem.entities.Ticket;

public class TicketService implements CrudService<Ticket> {
	private TicketDAO ticketDAO;

	public TicketService() {
		this.ticketDAO = new TicketDAO();
	}

	public List<Ticket> getByPurchaseDate(Date date) {
		return ticketDAO.getByPurchaseDate(date);
	}

	public List<Ticket> getByUser(int userId) {
		return ticketDAO.getByUser(userId);
	}

	public List<Ticket> getByTrip(int tripId) {
		return ticketDAO.getByTrip(tripId);
	}

	@Override
	public Ticket getById(int id) {
		return ticketDAO.getById(id);
	}

	@Override
	public List<Ticket> getAll() {
		return ticketDAO.getAll();
	}

	@Override
	public void save(Ticket ticket) {
		ticketDAO.save(ticket);
	}

	@Override
	public void deleteById(int id) {
		ticketDAO.deleteById(id);
	}

	@Deprecated
	@Override
	public Ticket getByName(String name) {
		return ticketDAO.getByName(name);
	}

	@Deprecated
	@Override
	public void updateName(Ticket ticket, String newValue) {
	}

	@Deprecated
	@Override
	public void deleteByName(String name) {
	}

	@Deprecated
	@Override
	public void update(Ticket ticket, String[] newValues) {
	}
}
