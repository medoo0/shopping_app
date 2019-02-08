package com.alaa.microprocess.lrahtk.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Gift {

        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("class")
        @Expose
        private String _class;
        @SerializedName("school")
        @Expose
        private String school;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getClass_() {
            return _class;
        }

        public void setClass_(String _class) {
            this._class = _class;
        }

        public String getSchool() {
            return school;
        }

        public void setSchool(String school) {
            this.school = school;
        }


}
