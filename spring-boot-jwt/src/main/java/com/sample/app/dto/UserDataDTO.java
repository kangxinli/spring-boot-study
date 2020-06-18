package com.sample.app.dto;

import java.util.List;

import com.sample.app.entity.Role;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserDataDTO {

	@ApiModelProperty(position = 0)
	private String username;
	@ApiModelProperty(position = 1)
	private String email;
	@ApiModelProperty(position = 2)
	private String password;
	@ApiModelProperty(position = 3)
	List<Role> roles;

}
