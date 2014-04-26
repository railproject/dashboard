package org.springframework.samples.portfolio.model;

/**
 * Created by star on 4/26/14.
 */
public class Grid {
    //路局ID
    private String id;
    //路局全称
    private String name;
    // 路局编号
    private int code;
    //图定始发终到
    private int td_sf_zd;
    //图定始发交出
    private int td_sf_jc;
    //图定接入终到
    private int td_jr_zd;
    //图定接入交出
    private int td_jr_jc;
    //临客始发终到
    private int lk_sf_zd;
    //临客始发交出
    private int lk_sf_jc;
    //临客接入终到
    private int lk_jr_zd;
    //临客接入交出
    private int lk_jr_jc;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTd_sf_zd() {
        return td_sf_zd;
    }

    public void setTd_sf_zd(int td_sf_zd) {
        this.td_sf_zd = td_sf_zd;
    }

    public int getTd_sf_jc() {
        return td_sf_jc;
    }

    public void setTd_sf_jc(int td_sf_jc) {
        this.td_sf_jc = td_sf_jc;
    }

    public int getTd_jr_zd() {
        return td_jr_zd;
    }

    public void setTd_jr_zd(int td_jr_zd) {
        this.td_jr_zd = td_jr_zd;
    }

    public int getTd_jr_jc() {
        return td_jr_jc;
    }

    public void setTd_jr_jc(int td_jr_jc) {
        this.td_jr_jc = td_jr_jc;
    }

    public int getLk_sf_zd() {
        return lk_sf_zd;
    }

    public void setLk_sf_zd(int lk_sf_zd) {
        this.lk_sf_zd = lk_sf_zd;
    }

    public int getLk_sf_jc() {
        return lk_sf_jc;
    }

    public void setLk_sf_jc(int lk_sf_jc) {
        this.lk_sf_jc = lk_sf_jc;
    }

    public int getLk_jr_zd() {
        return lk_jr_zd;
    }

    public void setLk_jr_zd(int lk_jr_zd) {
        this.lk_jr_zd = lk_jr_zd;
    }

    public int getLk_jr_jc() {
        return lk_jr_jc;
    }

    public void setLk_jr_jc(int lk_jr_jc) {
        this.lk_jr_jc = lk_jr_jc;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Grid grid = (Grid) o;

        if (lk_jr_jc != grid.lk_jr_jc) return false;
        if (lk_jr_zd != grid.lk_jr_zd) return false;
        if (lk_sf_jc != grid.lk_sf_jc) return false;
        if (lk_sf_zd != grid.lk_sf_zd) return false;
        if (td_jr_jc != grid.td_jr_jc) return false;
        if (td_jr_zd != grid.td_jr_zd) return false;
        if (td_sf_jc != grid.td_sf_jc) return false;
        if (td_sf_zd != grid.td_sf_zd) return false;
        if (code != grid.code) return false;
        if (!id.equals(grid.id)) return false;
        if (!name.equals(grid.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + td_sf_zd;
        result = 31 * result + td_sf_jc;
        result = 31 * result + td_jr_zd;
        result = 31 * result + td_jr_jc;
        result = 31 * result + lk_sf_zd;
        result = 31 * result + lk_sf_jc;
        result = 31 * result + lk_jr_zd;
        result = 31 * result + lk_jr_jc;
        result = 31 * result + code;
        return result;
    }
}
