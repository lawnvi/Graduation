package com.buct.graduation.model.pojo;

/**
 * 会议论文
 */
public class ConferencePaper {
    private Integer id;
    private Integer uid;
    private String name;//论文名称
    private String conference;//会议名称
    private String section;//分区 CCF-A/B/C
    private Integer citation;
    private String notes;//备注
    private String role;//担任角色
    
    private String AttrName;
    private String AttrMeeting;

    public String getAttrMeeting() {
        return AttrMeeting;
    }

    public void setAttrMeeting() {
        if(this.conference.length() > 70) {
            int i = 69;
            while (i < this.conference.length() && this.conference.charAt(i) == ' '){
                i++;
            }
            this.AttrMeeting = conference.substring(0, i+1) + " ...";
        }
        else {
            this.AttrMeeting = this.conference;
        }
    }

    public String getAttrName() {
        return AttrName;
    }

    public void setAttrName() {
        if(this.name.length() > 70) {
            int i = 69;
            while (i < this.name.length() && this.name.charAt(i) == ' '){
                i++;
            }
            this.AttrName = name.substring(0, i+1) + " ...";
        }
        else {
            this.AttrName = this.name;
        }
    }

    private boolean isEsi;

    public boolean isEsi() {
        return isEsi;
    }

    public void setEsi(boolean esi) {
        isEsi = esi;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getConference() {
        return conference;
    }

    public void setConference(String conference) {
        this.conference = conference;
        setAttrMeeting();
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
        setAttrName();
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public Integer getCitation() {
        return citation;
    }

    public void setCitation(Integer citation) {
        this.citation = citation;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
