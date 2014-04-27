package org.springframework.samples.portfolio.model;

/**
 * Created by star on 4/27/14.
 */
public class Grid2 {
    private String id;
    private String name;
    private String shortname;
    private int code;
    private int td_jc;
    private int td_zd;
    private int lk_jc;
    private int lk_zd;
    private int hc_zd;
    private int hc_jc;
    private int xywd_jc;
    private int xywd_zd;
    private int ly_jc;
    private int ly_zd;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Grid2 grid2 = (Grid2) o;

        if (code != grid2.code) return false;
        if (hc_jc != grid2.hc_jc) return false;
        if (hc_zd != grid2.hc_zd) return false;
        if (lk_jc != grid2.lk_jc) return false;
        if (lk_zd != grid2.lk_zd) return false;
        if (ly_jc != grid2.ly_jc) return false;
        if (ly_zd != grid2.ly_zd) return false;
        if (td_jc != grid2.td_jc) return false;
        if (td_zd != grid2.td_zd) return false;
        if (xywd_jc != grid2.xywd_jc) return false;
        if (xywd_zd != grid2.xywd_zd) return false;
        if (!id.equals(grid2.id)) return false;
        if (!name.equals(grid2.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + code;
        result = 31 * result + td_jc;
        result = 31 * result + td_zd;
        result = 31 * result + lk_jc;
        result = 31 * result + lk_zd;
        result = 31 * result + hc_zd;
        result = 31 * result + hc_jc;
        result = 31 * result + xywd_jc;
        result = 31 * result + xywd_zd;
        result = 31 * result + ly_jc;
        result = 31 * result + ly_zd;
        return result;
    }

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

    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getTd_jc() {
        return td_jc;
    }

    public void setTd_jc(int td_jc) {
        this.td_jc = td_jc;
    }

    public int getTd_zd() {
        return td_zd;
    }

    public void setTd_zd(int td_zd) {
        this.td_zd = td_zd;
    }

    public int getLk_jc() {
        return lk_jc;
    }

    public void setLk_jc(int lk_jc) {
        this.lk_jc = lk_jc;
    }

    public int getLk_zd() {
        return lk_zd;
    }

    public void setLk_zd(int lk_zd) {
        this.lk_zd = lk_zd;
    }

    public int getHc_zd() {
        return hc_zd;
    }

    public void setHc_zd(int hc_zd) {
        this.hc_zd = hc_zd;
    }

    public int getHc_jc() {
        return hc_jc;
    }

    public void setHc_jc(int hc_jc) {
        this.hc_jc = hc_jc;
    }

    public int getXywd_jc() {
        return xywd_jc;
    }

    public void setXywd_jc(int xywd_jc) {
        this.xywd_jc = xywd_jc;
    }

    public int getXywd_zd() {
        return xywd_zd;
    }

    public void setXywd_zd(int xywd_zd) {
        this.xywd_zd = xywd_zd;
    }

    public int getLy_jc() {
        return ly_jc;
    }

    public void setLy_jc(int ly_jc) {
        this.ly_jc = ly_jc;
    }

    public int getLy_zd() {
        return ly_zd;
    }

    public void setLy_zd(int ly_zd) {
        this.ly_zd = ly_zd;
    }
}
