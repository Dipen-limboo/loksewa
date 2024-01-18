package com.example.loksewa.aayog.LoksewaAayog.payload.response;

import java.util.List;

public class AdminDashboardDto {
	private List<CategoryListDto> categoryList;
	
	private List<PositionListDto> positionList;
	
	private List<UserListDto> userList;

	public AdminDashboardDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AdminDashboardDto(List<CategoryListDto> categoryList, List<PositionListDto> positionList,
			List<UserListDto> userList) {
		super();
		this.categoryList = categoryList;
		this.positionList = positionList;
		this.userList = userList;
	}

	public List<CategoryListDto> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<CategoryListDto> categoryList) {
		this.categoryList = categoryList;
	}

	public List<PositionListDto> getPositionList() {
		return positionList;
	}

	public void setPositionList(List<PositionListDto> positionList) {
		this.positionList = positionList;
	}

	public List<UserListDto> getUserList() {
		return userList;
	}

	public void setUserList(List<UserListDto> userList) {
		this.userList = userList;
	}
	
	
}
