package com.nagarro.dto;

import java.util.List;

import com.nagarro.entity.User;

//Random User List DTO
public class RandomUserList {

	private List<User> randomuser;
	PageData pageinfo;
	
	public List<User> getRandomuser() {
		return randomuser;
	}

	public void setRandomuser(List<User> randomuser) {
		this.randomuser = randomuser;
	}

	public PageData getPageinfo() {
		return pageinfo;
	}

	public void setPageinfo(PageData pageinfo) {
		this.pageinfo = pageinfo;
	}

	public RandomUserList() {
		
		this.pageinfo = new PageData();
	}

	public class PageData{
		private boolean hasNext;
		private boolean hasPrevious;
		private long total ;
		public boolean isHasNext() {
			return hasNext;
		}
		public void setHasNext(boolean hasNext) {
			this.hasNext = hasNext;
		}
		public boolean isHasPrevious() {
			return hasPrevious;
		}
		public void setHasPrevious(boolean hasPrevious) {
			this.hasPrevious = hasPrevious;
		}
		public long getTotal() {
			return total;
		}
		public void setTotal(long total) {
			this.total = total;
		}
		
	}
}

