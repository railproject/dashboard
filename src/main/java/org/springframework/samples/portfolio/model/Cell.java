package org.springframework.samples.portfolio.model;

/**
 * Created by star on 4/14/14.
 */
public class Cell {

    private String date;

    private String dayOfWeek;

    private int zysx;

    private int td;

    private int td_sj;

    private int lk;

    private int lk_sj;

    private int sg;

    private int hc;

    private int hc_sj;

    private int qt;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public int getZysx() {
        return zysx;
    }

    public void setZysx(int zysx) {
        this.zysx = zysx;
    }

    public int getTd() {
        return td;
    }

    public void setTd(int td) {
        this.td = td;
    }

    public int getLk() {
        return lk;
    }

    public void setLk(int lk) {
        this.lk = lk;
    }

    public int getLk_sj() {
        return lk_sj;
    }

    public void setLk_sj(int lk_sj) {
        this.lk_sj = lk_sj;
    }

    public int getSg() {
        return sg;
    }

    public void setSg(int sg) {
        this.sg = sg;
    }

    public int getHc() {
        return hc;
    }

    public void setHc(int hc) {
        this.hc = hc;
    }

    public int getQt() {
        return qt;
    }

    public void setQt(int qt) {
        this.qt = qt;
    }

    public int getTd_sj() {
        return td_sj;
    }

    public void setTd_sj(int td_sj) {
        this.td_sj = td_sj;
    }

    public int getHc_sj() {
        return hc_sj;
    }

    public void setHc_sj(int hc_sj) {
        this.hc_sj = hc_sj;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Cell)) {
            return false;
        }
        Cell cell = (Cell)obj;
        if (this.td == cell.getTd() && this.zysx == cell.getZysx() && this.sg == cell.getSg()
                && this.lk == cell.getLk() && this.qt == cell.getQt() && this.getHc() == cell.getHc()
                && this.date.equals(cell.getDate())/* && this.dayOfWeek.equals(cell.getDayOfWeek())*/
                && this.td_sj == cell.td_sj
                && this.hc_sj == cell.hc_sj) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.date.hashCode()/* + this.dayOfWeek.hashCode()*/ + this.td + this.sg + this.zysx + this.hc + this.qt + this.lk + this.td_sj + this.hc_sj;
    }
}
