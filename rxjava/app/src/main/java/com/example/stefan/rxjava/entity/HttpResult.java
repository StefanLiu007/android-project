package com.example.stefan.rxjava.entity;

/**
 * Created by Stefan on 2016/7/21.
 */
public class HttpResult<T> {
    private int count;
    private int start;
    private int total;
    private String title;
    private T subject;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public T getSubject() {
        return subject;
    }

    public void setSubject(T subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("title=" + title + " count=" + count + " start=" + start);
        if (null != subject) {
            sb.append(" subjects:" + subject.toString());
        }
        return sb.toString();
    }
}
