package com.buct.graduation.model.pojo;
/**
专利：仅列出第一发明人或导师第一本人第二且已授权的专利
 */
public class Patent {
    private Integer id;
    private Integer uid;
    private String name;//专利名称
    private String category;//类别 国内/外
    private String notes;//备注 如链接
    private String role;//参与角色

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
