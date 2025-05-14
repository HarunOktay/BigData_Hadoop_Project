package com.mapreduce.jobs.ebikeMechBikeComp;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.apache.hadoop.io.Writable;


public class EbikeMechBikeCount implements Writable{

    private Integer eBike = Integer.valueOf(0);
	private Integer mechBike = Integer.valueOf(0);
    private Integer equalBikeCount = Integer.valueOf(0);

	public EbikeMechBikeCount() {}

	public EbikeMechBikeCount(Integer eBike, Integer mechBike) {
		this.eBike = eBike;
		this.mechBike = mechBike;
	}

	public EbikeMechBikeCount(Integer eBike, Integer mechBike,Integer equal) {
		this.eBike = eBike;
		this.mechBike = mechBike;
        this.equalBikeCount = equal;
	}

	public Integer getEbike() {
		return eBike;
	}

	public void setEbike(Integer eBike) {
		this.eBike = eBike;
	}

	public Integer getMechBike() {
		return mechBike;
	}

	public void setMechBike(Integer mechBike) {
		this.mechBike = mechBike;
	}

    public Integer getEqualBike() {
		return equalBikeCount;
	}

	public void setEqualBike(Integer equalBike) {
		this.equalBikeCount = equalBike;
	}

	public void readFields(DataInput in) throws IOException {
		eBike = in.readInt();
		mechBike = in.readInt();
        equalBikeCount = in.readInt();
	}

	public void write(DataOutput out) throws IOException {
		out.writeInt(eBike);
		out.writeInt(mechBike);
        out.writeInt(equalBikeCount);
	}

	public String toString() {
		return eBike + "\t" + mechBike + "\t" + equalBikeCount;
	}

}
