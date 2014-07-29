package net.servehttp.bytecom.persistence.entity.security;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 
 * @author Clairton Luz - clairton.c.l@gmail.com
 *
 */
@Entity
@Table(name = "user_group")
public class UserGroup {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @ManyToOne
  @JoinColumn(name = "user_id")
  private UserAccount userAccount;
  @ManyToOne
  @JoinColumn(name = "group_id")
  private AccessGroup accessGroup;
  private String username;
  private String groupName;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public UserAccount getUserAccount() {
    return userAccount;
  }

  public void setUserAccount(UserAccount userAccount) {
    this.userAccount = userAccount;
  }

  public AccessGroup getAccessGroup() {
    return accessGroup;
  }

  public void setAccessGroup(AccessGroup accessGroup) {
    this.accessGroup = accessGroup;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getGroupName() {
    return groupName;
  }

  public void setGroupName(String groupName) {
    this.groupName = groupName;
  }

}
