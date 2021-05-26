package com.awais.homeservices;

public class URLs {
    public static  String ip  ="http://192.168.8.100";
    private static final String ROOT_URL = ip+"/homeservice/api/registrationapi.php?apicall=";
    public static final String URL_REGISTER = ROOT_URL + "signup";
    public static final String URL_LOGIN= ROOT_URL + "login";
    public static final String Get_All_Service = ip+"/homeservice/api/getAllServices.php";
}