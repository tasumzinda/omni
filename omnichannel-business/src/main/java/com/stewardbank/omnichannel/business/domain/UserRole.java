/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.stewardbank.omnichannel.business.domain;

import com.stewardbank.omnichannel.business.util.StringUtils;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author Tasu Muzinda
 */
@Entity
@Table(name = "role")
public class UserRole implements Serializable{
    
    @Id
    private String id;
    
    private String name;
    
    @Transient
    private String printName;
    
    
    @ManyToMany(mappedBy = "userRoles")
    private Set<User> users = new HashSet<>();
    
    public UserRole(){
        
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
    
    public String getPrintName(){
        return StringUtils.toCamelCase3(getName());
    }
}
