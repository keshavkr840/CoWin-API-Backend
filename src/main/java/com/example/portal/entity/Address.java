package com.example.portal.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name="address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long aid;
    @Column(nullable = false)
    private String line_one;
    @Column
    private String line_two;
    @Column(nullable=false)
    private String city;
    @Column(nullable=false)
    @Size(min=6,max=6)
    private String pincode;
    @Column(nullable = false)
    private String state;
    @Column
    private String current_line_one;
    @Column
    private String current_line_two;
    @Column
    private String current_city;
    @Column
    private String current_pincode;
    @Column
    private String current_state;

    public Address(){}
    public Address(String lineone, String linetwo, String city, String pincode, String state,
                   String currentlineone, String currentlinetwo, String currentcity, String currentpincode, String currentstate) {
        this.line_one = lineone;
        this.line_two = linetwo;
        this.city = city;
        this.pincode = pincode;
        this.state = state;
        this.current_line_one = lineone;
        this.current_line_two = linetwo;
        this.current_city = city;
        this.current_pincode = pincode;
        this.current_state = state;
//        if(currentlineone.equals("")) this.current_line_one = lineone;
//        else this.current_line_one = currentlineone;
//        if(currentlinetwo.equals("")) this.current_line_two = linetwo;
//        else this.current_line_two = currentlinetwo;
//        if(currentcity.equals("")) this.current_city = city;
//        else this.current_city = currentcity;
//        if(currentpincode.equals("")) this.current_pincode = pincode;
//        else this.current_pincode = currentpincode;
//        if(currentstate.equals("")) this.current_state = state;
//        else this.current_state = currentstate;
    }

    public long getAid() {
        return aid;
    }
    public void setAid(long aid) {
        this.aid = aid;
    }
    public String getLine_one() {
        return line_one;
    }
    public void setLine_one(String line_one) {
        this.line_one = line_one;
    }
    public String getLine_two() {
        return line_two;
    }
    public void setLine_two(String line_two) {
        this.line_two = line_two;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getPincode() {
        return pincode;
    }
    public void setPincode(String pincode) {
        this.pincode = pincode;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public String getCurrent_line_one() {return current_line_one;}
    public void setCurrent_line_one(String current_line_one) {this.current_line_one = current_line_one;}
    public String getCurrent_line_two() {return current_line_two;}
    public void setCurrent_line_two(String current_line_two) {this.current_line_two = current_line_two;}
    public String getCurrent_city() {return current_city;}
    public void setCurrent_city(String current_city) {this.current_city = current_city;}
    public String getCurrent_pincode() {return current_pincode;}
    public void setCurrent_pincode(String current_pincode) {this.current_pincode = current_pincode;}
    public String getCurrent_state() {return current_state;}
    public void setCurrent_state(String current_state) {this.current_state = current_state;}
}