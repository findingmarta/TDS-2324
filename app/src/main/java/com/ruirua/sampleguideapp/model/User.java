package com.ruirua.sampleguideapp.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;


@Entity(tableName = "users",indices = @Index(value = {"id"},unique = true))
public class User {

    @PrimaryKey
    @NonNull
    //@SerializedName("id")
    @ColumnInfo(name = "id")
    Integer id;
    @ColumnInfo(name = "username")
    String username;
    //@SerializedName("image_url")
    @ColumnInfo(name = "user_type")
    String user_type;
    @ColumnInfo(name = "last_login")
    String last_login;
    @ColumnInfo(name = "is_superuser")
    Boolean is_superuser;
    @ColumnInfo(name = "first_name")
    String first_name;
    @ColumnInfo(name = "last_name")
    String last_name;
    @ColumnInfo(name = "email")
    String email;
    @ColumnInfo(name = "is_staff")
    Boolean is_staff;
    @ColumnInfo(name = "is_active")
    Boolean is_active;
    @ColumnInfo(name = "data_joined")
    String date_joined;

    public User(Integer id, String username, String user_type, String last_login, Boolean is_superuser, String first_name, String last_name, String email, Boolean is_staff, Boolean is_active, String date_joined){
        this.id = id;
        this.username = username;
        this.user_type = user_type;
        this.last_login = last_login;
        this.is_superuser = is_superuser;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.is_staff = is_staff;
        this.is_active = is_active;
        this.date_joined = date_joined;
    }

    public User(User user){
        this.id = user.id;
        this.username = user.username;
        this.user_type = user.user_type;
        this.last_login = user.last_login;
        this.is_superuser = user.is_superuser;
        this.first_name = user.first_name;
        this.last_name = user.last_name;
        this.email = user.email;
        this.is_staff = user.is_staff;
        this.is_active = user.is_active;
        this.date_joined = user.date_joined;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUsername(@NonNull String username) {
        this.username = username;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public void setLast_login(String last_login) {
        this.last_login = last_login;
    }

    public void setIs_superuser(Boolean is_superuser) {
        this.is_superuser = is_superuser;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setIs_staff(Boolean is_staff) {
        this.is_staff = is_staff;
    }

    public void setIs_active(Boolean is_active) {
        this.is_active = is_active;
    }

    public void setDate_joined(String date_joined) {
        this.date_joined = date_joined;
    }

    public Integer getId() { return id; }

    @NonNull
    public String getUsername() {
        return username;
    }

    public String getUser_type() {
        return user_type;
    }

    public String getLast_login() {
        return last_login;
    }

    public Boolean getIs_superuser() {
        return is_superuser;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getEmail() {
        return email;
    }

    public Boolean getIs_staff() {
        return is_staff;
    }

    public Boolean getIs_active() {
        return is_active;
    }

    public String getDate_joined() {
        return date_joined;
    }

    public User clone(){ return new User(this);
    }
}
