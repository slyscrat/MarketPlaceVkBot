package com.bot.vk.vkbot.Entity;

import org.springframework.jmx.export.annotation.ManagedAttribute;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "item")
public class item implements Serializable {

    @Id
    private int id;

    @Column
    private String description;

    @Column
    private int picture;

    @Column
    private float price;

    @Column
    private Boolean isSold;

    @OneToMany(mappedBy = "Item")
    private Set<type> types;

    @ManyToOne
    private messages Messages;

    public item(){

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPicture() {
        return picture;
    }

    public void setPicture(int picture) {
        this.picture = picture;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Boolean getSold() {
        return isSold;
    }

    public void setSold(Boolean sold) {
        isSold = sold;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Set<type> getTypes() {
        return types;
    }

    public void setTypes(Set<type> types) {
        this.types = types;
    }

    public messages getMessages() {
        return Messages;
    }

    public void setMessages(messages Messages) {
        this.Messages = Messages;
    }

}
