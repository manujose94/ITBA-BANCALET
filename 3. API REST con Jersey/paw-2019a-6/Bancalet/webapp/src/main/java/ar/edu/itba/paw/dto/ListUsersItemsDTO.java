package ar.edu.itba.paw.dto;

import java.util.Collection;
import java.util.Iterator;
import java.util.TreeMap;

import ar.edu.itba.paw.models.Item;
import ar.edu.itba.paw.models.User;

public class ListUsersItemsDTO {

	private TreeMap<Long, TreeMap<Long, Item>> useritems;
	private TreeMap<Long, User> nearusers;
	private Integer rangekm;

	public ListUsersItemsDTO() {
		super();
	}

	public ListUsersItemsDTO(TreeMap<Long, TreeMap<Long, Item>> useritems, Collection<User> nearusers,
			Integer rangekm) {
		super();
		this.useritems = new TreeMap<Long, TreeMap<Long, Item>>();
		this.useritems = useritems;

		this.nearusers = new TreeMap<Long, User>();
		for (Iterator<User> iterator = nearusers.iterator(); iterator.hasNext();) {
			User user = iterator.next();
			this.nearusers.put(user.getUserId(), user);
		}
		this.rangekm = rangekm;
	}

	public TreeMap<Long, TreeMap<Long, Item>> getUseritems() {
		return useritems;
	}

	public void setUseritems(TreeMap<Long, TreeMap<Long, Item>> useritems) {
		this.useritems = useritems;
	}

	public TreeMap<Long, User> getNearusers() {
		return nearusers;
	}

	public void setNearusers(TreeMap<Long, User> nearusers) {
		this.nearusers = nearusers;
	}

	public Integer getRangekm() {
		return rangekm;
	}

	public void setRangekm(Integer rangekm) {
		this.rangekm = rangekm;
	}

}
