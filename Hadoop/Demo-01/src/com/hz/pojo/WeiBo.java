package com.hz.pojo;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class WeiBo implements WritableComparable<Object> {
    private int friends;
    private int followers;
    private int statuses;

    public int getFriends() {
        return friends;
    }

    public void setFriends(int friends) {
        this.friends = friends;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public int getStatuses() {
        return statuses;
    }

    public void setStatuses(int statuses) {
        this.statuses = statuses;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }

    // 实现WritableComparable的readFields()方法，以便该数据能被序列化后完成网络传输或文件输入
    @Override
    public void readFields(DataInput in) throws IOException {
        friends  = in.readInt();
        followers = in.readInt();
        statuses = in.readInt();
    }

    // 实现WritableComparable的write()方法，以便该数据能被序列化后完成网络传输或文件输出
    @Override
    public void write(DataOutput out) throws IOException {
        out.writeInt(friends);
        out.writeInt(followers);
        out.writeInt(statuses);
    }

    public void set(int friends, int followers, int statuses) {
        this.followers = followers;
        this.friends = friends;
        this.statuses = statuses;
    }
}
