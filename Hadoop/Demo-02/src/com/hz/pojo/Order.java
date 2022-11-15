package com.hz.pojo;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.math.BigDecimal;

public class Order implements WritableComparable<Object> {
    private int orderid;
    private int productid;
    private BigDecimal price;

    public int getOrderid() {
        return orderid;
    }

    public void setOrderid(int orderid) {
        this.orderid = orderid;
    }

    public int getProductid() {
        return productid;
    }

    public void setProductid(int productid) {
        this.productid = productid;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public int compareTo(Object o) {
        return 0; //不排序
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(orderid);
        dataOutput.writeInt(productid);
        dataOutput.writeChars(price.toString());
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        orderid = dataInput.readInt();
        productid = dataInput.readInt();
        price = new BigDecimal(dataInput.readLine());
    }

    public void set(int productid, String price) {
        this.productid = productid;
        this.price = new BigDecimal( price);
    }
}
