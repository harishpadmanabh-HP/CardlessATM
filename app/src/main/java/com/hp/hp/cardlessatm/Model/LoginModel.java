package com.hp.hp.cardlessatm.Model;

public class LoginModel {

    /**
     * details : {"userid":"2","name":"Harish P","email":"harishpadmanabh@gmail.com","number":"7012069385","qrcode":"UrNiR"}
     * message : Success
     * status : true
     */

    private DetailsBean details;
    private String message;
    private String status;

    public DetailsBean getDetails() {
        return details;
    }

    public void setDetails(DetailsBean details) {
        this.details = details;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static class DetailsBean {
        /**
         * userid : 2
         * name : Harish P
         * email : harishpadmanabh@gmail.com
         * number : 7012069385
         * qrcode : UrNiR
         */

        private String userid;
        private String name;
        private String email;
        private String number;
        private String qrcode;

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getQrcode() {
            return qrcode;
        }

        public void setQrcode(String qrcode) {
            this.qrcode = qrcode;
        }
    }
}
