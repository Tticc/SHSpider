package wenc.shspider.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.stereotype.Component;


@Component
@Entity
@Table(name = "urlset_all")
public class AllUrlEntity {
	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;

	@Column(name = "url", nullable = false, unique = true)
	private String url;

	@Column(name = "pagetitle", nullable = true)
	private String pageTitle;

	@Column(name = "charset", nullable = false)
	private String charset = " ";

	@Column(name = "isprocess", nullable = false)
	private boolean isprocess = false;

	@Column(name = "tag_ids", nullable = true)
	private String tagIDs;

	@Column(name = "createtime", nullable = false)
	private Date createTime = new Date();

	@Column(name = "modifytime", nullable = false)
	private Date modifyTime = new Date();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPageTitle() {
		return pageTitle;
	}

	public void setPageTitle(String pageTitle) {
		this.pageTitle = pageTitle;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public boolean isIsprocess() {
		return isprocess;
	}

	public void setIsprocess(boolean isprocess) {
		this.isprocess = isprocess;
	}

	public String getTagIDs() {
		return tagIDs;
	}

	public void setTagIDs(String tagIDs) {
		this.tagIDs = tagIDs;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
}
