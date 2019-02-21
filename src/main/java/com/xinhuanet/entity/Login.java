package com.xinhuanet.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class Login implements Serializable {

    private Integer id;

    private String username;

    private String password;

}
