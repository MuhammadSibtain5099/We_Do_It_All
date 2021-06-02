package com.awais.homeservices.Model;

public class Vendor {
    String id,f_name,l_name,cnic,serviceId,email,phoneno,address,status;

    public Vendor(String id, String f_name, String l_name, String cnic, String serviceId, String email, String phoneno, String address,String status) {
        this.id = id;
        this.f_name = f_name;
        this.l_name = l_name;
        this.cnic = cnic;
        this.serviceId = serviceId;
        this.email = email;
        this.phoneno = phoneno;
        this.address = address;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getF_name() {
        return f_name;
    }

    public void setF_name(String f_name) {
        this.f_name = f_name;
    }
    public String getStatus(){

        return status.equals("0")?"Pending":status.equals("1")?"Approved":"Rejected";
    }
    public String getL_name() {
        return l_name;
    }

    public void setL_name(String l_name) {
        this.l_name = l_name;
    }

    public String getCnic() {
        return cnic;
    }

    public void setCnic(String cnic) {
        this.cnic = cnic;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
