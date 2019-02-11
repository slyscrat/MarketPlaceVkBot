package com.bot.vk.vkbot.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "BLACK_LIST")
public class BlackList {

    @Id
    @Column(name = "USER_ID")
    private int userId;

    @Column(name = "WARNINGS")
    private int warnings;

    public BlackList(){

    }

    public int getWarnings() {
        return warnings;
    }

    public void setWarnings(int warnings) {
        this.warnings = warnings;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
