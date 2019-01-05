package com.alaa.microprocess.lrahtk.pojo;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Categories {



        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("createdAt")
        @Expose
        private String createdAt;
        @SerializedName("updatedAt")
        @Expose
        private String updatedAt;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("thumbnail")
        @Expose
        private String thumbnail;
        @SerializedName("parent")
        @Expose
        private parent_Categories parent;
        @SerializedName("__v")
        @Expose
        private int v;
        @SerializedName("id")
        @Expose
        private String id;

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public parent_Categories getParent() {
            return parent;
        }

        public void setParent(parent_Categories parent) {
            this.parent = parent;
        }

        public int getV() {
            return v;
        }

        public void setV(int v) {
            this.v = v;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }


}
