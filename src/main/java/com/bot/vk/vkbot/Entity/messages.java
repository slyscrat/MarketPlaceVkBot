package com.bot.vk.vkbot.Entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.persistence.*;


@Entity
@Table(name = "messages")
public class messages implements Serializable {

    @Id
    @Column
    private Date date;

    @Column
    private Integer userId;

    @Column
    private String message;

    @OneToMany(mappedBy = "Messages", fetch = FetchType.LAZY)
    private Set<item> items;

    public messages(){

    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Set<item> getItems() {
        return items;
    }

    public void setItems(Set<item> items) {
        this.items = items;
    }
}
