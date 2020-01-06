package com.hp.hp.cardlessatm.Model;

import java.util.List;

public class HistoryModel {

    /**
     * details : [{"bank_id":"27","user_id":"2","credit":"0","debit":"99","balance":"3811","date":"2019-03-11 12:30:55"},{"bank_id":"13","user_id":"2","credit":"0","debit":"320","balance":"3910","date":"2019-03-09 09:18:05"},{"bank_id":"12","user_id":"2","credit":"0","debit":"500","balance":"4230","date":"2019-03-09 08:34:15"},{"bank_id":"11","user_id":"2","credit":"0","debit":"50","balance":"4730","date":"2019-03-09 08:21:08"},{"bank_id":"10","user_id":"2","credit":"0","debit":"200","balance":"4780","date":"2019-03-09 08:18:48"},{"bank_id":"9","user_id":"2","credit":"0","debit":"20","balance":"4980","date":"2019-03-09 08:15:38"},{"bank_id":"8","user_id":"2","credit":"5000","debit":"0","balance":"5000","date":"2019-03-08 05:50:19"}]
     * message : Success
     * status : true
     */

    private String message;
    private String status;
    private List<DetailsBean> details;

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

    public List<DetailsBean> getDetails() {
        return details;
    }

    public void setDetails(List<DetailsBean> details) {
        this.details = details;
    }

    public static class DetailsBean {
        /**
         * bank_id : 27
         * user_id : 2
         * credit : 0
         * debit : 99
         * balance : 3811
         * date : 2019-03-11 12:30:55
         */

        private String bank_id;
        private String user_id;
        private String credit;
        private String debit;
        private String balance;
        private String date;

        public String getBank_id() {
            return bank_id;
        }

        public void setBank_id(String bank_id) {
            this.bank_id = bank_id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getCredit() {
            return credit;
        }

        public void setCredit(String credit) {
            this.credit = credit;
        }

        public String getDebit() {
            return debit;
        }

        public void setDebit(String debit) {
            this.debit = debit;
        }

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }
}
