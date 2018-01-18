package mike.dpwrapp;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author mike_yi
 * @description ip实体
 */
@Entity
@Table(name = "ip_info")
public class IpInfo {
	private int id;
	private String address;
	private int port;
	private String username;
	private String password;
	private int statu;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getStatu() {
		return statu;
	}

	public void setStatu(int statu) {
		this.statu = statu;
	}

	@Override
	public String toString() {
		return "IpInfo [id=" + id + ", address=" + address + ", port=" + port + ", username=" + username + ", password="
				+ password + ", statu=" + statu + "]";
	}
}
